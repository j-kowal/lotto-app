package javaapp;

/**
 *
 * @author Jan Kowal x15009939
 */

// import
import java.io.Serializable;


public class JavaWinner implements Serializable{
    // variables 
    private String name;
    private int totalWin;
   
    // constructor
    public JavaWinner(String name, int totalWin){
        this.name=name;
        this.totalWin=totalWin;
    }
    // name getter
    public String getName(){
        return name;
    }
    // totalWin getter
    public int getWinning(){
        return totalWin;
    }
}
