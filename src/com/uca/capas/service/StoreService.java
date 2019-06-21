package com.uca.capas.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.domain.Store;

public interface StoreService {
	List<Store> getStoresList() throws DataAccessException;
	
	Store getStore(Integer code) throws DataAccessException;
	
	Store getStoreWithEmployees(Integer code) throws DataAccessException;
	
	void save(Store store) throws DataAccessException;
	
	void delete(Store store) throws DataAccessException;
	
}
