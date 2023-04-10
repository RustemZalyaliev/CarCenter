package com.example.carcenter.pojo;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Component
public class Company {

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    @NotEmpty
    @Size(min = 1)
    private List<Department> departments;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "\n_____________________________________________________________________________________________________________________________________________\n" +
                "\nCompany \"" +
                name + '\"' +
                ", departments:\n" + departments;
    }

}
