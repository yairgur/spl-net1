package bgu.spl.net.srv;

import bgu.spl.net.MessageIdCounter;
import bgu.spl.net.Pair;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Brain {

    private ConcurrentMap<String, ConcurrentLinkedQueue<Pair<User, String>>> genresMap; // second in Pair is subscription id
    private ConcurrentMap<String, User> userNamesMap; // FIXME should be handled and deleted
    private ConcurrentMap<Integer, ConnectionHandler> connectionsMap;
    private ConcurrentMap<Integer, User> userConnectionsIdMap;
    private LinkedList<User> loggedInUsers; // daniel add
    private MessageIdCounter counter;


    private static class SingletonHolder {
        private static Brain instance = new Brain();
    }

    public static Brain getInstance() {
        return SingletonHolder.instance;
    }

    public Brain()
    {
        genresMap = new ConcurrentHashMap<>();
        userNamesMap = new ConcurrentHashMap<>();
        connectionsMap = new ConcurrentHashMap<>();
        userConnectionsIdMap = new ConcurrentHashMap<>();
        counter = new MessageIdCounter();
        loggedInUsers = new LinkedList<>();
    }

    public Map<String, ConcurrentLinkedQueue<Pair<User, String>>> getGenresMap()
    {
        return genresMap;
    }

    public ConcurrentLinkedQueue<Pair<User, String>> getUserByGenreQueue(String genre)
    {
        return genresMap.get(genre);
    }

    public ConcurrentMap<String, User> getUserNamesMap()
    {
        return userNamesMap;
    }

    public void addHandler(int connectionId, ConnectionHandler connectionHandler){
        connectionsMap.put(connectionId, connectionHandler);
    }

    public ConnectionHandler getConnectionHandler(int id)
    {
        if(connectionsMap.get(id)!=null)
            return connectionsMap.get(id);
        return null;
    }

    public void removeConnectionHandler(int connectionId){
        connectionsMap.remove(connectionId);
    }

    public Map<Integer, ConnectionHandler> getConnectionsMap()
    {
        return connectionsMap;
    }

    public User getsUser(int connectionId)
    {

        return userConnectionsIdMap.get(connectionId);
    }

    public void addToGenreMap(String genre, User user, String id)
    {
        if(genresMap.get(genre) == null)
        {
            ConcurrentLinkedQueue<Pair<User, String>> ls = new ConcurrentLinkedQueue<>();
            ls.add(new Pair(user, id));
            genresMap.put(genre, ls);
        }
        else
        {
            genresMap.get(genre).add(new Pair(user, id));
        }
    }

    public void unsubscribeFromGenreMap(User user, String id) //TODO we can improve that with saving list of genres for every user
    {
        for(String genre:genresMap.keySet()) {
            for (Pair<User, String> pair : genresMap.get(genre)) {
                if (pair.getFirst() == user && pair.getSecond().equals(id)) {
                    genresMap.remove(pair);
                }
            }
        }
    }

    public MessageIdCounter getCounter() // message id atomic counter for all program
    {
        return counter;
    }

    public User findUserByConnectionId(int connectionId)
    {
        return userConnectionsIdMap.get(connectionId);
    }

    public void addToUserConnectionsMap(int connectionId, User user)
    {
        userConnectionsIdMap.put(connectionId, user);
    }

    public void setToUserConnectionsMap(int newConnectionId, User user)
    {
        boolean isExist = false;
        for(int id:userConnectionsIdMap.keySet()){
            if (userConnectionsIdMap.get(id).equals(user)) {
                userConnectionsIdMap.remove(id);
                isExist = true;
                break;
            }
        }
        if(isExist){
            System.out.println("we found the previous user and updated the connection id");
            userConnectionsIdMap.put(newConnectionId, user);
        }
    }

    public void addLoggedInUser(User user){
        loggedInUsers.add(user);
    }

    public void RemoveFromLoggedInList(User user){
        for(User loggedInUser:loggedInUsers){
            if(loggedInUser.equals(user)){
                loggedInUsers.remove(loggedInUser);
                break;
            }
        }
    }
}
