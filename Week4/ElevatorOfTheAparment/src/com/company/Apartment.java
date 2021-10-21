package com.company;

import java.security.InvalidParameterException;
import java.util.List;

public class Apartment {
    private List<Elevator> elevators;

    private static final int minElevatorCount = 1;
    private static final int maxElevatorCount = 4;

    public Apartment(List<Elevator> elevators) {
        if (elevators.size() < minElevatorCount || elevators.size() > maxElevatorCount)
            throw new InvalidParameterException("Elevator count of apartment must be between "+minElevatorCount+" and "+maxElevatorCount);

        this.elevators = elevators;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public Elevator getElevatorByElevatorId(int id){
        for(Elevator searchedElevator : getElevators()){
            if (searchedElevator.getId() == id) return searchedElevator;
        }

        return null;
    }
}
