package com.uca.capas.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.domain.Employee;

public interface EmployeeService {
	List<Employee> getEmployees() throws DataAccessException;
	
	Employee getEmployee(Integer code) throws DataAccessException;
	
	void save(Employee emp) throws DataAccessException;
	
	void delete(Employee emp) throws DataAccessException;
}
