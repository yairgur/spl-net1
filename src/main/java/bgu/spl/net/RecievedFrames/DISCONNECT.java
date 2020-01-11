package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.RECEIPT;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;

public class DISCONNECT implements Frame{
    private String receipt;
    private ConnectionsImpl connectionImpl;

    public DISCONNECT(String receipt)
    {
        this.receipt = receipt;
        this.connectionImpl = ConnectionsImpl.getInstance();
    }

    public void run(int connectionId)
    {
        if(Brain.getInstance().getConnectionsMap().get(connectionId) != null) //User exists
        {
            Brain brain = Brain.getInstance();
            User user = brain.getsUser(connectionId);
            user.disconnect(); // isLoggedin = false
            RECEIPT receiptFrame = new RECEIPT(receipt, "disconnect");
            brain.RemoveFromLoggedInList(user);
            connectionImpl.send(connectionId, receiptFrame);
            brain.getConnectionsMap().remove(connectionId); // removes from map
        }
    }
}
