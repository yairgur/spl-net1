package bgu.spl.net.srv;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Brain {

    private ConcurrentMap<String, ConcurrentLinkedQueue<User>> genresMap;
    private ConcurrentMap<String, User> userNamesMap;
    private ConcurrentMap<Integer, ConnectionHandler> connectionsMap;



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
    }

    public Map<String, ConcurrentLinkedQueue<User>> getGenresMap()
    {
        return genresMap;
    }

    public ConcurrentLinkedQueue<User> getConnectionHandlersList(String genre)
    {
        return genresMap.get(genre);
    }

    public ConcurrentMap<String, User> getUserNamesMap()
    {
        return userNamesMap;
    }

    public ConnectionHandler getConnectionHandler(int id)
    {
        if(connectionsMap.get(id)!=null)
            return connectionsMap.get(id);
        return null;
    }

}
