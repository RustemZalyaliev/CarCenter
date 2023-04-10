package com.example.carcenter.pojo;

import java.util.Random;

public class Employee {

    private String firstName;
    private String lastName;
    private int age;
    private int salary;

    public Employee() {
    }

    public Employee(String firstName, String lastName, int salary) {
        Random rnd = new Random();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = rnd.nextInt(24) + 20;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "\nEmployee - " +
                firstName +
                " " + lastName +
                ", " + age +
                " years old, salary " + salary +
                " rubles.";
    }
}
