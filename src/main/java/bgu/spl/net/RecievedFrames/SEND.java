package bgu.spl.net.RecievedFrames;

import bgu.spl.net.Pair;
import bgu.spl.net.SentFrames.MESSAGE;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;
import com.sun.xml.internal.ws.api.message.Message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SEND implements Frame{
    private String genre;
    private String body;
    private ConnectionsImpl connectionImpl;


    public SEND(String genre, String body){ // TODO we should understand who initialize MessageIdCounter Atomic Integer
        this.genre = genre;
        this.body = body;
        this.connectionImpl = ConnectionsImpl.getInstance();

    }

    public void run(int connectionId)
    {
        Brain brain = Brain.getInstance();
        if(body.contains("added"))
        {
            User user = Brain.getInstance().getsUser(connectionId);
            String book = body.substring(body.lastIndexOf(' '));
            user.addToInventory(genre, book);
            ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(genre);
            for(Pair<User, String> pair:subscribedUsersList){
                if(pair.getFirst().equals(user))
                {
                    String subscriptionId = pair.getSecond();
                    MESSAGE message = new MESSAGE(subscriptionId, ("" + Brain.getInstance().getCounter().getValue()), genre, "" + pair.getFirst().getUserName() + " has added the book " + book);
                    Brain.getInstance().getCounter().increase();
                    connectionImpl.send(connectionId, message);
                    break;
                }
            }
        }
        else if(body.contains("borrow"))
        {

        }
        else if(body.contains("Returning"))
        {

        }
        else if(body.contains("book status"))
        {
            ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(genre);
            for(Pair<User, String> pair:subscribedUsersList){
                String body = pair.getFirst().printInventory();
                MESSAGE message = new MESSAGE(pair.getSecond(), ("" + Brain.getInstance().getCounter().getValue()), genre, "" + pair.getFirst().getUserName() + ":" + body);
                Brain.getInstance().getCounter().increase();
                connectionImpl.send(connectionId, message);
            }
        }
    }

}
