package main;

import java.awt.*;

public class Cell {

    Point position;
    int danger;

    Cell(Point pos, int d){
        position = pos;
        danger = d;
    }

    @Override
    public String toString(){
        return "["+this.position.x+", "+this.position.y+", danger : "+this.danger+"]";
    }
}
