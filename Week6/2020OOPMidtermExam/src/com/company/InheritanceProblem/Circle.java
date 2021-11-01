package com.company.InheritanceProblem;

public class Circle extends TwoDimensionShape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius,2);
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void GetInfosAboutShape() {
        System.out.println("Shape : "+this.getClass().getName().replace("com.company.InheritanceProblem.",""));
        System.out.println("Area : "+calculateArea());
        System.out.println("Perimeter : "+calculatePerimeter());
    }
}
