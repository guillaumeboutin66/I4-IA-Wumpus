package testagent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Erik
 */
public class Agent extends Cell {

    private boolean Shoot;
    private int Direction;

    public Agent(int x, int y, int danger) {
        super(x, y, danger);
        
        this.Direction = 90;
        this.Shoot = true;

    }
    
    public void Walk() {
    
        int CurrentDirection = this.Direction;
        
        switch(CurrentDirection){
            
            case 0: this.x +=1;
                     break;
            case 90:  this.y +=1;
                      break;
            case 180: this.x -=1;
                     break;
            case 270: this.y -=1;
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
            
            case 0:   if(this.y == monster.y && monster.x > this.x){ this.setShoot(false); return true;};
                     break;
            case 90:  if(this.x == monster.x && monster.y > this.y){ this.setShoot(false); return true;};
                     break;
            case 180: if(this.y == monster.y && monster.x < this.x){ this.setShoot(false); return true;};
                     break;
            case 270: if(this.x == monster.x && monster.y < this.y){ this.setShoot(false); return true;};
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

    public int getDirection() {
        return Direction;
    }

    public void setDirection(int Direction) {
        this.Direction = Direction;
    }
   

}
