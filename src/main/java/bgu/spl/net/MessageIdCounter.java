package bgu.spl.net;

import java.util.concurrent.atomic.AtomicInteger;

public class MessageIdCounter {
    public AtomicInteger counter;

    public MessageIdCounter()
    {
        counter = new AtomicInteger(1);
    }

    public void increase()
    {
        counter.incrementAndGet();
    }

    public int getValue()
    {
        return counter.intValue();
    }
}
