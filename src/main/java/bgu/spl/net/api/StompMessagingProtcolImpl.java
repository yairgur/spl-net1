package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.*;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Connections;

import java.util.LinkedList;

public class StompMessagingProtcolImpl implements StompMessagingProtocol {
    private int connectionId;
    private Connections<String> connections;


    public void start(int connectionId, Connections<String> connections)
    {
        this.connectionId = connectionId;
        this.connections = connections;
    }

    public void process(Frame frame)
    {
        if(frame.getClass().equals("CONNECT"))
        {
            CONNECT connect = (CONNECT)frame;
            connect.run(connectionId);
        }
        else if(frame.getClass().equals("DISCONNECT"))
        {
            DISCONNECT disconnect = (DISCONNECT)frame;
            disconnect.run(connectionId);
        }
        else if(frame.getClass().equals(("SUBSCRIBE")))
        {
            SUBSCRIBE subscribe = (SUBSCRIBE)frame;
            subscribe.run(connectionId);
        }
        else if(frame.getClass().equals(("UNSUBSCRIBE")))
        {
            UNSUBSCRIBE unsubscribe = (UNSUBSCRIBE)frame;
            unsubscribe.run(connectionId);
        }
        else if(frame.getClass().equals(("SEND")))
        {
            SEND send = (SEND)frame;
            send.run(connectionId);
        }
    }

    public boolean shouldTerminate()
    {
        return true;
    }

}
