package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.CONNECT;
import bgu.spl.net.RecievedFrames.DISCONNECT;
import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Connections;

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
            //disconnect.run(/*connectionId*/);
        }
        //return "";
    }

    public boolean shouldTerminate()
    {
        return true;
    }

}
