package bgu.spl.net.SentFrames;

public class RECEIPT implements SentFrame{
    private String receiptId;
    private String message;

    public RECEIPT(String reciptId, String message)
    {
        this.receiptId = reciptId;
        this.message = message;
    }
}
