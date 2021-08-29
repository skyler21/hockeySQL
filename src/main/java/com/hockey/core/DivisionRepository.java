package com.hockey.core;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called divisionRepository
// CRUD refers Create, Read, Update, Delete

public interface DivisionRepository extends CrudRepository<Division, Integer> {

}