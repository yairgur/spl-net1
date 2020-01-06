package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.CONNECT;
import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.srv.Brain;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    public Frame createFrame(String message)
    {
        Brain brain = Brain.getInstance();
        String[] lines = message.split("\n"); //FIXME
        String command = lines[0];
        Map<String, String> content = new HashMap<>();
        for(int i=1; i<lines.length; i++)
        {
            content.put(lines[i].substring(0, lines[i].indexOf(':')), lines[i].substring(lines[i].indexOf(':')+1));
        }
        switch(command)
        {
            case "CONNECT":
                Frame connect = new CONNECT(content.get("accept-version"), content.get("host"), content.get("login"), content.get("password"), content.get("recipt-id"));
                return connect;
            case "SUBSCRIBE":
                break;
            case "SEND":
                break;
            case "DISCONNECT":
                break;
        }
        //return Frame;
        return null;
    }

    @Override
    public Frame decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\0') { // Fixme changed to \0 and not \n
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
            return createFrame(result);
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(Object message) {
        return (message + "\0").getBytes(); //uses utf8 by default
    } // Fixme changed to \0 and not \n

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

