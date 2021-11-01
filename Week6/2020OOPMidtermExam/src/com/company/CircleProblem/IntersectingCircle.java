package com.company.CircleProblem;

public class IntersectingCircle {
    private double xCoordination;
    private double yCoordination;
    private double radius;

    public IntersectingCircle(double xCoordination, double yCoordination, double radius) {
        this.xCoordination = xCoordination;
        this.yCoordination = yCoordination;
        this.radius = radius;
    }

    public boolean IsIntersectWithAnotherCircle(IntersectingCircle secondCircle){
        double substanceCirclesXCoordinates = this.xCoordination - secondCircle.xCoordination;
        double substanceCirclesYCoordinates = this.yCoordination - secondCircle.yCoordination;

        double absoluteRadius = Math.abs(this.radius - secondCircle.radius);

        double distance = Math.sqrt(Math.pow(substanceCirclesXCoordinates,2) + Math.pow(substanceCirclesYCoordinates,2));

        if (this.radius + secondCircle.radius < distance)
            return false;
        else if (absoluteRadius > distance)
            return false;
        else if(distance == 0 && this.radius == secondCircle.getRadius()){
            System.out.println("Same circles , that's why infinite intersection points");
            return true;
        }



        return true;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getyCoordination() {
        return yCoordination;
    }

    public void setyCoordination(double yCoordination) {
        this.yCoordination = yCoordination;
    }

    public double getxCoordination() {
        return xCoordination;
    }

    public void setxCoordination(double xCoordination) {
        this.xCoordination = xCoordination;
    }
}


