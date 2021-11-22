package com.hockey.core.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hockey.core.model.Conference;


// This will be AUTO IMPLEMENTED by Spring into a Bean called conferenceRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ConferenceRepository extends CrudRepository<Conference, Integer> {

	@Modifying
	@Query (
			value = "truncate table conference",
			nativeQuery = true
			)
	void truncateConference();
	
}
