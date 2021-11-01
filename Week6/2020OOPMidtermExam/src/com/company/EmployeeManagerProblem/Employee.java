package com.company.EmployeeManagerProblem;

public class Employee {
    private String name;
    private String surname;
    private Employee manager;

    public Employee(String name,String surname){
        this.name = name;
        this.surname = surname;
    }

    public Employee(String name,String surname,Employee manager){
        this.name = name;
        this.surname = surname;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
