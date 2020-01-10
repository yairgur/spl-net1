package bgu.spl.net.SentFrames;

public class MESSAGE implements SentFrame {
    private String subscription;
    private String messageId;
    private String destination;
    private String body;

    public MESSAGE(String subscription, String messageId, String destination, String body){
        this.subscription = subscription;
        this.messageId = messageId;
        this.destination = destination;
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "MESSAGE\nsubscription:" + subscription + "\nMessage-id:" + messageId + "\ndestination:" + destination + "\n\n" + body + "\n\n" + '\u0000';
    }

}
