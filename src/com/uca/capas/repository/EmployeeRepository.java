package com.uca.capas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uca.capas.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
