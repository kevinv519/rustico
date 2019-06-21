package com.uca.capas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uca.capas.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {

	@Query("SELECT s FROM Store s JOIN FETCH s.employees WHERE s.code = ?1")
	Store findStoreWithEmployees(Integer code);
}
