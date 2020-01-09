package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.StompMessagingProtcolImpl;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Server;

public class StompServer {

    public static void main(String[] args) {
        if(args[1].equals("tpc"))
        {
            Brain brain = new Brain();
            Server.threadPerClient(
                    Integer.parseInt((args[0])), //port
                    () -> new StompMessagingProtcolImpl(brain), //protocol factory
                    MessageEncoderDecoderImpl::new //message encoder decoder factory
            ).serve();
        }
//        else if(args[1].equals("reactor"))
//        {
//            DataBase db = new DataBase();
//
//            Server.reactor(
//                    Integer.parseInt(args[1]),
//                    Integer.parseInt(args[0]), //port
//                    () ->  new BGSProtocol(db), //protocol factory
//                    BGSEncoderDecoder::new //message encoder decoder factory
//            ).serve();
//        }

    }
}

