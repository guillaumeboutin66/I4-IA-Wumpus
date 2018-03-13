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

public class Agent extends Cell {

    private boolean Shoot;
    private int Direction;
    private Cell[][] knownCells;
    private Cell[][] supposedCells;
    private Cell lastCell;

    public Agent(Point p, int weight, int height) {
        super(p);
        this.Direction = 90;
        this.Shoot = true;

        knownCells = new Cell[weight][height];
        supposedCells = new Cell[weight][height];
    }

    public void Walk() {

        int CurrentDirection = this.Direction;

        switch(CurrentDirection){

            case 0: this.position.x +=1;
                     break;
            case 90: this.position.y +=1;
                      break;
            case 180: this.position.x -=1;
                     break;
            case 270: this.position.y -=1;
                     break;
            default: System.out.println("Invalid direction");
                     break;
        }
    }

    public void Turn(boolean TurnAction){

        int InitialDirection = this.getDirection();

        //Turn Left
        if (TurnAction == true) {

            if(InitialDirection == 270){

                this.setDirection(0);

            }
            else{

                this.setDirection(InitialDirection + 90);
            }

        }
        else /*if(TurnAction == false)*/ {

            //turn right
            if(InitialDirection == 0){

                this.setDirection(270);

            }
            else{

                  this.setDirection(InitialDirection-90);
            }
        }
    }

     public boolean UseWeapon(Cell monster) {

        int CurrentDirection = this.Direction;

        switch(CurrentDirection){

            case 0:   if(this.position.y == monster.position.y && monster.position.x > this.position.x){ this.setShoot(false); return true;};
                     break;
            case 90:  if(this.position.x == monster.position.x && monster.position.y > this.position.y){ this.setShoot(false); return true;};
                     break;
            case 180: if(this.position.y == monster.position.y && monster.position.x < this.position.x){ this.setShoot(false); return true;};
                     break;
            case 270: if(this.position.x == monster.position.x && monster.position.y < this.position.y){ this.setShoot(false); return true;};
                     break;
            default: System.out.println("Invalid direction");
                     break;
        }

         return false;
     }

    public boolean isShoot() {
        return Shoot;
    }

    //TODO : Add tests
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

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int Direction) {
        this.Direction = Direction;
    }

    public Cell[][] getSupposedCells() {
        return supposedCells;
    }

    public Cell[][] getKnownCells() {
        return knownCells;
    }
}
