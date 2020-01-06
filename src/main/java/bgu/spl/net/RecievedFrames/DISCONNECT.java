package bgu.spl.net.RecievedFrames;

import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionHandler;

import java.util.Map;

public class DISCONNECT implements Frame{
    private int recipt;

    public DISCONNECT(int recipt)
    {
        this.recipt = recipt;
    }

    public void run(int connectionId)
    {
        if(Brain.getInstance().getConnectionsMap().get(connectionId) != null) // The user exists
        {
            Brain.getInstance().getConnectionsMap().remove(connectionId); // removes from map
            //TODO should send frame DISCONNECTED

        }
    }
}
