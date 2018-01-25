package main;

import main.Cell;

import java.awt.*;
import java.util.ArrayList;

public class GameMap {

    private int width;
    private int length;
    private Cell[][] cells;
    private ArrayList<Point> lockedPoints = new ArrayList<Point>();

    public GameMap(int w, int l){
        width = w;
        length = l;

        cells = new Cell[width][length];

        //Agent
        Point agentPos = new Point(0,length-1);
        cells[0][length-1] = new Cell(agentPos,1);
        lockedPoints.add(agentPos);

        // Wumpus
        Point posWumpus = generatePoint(width, length, lockedPoints);
        cells[posWumpus.x][posWumpus.y] = new Cell(posWumpus,1000);
        lockedPoints.add(posWumpus);

        // Gold
        Point posGold = generatePoint(width, length, lockedPoints);
        cells[posGold.x][posGold.y] = new Cell(posGold,100);
        lockedPoints.add(posWumpus);

        // Pit
        // RANDOM INT BETWEEN 1 & 20% of the number of cells
        int maxNumberOfPits = (int) Math.round(w*l*0.20);
        int randomNumberOfPits = generateRandom(maxNumberOfPits);
        for(int i = 0; i < randomNumberOfPits; i++){
            Point posPit = generatePoint(width, length, lockedPoints);
            cells[posPit.x][posPit.y] = new Cell(posPit,100);
            lockedPoints.add(posPit);
        }

        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++) {
                if(cells[i][j] == null){
                    cells[i][j] = new Cell(new Point(i,j),0);
                }
            }
        }

    }

    private Point generatePoint(int maxWidth, int maxLength, ArrayList<Point> avoidPoints){
        int x = generateRandom(maxWidth);
        int y = generateRandom(maxLength);

        boolean alreadyExist = false;

        for (Point avoidPoint: avoidPoints) {
            if(avoidPoint.x == x && avoidPoint.y == y){
                alreadyExist = true;
            }
        }

        if(alreadyExist){
            return generatePoint(maxWidth,maxLength, avoidPoints);
        } else {
            return new Point(x,y);
        }

    }

    private int generateRandom(int Max){
        return (int) Math.round((Math.random() * (Max - 1)));
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<Point> getLockedPoints() {
        return lockedPoints;
    }

    public Agent getAgent(){
        return null;
    }

    public Wumpus getWumpus(){
        return null;
    }
}
