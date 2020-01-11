package bgu.spl.net.RecievedFrames;

import bgu.spl.net.Pair;
import bgu.spl.net.SentFrames.MESSAGE;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SEND implements Frame {
    private String genre;
    private String body;
    private ConnectionsImpl connectionImpl;


    public SEND(String genre, String body) {
        this.genre = genre;
        this.body = body;
        this.connectionImpl = ConnectionsImpl.getInstance();

    }

    public void run(int connectionId) {
        Brain brain = Brain.getInstance();
        ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(genre);
        for (Pair<User, String> pair : subscribedUsersList) {
            MESSAGE message = new MESSAGE(pair.getSecond(), ("" + Brain.getInstance().getCounter().getValue()), genre, "" + body);
            Brain.getInstance().getCounter().increase();
            connectionImpl.send(connectionId, message); // TODO - check if we need to use send(channel, T message)
        }
    }
}
//        if(body.contains("added"))
//        {
//            String book = body.substring(body.lastIndexOf(' '));
//            user.addToInventory(genre, book);
//            for(Pair<User, String> pair:subscribedUsersList){
//                if(pair.getFirst().equals(user))
//                {
//                    String subscriptionId = pair.getSecond();
//                    MESSAGE message = new MESSAGE(subscriptionId, ("" + Brain.getInstance().getCounter().getValue()), genre, "" + pair.getFirst().getUserName() + " has added the book " + book);
//                    Brain.getInstance().getCounter().increase();
//                    connectionImpl.send(connectionId, message);
//                    break;
//                }
//            }
//        }
//        else if(body.contains("borrow"))
//        {
//
//        }
//        else if(body.contains("Returning"))
//        {
//
//        }
//        else if(body.contains("book status"))
//        {
//            ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(genre);
//            for(Pair<User, String> pair:subscribedUsersList){
//                String body = pair.getFirst().printInventory();
//                MESSAGE message = new MESSAGE(pair.getSecond(), ("" + Brain.getInstance().getCounter().getValue()), genre, "" + pair.getFirst().getUserName() + ":" + body);
//                Brain.getInstance().getCounter().increase();
//                connectionImpl.send(connectionId, message);
//            }
//        }
