package classes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import javax.ejb.Stateful;

/**
 *
 * @author ali
 */
@Stateful
public class GuessBean {
    private int rightNumber;
    private int nrGuesses;
    private int lastGuess;
    private boolean isLastGuessRight;
    
    public GuessBean (){
        initGame();
    }
    
    public void initGame (){
        Random rnd =  new Random();
        rightNumber = rnd.nextInt(100);
        nrGuesses = 0;
        isLastGuessRight = false;
    }
    
    public void guess(int guessNr){
        if (isLastGuessRight){
            initGame(); // start new game if last guess was right
        }
        lastGuess = guessNr;
        nrGuesses++;
        isLastGuessRight = lastGuess == rightNumber;
    }
    
    public String generateLastGuessMsg (){
        if(this.nrGuesses < 1) return "No guess made yet";
        if (this.isLastGuessRight) return "The number " + this.lastGuess + " is Correct!";
        return lastGuessLessThan()?"The number is larger than last guess":"The number is smaller then last guess";
    }

    public boolean lastGuessLessThan(){
        if (this.nrGuesses < 1) return true;
        return lastGuess < rightNumber;
    }

    public int getNrGuesses() {
        return nrGuesses;
    }

    public int getRightNumber() {
        return rightNumber;
    }

    public int getLastGuess() {
        if (this.nrGuesses < 1) return -1;
        return lastGuess;
    }

    public boolean getIsLastGuessRight() {
        return isLastGuessRight;
    }  
}
