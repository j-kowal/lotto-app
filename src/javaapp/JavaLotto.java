package javaapp;

/** 
 *
 * @author Jan Kowal x15009939
 */

// imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class JavaLotto {
    //variables and objects
    private final ArrayList<Integer> lotteryResults = new ArrayList<>();
    Random random = new Random();
    
    //lottery draw section
    public void run(){
        lotteryResults.clear();
        for (int i = 0; i < 6; i++) {
            int randomNo = random.nextInt((47 - 1) + 1) + 1;
            while (lotteryResults.contains(randomNo)) {
                randomNo = random.nextInt((47 - 1) + 1) + 1;
            }
            lotteryResults.add(randomNo);
        } 
    }
    // results getter
    public ArrayList getResults(){
        Collections.sort(lotteryResults);
        return lotteryResults;
    }
    // getWinning method returning integer with winning, accordingly to well guessed numbers
    public int getWinning(int lineHits){
        switch(lineHits){
            case 3: return 50;
            case 4: return 250;
            case 5: return 25000;
            case 6: return 4000000;
            default: return 0;
        }
    }
    
}
