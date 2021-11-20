package com.company;

import java.util.ArrayList;

public class Character {

    ArrayList<Side> sides = new ArrayList<Side>();
    public String SideColor;
    public int defaultLocationX;
    public int defaultLocationY;
    public String description;
    private String name;

    public String getName(){
        return this.getClass().getName().substring(12);
    }

    public void Move(){
        System.out.println(this.getName() + " moved");
    }

    public void ValidateMove(){
        System.out.println(this.getName() + " validated to move");
    }

    public void TakeAPeace(Character character){
        System.out.println("Took peace with "+character.getName());
    }
}
