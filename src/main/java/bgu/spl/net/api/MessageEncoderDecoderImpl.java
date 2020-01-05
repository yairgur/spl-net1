package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.CONNECT;
import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.srv.Brain;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    public Frame createFrame(String message)
    {
        Brain brain = Brain.getInstance();
        String[] lines = message.split("\n");
        String command = lines[0];
        switch(command)
        {
            case "CONNECT":
                String acceptVersion = lines[1].substring(lines[1].indexOf(':'));
                String host = lines[2].substring(lines[2].indexOf(':'));
                String userName = lines[3].substring(lines[3].indexOf(':'));
                String password = lines[4].substring(lines[4].indexOf(':'));


                Frame handler = new CONNECT(acceptVersion, host, userName, password);
                return handler;
                break;
            case "SUBSCRIBE":
                break;
            case "SEND":
                break;
            case "DISCONNECT":
                break;
        }
        return Frame;
    }

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\0') { // Fixme changed to \0 and not \n
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
            createFrame(result);
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(String message) {
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

