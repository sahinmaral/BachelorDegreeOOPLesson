package com.company.InheritanceProblem;

public class Rectangle extends TwoDimensionShape{
    private double side1;
    private double side2;

    public Rectangle(double side1,double side2){
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public double calculateArea() {
        return side1 *side2;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (side1+side2);
    }


    public double getSide2() {
        return side2;
    }

    public void setSide2(double side2) {
        this.side2 = side2;
    }

    public double getSide1() {
        return side1;
    }

    public void setSide1(double side1) {
        this.side1 = side1;
    }

    @Override
    public void GetInfosAboutShape() {
        System.out.println("Shape : "+this.getClass().getName().replace("com.company.InheritanceProblem.",""));
        System.out.println("Area : "+calculateArea());
        System.out.println("Perimeter : "+calculatePerimeter());
    }
}
