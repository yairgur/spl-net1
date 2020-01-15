package bgu.spl.net.srv;

import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionId {

    private AtomicInteger connctionId;

    private static class SingletonHolder {
        private static ConnectionId instance = new ConnectionId();
    }

    public static ConnectionId getInstance() {
        return ConnectionId.SingletonHolder.instance;
    }

    public ConnectionId(){
        connctionId = new AtomicInteger(1);
    }

    public int getAndIncreaseConnectionId(){
        int returnValue = connctionId.intValue();
        connctionId.incrementAndGet();
        return returnValue;
    }
}
