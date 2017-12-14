package test;

import main.Cell;
import main.GameMap;

public class GameMapTest{

    public void main(String[] args) {
        testCreationMap();
    }

    @org.junit.Test
    public final void testCreationMap(){
        GameMap gmt = new GameMap(10, 10);
        System.out.println(gmt.toString());
    }
}