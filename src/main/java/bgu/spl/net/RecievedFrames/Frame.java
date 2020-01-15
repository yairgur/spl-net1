package bgu.spl.net.RecievedFrames;

import bgu.spl.net.SentFrames.SentFrame;

import java.util.LinkedList;

public interface Frame {
    void run(int connectionId);

    boolean getTerminate();
}
