package com.example.carcenter.repository;

import com.example.carcenter.persistence.CompanyH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends CrudRepository<CompanyH2, String> {
}
