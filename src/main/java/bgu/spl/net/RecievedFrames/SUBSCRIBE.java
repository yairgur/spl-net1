package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.RECEIPT;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;

public class SUBSCRIBE implements Frame{
    private String destination; // genre
    private String id;
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
        System.out.println(user.getIsLoggedIn() + " " + user.getUserName() + " " + user.getPassword());
        brain.addToGenreMap(destination, user, id);
        RECEIPT receiptFrame = new RECEIPT(receipt, "Joined club " + destination); // appropriate message to the client
        connectionImpl.send(connectionId, receiptFrame);
    }
}
