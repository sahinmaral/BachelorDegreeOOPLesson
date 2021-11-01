package com.company.InheritanceProblem;

public class Cube extends ThreeDimensionShape{

    private double side1;

    public Cube(double side1){
        this.side1 = side1;
    }

    @Override
    public void GetInfosAboutShape() {
        System.out.println("Shape : "+this.getClass().getName().replace("com.company.InheritanceProblem.",""));
        System.out.println("Volume : "+calculateVolume());
        System.out.println("Area : "+calculateArea());
        System.out.println("Perimeter : "+calculatePerimeter());
    }

    @Override
    public double calculateArea() {
        return 6 * Math.pow(side1,2);
    }

    @Override
    public double calculatePerimeter() {
        return 12 * Math.pow(side1,4);
    }

    @Override
    public double calculateVolume() {
        return Math.pow(side1,3);
    }
}
