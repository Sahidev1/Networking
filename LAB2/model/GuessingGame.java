package model;

import util.CookieGenerator;

public class GuessingGame {
    private int rightNumber;
    private int nrGuesses;
    private int lastGuess;
    private String cookie;

    public GuessingGame (long threadId){
        initGame();
        cookie = CookieGenerator.genCookie(threadId);
    }

    public void initGame (){
        RandomGen rnd = RandomGen.getRandomGen();
        rightNumber = rnd.genRandom(100);
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

    public String getCookie() {
        return cookie;
    }

    public int getRightNumber() {
        return rightNumber;
    }
}
