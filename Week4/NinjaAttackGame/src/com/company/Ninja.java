package com.company;

public class Ninja {
    private String name;
    private int health;
    private int attackPoint;
    private int diceRollPoint;
    private final int maxPercentAttack = 100;
    private int minPercentAttack = 50;

    public Ninja(String name){
        this.name = name;
        health = 10;
        attackPoint = 1;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
    }

    public int getDiceRollPoint() {
        return diceRollPoint;
    }

    public void setDiceRollPoint(int diceRollPoint) {
        this.diceRollPoint = diceRollPoint;
    }

    public boolean Defense(){
        int randomNumber = (int) ((Math.random() * maxPercentAttack-minPercentAttack+1) + minPercentAttack);
        System.out.println(randomNumber);
        return randomNumber > 50 ? true : false;
    }

    public void Attack(Ninja enemyNinja){
        if (!enemyNinja.Defense()){
            System.out.println("Successfull hit from "+enemyNinja.getName() + " by "+enemyNinja.getAttackPoint() + " hit");
            enemyNinja.setHealth(enemyNinja.getHealth()-getAttackPoint());
        }
        else {
            System.out.println("Missed , maybe next time "+enemyNinja.getName());
        }


    }

    public String getName() {
        return name;
    }
}
