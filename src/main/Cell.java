package main;

import java.awt.*;
import java.util.ArrayList;

public class Cell {

    private ArrayList<Event> events = new ArrayList<>();

    public Point position;

    Cell(Point pos){
        this.position = pos;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return "["+this.position.x+", "+this.position.y+"]/"+ getEvents().toString();
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
        wind,
        agent
    }
    public void removeEvent(Event e){
        this.events.remove(e);
    }
}
