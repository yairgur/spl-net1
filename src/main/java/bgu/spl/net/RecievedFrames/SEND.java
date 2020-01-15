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
    private boolean shouldTerminate;
    private String genre;
    private String body;
    private ConnectionsImpl connectionImpl;


    public SEND(String genre, String body) {
        this.shouldTerminate = false;
        this.genre = genre;
        this.body = body;
        this.connectionImpl = ConnectionsImpl.getInstance();

    }

    public void run(int connectionId) {
        Brain brain = Brain.getInstance();
        ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(genre);
        ConcurrentLinkedQueue<Pair<User, String>> queue = brain.getUserByGenreQueue(genre);
        boolean userInGenreExist = false;
        User user = brain.getsUser(connectionId);
        if(queue !=null){
            for(Pair<User, String> p:queue){
                if(p.getFirst() == user){
                    userInGenreExist = true;
                }
            }
            if(userInGenreExist) {
                for (Pair<User, String> pair : subscribedUsersList) {
                    MESSAGE message = new MESSAGE(pair.getSecond(), ("" + Brain.getInstance().getCounter().getValue()), genre, "" + body);
                    Brain.getInstance().getCounter().increase();
                    connectionId = brain.getConnectionIdByUser(pair.getFirst());
                    connectionImpl.send(connectionId, message); // TODO - check if we need to use send(channel, T message)

                }
            }
        }
    }

    public boolean getTerminate()
    {
        return shouldTerminate;
    }
}



