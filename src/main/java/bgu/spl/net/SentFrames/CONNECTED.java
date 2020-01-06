package bgu.spl.net.SentFrames;

import bgu.spl.net.RecievedFrames.Frame;

public class CONNECTED implements SentFrame
{
    private String version;

    public CONNECTED(String version)
    {
        this.version = version;
    }
}
