package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
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
        else if(args[1].equals("reactor"))
        {
            Brain brain = new Brain();

            Server.reactor(
                    Integer.parseInt("3"),
                    Integer.parseInt(args[0]), //port
                    () ->  new StompMessagingProtcolImpl(brain), //protocol factory
                    MessageEncoderDecoderImpl::new //message encoder decoder factory
            ).serve();
        }

    }
}

