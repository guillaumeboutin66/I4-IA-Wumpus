package main;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {


    Random randomPeer = new Random();
    Cell[][] knownCases;
    Cell[][] map;
    Point playerPosition;

    public ComputedDecision(Cell[][] map, Cell[][] knownCases, Point playerPosition){
        this.map = map;
        this.knownCases = knownCases;
        this.playerPosition = playerPosition;
    }

    private Direction TakeRandomDecision(){
        int rand =  randomPeer.nextInt(4);
        return Direction.values()[rand];
    }

    public Direction ComputedDecision(){

        SafeLevel caseUp = CheckIfCaseIsSafe(playerPosition.x, playerPosition.y+1);
        SafeLevel caseDown = CheckIfCaseIsSafe(playerPosition.x, playerPosition.y-1);
        SafeLevel caseRight = CheckIfCaseIsSafe(playerPosition.x+1, playerPosition.y);
        SafeLevel caseLeft = CheckIfCaseIsSafe(playerPosition.x-1, playerPosition.y);

        return TakeRandomDecision();
    }


    public SafeLevel CheckIfCaseIsSafe(int x, int y){
        if(x < -1){ //Si on sait qu'on sort du map connu
            return SafeLevel.notReachable;
        }

        if(y > map[0].length){ //Si on sait qu'on sort du map connu
            return  SafeLevel.notReachable;
        }
        try{//Le try permet de s'assurer que si on tape dans
            Cell tmp = knownCases[x][knownCases[0].length -y];
            if(tmp!= null){
                if(tmp.getEvents().size() == 0){
                    return SafeLevel.safe;
                }else{
                    return SafeLevel.unsafe;
                }
            }
            return SafeLevel.unknown;
        }catch (Exception ex){
            return  SafeLevel.unknown;
        }
    }



    public enum Direction{
        up,
        down,
        right,
        left
    }

    public enum SafeLevel{
        safe,
        unsafe,
        unknown,
        notReachable
    }


}
