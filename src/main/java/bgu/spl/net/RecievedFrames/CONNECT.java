package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.CONNECTED;
import bgu.spl.net.SentFrames.ERROR;
import bgu.spl.net.SentFrames.SentFrame;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

import java.util.LinkedList;

public class CONNECT implements Frame{

    private String type;
    private String acceptVersion;
    private String host;
    private String userName;
    private String password;
    private ConnectionsImpl connectionImpl;
    private String receiptId; // this field is for the error to send reciptId of specific frame



    public CONNECT(String type, String acceptVersion, String host, String userName, String password, String reciptId){
        this.type = type;
        this.acceptVersion = acceptVersion;
        this.host = host;
        this.userName = userName;
        this.password = password;
        this.connectionImpl = ConnectionsImpl.getInstance();
        this.receiptId = reciptId;
    }

    public void run(int connectionId){
        //TODO add try catch for socket error
        Brain brain = Brain.getInstance();
        ConnectionHandler handler = brain.getConnectionHandler(connectionId);
        String errorMessage = "";
        if(!brain.getUserNamesMap().keySet().contains(userName))
        {
            User user = new User(handler, userName, password, connectionId);
            brain.getUserNamesMap().put(userName, user);
            CONNECTED connected = new CONNECTED(acceptVersion);
            System.out.println("connectionId is " + connectionId);
            this.connectionImpl.send(connectionId, connected);
        }
        else{
            User user = brain.getUserNamesMap().get(userName);
            if(user.getIsLoggedIn()){
                errorMessage = "User already logged in";
                ERROR error = new ERROR(receiptId, errorMessage, "");
                connectionImpl.send(connectionId ,error);
            }
            else{
                if(!user.getPassword().equals(password)){
                    errorMessage = "Wrong password";
                    ERROR error = new ERROR(receiptId,errorMessage, "");
                    connectionImpl.send(connectionId ,error);
                }
                else if(brain.getConnectionHandler(connectionId)==null)
                {
                    CONNECTED connected = new CONNECTED(acceptVersion);
                    this.connectionImpl.send(connectionId, connected);
                }
            }
        }
    }

    public String getAcceptVersion()
    {
        return acceptVersion;
    }

    public String getHost()
    {
        return host;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getReciptId() { return receiptId; }

    public String getType()
    {
        return type;
    }

}
