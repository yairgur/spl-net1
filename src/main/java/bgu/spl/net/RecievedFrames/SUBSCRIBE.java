package bgu.spl.net.RecievedFrames;

public class SUBSCRIBE implements Frame{
    private String destination;
    private int id;
    private int recipt;

    public SUBSCRIBE(String destination, int id, int recipt)
    {
        this.destination = destination;
        this.id = id;
        this .recipt = recipt;
    }

    public void run(int connectionId)
    {

    }
}
