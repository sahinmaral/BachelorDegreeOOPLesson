package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Player player1 = new Player("sahin");
        Player player2 = new Player("mustafa-cem");

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Dustbin dustbin = new Dustbin();

        PlayingArea playingArea = new PlayingArea(dustbin,players);
        playingArea.TryBasket();

    }
}
