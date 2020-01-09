//package bgu.spl.net.impl.echo;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//
//public class EchoClient {
//
//    public static void main(String[] args) throws IOException {
//
//        if (args.length == 0) {
//            args = new String[]{"localhost", "hello"};
//        }
//
//        if (args.length < 2) {
//            System.out.println("you must supply two arguments: host, message");
//            System.exit(1);
//        }
//
//        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
//        try (Socket sock = new Socket(args[0], 7777);
//                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
//
//            System.out.println("sending message to server");
//            out.write(args[1]);
//            out.newLine();
//            out.flush();
//
//            System.out.println("awaiting response");
//            String line = in.readLine();
//            System.out.println("message from server: " + line);
//        }
//    }
//}



package bgu.spl.net.impl.echo;

import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.api.StompMessagingProtcolImpl;
import bgu.spl.net.api.MessageEncoderDecoderImpl;

import java.io.*;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        MessageEncoderDecoderImpl endc = new MessageEncoderDecoderImpl();
        if (args.length == 0) {
            args = new String[]{"localhost", "hello"};
        }

        if (args.length < 2) {
            System.out.println("you must supply two arguments: host, message");
            System.exit(1);
        }

        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
        try (Socket sock = new Socket(args[0], 0);
             BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
             BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream());) {
            System.out.println("socketIsNull = " +(sock==null));
            System.out.println("sending message to server");

            out.write(endc.encode("CONNECT\n" +
                    "accept-version:1.2\n" +
                    "host:stomp.cs.bgu.ac.il\n" +
                    "login:bob\n" +
                    "passcode:alice\n" +
                    "\n"+
                    "^@"));
            out.flush();

            int read;
            while ((read = in.read()) >= 0) {
                Frame msg = endc.decodeNextByte((byte) read);
                if (msg != null) {
                    System.out.println(msg);
                }
            }

            System.out.println("awaiting response");


        }
    }
}