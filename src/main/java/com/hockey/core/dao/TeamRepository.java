package com.hockey.core.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hockey.core.model.Team;


// This will be AUTO IMPLEMENTED by Spring into a Bean called teamRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
	@Modifying
	@Query (
			value = "truncate table team",
			nativeQuery = true
			)
	void truncateTeam();

}
