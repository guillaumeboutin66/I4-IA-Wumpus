package main;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {

    Random randomPeer = new Random();
    SuspiciousCell[][] knownCases;
    Cell[][] map;
    Point playerPosition;

    //SuspiciousCell
    public ComputedDecision(Cell[][] map, SuspiciousCell[][] knownCases, Point playerPosition){
        this.map = map;
        this.knownCases = knownCases;
        this.playerPosition = playerPosition;
    }


    public Direction[] takeDecision(){
        //return RandomDecision();
        return goTo(BasicComputedDecision());
    } 

    private SuspiciousCell BasicComputedDecision(){
        ArrayList<SuspiciousCell> cells = new ArrayList<SuspiciousCell>();
        cells.add(knownCases[playerPosition.x][playerPosition.y+1]);
        cells.add(knownCases[playerPosition.x][playerPosition.y-1]);
        cells.add(knownCases[playerPosition.x+1][playerPosition.y]);
        cells.add(knownCases[playerPosition.x-1][playerPosition.y]);
        Collections.shuffle(cells);

        SuspiciousCell choosedCell = cells.get(0);

        /* On priorise les cases non découvertes */
        for (SuspiciousCell cell : cells) {
            if(cell.safeLevel == SuspiciousCell.SafeLevel.unknown){
                if(choosedCell.safeLevel != SuspiciousCell.SafeLevel.unknown){
                    cell = choosedCell;
                }else if(cell.danger < choosedCell.danger){
                    cell = choosedCell;
                }
            }
        }

        if(choosedCell.safeLevel == SuspiciousCell.SafeLevel.unknown){
            return choosedCell; //On a trouvé une celle où aller
        }

        /* On a pas trouvé une case inconnu autour de soit */
        for (SuspiciousCell cell : cells) {
            if(cell.safeLevel == SuspiciousCell.SafeLevel.safe){
                return cell;
            }
        }

        for (SuspiciousCell cell : cells) {
            if(cell.safeLevel == SuspiciousCell.SafeLevel.unsafe){
                return cell;
            }
        }

        return choosedCell;
    }

    private Direction[] goTo(SuspiciousCell cell){
        ArrayList<Direction> direction = new ArrayList<Direction>();
        if(cell.position.y > playerPosition.y){
            direction.add(Direction.up);
        }else if(cell.position.y < playerPosition.y){
            direction.add(Direction.down);
        }else if(cell.position.x > playerPosition.x){
            direction.add(Direction.right);
        }else{
            direction.add(Direction.left);
        }

        return direction.toArray(new Direction[direction.size()]);
    }

    private Direction[] RandomDecision(){
        int rand =  randomPeer.nextInt(4);
        Direction[] directions = new Direction[1];
        directions[0] = Direction.values()[rand];
        return directions;
    }

    /*
    public ArrayList<Direction> ComputedDecision(){

        SafeLevel caseUp = CheckIfCaseIsSafe(playerPosition.x, playerPosition.y+1);
        SafeLevel caseDown = CheckIfCaseIsSafe(playerPosition.x, playerPosition.y-1);
        SafeLevel caseRight = CheckIfCaseIsSafe(playerPosition.x+1, playerPosition.y);
        SafeLevel caseLeft = CheckIfCaseIsSafe(playerPosition.x-1, playerPosition.y);

        return TakeRandomDecision();
    }

    private ArrayList<Direction> TakeBasicDecision(SafeLevel[] safeLevels){
        ArrayList<SafeLevel> safe = new ArrayList<SafeLevel>();
        ArrayList<SafeLevel> unknown = new ArrayList<SafeLevel>();

        
        for (int i = 0; i < safeLevels.length; i++) {
            
        }
        SafeLevel caseUp = CheckIfCaseIsSafe(positionX, positionY+1);
        SafeLevel caseDown = CheckIfCaseIsSafe(positionX, positionY-1);
        SafeLevel caseRight = CheckIfCaseIsSafe(positionX+1, positionY);
        SafeLevel caseLeft = CheckIfCaseIsSafe(positionX-1, positionY);

        return null;
        //return TakeRandomDecision();
    }

    public SafeLevel CheckIfCaseIsSafe(int x, int y){
        if(x < -1){ //Si on sait qu'on sort du map connu
            return new SafeLevel(SafeLevel.Level.notReachable, x, y);
        }

        if(y > map[0].length){ //Si on sait qu'on sort du map connu
            return new SafeLevel(SafeLevel.Level.notReachable, x, y);
        }

        try{ //Le try permet de s'assurer que si on tape dans
            Cell tmp = knownCases[x][knownCases[0].length -y];
            if(tmp!= null){
                if(tmp.getEvents().size() == 0){
                    return new SafeLevel(SafeLevel.Level.safe, x, y);
                }else{
                    return new SafeLevel(SafeLevel.Level.unsafe, x, y);
                }
            }
            return new SafeLevel(SafeLevel.Level.notReachable, x, y);
        }catch (Exception ex){
            return new SafeLevel(SafeLevel.Level.unknown, x, y);
        }
    }*/

    public enum Direction{
        up,
        down,
        right,
        left
    }

}
