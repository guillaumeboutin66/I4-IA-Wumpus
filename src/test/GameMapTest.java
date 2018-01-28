package test;

import main.Cell;
import main.GameMap;

public class GameMapTest{

    public void main(String[] args) {
        try {
            testCreationMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public final void testCreationMap() throws Exception{
        GameMap gmt = new GameMap(10, 10);
        for(int i = 0; i < gmt.getWidth(); i++){
            for(int j = 0; j < gmt.getLength(); j++) {
                System.out.println(gmt.getCells()[i][j].toString());
            }
        }
    }

    @org.junit.Test
    public final void testAddAdjacentEvent() throws Exception{

    }
}