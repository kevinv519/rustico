package com.uca.capas.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.domain.Employee;
import com.uca.capas.dto.EmployeeDTO;

public interface EmployeeService {
	List<Employee> getEmployees() throws DataAccessException;
	
	EmployeeDTO getEmployee(Integer code) throws DataAccessException;
	
	void save(EmployeeDTO empdto) throws DataAccessException;
	
	void delete(Integer code) throws DataAccessException;
}
