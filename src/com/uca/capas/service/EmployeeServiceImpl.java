package com.uca.capas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.uca.capas.domain.Employee;
import com.uca.capas.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepo;
	
	@Override
	public List<Employee> getEmployees() throws DataAccessException {
		return empRepo.findAll();
	}

	@Override
	public Employee getEmployee(Integer code) throws DataAccessException {
		return empRepo.findById(code).get();
	}

	@Override
	public void save(Employee emp) throws DataAccessException {
		empRepo.save(emp);
	}

	@Override
	public void delete(Employee emp) throws DataAccessException {
		empRepo.delete(emp);
	}

}
