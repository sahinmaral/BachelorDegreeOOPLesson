package com.company;

import java.security.spec.InvalidParameterSpecException;

public class Elevator {
    private int id;
    private String brand;
    private String model;
    private int dedicatedFloorNumber;
    private int currentFloorNumber;

    private static final int maxFloorNumber = 15;
    private static final int minFloorNumber = 1;
    private int currentPassengerCount;

    public Elevator(int dedicatedFloorNumber,int id,String brand,String model) throws Exception {
        if (dedicatedFloorNumber <= minFloorNumber || dedicatedFloorNumber >= maxFloorNumber)
            throw new InvalidParameterSpecException("Flat number must be between " + minFloorNumber + " and "+maxFloorNumber);

        if (id < 0)
            throw new IllegalArgumentException("Id must be greater than zero");
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.dedicatedFloorNumber = dedicatedFloorNumber;
        currentPassengerCount = 0;
        currentFloorNumber = 1;
    }

    public int getCurrentFloor() {
        return currentFloorNumber;
    }

    public void setCurrentFloor(int currentFloorNumber) {
        this.currentFloorNumber = currentFloorNumber;
    }

    public String IsElevatorBusy(int waitingFloorNumber){
        if (waitingFloorNumber > currentFloorNumber) return "Elevator is coming from down , wait please";
        else if (waitingFloorNumber < currentFloorNumber) return "Elevator is coming from up , wait please";
        else return "Elevator is on your floor , opening doors";
    }

    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    public void setCurrentPassengerCount(int currentPassengerCount) {
        this.currentPassengerCount = currentPassengerCount;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }
}
