package com.company;

import com.company.CircleProblem.IntersectingCircle;
import com.company.DiceProblem.Dice;
import com.company.InheritanceProblem.Circle;
import com.company.InheritanceProblem.Cube;
import com.company.InheritanceProblem.Rectangle;
import com.company.InheritanceProblem.Square;

public class Main {

    public static void main(String[] args) {
        //DiceRoll();
        //CircleIntersection();

        InheritanceShapeProblem();
    }

    private static void InheritanceShapeProblem() {
        Square square1 = new Square(5);
        Rectangle rectangle1 = new Rectangle(3,4);
        Circle circle1 = new Circle(5);
        Cube cube1 = new Cube(7);


        square1.GetInfosAboutShape();

        System.out.println();

        rectangle1.GetInfosAboutShape();

        System.out.println();

        circle1.GetInfosAboutShape();

        System.out.println();

        cube1.GetInfosAboutShape();
    }

    private static void CircleIntersection() {
        IntersectingCircle intersectingCircle1 = new IntersectingCircle(1,15,3);
        IntersectingCircle circle2 = new IntersectingCircle(1,15,3);

        if (intersectingCircle1.IsIntersectWithAnotherCircle(circle2))
            System.out.println("Intersects");
        else
            System.out.println("Does not intersect");
    }

    private static void DiceRoll() {
        Dice dice = new Dice();

        for (int i = 0;i <= 3000000;i++){
            dice.roll();
        }

        dice.report();
    }
}
