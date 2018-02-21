package main;

import java.awt.*;
import java.util.ArrayList;

public class Cell {

    private ArrayList<Event> events = new ArrayList<>();
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


    public ArrayList<Event> getEvents(){
        return events;
    }

    public int getDanger(){
        return this.danger;
    }

    public enum Event{
        smell,
        wind
    }
}
