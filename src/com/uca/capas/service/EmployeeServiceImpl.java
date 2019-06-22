package com.uca.capas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uca.capas.domain.Employee;
import com.uca.capas.domain.Store;
import com.uca.capas.dto.EmployeeDTO;
import com.uca.capas.repository.EmployeeRepository;
import com.uca.capas.repository.StoreRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	StoreRepository storeRepo;
	
	@Override
	public List<Employee> getEmployees() throws DataAccessException {
		return empRepo.findAll();
	}

	@Override
	public EmployeeDTO getEmployee(Integer code) throws DataAccessException {
		Optional<Employee> empo = empRepo.findById(code);
		if (empo.isPresent()) {
			Employee emp = empo.get();
			return new EmployeeDTO(emp.getCode(), emp.getName(), emp.getAge(),
					emp.getGender(),emp.isStatus(), emp.getStore().getCode());
		} else return null;
	}

	@Override
	public void save(EmployeeDTO empdto) throws DataAccessException {
		Store store = storeRepo.getOne(empdto.getStoreId());
		Employee emp = new Employee(empdto.getCode(), empdto.getName(), empdto.getAge(),
				empdto.getGender(), empdto.isStatus(), store);
		empRepo.save(emp);
	}

	@Override
	@Transactional
	public void delete(Integer code) throws DataAccessException {
		empRepo.deleteById(code);
	}

}
