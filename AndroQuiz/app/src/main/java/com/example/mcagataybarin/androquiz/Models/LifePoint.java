package com.example.mcagataybarin.androquiz.Models;

/**
 * Created by mcagataybarin on 3/26/17.
 */

public class LifePoint {
    private int maxLife = 5;
    private int remainingLife = maxLife;

    public int getMaxLife() {
        return maxLife;
    }

    public int getRemainingLife() {
        return remainingLife;
    }

    public boolean decrementRemainingLife() {
        this.remainingLife -= 1;
        return isFailed();
    }

    public boolean isFailed(){
        return this.remainingLife == 0;
    }
}
