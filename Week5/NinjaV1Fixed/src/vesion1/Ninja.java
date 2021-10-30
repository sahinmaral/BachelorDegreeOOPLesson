package vesion1;

import java.util.Random;

public class Ninja {

    private int healthPoint = 3;
    private int attackPoint = 1;
    private String name;

    @Override
    public String toString() {

        String info = "\n\n Name: " + name;
        info += "\n Healt Point: " + healthPoint;
        info += "\n Attack Point: " + attackPoint;
        return info;

    }

    public Ninja(String name) {

        this.name = name;

    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
    }

    public String getName() {
        return name;
    }

    public boolean Attack(Ninja attackingNinja, Ninja enemyNinja) {
        Random r = new Random();
        if (r.nextInt(1, 100) >= 50) {
            return !(enemyNinja.Defend(enemyNinja, attackingNinja));
        }
        System.out.println(attackingNinja.getName() + "'s attack missed");
        return false;
    }
    private boolean Defend(Ninja defendingNinja, Ninja enemyNinja) {
        Random r = new Random();
        if (r.nextInt(1, 100) >= 50) {
            System.out.println(enemyNinja.getName() + "'s attack successfully defended");
            return true;
        }
        defendingNinja.healthPoint = defendingNinja.healthPoint - defendingNinja.attackPoint;
        return false;
    }
}
