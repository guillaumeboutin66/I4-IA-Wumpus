package test;

import main.GameMap;

public class GameMapTest{

    @org.junit.Test
    public final void testCreationMap() throws Exception{
        GameMap gmt = new GameMap(10, 10);
        for(int i = 0; i < gmt.getWidth(); i++){
            for(int j = 0; j < gmt.getLength(); j++) {
                System.out.print(gmt.getCells()[i][j].toString());
            }
            System.out.println();
        }
    }

    @org.junit.Test
    public final void testAddAdjacentEvent() throws Exception{

    }
}