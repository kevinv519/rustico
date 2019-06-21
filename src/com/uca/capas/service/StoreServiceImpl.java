package com.uca.capas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uca.capas.domain.Store;
import com.uca.capas.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreRepository storeRepo;
	
	@Override
	public List<Store> getStoresList() throws DataAccessException {
		return storeRepo.findAll(Sort.by("name"));
	}

	@Override
	@Transactional
	public void save(Store store) throws DataAccessException {
		storeRepo.save(store);		
	}

	@Override
	public void delete(Store store) throws DataAccessException {
		storeRepo.delete(store);
	}

	@Override
	public Store getStore(Integer code) throws DataAccessException {
		Optional<Store> store = storeRepo.findById(code);
		return store.isPresent()? store.get() : null;
	}
	
}
