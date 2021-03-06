package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.RECEIPT;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;

public class UNSUBSCRIBE implements Frame {
    private boolean shouldTerminate;
    private String id;
    private int receiptId;
    private ConnectionsImpl connectionImpl;

    public UNSUBSCRIBE(String id, int receiptId)
    {
        this.shouldTerminate = false;
        this.id = id;
        this.receiptId = receiptId;
        this.connectionImpl = ConnectionsImpl.getInstance();
    }

    public void run(int connectionId)
    {
        User user = Brain.getInstance().getsUser(connectionId);
        Brain.getInstance().unsubscribeFromGenreMap(user);
        RECEIPT receiptFrame = new RECEIPT("" + receiptId, "unsubscribe"); // appropriate message to the client
        //retList.addLast(message);
        connectionImpl.send(connectionId, receiptFrame);
        //FIXME receiptFrame should be initialize somehow
    }

    public boolean getTerminate()
    {
        return shouldTerminate;
    }
}
