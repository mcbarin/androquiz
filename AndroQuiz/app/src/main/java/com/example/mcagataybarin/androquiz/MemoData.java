package com.example.mcagataybarin.androquiz;


import com.example.mcagataybarin.androquiz.Models.LifePoint;
import com.example.mcagataybarin.androquiz.Models.Score;
import com.example.mcagataybarin.androquiz.Models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by mcagataybarin on 3/26/17.
 */

public class MemoData {
    private User currentUser;
    public Score score;
    public LifePoint lifePoint;
    public ArrayList<ArrayList<String>> target_flags;
    public ArrayList<ArrayList<String>> flag_list;

    public ArrayList<Integer> clickableCells;

    private static final MemoData ourInstance = new MemoData();

    public static MemoData getInstance() {
        return ourInstance;
    }

    private MemoData() {
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /*
        * Initializes the Memory Game's data.
        * */
    public void initialize(){
        this.score = new Score();
        this.lifePoint = new LifePoint();
        this.initialize_flags();
        clickableCells = new ArrayList<>();
    }

    /*
    * This function initializes the info about memory game.
    * */
    public void initialize_flags(){
        target_flags = new ArrayList<ArrayList<String>>();
        flag_list = new ArrayList<ArrayList<String>>();

        // For level 1;
        target_flags.add(new ArrayList<String>());
        flag_list.add(new ArrayList<String>());
        String[] level1_flags = getFlagNames(12);

        // First 4 of the flags are the target flags
        for (int i=0;i<4;i++) {
            target_flags.get(0).add(level1_flags[i]);
            flag_list.get(0).add(level1_flags[i]);
            flag_list.get(0).add(level1_flags[i]);
        }
        for (int i=4; i<12; i++)
            flag_list.get(0).add(level1_flags[i]);

        // Shuffle it.
        Collections.shuffle(target_flags.get(0));
        Collections.shuffle(flag_list.get(0));


        // For level 2;
        target_flags.add(new ArrayList<String>());
        flag_list.add(new ArrayList<String>());
        String[] level2_flags = getFlagNames(20);

        // First 5 of the flags are the target flags.
        for (int i=0; i<5; i++){
            target_flags.get(1).add(level2_flags[i]);
            flag_list.get(1).add(level2_flags[i]);
            flag_list.get(1).add(level2_flags[i]);
        }

        for (int i=5;i<20;i++){
            flag_list.get(1).add(level2_flags[i]);
        }

        Collections.shuffle(target_flags.get(1));
        Collections.shuffle(flag_list.get(1));


        // For level 3;
        target_flags.add(new ArrayList<String>());
        flag_list.add(new ArrayList<String>());
        String[] level3_flags = getFlagNames(30);

        for (int i=0; i<6; i++){
            target_flags.get(2).add(level3_flags[i]);
            flag_list.get(2).add(level3_flags[i]);
            flag_list.get(2).add(level3_flags[i]);
        }

        for (int i=6; i<30; i++){
            flag_list.get(2).add(level3_flags[i]);
        }

        Collections.shuffle(target_flags.get(2));
        Collections.shuffle(flag_list.get(2));

    }

    /*
    * Returns distinct flag filenames for given count number.
    * */
    public String[] getFlagNames(int count){
        String[] flagNames = new String[count];
        int[] randomNumbers = this.getRandomNumbersInRange(count);

        for(int i=0;i<count;i++){
            flagNames[i] = "img" + ""+randomNumbers[i] + ".png";
        }
        return flagNames;
    }

    /*
    * Returns distinct numbers in range for given count number.
    * */
    private int[] getRandomNumbersInRange(int count){
        int[] numbers = new int[count];
        Random random = new Random();
        int range=223;
        int i=0;
        while (i != count){
            int newNumber = random.nextInt(range);
            if (!isDuplicate(numbers, i, newNumber)){
                numbers[i] = newNumber;
                i++;
            }
        }
        return numbers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /*
        * Checks if a given array contains the given number.
        * */
    private boolean isDuplicate(int[] numbers, int count, int newNumber){
        for (int i=0; i<count; i++){

            if (numbers[i] == newNumber) return true;
        }
        return false;
    }

    public ArrayList<String> getTargetFlags(int level){
        return this.target_flags.get(level-1);
    }

    public ArrayList<String> getFlagList(int level){
        return this.flag_list.get(level-1);
    }

    public boolean isCellClickable(int position){
        return !clickableCells.contains(position);
    }

    public void setCellUnclickable(int position){
        clickableCells.add(position);
    }

    public boolean isFlagMatched(int position){
        return this.clickableCells.contains(position);
    }

    public void levelUp(){
        this.clickableCells.clear();
    }
}
