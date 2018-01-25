import java.util.ArrayList;

/**
 * Created by Azuro on 14/12/2017.
 */
public class Case {
    private ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> getEvents(){
        return events;
    }


    public enum Event{
        smell,
        wind
    }
}
