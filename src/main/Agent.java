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
    private Direction direction;
    private Cell[][] knownCells;
    private Cell[][] supposedCells;
    private Cell lastCell;

    public Agent(Point p, int weight, int height) {
        super(p);
        
        this.direction = Direction.up;
        this.Shoot = true;

        knownCells = new Cell[weight][height];
        supposedCells = new Cell[weight][height];
    }

    public void Walk() {
    
        Direction CurrentDirection = this.direction;
        
        switch(CurrentDirection){
            
            case right: this.position.x +=1;
                     break;
            case up: this.position.y -=1;
                      break;
            case left: this.position.x -=1;
                     break;
            case down: this.position.y +=1;
                     break;
            default: System.out.println("Invalid direction"); 
                     break;
        }    
    }

    public Agent move(String action) {
        if (action.equals("turn_right")) {
            Turn(false);
        } else if (action.equals("turn_left")) {
            Turn(true);
        } else if (action.equals("walk")) {
            Walk();
        }
        return this;
    }

    public ArrayList<String> allerA(Direction action) {
        ArrayList<String> listeActions = new ArrayList<>();
        if (action.equals(Direction.right)) {
            switch (this.direction) {
                case right:
                    listeActions.add("walk");
                    break;
                case up:
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case left:
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case down:
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals(Direction.left)){
            switch (this.direction) {
                case left:
                    listeActions.add("walk");
                    break;
                case down:
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case right:
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case up:
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals(Direction.up)){
            switch (this.direction) {
                case up:
                    listeActions.add("walk");
                    break;
                case left:
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case down:
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case right:
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }else
        if(action.equals(Direction.down)){
            switch (this.direction) {
                case down:
                    listeActions.add("walk");
                    break;
                case right:
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case up:
                    listeActions.add("turn_right");
                    listeActions.add("turn_right");
                    listeActions.add("walk");
                    break;
                case left:
                    listeActions.add("turn_left");
                    listeActions.add("walk");
                    break;

            }
        }
        return listeActions;
    }



    public void Turn(boolean TurnAction){
        
        Direction InitialDirection = this.getDirection();

        //Turn Left
        if (TurnAction == true) {
            
            if(InitialDirection == Direction.down){

                this.setDirection(Direction.right);

            }
            else{
                if(InitialDirection == Direction.right){

                    this.setDirection(Direction.up);

                }else{

                    if(InitialDirection == Direction.up){

                        this.setDirection(Direction.left);

                    }else{

                        this.setDirection(Direction.down);

                    }
                }
            }
        }
        else /*if(TurnAction == false)*/ {

            if(InitialDirection == Direction.down){

                this.setDirection(Direction.left);

            }
            else{
                if(InitialDirection == Direction.left){

                    this.setDirection(Direction.up);

                }else{

                    if(InitialDirection == Direction.up){

                        this.setDirection(Direction.right);

                    }else{

                        this.setDirection(Direction.down);

                    }
                }
            }       
        }    
    }

     public boolean UseWeapon(Cell monster) {
         
        Direction CurrentDirection = this.direction;
        
        switch(CurrentDirection){
            
            case right:   if(this.position.y == monster.position.y && monster.position.x > this.position.x){ this.setShoot(false); return true;};
                     break;
            case up:  if(this.position.x == monster.position.x && monster.position.y > this.position.y){ this.setShoot(false); return true;};
                     break;
            case left: if(this.position.y == monster.position.y && monster.position.x < this.position.x){ this.setShoot(false); return true;};
                     break;
            case down: if(this.position.x == monster.position.x && monster.position.y < this.position.y){ this.setShoot(false); return true;};
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
        Cell cell = new Cell(new Point(currentCell.position.x, currentCell.position.y));
        knownCells[currentCell.position.x][currentCell.position.y] = cell;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Cell[][] getSupposedCells() {
        return supposedCells;
    }

    public ArrayList<Point> getSupposedCellsToList() {
        ArrayList<Point> list = new ArrayList<Point>();
        for(int i=1;i<supposedCells.length; i++){
            for(int j=0;j<supposedCells[0].length; j++){
                if(supposedCells[i][j] != null && (supposedCells[i][j].position.x != this.position.x && supposedCells[i][j].position.y != this.position.y )){
                    list.add(new Point(supposedCells[i][j].position.x, supposedCells[i][j].position.y));   
                }
            }
        }
        
        return list;
    }

    
    public Cell[][] getKnownCells() {
        return knownCells;
    }
    
}
