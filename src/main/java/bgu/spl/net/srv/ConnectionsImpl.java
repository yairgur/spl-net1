package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionsImpl implements Connections<String> { // Fixme Todo

    Map<Integer, ConnectionHandler> clientsConnectionsMap = new HashMap<>();

    public boolean send(int connectionId, String msg){
        ConnectionHandler currentHandler = clientsConnectionsMap.get(connectionId);
        if(msg != null)
        {
            currentHandler.send(msg);
        }
        else {
            return false;
        }
        return true; // Fixme
    }

    public void send(String channel, String msg){
        Brain brain = Brain.getInstance();
        ConcurrentLinkedQueue<User> subscribedUsersList = brain.getConnectionHandlersList(channel);
        for(User user: subscribedUsersList)
        {
            user.getConnectionHandler().send(msg);
        }
    }

    public void disconnect(int connectionId){

    }

}
