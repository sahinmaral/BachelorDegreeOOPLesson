package com.company;

import java.util.List;

public class Dice {
    public void Roll(List<Ninja> ninjas){
        for(Ninja ninja : ninjas){
            int randomNumber = (int)Math.floor(Math.random()*(100)+1);
            ninja.setDiceRollPoint(randomNumber);
        }
    }
}
