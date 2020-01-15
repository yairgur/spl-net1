package bgu.spl.net.srv;
import bgu.spl.net.Pair;
import bgu.spl.net.SentFrames.SentFrame;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionsImpl implements Connections<SentFrame> {

    Map<Integer, ConnectionHandler> clientsConnectionsMap = Brain.getInstance().getConnectionsMap();
    private Brain brain;


    private static class SingletonHolder {
        private static ConnectionsImpl instance = new ConnectionsImpl();
    }

    public static ConnectionsImpl getInstance() {
        return ConnectionsImpl.SingletonHolder.instance;
    }


    public boolean send(int connectionId, SentFrame msg){
        brain = Brain.getInstance();
        ConnectionHandler currentHandler = brain.getConnectionHandler(connectionId);
        synchronized (currentHandler){
            if(msg != null)
            {
             currentHandler.send(msg.toString());
            }
            else {
                return false;
            }
        }
        return true;
    }

    public void send(String channel, SentFrame msg){
        Brain brain = Brain.getInstance();
        ConcurrentLinkedQueue<Pair<User, String>> subscribedUsersList = brain.getUserByGenreQueue(channel);
        for(Pair<User, String> pair: subscribedUsersList)
        {
            ConnectionHandler currentHandler1 = pair.getFirst().getConnectionHandler();
            synchronized (currentHandler1){
                currentHandler1.send(msg);
            }
        }

    }

    public void disconnect(int connectionId){
        Brain brain = Brain.getInstance();
        ConnectionHandler currentHandler = brain.getConnectionHandler(connectionId);
        synchronized (currentHandler){
            brain.getConnectionsMap().remove(connectionId); // removes connectionId from the map in brain
            try {
                brain.getConnectionsMap().get(connectionId).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToConnectionsMap(int connectionId, ConnectionHandler connectionHandler)
    {
        clientsConnectionsMap.put(connectionId, connectionHandler);
    }

}
