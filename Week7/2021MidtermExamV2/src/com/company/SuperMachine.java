package com.company;

public class SuperMachine extends Machine{
    public SuperMachine(){
        System.out.println("Hello , this is SuperMachine");
    }

    public void go(int x){
        System.out.println("Hello!");
        super.go(x+1);
    }

    public String toString(){
        return "Super Machine " + super.toString();
    }

    public void SuperMethod(){
        System.out.println("Super METHOD");
    }
}
