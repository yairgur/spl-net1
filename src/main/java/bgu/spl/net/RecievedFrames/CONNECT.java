package bgu.spl.net.RecievedFrames;

import bgu.spl.net.srv.Brain;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.User;

public class CONNECT implements Frame{

    private String acceptVersion;
    private String host;
    private String userName;
    private String password;
//    private int connectionId;



    public CONNECT(String acceptVersion, String host, String userName, String password){
        this.acceptVersion = acceptVersion;
        this.host = host;
        this.userName = userName;
        this.password = password;
//        this.connectionId = connectionId;
    }

    public void run(){
        //TODO add try catch for socket error
        Brain brain = Brain.getInstance();
//        ConnectionHandler handler = brain.getConnectionHandler(connectionId);

        if(!brain.getUserNamesMap().keySet().contains(userName))
        {
            //User user = new User(handler,userName, password);
            //brain.getUserNamesMap().put(userName, user);
            //TODO send CONNECTED frame to the client --> client prints

        }
        else{
            User user = brain.getUserNamesMap().get(userName);
            if(user.getIsLoggedIn()){
                // trow exeption - User already logged in
            }
            else{
                if(!user.getPassword().equals(password)){
                    // TODO send ERROR frame to client - wrong password
                }
//                else if(brain.getConnectionHandler(connectionId)==null)
                {
                    //TODO send CONNECTED frame
                }
            }
        }
    }

}
