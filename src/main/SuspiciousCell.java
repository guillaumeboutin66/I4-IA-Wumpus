package main;

import java.awt.*;

/**
 * Created by Azuro on 14/12/2017.
 */
public class SuspiciousCell {
    SafeLevel safeLevel;
    Point position;
    int danger = 0;

    public SuspiciousCell(Point p, int i){
        this.position = p;
        this.danger = i;
    }
    public enum SafeLevel{
        safe,
        unsafe,
        unknown,
        notReachable
    }
}
