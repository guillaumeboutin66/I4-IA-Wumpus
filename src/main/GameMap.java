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

        //Wumpus
        Point posWumpus = generatePoint(width, length, lockedPoints);
        cells[posWumpus.x][posWumpus.y] = new Cell(posWumpus,1000);
        lockedPoints.add(posWumpus);

        //Gold
        Point posGold = generatePoint(width, length, lockedPoints);
        cells[posGold.x][posGold.y] = new Cell(posGold,1000);
        lockedPoints.add(posWumpus);

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
        return (int) Math.round(1 + (Math.random() * (Max - 1)));
    }
}
