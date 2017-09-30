package com.algonquincollege.hass0302.hilogame;
import java.util.Random;

/**
 * Created by damian on 2017-09-27.
 */

public class hilow {
    final int maximum = 1000;
    final int minimum = 1;
    private int guessNum;
    private int counter;
    private boolean winFlag;

    public void init () {
        this.guessNum = -1;
        this.counter = 0;
        this.winFlag = false;
        setNum();
    }

    public void setNum () {
        Random rn = new Random();
        int range = maximum - minimum + 1;
        int randomNum =  rn.nextInt(range) + minimum;

     //   System.out.println ( randomNum);
        this.guessNum = randomNum;
    }

    public int getNum () {
        return guessNum;
    }

    public void setCounter () {
        counter++;
    }

    public int getCounter () {
        return counter;
    }

    public boolean getWinFlag () {
        return this.winFlag;
    }

    public String getHiLow (int inputNum) {
        if (inputNum > this.guessNum) {
            return "too high";
        } else if (inputNum < this.guessNum) {
            return "too low";
        } else {
            this.winFlag = true;

            if (getCounter() <= 5)
                return "Superior Win!";
            else
                return "Excellent win!";
        }
    }
}
