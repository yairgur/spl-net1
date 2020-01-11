package bgu.spl.net.SentFrames;

public class RECEIPT implements SentFrame{
    private String receiptId;
    private String type;

    public RECEIPT(String reciptId, String type)
    {
        this.receiptId = reciptId;
        this.type = type;
    }

    public String toString()
    {
        if(type.equals("disconnect"))
            return "RECEIPT\nreceipt-id:" + receiptId + "\n\n" ;
        if(type.equals("subscribe"))
            return "RECEIPT\nreceipt-id:" + receiptId + "\n\n" ;
        if(type.equals("unsubscribe"))
            return "RECEIPT\nid:" + receiptId + "\n\n" ;
        return "";
    }
}

//TODO check if we need the field "message" - or interpreted in the client