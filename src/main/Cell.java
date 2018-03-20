package main;

import java.awt.*;
import java.util.ArrayList;

public class Cell {

    private ArrayList<Event> events = new ArrayList<>();
    public Point position;
    private Boolean deadly;

    public Cell(Point pos){
        position = pos;
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
        if(e == Event.wumpus || e == Event.pit){
            deadly = true;
        }
    }

    public void clearEvent(){
        events.clear();
    }

    public Boolean isDangerous(){
        return deadly;
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
