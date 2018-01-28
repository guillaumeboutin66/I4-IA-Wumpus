package main;

import java.awt.*;
import java.util.ArrayList;

public class Cell {

    private ArrayList<Event> events = new ArrayList<>();
    public Point position;

    Cell(Point pos){
        position = pos;
    }

    @Override
    public String toString(){
        return "["+this.position.x+", "+this.position.y+", events : "+ getEvents().toString();
    }


    public ArrayList<Event> getEvents(){
        return events;
    }

    public void addEvent(Event e){
        events.add(e);
    }

    public enum Event{
        wumpus,
        gold,
        pit,
        smell,
        wind
    }
}
