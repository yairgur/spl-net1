package bgu.spl.net.srv;

public class User {
    private ConnectionHandler connectionHandler;
    private String username;
    private String password;
    private boolean isLoggedIn;

    public User(ConnectionHandler connectionHandler, String username, String password)
    {
        isLoggedIn = false;
        this.connectionHandler = connectionHandler;
        this.username = username;
        this. password = password;
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


}
