package com.company;

import java.util.List;
import java.util.Random;

public class PlayingArea {
    private Dustbin dustbin;
    private List<Player> Players;

    public PlayingArea(Dustbin dustbin , List<Player> players) {
        this.dustbin = dustbin;
        Players = players;
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

    public void TryBasket(){
        for (int i = 1; i <= dustbin.getCapacity(); i++){
            for(Player player : Players){
                Random rand = new Random();
                int score = rand.nextInt(0,2);
                player.setScore(player.getScore()+score);
            }
        }


        System.out.println("----- Player score ------");
        for (Player player : Players){
            System.out.println(player.getName() + " : " + player.getScore());
        }
    }
}
