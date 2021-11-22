package com.hockey.core.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hockey.core.model.Division;

// This will be AUTO IMPLEMENTED by Spring into a Bean called divisionRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface DivisionRepository extends CrudRepository<Division, Integer> {
	@Modifying
	@Query (
			value = "truncate table division",
			nativeQuery = true
			)
	void truncateDivision();

}