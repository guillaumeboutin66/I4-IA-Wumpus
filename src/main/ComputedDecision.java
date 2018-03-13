package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import main.SuspiciousCell.SafeLevel;

/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {

    Random randomPeer = new Random();
    int knownMaxHeight = 0;
    int knownMaxWidth = 0;
    SuspiciousCell[][] knownCases;
    Cell[][] map;
    Point playerPosition;

    //SuspiciousCell
    public ComputedDecision(Cell[][] map, SuspiciousCell[][] knownCases, Point playerPosition) {
        this.map = map;
        this.knownCases = knownCases;
        this.playerPosition = playerPosition;
    }

    public Direction[] takeDecision() {
        //maybe check first on a low radius, then increse it ?
        SuspiciousCell targetCell = findCell(5, knownCases[playerPosition.x][playerPosition.y]);

        //return toDirections(AStart(targetCell, playerPosition));
        return null;
    }


    private SuspiciousCell findCell(int radius, SuspiciousCell cell) {
        HashSet<SuspiciousCell> radiusOldCell = new HashSet<SuspiciousCell>();
        HashSet<SuspiciousCell> radiusNewCell = new HashSet<SuspiciousCell>();
        radiusOldCell.add(cell);

        for (int i = 0; i < radius; i++) {
            for (SuspiciousCell suCell : radiusOldCell) {
                SuspiciousCell tmp;
                if (suCell.position.x - 1 >= 0) {
                    tmp = knownCases[suCell.position.x - 1][suCell.position.y];
                    if (tmp.safeLevel == SafeLevel.unknown) {
                        return tmp;
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp)
                            && tmp.safeLevel != SafeLevel.unsafe) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.x + 1 < knownCases.length) {
                    tmp = knownCases[suCell.position.x + 1][suCell.position.y];
                    if (tmp.safeLevel == SafeLevel.unknown) {
                        return tmp;
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp)
                            && tmp.safeLevel != SafeLevel.unsafe) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y - 1 >= 0) {
                    tmp = knownCases[suCell.position.x][suCell.position.y - 1];
                    if (tmp.safeLevel == SafeLevel.unknown) {
                        return tmp;
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp)
                            && tmp.safeLevel != SafeLevel.unsafe) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y + 1 < knownCases[0].length) {
                    tmp = knownCases[suCell.position.x][suCell.position.y + 1];
                    if (tmp.safeLevel == SafeLevel.unknown) {
                        return tmp;
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp)
                            && tmp.safeLevel != SafeLevel.unsafe) {
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

    private Direction[] toDirections(ArrayList<Point> path) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Point point : path) {
            if (point.y > playerPosition.y) {
                direction.add(Direction.up);
            } else if (point.y < playerPosition.y) {
                direction.add(Direction.down);
            } else if (point.x > playerPosition.x) {
                direction.add(Direction.right);
            } else {
                direction.add(Direction.left);
            }
        }

        return direction.toArray(new Direction[direction.size()]);
    }

    private Direction[] RandomDecision() {
        int rand = randomPeer.nextInt(4);
        Direction[] directions = new Direction[1];
        directions[0] = Direction.values()[rand];
        return directions;
    }

    public enum Direction {
        up, down, right, left
    }

}
