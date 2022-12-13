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
    
    public GuessBean (){
        initGame();
    }
    
    public void initGame (){
        Random rnd =  new Random();
        rightNumber = rnd.nextInt(100);
        nrGuesses = 0;
    }
    
    public boolean guess(int guessNr){
        lastGuess = guessNr;
        nrGuesses++;
        return guessNr == rightNumber;
    }

    public boolean lastGuessLessThan(){
        return lastGuess < rightNumber;
    }

    public int getNrGuesses() {
        return nrGuesses;
    }

    public int getRightNumber() {
        return rightNumber;
    }
}
