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
    public ComputedDecision(GameMap map){
        this.map = map.getCells();
        this.knownCases = map.getAgent().getSuspicious();
        this.playerPosition = map.getAgent().position;
    }


    public ArrayList<String> takeDecision(){
        return goTo(BasicComputedDecision());
    } 

    private SuspiciousCell BasicComputedDecision(){
        ArrayList<SuspiciousCell> cells = new ArrayList<SuspiciousCell>();
        /*cells.add(knownCases[playerPosition.x][playerPosition.y+1]);
        cells.add(knownCases[playerPosition.x+1][playerPosition.y]);
        if(playerPosition.y!=0) {
            cells.add(knownCases[playerPosition.x][playerPosition.y - 1]);
        }
        if(playerPosition.x!=0) {
            cells.add(knownCases[playerPosition.x - 1][playerPosition.y]);
        }
        Collections.shuffle(cells);*/
        cells.add(new SuspiciousCell(new Point(9,0),0));
        SuspiciousCell choosedCell = cells.get(0);

        /* On priorise les cases non découvertes */
        for (SuspiciousCell cell : cells) {
            if(cell.safeLevel ==
                    SuspiciousCell.SafeLevel.unknown){
                if(choosedCell.safeLevel != SuspiciousCell.SafeLevel.unknown){
                    choosedCell = cell;
                }else if(cell.danger < choosedCell.danger){
                    choosedCell = cell;
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

    private ArrayList<String> goTo(SuspiciousCell cell){
        ArrayList<String> direction = new ArrayList<>();
        if(cell.position.y < playerPosition.y){
            direction.add(Direction.up);
        }else if(cell.position.y > playerPosition.y){
            direction.add(Direction.down);
        }else if(cell.position.x > playerPosition.x){
            direction.add(Direction.right);
        }else{
            direction.add(Direction.left);
        }
        return direction;
    }
}
