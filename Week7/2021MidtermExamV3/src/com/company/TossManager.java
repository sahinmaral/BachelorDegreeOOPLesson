package com.company;

public class TossManager {

    String[] customers;
    Integer[] counter;
    public int totalDailyPrice = 0;

    public TossManager(){
        customers = new String[4];
        counter = new Integer[4];

        customers[0] = "AdultGuest";
        customers[1] = "AdultSubscriber";
        customers[2] = "ChildGuest";
        customers[3] = "ChildSubscriber";

        counter[0] = 0;
        counter[1] = 0;
        counter[2] = 0;
        counter[3] = 0;
    }

    public void Pay(Customer customer){
       switch (customer.getClass().getName()){
           case("com.company.AdultGuest"):{
                counter[0]++;
                totalDailyPrice+=customer.getPrice();
                break;
           }
           case("com.company.AdultSubscriber"):{
               counter[1]++;
               totalDailyPrice+=customer.getPrice();
               break;
           }
           case("com.company.ChildGuest"):{
               counter[2]++;
               totalDailyPrice+=customer.getPrice();
               break;
           }
           case("com.company.ChildSubscriber"):{
               counter[3]++;
               totalDailyPrice+=customer.getPrice();
               break;
           }
       }
    }

    public void print(){
        System.out.println("totalDailyPrice : "+totalDailyPrice);

        for(int i=0;i<4;i++){
            System.out.println(customers[i] + " : " + counter[i]);
        }
    }
}
