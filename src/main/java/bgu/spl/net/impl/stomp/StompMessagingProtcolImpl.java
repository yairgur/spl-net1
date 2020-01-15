package bgu.spl.net.impl.stomp;

import bgu.spl.net.RecievedFrames.*;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Connections;

public class StompMessagingProtcolImpl implements StompMessagingProtocol {
    private int connectionId;
    private Connections<SentFrame> connections;
    private Brain brain;
    private boolean terminate;

    public StompMessagingProtcolImpl(Brain brain)
    {
        brain = brain;
        terminate = false;
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
        }
        else if(frame.getClass().equals(DISCONNECT.class))
        {
            DISCONNECT disconnect = (DISCONNECT)frame;
        }
        else if(frame.getClass().equals((SUBSCRIBE.class)))
        {
            SUBSCRIBE subscribe = (SUBSCRIBE)frame;
        }
        else if(frame.getClass().equals((UNSUBSCRIBE.class)))
        {
            UNSUBSCRIBE unsubscribe = (UNSUBSCRIBE)frame;
        }
        else if(frame.getClass().equals((SEND.class)))
        {
            SEND send = (SEND)frame;
        }
        frame.run(connectionId);
    }

    public boolean shouldTerminate()
    {
        return terminate;
    }
}
