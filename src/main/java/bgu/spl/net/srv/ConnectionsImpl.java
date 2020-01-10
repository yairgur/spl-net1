package bgu.spl.net.srv;

import bgu.spl.net.Pair;
import bgu.spl.net.RecievedFrames.Frame;
import bgu.spl.net.SentFrames.SentFrame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionsImpl implements Connections<SentFrame> {

    Map<Integer, ConnectionHandler> clientsConnectionsMap = Brain.getInstance().getConnectionsMap();


    private static class SingletonHolder {
        private static ConnectionsImpl instance = new ConnectionsImpl();
    }

    public static ConnectionsImpl getInstance() {
        return ConnectionsImpl.SingletonHolder.instance;
    }


    public boolean send(int connectionId, SentFrame msg){
        ConnectionHandler currentHandler = clientsConnectionsMap.get(connectionId);
        if(currentHandler == null)
        {
            System.out.println("currentHandler is null!!!");
        }
        if(msg != null)
        {
            currentHandler.send(msg.toString());
        }
        else {
            System.out.println("we will return false now"); //FIXME
            return false;
        }
        return true;
    }

    public void send(String channel, SentFrame msg){
        Brain brain = Brain.getInstance();
        ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(channel);
        for(Pair<User, String> pair: subscribedUsersList)
        {
            pair.getFirst().getConnectionHandler().send(msg);
        }
    }

    public void disconnect(int connectionId){
        Brain.getInstance().getConnectionsMap().remove(connectionId); // removes connectionId from the map in brain
    }

    public void addToConnectionsMap(int connectionId, ConnectionHandler connectionHandler)
    {
        clientsConnectionsMap.put(connectionId, connectionHandler);
    }

}
