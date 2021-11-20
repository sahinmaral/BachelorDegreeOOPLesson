package com.company;

public class Machine {
    public Machine(){
        System.out.println("Hello , this is Machine");
    }

    public void go(){
        System.out.println("I am going!");
    }

    public void go(int y){
        System.out.println("Your number is "+y);
    }

    @Override
    public String toString(){
        return "Machine";
    }
}
