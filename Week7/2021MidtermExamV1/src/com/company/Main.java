package com.company;

public class Main {

    public static void main(String[] args) {
        Bishop bishop1 = new Bishop();

        DarkSide darkSide1 = new DarkSide();
        DarkSide darkSide2 = new DarkSide();

        WhiteSide whiteSide1 = new WhiteSide();

        bishop1.sides.add(darkSide1);
        bishop1.sides.add(whiteSide1);

        Knight knight1 = new Knight();
        knight1.TakeAPeace(bishop1);
        knight1.ValidateMove();
        knight1.Move();
    }
}
