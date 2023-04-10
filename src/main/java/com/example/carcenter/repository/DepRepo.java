package com.example.carcenter.repository;

import com.example.carcenter.persistence.DepartmentH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepRepo extends CrudRepository<DepartmentH2, String> {
}
