package main;


import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class Game {
    //initialisation de la map

    public static void main(String[] args) throws InterruptedException {
        GameMap map = new GameMap(10,10);
        int tourCount = 0;
        boolean fin = false;
        System.out.println("Wumpus Game");

        map.generate();
        Agent agent = map.getAgent();

        //boucle de jeu principale
        while (!fin) {
            //L'agent choisit une action
            ComputedDecision compute = new ComputedDecision(map);
            ArrayList<String> directions = compute.takeDecision();
            // tant qu'il reste une direction à prendre on avance
            for(String direction : directions){
               ArrayList<String> actions = agent.allerA(direction);
               String nextAction = actions.get(0);
               boolean legal = true;
               for(String action :actions){
                       if(("walk".equals(nextAction))){
                           legal = isDeplacementValid(agent,map);
                       } //dire au joueur qu'il a heurté un mur
                   if(legal){
                       map.getCells()[agent.position.x][agent.position.y].removeEvent(Cell.Event.agent);
                       agent.move(action);
                       map.refreshAgent(agent);
                       map.generate();
                   }
                   TimeUnit.SECONDS.sleep(1);
               }
               fin = checkEnd(map);
            }
            //check condition de sortie
            if (fin) {
                //map.endAnimation();
            }
        }
    }

    public static boolean isDeplacementValid(Agent agent,GameMap map){
        boolean valid = true;
            Point nextCellCoord = simuleDeplacement(agent);
            if(nextCellCoord.x>map.getWidth()||
                    nextCellCoord.x<0||
                    nextCellCoord.y>map.getLength()||
                    nextCellCoord.y<0){
                valid = false;
            }
            return valid;
    }

    public static Point simuleDeplacement(Agent agent){

        Point similusPosition = new Point();
        similusPosition.setLocation(agent.position);
        String CurrentDirection = agent.getDirection();

        switch(CurrentDirection){

            case "right": similusPosition.x +=1;
                break;
            case "up": similusPosition.y +=1;
                break;
            case "left": similusPosition.x -=1;
                break;
            case "down": similusPosition.y -=1;
                break;
            default: System.out.println("Invalid direction");
                break;
        }
        return similusPosition;
    }

    public static boolean checkEnd(GameMap map){
        boolean end = false;
        ArrayList<Cell> deads = map.getPits();
        deads.add(map.getWumpus());
        for(Cell dead: deads){
            if(map.getAgent().position.equals(dead.position)){
                end = true;
            }
        }
        return end;
    }
}