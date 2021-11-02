package com.company.InheritanceProblem;

public class Prism extends ThreeDimensionShape{
    private double side1;
    private double side2;
    private double side3;

    public Prism(double side1,double side2,double side3){
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    @Override
    public void GetInfosAboutShape() {
        System.out.println("Shape : "+this.getClass().getName().replace("com.company.InheritanceProblem.",""));
        System.out.println("Volume : "+calculateVolume());
        System.out.println("Area : "+calculateArea());
        System.out.println("Perimeter : "+calculatePerimeter());
    }

    @Override
    public double calculateVolume() {
        return side1*side2*side3;
    }

    @Override
    public double calculateArea() {
        return 2 * ((side1*side2) + (side1*side3) + (side2*side3));
    }

    @Override
    public double calculatePerimeter() {
        return 8 * (side1+side2+side3);
    }
}
