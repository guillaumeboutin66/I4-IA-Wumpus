package main;

import java.awt.Point;
import java.util.*;


/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {

    Random randomPeer = new Random();
    int knownMaxHeight = 0;//Those will be used later
    int knownMaxWidth = 0;
    Cell[][] knownCases;
    Cell[][] map;
    Agent agent;
    Point playerPosition;

    public ComputedDecision(GameMap map, Cell[][] knownCases, Point playerPosition) {
        this.map = map.getCells();
        this.agent = map.getAgent();
        this.knownCases = knownCases;
        this.playerPosition = playerPosition;
    }

    public Direction[] takeDecision() {
        //maybe check first on a low radius, then increse it ?
        Point targetPosition = findCell(10, knownCases[playerPosition.x][playerPosition.y]);
        System.out.println("Current position x:" +  this.playerPosition.x + "  y:" + this.playerPosition.y);
        System.out.println("Target position x:" +  targetPosition.x + "  y:" + targetPosition.y);
        if(targetPosition != null) {
            List<Point> result = AlgoA.getSolution(10, 10, this.playerPosition.x, this.playerPosition.y, targetPosition.x, targetPosition.y, this.agent.getSupposedCellsToList());
            
            
            System.out.println(result);
            return toDirections(result);
            
        }else{
            //this.randomDecision();
           // return this.randomDecision();
           return null;
        }
    }



    private Point findCell(int radius, Cell startCell){
        HashSet<Cell> radiusOldCell = new HashSet<Cell>();
        HashSet<Cell> radiusNewCell = new HashSet<Cell>();
        radiusOldCell.add(startCell);
        
        for(int i = 0; i < radius; i++){
            for (Cell mainCell : radiusOldCell) {
                Cell cell;
                Cell suspiciousCell;
                if(mainCell.position.x - 1 >= 0){

                    cell = knownCases[mainCell.position.x - 1][mainCell.position.y];
                    if(cell == null){
                        suspiciousCell = this.agent.getSupposedCells()[mainCell.position.x - 1][mainCell.position.y];
                        if(suspiciousCell == null || (suspiciousCell != null && !suspiciousCell.isDangerous())){
                            return new Point(mainCell.position.x - 1, mainCell.position.y);
                        }else{
                            System.out.print("NSM");
                        }
                    } else if (!radiusNewCell.contains(cell)){
                        radiusNewCell.add(cell);                        
                    }
                }
                
                if(mainCell.position.x + 1 < knownCases.length){

                    cell = knownCases[mainCell.position.x + 1][mainCell.position.y];
                    if(cell == null){
                        suspiciousCell = this.agent.getSupposedCells()[mainCell.position.x + 1][mainCell.position.y];
                        if(suspiciousCell == null || (suspiciousCell != null && !suspiciousCell.isDangerous())){
                            return new Point(mainCell.position.x + 1, mainCell.position.y);
                        }else{
                            System.out.print("NSM");
                        }
                    } else if (!radiusNewCell.contains(cell)){
                        radiusNewCell.add(cell);                        
                    }
                }
                
                if(mainCell.position.y - 1 >= 0){

                    cell = knownCases[mainCell.position.x][mainCell.position.y - 1];
                    if(cell == null){
                        suspiciousCell = this.agent.getSupposedCells()[mainCell.position.x][mainCell.position.y - 1];
                        if(suspiciousCell == null || (suspiciousCell != null && !suspiciousCell.isDangerous())){
                            return new Point(mainCell.position.x, mainCell.position.y - 1);
                        }else{
                            System.out.print("NSM");
                        }
                    } else if (!radiusNewCell.contains(cell)){
                        radiusNewCell.add(cell);                        
                    }
                }
                
                if(mainCell.position.y + 1 < knownCases[0].length){

                    cell = knownCases[mainCell.position.x][mainCell.position.y + 1];
                    if(cell == null){
                        suspiciousCell = this.agent.getSupposedCells()[mainCell.position.x][mainCell.position.y + 1];
                        if(suspiciousCell == null || (suspiciousCell != null && !suspiciousCell.isDangerous())){
                            return new Point(mainCell.position.x, mainCell.position.y + 1);
                        }else{
                            System.out.print("NSM");
                        }
                    } else if (!radiusNewCell.contains(cell)){
                        radiusNewCell.add(cell);                        
                    }
                } 

            }

            radiusOldCell.addAll(radiusNewCell);
            radiusNewCell.clear();
        }

        System.out.println("no case have been found in this range.");
        return null;
    }

/*
    private Point findCell(int radius, Cell cell) {
        HashSet<Cell> radiusOldCell = new HashSet<Cell>();
        HashSet<Cell> radiusNewCell = new HashSet<Cell>();
        radiusOldCell.add(cell);

        for (int i = 0; i < radius; i++) {
            for (Cell suCell : radiusOldCell) {
                Cell tmp = null;
                if (suCell.position.x - 1 >= 0) {
                    //if(this.agent.getSupposedCells()[suCell.position.x - 1][suCell.position.y] != null && !this.agent.getSupposedCells()[suCell.position.x - 1][suCell.position.y].isDangerous()){
                        tmp = knownCases[suCell.position.x - 1][suCell.position.y];   
                    //}
                    
                    if (tmp == null) {
                        return new Point(suCell.position.x - 1, suCell.position.y);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.x + 1 < knownCases.length) {
                    
                    //if(this.agent.getSupposedCells()[suCell.position.x + 1][suCell.position.y] != null && !this.agent.getSupposedCells()[suCell.position.x + 1][suCell.position.y].isDangerous()){
                        tmp = knownCases[suCell.position.x + 1][suCell.position.y];
                    //}
                    
                    if (tmp == null ) {
                        return new Point(suCell.position.x + 1, suCell.position.y);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y - 1 >= 0) {
                    
                    //if(this.agent.getSupposedCells()[suCell.position.x][suCell.position.y - 1] != null && !this.agent.getSupposedCells()[suCell.position.x][suCell.position.y - 1].isDangerous()){
                        tmp = knownCases[suCell.position.x][suCell.position.y - 1];
                    //}
                     if (tmp == null) {
                        return new Point(suCell.position.x, suCell.position.y - 1);
                    } else if (!radiusNewCell.contains(tmp) && !radiusOldCell.contains(tmp) && !tmp.isDangerous()) {
                        radiusNewCell.add(tmp);
                    }
                }

                if (suCell.position.y + 1 < knownCases[0].length) {
                    
                    //if(this.agent.getSupposedCells()[suCell.position.x][suCell.position.y - 1]!= null && !this.agent.getSupposedCells()[suCell.position.x][suCell.position.y + 1 ].isDangerous()){
                        tmp = knownCases[suCell.position.x][suCell.position.y + 1];
                    //}
                    
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
*/

    private Direction[] toDirections(List<Point> path) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        Point temporalPosition = new Point(playerPosition.x, playerPosition.y);
        for (Point point : path) {
            if (point.y < temporalPosition.y) {
                directions.add(Direction.up);
                temporalPosition.setLocation(temporalPosition.x, temporalPosition.y - 1);
            } else if (point.y > temporalPosition.y) {
                directions.add(Direction.down);
                temporalPosition.setLocation(temporalPosition.x, temporalPosition.y + 1);
            } else if (point.x > temporalPosition.x) {
                directions.add(Direction.right);
                temporalPosition.setLocation(temporalPosition.x - 1, temporalPosition.y);
            } else if (point.x < temporalPosition.x) {
                directions.add(Direction.left);
                temporalPosition.setLocation(temporalPosition.x + 1, temporalPosition.y);
            }
        }
        
        System.out.println("Pour aller de  : X" + playerPosition.x + " Y" + playerPosition.y + " à  la destination");
         for(Direction direction : directions){
             System.out.print(direction + " => ");
         }      
        
        return directions.toArray(new Direction[directions.size()]);
    }


    private Direction[] randomDecision() {
        int rand = randomPeer.nextInt(4);
        Direction[] directions = new Direction[1];
        directions[0] = Direction.values()[rand];
        return directions;
    }

}