package com.example.carcenter.persistence;

import javax.persistence.*;

@Entity
@Table(name = "Employees")
public class EmployeeH2 {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long employeeId;
    private String lastName;
    private String firstName;
    private int age;
    private int salary;
    @Column(name = "Department")
    private String dep;
    private String company;

    public EmployeeH2() {
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long id) {
        this.employeeId = id;
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

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
