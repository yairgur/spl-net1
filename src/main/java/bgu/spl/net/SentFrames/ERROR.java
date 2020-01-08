package bgu.spl.net.SentFrames;

public class ERROR implements SentFrame{
    private String reciptId;
    private String message;
    private String body;


    public ERROR(String reciptId, String message, String body)
    {
        this.reciptId = reciptId;
        this.message = message;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "";
    }

}
