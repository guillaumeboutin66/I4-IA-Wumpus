package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Erik
 */


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Agent extends Cell {

    private boolean Shoot;
    private String Direction;
    private Cell[][] knownCells;
    private Cell[][] supposedCells;
    private Cell lastCell;

    public Agent(Point p, int weight, int height) {
        super(p);
        
        this.Direction = "up";
        this.Shoot = true;

        knownCells = new Cell[weight][height];
        supposedCells = new Cell[weight][height];
    }

    public void Walk() {
    
        String CurrentDirection = this.Direction;
        
        switch(CurrentDirection){
            
            case "right": this.position.x +=1;
                     break;
            case "up": this.position.y -=1;
                      break;
            case "left": this.position.x -=1;
                     break;
            case "down": this.position.y +=1;
                     break;
            default: System.out.println("Invalid direction"); 
                     break;
        }    
    }

    public void move(String action) {
        if (action.equals("turn_right")) {
            Turn(false);
        } else if (action.equals("turn_left")) {
            Turn(true);
        } else if (action.equals("walk")) {
            Walk();
        }
    }

    public ArrayList<String> allerA(String action) {
        ArrayList<String> listeActions = new ArrayList<>();
        if (action.equals("right")) {
            switch (this.Direction) {
                case "right":
                    listeActions.add("walk");
                    break;
                case "up":
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "left":
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "down":
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals("left")){
            switch (this.Direction) {
                case "left":
                    listeActions.add("walk");
                    break;
                case "down":
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "right":
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "up":
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals("up")){
            switch (this.Direction) {
                case "up":
                    listeActions.add("walk");
                    break;
                case "left":
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "down":
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "right":
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals("down")){
            switch (this.Direction) {
                case "down":
                    listeActions.add("walk");
                    break;
                case "right":
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "up":
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case "left":
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }
        return listeActions;
    }



    public void Turn(boolean TurnAction){
        
        String InitialDirection = this.getDirection();

        //Turn Left
        if (TurnAction == true) {
            
            if(InitialDirection == "down"){

                this.setDirection("right");

            }
            else{
                if(InitialDirection == "right"){

                    this.setDirection("up");

                }else{

                    if(InitialDirection == "up"){

                        this.setDirection("left");

                    }else{

                        this.setDirection("down");

                    }
                }
            }
        }
        else /*if(TurnAction == false)*/ {

            if(InitialDirection == "down"){

                this.setDirection("left");

            }
            else{
                if(InitialDirection == "left"){

                    this.setDirection("up");

                }else{

                    if(InitialDirection == "up"){

                        this.setDirection("right");

                    }else{

                        this.setDirection("down");

                    }
                }
            }       
        }    
    }

     public boolean UseWeapon(Cell monster) {
         
        String CurrentDirection = this.Direction;
        
        switch(CurrentDirection){
            
            case "right":   if(this.position.y == monster.position.y && monster.position.x > this.position.x){ this.setShoot(false); return true;};
                     break;
            case "up":  if(this.position.x == monster.position.x && monster.position.y > this.position.y){ this.setShoot(false); return true;};
                     break;
            case "left": if(this.position.y == monster.position.y && monster.position.x < this.position.x){ this.setShoot(false); return true;};
                     break;
            case "down": if(this.position.x == monster.position.x && monster.position.y < this.position.y){ this.setShoot(false); return true;};
                     break;
            default: System.out.println("Invalid direction"); 
                     break;
        }

         return false;
     }

    public boolean isShoot() {
        return Shoot;
    }

    /**
     * Do both add known and supposed Cell
     * @param currentCell
     */
    public void addKnownAndSupposedCells(Cell currentCell){
        pushKnownCell(currentCell);
        addSupposedCells(currentCell);
    }

    /**
     * Add at the correct position in the knownCells array the current cell
     * @param currentCell
     */
    public void pushKnownCell(Cell currentCell){
        knownCells[currentCell.position.x][currentCell.position.y] = currentCell;
    }

    /**
     * Check if the cell send
     * @param currentCell
     */
    public void addSupposedCells(Cell currentCell){
        /* In case the current cell contains wind event */
        if(currentCell.getEvents().contains(Cell.Event.wind)){
            checkingSupposedCells(currentCell, Cell.Event.pit);
        }
         /* In case the current cell contains smell event */
        if(currentCell.getEvents().contains(Cell.Event.smell)){
            checkingSupposedCells(currentCell, Cell.Event.wumpus);
        }
    }

    /**
     * Will add suspicious cells if they are in the limit of the map,
     * and if they are not in the knownCells array
     * @param currentCell
     * @param event
     */
    private void checkingSupposedCells(Cell currentCell, Cell.Event event){
        int xCurrentCell = currentCell.position.x;
        int yCurrentCell = currentCell.position.y;
        if(xCurrentCell > 0 && knownCells[xCurrentCell-1][yCurrentCell] == null){
            createSupposedCell(xCurrentCell-1, yCurrentCell, event);
        }
        if(yCurrentCell > 0 && knownCells[xCurrentCell][yCurrentCell-1] == null){
            createSupposedCell(xCurrentCell, yCurrentCell-1, event);
        }
        if(xCurrentCell < knownCells.length -1 && knownCells[xCurrentCell+1][yCurrentCell] == null){
            createSupposedCell(xCurrentCell+1, yCurrentCell, event);
        }
        if(yCurrentCell < knownCells[0].length -1 && knownCells[xCurrentCell][yCurrentCell+1] == null){
            createSupposedCell(xCurrentCell, yCurrentCell+1, event);
        }
    }

    /**
     * Create a new cell into suspiciousCells array if not exist, else push the event if it exists,
     * but only if the cell doesn't contain the event
     * @param supposedX
     * @param supposedY
     * @param event
     */
    private void createSupposedCell(int supposedX, int supposedY, Cell.Event event){
        if (supposedCells[supposedX][supposedY] == null){
            supposedCells[supposedX][supposedY] = new Cell(new Point(supposedX,supposedY));
            supposedCells[supposedX][supposedY].addEvent(event);
        } else {
            if(!supposedCells[supposedX][supposedY].getEvents().contains(event)){
                supposedCells[supposedX][supposedY].addEvent(event);
            }
        }
    }

    public void setShoot(boolean Shoot) {
        this.Shoot = Shoot;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    public Cell[][] getSupposedCells() {
        return supposedCells;
    }

    public Cell[][] getKnownCells() {
        return knownCells;
    }
}
