package com.company.InheritanceProblem;

public class Cylinder extends ThreeDimensionShape{

    private double radius;
    private double height;

    public Cylinder(double radius,double height){
        this.radius = radius;
        this.height = height;
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
        return Math.PI * Math.pow(radius,2) * height;
    }

    @Override
    public double calculateArea() {
        return 2 * Math.PI * radius * (radius+height);
    }

    @Override
    public double calculatePerimeter() {
        return (8 * Math.PI * radius) + (2 * height);
    }
}
