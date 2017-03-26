package com.example.mcagataybarin.androquiz;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mcagataybarin on 3/26/17.
 */

class MemoData {
    private User currentUser;
    public Score score;
    public LifePoint lifePoint;

    private static final MemoData ourInstance = new MemoData();

    static MemoData getInstance() {
        return ourInstance;
    }

    private MemoData() {
    }

    public void initialize(User user){
        this.score = new Score();
        this.lifePoint = new LifePoint();
        this.currentUser = user;
    }


    public String[] getFlagNames(int count){
        String[] flagNames = new String[count];
        int[] randomNumbers = this.getRandomNumbersInRange(count);

        for(int i=0;i<count;i++){
            flagNames[i] = "MemoGameFlags/img" + ""+randomNumbers[i] + ".png";
        }
        return flagNames;
    }

    public int[] getRandomNumbersInRange(int count){
        int[] numbers = new int[count];
        Random random = new Random();
        int range=223;
        int i=0;
        while (i != count){
            int newNumber = random.nextInt(range);
            if (!isDuplicate(numbers, i, newNumber)){
                numbers[i] = newNumber;
                i++;
                break;
            }
        }
        return numbers;
    }

    public boolean isDuplicate(int[] numbers, int count, int newNumber){
        for (int i=0; i<count; i++){
            if (numbers[i] == newNumber) return true;
        }
        return false;
    }


}
