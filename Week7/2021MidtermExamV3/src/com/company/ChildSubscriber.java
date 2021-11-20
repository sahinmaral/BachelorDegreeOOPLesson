package com.company;

public class ChildSubscriber extends Subscriber {
    @Override
    public int getPrice() {
        return super.getPrice()/2;
    }
}
