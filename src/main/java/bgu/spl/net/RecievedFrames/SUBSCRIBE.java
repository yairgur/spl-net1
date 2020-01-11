package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.RECEIPT;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;

public class SUBSCRIBE implements Frame{
    private String destination; // genre
    private String id; // to unsubscribe later
    private String receipt;
    private ConnectionsImpl connectionImpl;
    private Brain brain;


    public SUBSCRIBE(String destination, String id, String receipt)
    {
        this.destination = destination;
        this.id = id;
        this.receipt = receipt;
        this.connectionImpl = ConnectionsImpl.getInstance();
        brain = Brain.getInstance();
    }

    public void run(int connectionId)
    {
        User user = Brain.getInstance().getsUser(connectionId);
        System.out.println(user.getIsLoggedIn() + " " + user.getUserName() + " " + user.getPasscode());
        brain.addToGenreMap(destination, user, id);
        RECEIPT receiptFrame = new RECEIPT(receipt, "subscribe"); // appropriate message to the client
        connectionImpl.send(connectionId, receiptFrame);
    }
}
