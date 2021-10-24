package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.List;
import java.util.Random;

public class PlayingArea {
    private Dustbin dustbin;
    private List<Player> Players;
    private int totalScore;

    public PlayingArea(Dustbin dustbin , List<Player> players) {
        this.dustbin = dustbin;
        Players = players;
        totalScore = 0;
    }

    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        Players = players;
    }

    public Dustbin getDustbin() {
        return dustbin;
    }

    public void setDustbin(Dustbin dustbin) {
        this.dustbin = dustbin;
    }

    private boolean CheckIfDustbinCapacityIsReached(){
        if (totalScore >= dustbin.getCapacity())
            return true;
        else
            return false;
    }

    public void TryBasket(){
        while(!CheckIfDustbinCapacityIsReached()){
            for(Player player : Players){
                if (CheckIfDustbinCapacityIsReached())
                    break;

                Random rand = new Random();
                int score = rand.nextInt(0,2);
                player.setScore(player.getScore()+score);
                totalScore += score;
            }
        }


        System.out.println("----- Player score ------");
        for (Player player : Players){
            System.out.println(player.getName() + " : " + player.getScore());
        }
    }
}
