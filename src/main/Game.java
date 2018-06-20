package main;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Game {

    private static InterfaceGame interfaceGame = null;
    private static int weigh = 10;
    private static int height = 10;

    //initialisation de la map
    public static void main(String[] args) throws InterruptedException {
        int tourCount = 0;
        boolean fin = false;

        GameMap map = new GameMap(height,weigh);

        System.out.println("Wumpus Game");

        interfaceGame = new InterfaceGame(map);
        Agent agent = map.getAgent();
        agent.addKnownAndSupposedCells(map.getAgent());

        // Search the best Path to go to the Gold
        searchShortPath(map);

        //boucle de jeu principale
        while (!fin) {

            //L'agent choisit une action
            ComputedDecision compute = new ComputedDecision(map,agent.getKnownCells(),agent.position);
            Direction[] directions = compute.takeDecision();

            // tant qu'il reste une direction à prendre on avance
            for(Direction direction : directions){
               ArrayList<String> actions = agent.allerA(direction);
               boolean legal = true;

               for(String action :actions){
                   if(("walk".equals(action))){
                        legal = isLocationValid(agent,map);
                   }
                   //dire au joueur qu'il a heurté un mur
                   if(legal){
                        map.getCells()[agent.position.x][agent.position.y].removeEvent(Cell.Event.agent);
                        map.refreshAgent(agent.move(action));
                        agent.addKnownAndSupposedCells(map.getAgent());
                       tourCount++;
                   }
                   TimeUnit.SECONDS.sleep(1);
                   interfaceGame.refresh(map, true);

               }
               fin = checkEnd(map);
                //check condition de sortie
                if (fin) {
                    interfaceGame.endGame(map);
                    System.out.println("Nombre de tours : "+tourCount);
                    break;
                }
            }
        }
    }

    // Check if we do the movement, if the location is possible (in the matrix)
    private static boolean isLocationValid(Agent agent,GameMap map){
        boolean valid = true;
        Point nextCellCoord = simulateMovement(agent);
        if(nextCellCoord.x>map.getWidth()-1||
                nextCellCoord.x<0||
                nextCellCoord.y>map.getLength()-1||
                nextCellCoord.y<0){
            valid = false;
        }
        return valid;
    }

    // Simu
    private static Point simulateMovement(Agent agent){
        Point simulateLocation = new Point();
        simulateLocation.setLocation(agent.position);
        Direction currentDirection = agent.getDirection();

        switch(currentDirection){
            case right: simulateLocation.x +=1;
                break;
            case up: simulateLocation.y -=1;
                break;
            case left: simulateLocation.x -=1;
                break;
            case down: simulateLocation.y +=1;
                break;
            default: System.out.println("Invalid direction");
                break;
        }
        return simulateLocation;
    }

    // Check if Dead
    private static boolean checkEnd(GameMap map){
        boolean end = false;
        System.out.println(map.getAgent().toString());
        if(map.getAgent().getEvents().contains(Cell.Event.wumpus)||map.getAgent().getEvents().contains(Cell.Event.pit)) {
            end = true;
        }
        return end;
    }

    // Search the best Path to go to the Gold
    private static void searchShortPath(GameMap map){
        Cell[][] mapCells = map.getCells();
        for(int i = 0; i < mapCells.length; i++) {
            for (int j = 0; j < mapCells[i].length; j++) {
                for (Cell.Event event : mapCells[j][i].getEvents()) {
                    if (event == Cell.Event.gold) {
                        List<Point> result2 = AlgoA.getSolution(weigh, height, 0, 9, j, i, map.getDangerousPoints());
                        System.out.print(result2);
                        interfaceGame.setBestSoluce(result2);
                    }
                }
            }
        }
    }
}