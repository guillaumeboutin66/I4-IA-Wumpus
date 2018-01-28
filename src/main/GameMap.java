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
        cells[0][length-1] = new Cell(agentPos);
        lockedPoints.add(agentPos);

        // Wumpus
        Point posWumpus = generatePoint(width, length, lockedPoints);
        cells[posWumpus.x][posWumpus.y] = new Cell(posWumpus);
        cells[posWumpus.x][posWumpus.y].addEvent(Cell.Event.wumpus);
        lockedPoints.add(posWumpus);

        // Gold
        Point posGold = generatePoint(width, length, lockedPoints);
        cells[posGold.x][posGold.y] = new Cell(posGold);
        cells[posGold.x][posGold.y].addEvent(Cell.Event.gold);
        lockedPoints.add(posGold);

        // Pit
        // RANDOM INT BETWEEN 1 & 20% of the number of cells
        int maxNumberOfPits = (int) Math.round(w*l*0.20);
        int randomNumberOfPits = generateRandom(maxNumberOfPits);
        for(int i = 0; i < randomNumberOfPits; i++){
            Point posPit = generatePoint(width, length, lockedPoints);
            cells[posPit.x][posPit.y] = new Cell(posPit);
            cells[posPit.x][posPit.y].addEvent(Cell.Event.pit);
            lockedPoints.add(posPit);
        }

        // Fill the rest of the Map of normal Cells
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++) {
                if(cells[i][j] == null){
                    cells[i][j] = new Cell(new Point(i,j));
                }
            }
        }

        // Fill the Map of side event Cells
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++) {
                if(cells[i][j].getEvents().contains(Cell.Event.wumpus)){
                    addAdjacentEvent(cells[i][j], Cell.Event.smell);
                }
                if(cells[i][j].getEvents().contains(Cell.Event.pit)){
                    addAdjacentEvent(cells[i][j], Cell.Event.wind);
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

    /**
     * Add the event to adjacents cells
     * @param cell
     * @param event
     */
    private void addAdjacentEvent(Cell cell, Cell.Event event){
        if(cell.position.x > 0){
            cells[cell.position.x-1][cell.position.y].addEvent(event);
        }
        if(cell.position.y > 0){
            cells[cell.position.x][cell.position.y-1].addEvent(event);
        }
        if(cell.position.x < width-1){
            cells[cell.position.x+1][cell.position.y].addEvent(event);
        }
        if(cell.position.y < length-1){
            cells[cell.position.x][cell.position.y+1].addEvent(event);
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

}
