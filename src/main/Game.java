package main;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import main.Direction;

class Game {

    private static InterfaceGame interfaceGame = null;

    //initialisation de la map
    public static void main(String[] args) throws InterruptedException {
        int weigh = 10;
        int height = 10;
        GameMap map = new GameMap(10,10);
        int tourCount = 0;
        boolean fin = false;
        System.out.println("Wumpus Game");

        interfaceGame = new InterfaceGame(map);
        Agent agent = map.getAgent();
        agent.addKnownAndSupposedCells(map.getAgent());

        testShortPath(map);

        //boucle de jeu principale
        while (!fin) {
            //L'agent choisit une action
            ComputedDecision compute = new ComputedDecision(map.getCells(),agent.getKnownCells(),agent.position);
            Direction[] directions = compute.takeDecision();
            // tant qu'il reste une direction à prendre on avance
            for(Direction direction : directions){
               ArrayList<String> actions = agent.allerA(direction);
               boolean legal = true;
               for(String action :actions){
                   if(("walk".equals(action))){
                       legal = isDeplacementValid(agent,map);
                   } //dire au joueur qu'il a heurté un mur
                   if(legal){
                       map.getCells()[agent.position.x][agent.position.y].removeEvent(Cell.Event.agent);
                       map.refreshAgent(agent.move(action));
                       agent.addKnownAndSupposedCells(map.getAgent());

                   }
                   TimeUnit.SECONDS.sleep(1);
                   interfaceGame.refresh(map, true);
               }
               fin = checkEnd(map);
            }
            //check condition de sortie
            if (fin) {
                interfaceGame.endGame(map);
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

    private static void testShortPath(GameMap map){
        AlgoA aa = new AlgoA();
        Cell[][] mycells = map.getCells();
        for(int i = 0; i < mycells.length; i++) {
            for (int j = 0; j < mycells[i].length; j++) {
                for (Cell.Event event : mycells[j][i].getEvents()) {
                    if (event == Cell.Event.gold) {
                        List<Point> result2 = aa.getSolution(10, 0, 9, j, i, map.getDangerousPoints());
                        System.out.print(result2);
                        interfaceGame.setBestSoluce(result2);
                    }
                }
            }
        }
    }
}