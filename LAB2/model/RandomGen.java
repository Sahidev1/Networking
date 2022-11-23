package model;

import java.util.Random;

public class RandomGen {
    private final static RandomGen singleRandom = new RandomGen();
    private Random rnd;

    private RandomGen (){
        rnd = new Random(System.currentTimeMillis());
    }

    public static RandomGen getRandomGen (){
        return singleRandom;
    }
    
    public int genRandom(int bound){
        return rnd.nextInt(bound);
    }
}
