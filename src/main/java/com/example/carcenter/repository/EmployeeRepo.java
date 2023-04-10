package com.example.carcenter.repository;

import com.example.carcenter.persistence.EmployeeH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends CrudRepository<EmployeeH2, Long> {
}
