package com.company.EmployeeManagerProblem;

import java.util.List;

public class Manager extends Employee{
    private List<Employee> employeeList;

    public Manager(String name, String surname) {
        super(name, surname);
    }

    public Manager(String name, String surname,Manager manager) {
        super(name, surname,manager);
    }

    public Manager(String name, String surname,Manager manager,List<Employee> employeeList) {
        super(name, surname,manager);
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    public void removeEmployee(Employee employee){
        employeeList.remove(employee);
    }
}
