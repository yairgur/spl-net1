package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.CONNECTED;
import bgu.spl.net.SentFrames.ERROR;
import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.ConnectionsImpl;
import bgu.spl.net.srv.User;

public class CONNECT implements Frame{

    private boolean terminate;
    private String type;
    private String acceptVersion;
    private String host;
    private String userName;
    private String passcode;
    private ConnectionsImpl connectionImpl;
    private String receiptId; // this field is for the error to send reciptId of specific frame



    public CONNECT(String type, String acceptVersion, String host, String userName, String passcode, String reciptId){
        this.type = type;
        this.acceptVersion = acceptVersion;
        this.host = host;
        this.userName = userName;
        this.passcode = passcode;
        this.connectionImpl = ConnectionsImpl.getInstance();
        this.receiptId = reciptId;
        this.terminate = false;
    }

    public void run(int connectionId){
        //TODO add try catch for socket error
        Brain brain = Brain.getInstance();
        ConnectionHandler handler = brain.getConnectionHandler(connectionId);
        String errorMessage = "";
        if(!brain.getUserNamesMap().keySet().contains(userName))
        {
            User user = new User(handler, userName, passcode, connectionId);
            brain.getUserNamesMap().put(userName, user);
            brain.addToUserConnectionsMap(connectionId, user); // add to connections map
            CONNECTED connected = new CONNECTED(acceptVersion);
            this.connectionImpl.send(connectionId, connected);
            user.logIn();
            brain.addLoggedInUser(user);
        }
        else{
            User user = brain.getUserNamesMap().get(userName);
            if(user.getIsLoggedIn()){
                errorMessage = "User already logged in";
                ERROR error = new ERROR(receiptId, errorMessage, "");
                terminate = true;
                connectionImpl.send(connectionId ,error);
                //connectionImpl.disconnect(connectionId);
                brain.removeConnectionHandler(connectionId);
            }
            else{
                if(!user.getPasscode().equals(passcode)){
                    errorMessage = "Wrong password";
                    ERROR error = new ERROR(receiptId,errorMessage, "");
                    terminate = true;
                    connectionImpl.send(connectionId ,error);
                    //connectionImpl.disconnect(connectionId);
                    brain.removeConnectionHandler(connectionId);
                }
                else //if(brain.getConnectionHandler(connectionId)==null)
                {
                    brain.getUserNamesMap().get(userName).setUserHandler(handler,connectionId);
                    brain.setToUserConnectionsMap(connectionId, user);
                    // todo: we can add brain.RemoveFromLoggedInList(user)
                    brain.addLoggedInUser(user);
                    CONNECTED connected = new CONNECTED(acceptVersion);
                    this.connectionImpl.send(connectionId, connected);
                }
            }
        }
    }

    public boolean getTerminate()
    {
        return terminate;
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

    public String getPasscode()
    {
        return passcode;
    }

    public String getReciptId() { return receiptId; }

    public String getType()
    {
        return type;
    }

}
