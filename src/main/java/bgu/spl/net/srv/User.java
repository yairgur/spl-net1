package bgu.spl.net.srv;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private ConnectionHandler connectionHandler;
    private String username;
    private String password;
    private boolean isLoggedIn;
    private int connectionId;
    private Map<String, ConcurrentLinkedQueue<String>> inventory;

    public User(ConnectionHandler connectionHandler, String username, String password, int connectionId)
    {
        isLoggedIn = true;
        this.connectionHandler = connectionHandler;
        this.username = username;
        this. password = password;
        this.connectionId = connectionId;
        this.inventory = new ConcurrentHashMap<>();
    }

    public String getUserName()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ConnectionHandler getConnectionHandler()
    {
        return connectionHandler;
    }

    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public int getConnectionId()
    {
        return connectionId;
    }

    public Map<String, ConcurrentLinkedQueue<String>> getInventory(){
        return inventory;
    }

    public void disconnect()
    {
        isLoggedIn = false;
    }


    //set

    public void setUserHandler(ConnectionHandler connectionHandler, int id){
        this.connectionHandler = connectionHandler;
        this.connectionId = id;
    }

//    public String printInventory(){
//        String toPrint = "";
//        for(String genre:inventory.keySet())
//        {
//            for (String book:inventory.get(genre)){
//                toPrint += book + ",";
//            }
//        }
//        return toPrint.substring(0, toPrint.length()-1);
//    }




    //public void addToInventory(String genre, String book)
//    {
//        inventory.get(genre).add(book);
//    }
}
