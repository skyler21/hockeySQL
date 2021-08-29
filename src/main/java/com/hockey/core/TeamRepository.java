package com.hockey.core;

import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called teamRepository
// CRUD refers Create, Read, Update, Delete
public interface TeamRepository extends CrudRepository<Team, Integer> {

}
