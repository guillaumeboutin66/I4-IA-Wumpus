import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Azuro on 14/12/2017.
 */
public class ComputedDecision {

    Random randomPeer = new Random();
    Case[][] knownCases = new Case[10][10];
    Case[][] tableau = new Case[10][10];
    int positionX =  0;
    int positionY = tableau[0].length;

    /*
    private Direction TakeRandomDecision(ArrayList<SafeLevel> levels){
        int rand =  randomPeer.nextInt(4);
        return Direction.values()[rand];
    }*/

    public Direction ComputedDecision(){



        SafeLevel caseUp = CheckIfCaseIsSafe(positionX, positionY+1);
        SafeLevel caseDown = CheckIfCaseIsSafe(positionX, positionY-1);
        SafeLevel caseRight = CheckIfCaseIsSafe(positionX+1, positionY);
        SafeLevel caseLeft = CheckIfCaseIsSafe(positionX-1, positionY);





        //return TakeRandomDecision();
    }

    public SafeLevel CheckIfCaseIsSafe(int x, int y){
        if(x < -1){ //Si on sait qu'on sort du tableau connu
            return SafeLevel.notReachable;
        }

        if(y > tableau[0].length){ //Si on sait qu'on sort du tableau connu
            return  SafeLevel.notReachable;
        }
        try{//Le try permet de s'assurer que si on tape dans
            Case tmp = knownCases[x][knownCases[0].length -y];
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
