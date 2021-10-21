package com.company;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        //If id of the elevator or dedicated flat number of elevator is less than zero , it throws exception
        /*
        try {
            Elevator elevator = new Elevator(-1,1,"Otis","Elevonic 401");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* try {
            Elevator elevator = new Elevator(2,-1,"Otis","Elevonic 401");
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //If elevator count of the apartment is not between 1 and 4 , it throws exception
       /*
        try {
            Apartment apartment = new Apartment(new ArrayList<Elevator>());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        List<Elevator> elevators = new ArrayList<Elevator>();

        try {
            elevators.add(new Elevator(10,1,"Otis","Elevonic 401"));
            elevators.add(new Elevator(10,2,"Otis","Autotronic"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Apartment apartment = new Apartment(elevators);
        Elevator searchedElevator = apartment.getElevatorByElevatorId(1);

        //Current floor is zero and elevator is coming from down
        System.out.println(searchedElevator.IsElevatorBusy(2));

        searchedElevator.setCurrentFloor(3);
        //Current floor is three and elevator is coming from up
        System.out.println(searchedElevator.IsElevatorBusy(2));

        searchedElevator.setCurrentFloor(2);
        //Current floor is same as our current floor and door will be opening
        System.out.println(searchedElevator.IsElevatorBusy(2));
    }
}
