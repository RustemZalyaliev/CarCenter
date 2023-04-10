package com.example.carcenter.persistence;

import com.example.carcenter.pojo.Position;

import javax.persistence.*;

@Entity
@Table(name = "Department")
public class DepartmentH2 {

    @Id
    @Column(name = "ID")
    private String depID;
    private String name;
    @Column(name = "Company")
    String companyName;
    private Position position;
    @Column(name = "EmployeesNumber")
    private int employeesNum;
    @Column(name = "MinimumSalary")
    private int minSalary;
    @Column(name = "MaximumPremium")
    private int deltaSalary;

    public DepartmentH2() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String name) {
        this.depID = name;
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

}
