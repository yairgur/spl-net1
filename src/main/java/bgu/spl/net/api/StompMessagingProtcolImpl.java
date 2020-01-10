package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.*;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Connections;

import java.util.LinkedList;

public class StompMessagingProtcolImpl implements StompMessagingProtocol {
    private int connectionId;
    private Connections<SentFrame> connections;
    private Brain brain;
    private boolean shouldTerminate;

    public StompMessagingProtcolImpl(Brain brain) // yair added, to delete FIXME
    {
        brain = brain;
        shouldTerminate = false;
    }

    public void start(int connectionId, Connections<SentFrame> connections)
    {
        this.connectionId = connectionId;
        this.connections = connections;
        //// TODO use start ----- add lines here, and in stompServer

    }

    public void process(Frame frame)
    {
        if(frame.getClass().equals(CONNECT.class))
        {
            CONNECT connect = (CONNECT)frame;
            System.out.println(connect.getAcceptVersion() + " " + connect.getHost() + " " + connect.getPassword() + " " + connect.getReciptId() + " " + connect.getUserName());
            connect.run(connectionId);
        }
        else if(frame.getClass().equals(DISCONNECT.class))
        {
            DISCONNECT disconnect = (DISCONNECT)frame;
            disconnect.run(connectionId);
        }
        else if(frame.getClass().equals((SUBSCRIBE.class)))
        {
            SUBSCRIBE subscribe = (SUBSCRIBE)frame;
            subscribe.run(connectionId);
        }
        else if(frame.getClass().equals((UNSUBSCRIBE.class)))
        {
            UNSUBSCRIBE unsubscribe = (UNSUBSCRIBE)frame;
            unsubscribe.run(connectionId);
        }
        else if(frame.getClass().equals((SEND.class)))
        {
            SEND send = (SEND)frame;
            send.run(connectionId);
        }
    }

    public void terminate(){
        shouldTerminate = true;
    }

    public boolean shouldTerminate()
    {
        return shouldTerminate;
    }
}
