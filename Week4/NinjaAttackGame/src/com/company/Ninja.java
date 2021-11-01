package com.company;

public class Ninja {
    private String name;
    private int health;
    private int attackPoint;
    private int diceRollPoint;
    private final int maxPercentAttack = 100;
    private int minPercentAttack;

    public Ninja(String name) {
        this.name = name;
        health = 10;
        attackPoint = 1;
        minPercentAttack = 50;
    }

    public int getMinPercentAttack() {
        return minPercentAttack;
    }

    public void setMinPercentAttack(int minPercentAttack) {
        this.minPercentAttack = minPercentAttack;
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

    public boolean Defense(Ninja enemyNinja) {
        int randomNumber = (int) ((Math.random() * enemyNinja.maxPercentAttack - enemyNinja.minPercentAttack + 1) + enemyNinja.minPercentAttack);
        System.out.println(randomNumber);
        if (randomNumber > enemyNinja.minPercentAttack){
            enemyNinja.minPercentAttack += 1;
            return true;
        }
        else{
            return false;
        }
    }

    public void Attack(Ninja enemyNinja) {
        if (!enemyNinja.Defense(enemyNinja)) {
            if (this.minPercentAttack != 70) {
                this.minPercentAttack += 1;
            }

            System.out.println("Successfull hit from " + enemyNinja.getName() + " by " + enemyNinja.getAttackPoint() + " hit");
            enemyNinja.setHealth(enemyNinja.getHealth() - getAttackPoint());
        } else {
            System.out.println("Missed , maybe next time " + enemyNinja.getName());
        }


    }

    public String getName() {
        return name;
    }
}
