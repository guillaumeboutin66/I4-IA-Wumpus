package test;

import main.Agent;
import main.Cell;
import org.junit.Assert;

import java.awt.*;

public class AgentTest {

    @org.junit.Test
    public final void addSupposedCellsTest() throws Exception{
        Agent agentTest = new Agent(new Point(0,9),10,10);
        Cell currentCellTest = new Cell(new Point(1,8));
        currentCellTest.addEvent(Cell.Event.smell);
        currentCellTest.addEvent(Cell.Event.wind);

        agentTest.addSupposedCells(currentCellTest);

        System.out.println(agentTest.getSupposedCells()[0][8].toString());
        System.out.println(agentTest.getSupposedCells()[2][8].toString());
        System.out.println(agentTest.getSupposedCells()[1][7].toString());
        System.out.println(agentTest.getSupposedCells()[1][9].toString());

        Assert.assertEquals(agentTest.getSupposedCells()[0][8].getEvents().contains(Cell.Event.wumpus), true);
        Assert.assertEquals(agentTest.getSupposedCells()[2][8].getEvents().contains(Cell.Event.wumpus), true);
        Assert.assertEquals(agentTest.getSupposedCells()[1][7].getEvents().contains(Cell.Event.wumpus), true);
        Assert.assertEquals(agentTest.getSupposedCells()[1][9].getEvents().contains(Cell.Event.wumpus), true);

        Assert.assertEquals(agentTest.getSupposedCells()[0][8].getEvents().contains(Cell.Event.pit), true);
        Assert.assertEquals(agentTest.getSupposedCells()[2][8].getEvents().contains(Cell.Event.pit), true);
        Assert.assertEquals(agentTest.getSupposedCells()[1][7].getEvents().contains(Cell.Event.pit), true);
        Assert.assertEquals(agentTest.getSupposedCells()[1][9].getEvents().contains(Cell.Event.pit), true);

    }

    @org.junit.Test
    public final void testAddAdjacentEvent() throws Exception{

    }
}