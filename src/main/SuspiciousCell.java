package main;

import java.awt.*;

//TODO : DELETE THIS SHIT PLS
/**
 * Created by Azuro on 14/12/2017.
 */
public class SuspiciousCell {
    SafeLevel safeLevel;
    Point position;
    int danger = 0;
    
    public enum SafeLevel{
        safe,
        unsafe,
        unknown,
        notReachable
    }
}
