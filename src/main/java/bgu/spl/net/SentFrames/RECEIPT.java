package bgu.spl.net.SentFrames;

public class RECEIPT implements SentFrame{
    private String receiptId;
    private String message;

    public RECEIPT(String reciptId, String message)
    {
        this.receiptId = reciptId;
        this.message = message;
    }

    public String toString()
    {
        return "RECEIPT\nreceipt-id:" + receiptId + "\n\n" + '\u0000';
    }
}

//TODO check if we need the field "message" - or interpreted in the client