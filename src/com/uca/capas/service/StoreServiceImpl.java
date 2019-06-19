package com.uca.capas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.uca.capas.domain.Store;
import com.uca.capas.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreRepository storeRepo;
	
	@Override
	public List<Store> getStoresList() throws DataAccessException {
		return storeRepo.findAll();
	}

	@Override
	public void save(Store store) throws DataAccessException {
		storeRepo.save(store);		
	}

	@Override
	public void delete(Store store) throws DataAccessException {
		storeRepo.delete(store);
	}

	@Override
	public Store getStore(Integer code) throws DataAccessException {
		return storeRepo.findById(code).get();
	}
	
}
