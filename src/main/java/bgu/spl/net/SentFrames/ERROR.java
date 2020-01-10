package bgu.spl.net.SentFrames;

public class ERROR implements SentFrame{
    private String receiptId;
    private String message;
    private String body;


    public ERROR(String receiptId, String message, String body)
    {
        this.receiptId = receiptId;
        this.message = message;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "ERROR\nreceipt-id:" + receiptId + "\nmessage:" + message + "\nThe message:\n" + body + "\n" + '\u0000';
    }

}
