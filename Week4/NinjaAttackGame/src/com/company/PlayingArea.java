package com.company;

import java.util.List;

public class PlayingArea {
    private List<Ninja> ninjas;
    private Dice dice;
    private int roundCount;

    public PlayingArea(List<Ninja> ninjas){
        this.ninjas = ninjas;
        this.dice = new Dice();
        roundCount = 1;
    }

    private boolean CheckIfAnyNinjaDead(List<Ninja> ninjas){
        for (Ninja ninja : ninjas){
            if (ninja.getHealth() <= 0)
                return false;
        }

        return true;
    }

    private Ninja CheckWhichNinjaIsDead(List<Ninja> ninjas){
        if (ninjas.get(0).getHealth() <= 0)
            return ninjas.get(0);
        else
            return ninjas.get(1);
    }



    private Ninja GetAttackingNinja(List<Ninja> ninjas){
        //it can be draw , or one winner

       if (ninjas.get(0).getDiceRollPoint() > ninjas.get(1).getDiceRollPoint())
           return ninjas.get(0);
       else if(ninjas.get(0).getDiceRollPoint() < ninjas.get(1).getDiceRollPoint())
           return ninjas.get(1);
       else
           return null;
    }



    public void Fight() throws InterruptedException {

        System.out.println("Let the fight begin ...");

        //Thread.sleep(3000);
        System.out.println();

        while(CheckIfAnyNinjaDead(ninjas)){
            System.out.println("ROUND "+roundCount+ " FIGHT");
            roundCount++;

            //Thread.sleep(3000);
            System.out.println();

            System.out.println("Rolling dice ...");
            dice.Roll(ninjas);

            //Thread.sleep(3000);
            System.out.println();

            List<Ninja> tempArray = ninjas;



            Ninja attackingNinja = GetAttackingNinja(ninjas);

            if (attackingNinja==null){
                System.out.println("This round is DRAW");
                System.out.println("---------------------------------------------");
                //Thread.sleep(3000);
                continue;
            }

            else{
                System.out.println("Attacking turn : " + attackingNinja.getName());
                tempArray.remove(attackingNinja);
                Ninja defendingNinja = tempArray.get(0);
                System.out.println("DEFEND YOURSELF , " + defendingNinja.getName());

                System.out.println();
                //Thread.sleep(2000);

                tempArray.add(attackingNinja);

                attackingNinja.Attack(defendingNinja);
                //Thread.sleep(3000);

            }

            System.out.println("---------------------------------------------");
        }

        Ninja deadNinja = CheckWhichNinjaIsDead(ninjas);
        System.out.println("RIP to the "+deadNinja.getName());

        //Thread.sleep(2000);

        ninjas.remove(deadNinja);
        Ninja winnerNinja = ninjas.get(0);
        System.out.println(winnerNinja.getName().toUpperCase()+" WINS");
        System.out.println("FATALITY");

        //Thread.sleep(3000);
    }
}
