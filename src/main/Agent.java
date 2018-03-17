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
    private SuspiciousCell[][] suspicious;
    private ArrayList<Cell> knownCases;

    public Agent(Point p) {
        super(p);
        
        this.Direction = "up";
        this.Shoot = true;

        this.suspicious = new SuspiciousCell[11][11];

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

    public void setShoot(boolean Shoot) {
        this.Shoot = Shoot;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    public SuspiciousCell[][] getSuspicious() {
        return suspicious;
    }

    public void setDirection(SuspiciousCell[][] suspicious) {
        this.suspicious = suspicious;
    }

    public ArrayList<Cell> getKnownCases() {
        return knownCases;
    }

    public void setKnownCases(ArrayList<Cell> knownCases) {
        this.knownCases = knownCases;
    }

}
