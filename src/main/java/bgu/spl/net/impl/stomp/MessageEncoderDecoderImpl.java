package bgu.spl.net.impl.stomp;

import bgu.spl.net.RecievedFrames.*;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.srv.Brain;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    public MessageEncoderDecoderImpl()
    {

    }

    public Frame createFrame(String message)
    {
        Brain brain = Brain.getInstance();
        String command;
        if(message.charAt(0) == '\n') {
            message = message.substring(message.indexOf('\n')+1);
        }
        if(message.charAt(message.length()-1) == '\n') {
            message = message.substring(0,message.length()-1);
        }
        String[] lines = message.split("\n"); //FIXME
        command = lines[0];
        Map<String, String> content = new HashMap<>();
        if(command.equals("SEND"))
        {
            for(int i=1; i<lines.length-1; i++)
            {
                if(i+1 < lines.length && lines[i].equals(""))
                {
                    content.put("Body", lines[i+1]);
                }
                else {
                    content.put(lines[i].substring(0, lines[i].indexOf(':')), lines[i].substring(lines[i].indexOf(':')+1));
                }
            }
        }
        else
        {
            for(int i=1; i<lines.length; i++)
            {
                content.put(lines[i].substring(0, lines[i].indexOf(':')), lines[i].substring(lines[i].indexOf(':')+1));
            }
        }
        switch(command)
        {
            case "CONNECT":
                Frame connect = new CONNECT("CONNECT", content.get("accept-version"), content.get("host"), content.get("login"), content.get("passcode"), content.get("receipt-id"));
                return connect;
            case "SUBSCRIBE":
                SUBSCRIBE subscribe = new SUBSCRIBE(content.get("destination"), content.get("id"), content.get("receipt"));
                return subscribe;
            case "SEND":
                SEND send = new SEND(content.get("destination"), content.get("Body"));
                return send;
            case "DISCONNECT":
                DISCONNECT disconnect = new DISCONNECT(content.get("receipt"));
                //DISCONNECT disconnect = new DISCONNECT(lines[1].substring(lines[1].indexOf(':')+1));
                return disconnect;
            case "UNSUBSCRIBE": // private case for UNSUBSCRIBE
                UNSUBSCRIBE unsubscribe = new UNSUBSCRIBE(content.get("id"), Integer.parseInt(content.get("receipt")));
                //UNSUBSCRIBE unsubscribe = new UNSUBSCRIBE(lines[1].substring(lines[1].indexOf(':')+1));
                return unsubscribe;
        }
        //return Frame;
        return null;
    }

    @Override
    public Frame decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\u0000') { // Fixme changed to \u0000 and not \n
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
            return createFrame(result);
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Object message) {

        byte[] bytes=(message + "\u0000").getBytes();
//        for (byte aByte : bytes) {
//            System.out.println("aByte = " + (int)aByte);
//        }
        return bytes; //uses utf8 by default //FIXME check if its legal writing
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }
}

