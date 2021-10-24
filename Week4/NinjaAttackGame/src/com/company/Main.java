package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Ninja ninja1 = new Ninja("Momochi Sandayu");
        Ninja ninja2 = new Ninja("Fuma Kotaro");

        List<Ninja> fightingNinjas = new ArrayList<Ninja>();
        fightingNinjas.add(ninja1);
        fightingNinjas.add(ninja2);

        PlayingArea playingArea = new PlayingArea(fightingNinjas);
        try {
            playingArea.Fight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
