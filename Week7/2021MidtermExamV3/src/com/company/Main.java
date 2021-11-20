package com.company;

public class Main {

    public static void main(String[] args) {
        TossManager manager = new TossManager();
        manager.Pay(new AdultGuest());
        manager.Pay(new AdultGuest());

        manager.Pay(new AdultSubscriber());
        manager.Pay(new AdultSubscriber());
        manager.Pay(new AdultSubscriber());

        manager.Pay(new ChildGuest());
        manager.Pay(new ChildGuest());

        manager.Pay(new ChildSubscriber());

        manager.print();
    }
}
