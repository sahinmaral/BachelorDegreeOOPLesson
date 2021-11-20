package com.company;

public class ChildGuest extends Guest{

    @Override
    public int getPrice() {
        return super.getPrice()/2;
    }
}
