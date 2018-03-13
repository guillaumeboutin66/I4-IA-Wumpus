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
    
    SuspiciousCell(Point pos, SafeLevel level){
        position = pos;
        safeLevel = level;
    }

    public enum SafeLevel{
        safe,
        unsafe,
        unknown,
        notReachable
    }

    @Override
    public String toString() {
        return "Suspicious cell :\n Position : x " + this.position.x + "| y " + this.position.y + "\n Safelevl : " +this.safeLevel.toString();
    }
}
