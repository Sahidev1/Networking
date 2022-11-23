package model;

import java.util.Random;

public class GuessingGame {
    private int rightNumber;
    private int nrGuesses;
    private int lastGuess;

    public GuessingGame (){
        RandomGen rnd = RandomGen.getRandomGen();
        rightNumber = rnd.genRandom(100);
        nrGuesses = 0;
    }

    public boolean guess(int guessNr){
        lastGuess = guessNr;
        return guessNr == rightNumber;
    }

    public boolean lastGuessLessThan(){
        return lastGuess < rightNumber;
    }

    public int getNrGuesses() {
        return nrGuesses;
    }
}
