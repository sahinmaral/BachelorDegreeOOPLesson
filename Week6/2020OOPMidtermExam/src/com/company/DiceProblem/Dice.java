package com.company.DiceProblem;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Random;

public class Dice {
    private HashMap<Integer, Double> diceRolledPoints;
    private HashMap<Integer,Integer> totalDiceRolledPoints;

    public Dice() {
        diceRolledPoints = new HashMap<Integer,Double>();
        diceRolledPoints.put(1,0.05);
        diceRolledPoints.put(2,0.15);
        diceRolledPoints.put(3,0.35);
        diceRolledPoints.put(4,0.4);
        diceRolledPoints.put(5,0.45);
        diceRolledPoints.put(6,0.5);
        diceRolledPoints.put(7,0.55);
        diceRolledPoints.put(8,0.6);
        diceRolledPoints.put(9,0.7);
        diceRolledPoints.put(10,1.0);

        totalDiceRolledPoints = new HashMap<Integer,Integer>();
        totalDiceRolledPoints.put(1,0);
        totalDiceRolledPoints.put(2,0);
        totalDiceRolledPoints.put(3,0);
        totalDiceRolledPoints.put(4,0);
        totalDiceRolledPoints.put(5,0);
        totalDiceRolledPoints.put(6,0);
        totalDiceRolledPoints.put(7,0);
        totalDiceRolledPoints.put(8,0);
        totalDiceRolledPoints.put(9,0);
        totalDiceRolledPoints.put(10,0);
    }

    private int findKeyFromValue(double rolledPoint){

        if (rolledPoint > 0 && rolledPoint <= 0.5)
            return 1;
        else if (rolledPoint > 0.5 && rolledPoint <= 0.15)
            return 2;
        else if (rolledPoint > 0.15 && rolledPoint <= 0.35)
            return 3;
        else if (rolledPoint > 0.35 && rolledPoint <= 0.4)
            return 4;
        else if (rolledPoint > 0.4 && rolledPoint <= 0.45)
            return 5;
        else if (rolledPoint > 0.45 && rolledPoint <= 0.5)
            return 6;
        else if (rolledPoint > 0.50 && rolledPoint <= 0.55)
            return 7;
        else if (rolledPoint > 0.55 && rolledPoint <= 0.6)
            return 8;
        else if (rolledPoint > 0.6 && rolledPoint <= 0.7)
            return 9;
        else if (rolledPoint > 0.7 && rolledPoint <= 1.0)
            return 10;

        return 0;
    }

    public void roll(){
        Random random = new Random();
        double rolledPoint = random.nextDouble(0,1);

        int key = findKeyFromValue(rolledPoint);
        totalDiceRolledPoints.put(key,totalDiceRolledPoints.get(key)+1);

    }

    public void report(){
        System.out.println("Total dice rolled points");

        for(int i = 1;i<=10;i++){
            System.out.println(i + "->" + totalDiceRolledPoints.get(i));
        }
    }


}
