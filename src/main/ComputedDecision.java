package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {

    Random randomPeer = new Random();
    int knownMaxHeight = 0;//Those will be used later
    int knownMaxWidth = 0;
    Cell[][] knownCases;
    Cell[][] map;
    Point playerPosition;

    public ComputedDecision(Cell[][] map, Cell[][] knownCases, Point playerPosition) {
        this.map = map;
        this.knownCases = knownCases;
        this.playerPosition = playerPosition;
    }

    public ArrayList<String> takeDecision() {
        //maybe check first on a low radius, then increse it ?
        //Point targetPosition = findCell(5, knownCases[playerPosition.x][playerPosition.y]);

        //return toDirections(AStart(targetCell, playerPosition));
        ArrayList <String> directions = new ArrayList<>();
        directions.add("right");
        directions.add("right");
        directions.add("left");
        directions.add("up");
        directions.add("up");
        directions.add("right");
        directions.add("right");
        return directions;
    }

    private Point findCell(int radius, Cell cell) {
        HashSet<Cell> radiusOldCell = new HashSet<Cell>();
        HashSet<Cell> radiusNewCell = new HashSet<Cell>();
        radiusOldCell.add(cell);

        for (int i = 0; i < radius; i++) {
            for (Cell suCell : radiusOldCell) {
                Cell tmp;
                if (suCell.position.x - 1 >= 0) {
                    tmp = knownCases[suCell.position.x - 1][suCell.position.y];
                    if (tmp == null) {
                        return new Point(suCell.position.x - 1, suCell.position.y);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.x + 1 < knownCases.length) {
                    tmp = knownCases[suCell.position.x + 1][suCell.position.y];
                    if (tmp == null) {
                        return new Point(suCell.position.x + 1, suCell.position.y);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y - 1 >= 0) {
                    tmp = knownCases[suCell.position.x][suCell.position.y - 1];
                    if (tmp == null) {
                        return new Point(suCell.position.x, suCell.position.y - 1);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y + 1 < knownCases[0].length) {
                    tmp = knownCases[suCell.position.x][suCell.position.y + 1];
                    if (tmp == null) {
                        return new Point(suCell.position.x, suCell.position.y + 1);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }
            }

            radiusOldCell.addAll(radiusNewCell);
            radiusNewCell.clear();

        }

        System.out.println("no case have been found in this range.");
        return null;
    }

    private ArrayList<String> toDirections(ArrayList<Point> path) {
        ArrayList<String> directions = new ArrayList<String>();
        for (Point point : path) {
            if (point.y > playerPosition.y) {
                directions.add(Direction.up);
            } else if (point.y < playerPosition.y) {
                directions.add(Direction.down);
            } else if (point.x > playerPosition.x) {
                directions.add(Direction.right);
            } else {
                directions.add(Direction.left);
            }
        }

        return directions;
    }

    /*
    private Direction[] RandomDecision() {
        int rand = randomPeer.nextInt(4);
        Direction[] directions = new Direction[1];
        directions[0] = Direction.values()[rand];
        return directions;
    }*/

}