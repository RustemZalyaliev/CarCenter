package com.example.carcenter.pojo;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;
    private Position position;
    private int employeesNum;
    private int minSalary;
    private int deltaSalary;
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getEmployeesNum() {
        return employeesNum;
    }

    public void setEmployeesNum(int employeesNum) {
        this.employeesNum = employeesNum;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getDeltaSalary() {
        return deltaSalary;
    }

    public void setDeltaSalary(int deltaSalary) {
        this.deltaSalary = deltaSalary;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "\n\n" + name +
                ", position - " + position +
                ", employees in the department - " + employeesNum +
                ", minimal salary - " + minSalary +
                " rubles, maximum premium - " + (deltaSalary - 1) +
                " rubles, employee list:\n" + employees;
    }

}
