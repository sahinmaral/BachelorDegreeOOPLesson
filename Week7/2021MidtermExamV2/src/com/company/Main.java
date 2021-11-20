package com.company;

public class Main {

    public static void main(String[] args) {
        Machine x = new SuperMachine();

        x.go(9);    //Your number is 9
        x.go();       //I am going!

        System.out.println(x.toString()); //Machine

        //We instanced Machine not SuperMachine.
        //If we have to access SuperMachine , we need to unbox this variable like that

        SuperMachine superMachine = ((SuperMachine) x);
        ((SuperMachine) x).SuperMethod();
    }
}


