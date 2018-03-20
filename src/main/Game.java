package main;


import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import main.Direction;

class Game {
    //initialisation de la map

    public static void main(String[] args) throws InterruptedException {
        int weigh = 10;
        int height = 10;
        GameMap map = new GameMap(10,10);
        int tourCount = 0;
        boolean fin = false;
        System.out.println("Wumpus Game");

        map.generate();
        Agent agent = map.getAgent();

        //boucle de jeu principale
        while (!fin) {
            //L'agent choisit une action
            ComputedDecision compute = new ComputedDecision(map.getCells(),agent.getKnownCells(),agent.position);
            Direction[] directions = compute.takeDecision();
            // tant qu'il reste une direction à prendre on avance
            for(Direction direction : directions){
               ArrayList<String> actions = agent.allerA(direction);
//               String nextAction = actions.get(0);
               boolean legal = true;
               for(String action :actions){
                       if(("walk".equals(action))){
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
               if(!fin){
                   agent.addKnownAndSupposedCells(map.getAgent());
               }
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
        if(nextCellCoord.x>map.getWidth()-1||
                nextCellCoord.x<0||
                nextCellCoord.y>map.getLength()-1||
                nextCellCoord.y<0){
            valid = false;
        }
        return valid;
    }

    public static Point simuleDeplacement(Agent agent){

        Point similusPosition = new Point();
        similusPosition.setLocation(agent.position);
        Direction currentDirection = agent.getDirection();

        switch(currentDirection){

            case right: similusPosition.x +=1;
                break;
            case up: similusPosition.y -=1;
                break;
            case left: similusPosition.x -=1;
                break;
            case down: similusPosition.y +=1;
                break;
            default: System.out.println("Invalid direction");
                break;
        }
        return similusPosition;
    }

    public static boolean checkEnd(GameMap map){
        boolean end = false;
        System.out.println(map.getAgent().toString());
        if(map.getAgent().getEvents().contains(Cell.Event.wumpus)||map.getAgent().getEvents().contains(Cell.Event.pit)) {
            end = true;
        }
        return end;
    }
}