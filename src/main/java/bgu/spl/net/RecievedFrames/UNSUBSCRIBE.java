package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.RECEIPT;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

public class UNSUBSCRIBE implements Frame {

    private String id;
    private ConnectionsImpl connectionImpl;

    public UNSUBSCRIBE(String id)
    {
        this.id = id;
        this.connectionImpl = ConnectionsImpl.getInstance();
    }

    public void run(int connectionId)
    {
        User user = Brain.getInstance().getsUser(connectionId);
        Brain.getInstance().unsubscribeFromGenreMap(user, id);
        //RECEIPT receiptFrame = new RECEIPT(receipt, "Exited club " + destination); // appropriate message to the client
        //connectionImpl.send(connectionId, receiptFrame);
    }
}
