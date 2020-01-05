package bgu.spl.net.api;

import bgu.spl.net.RecievedFrames.CONNECT;
import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.Connections;

public class StompMessagingProtcolImpl implements StompMessagingProtocol {
    private int connectionId;

    public void start(int connectionId, Connections<String> connections)
    {
        this.connectionId = connectionId;
    }

    public void process(String message) // Fixme - we changed from void to String
    {

        //return "";
    }

    public boolean shouldTerminate()
    {
        return true;
    }

}
