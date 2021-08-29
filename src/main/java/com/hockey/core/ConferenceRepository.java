package com.hockey.core;

import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called conferenceRepository
// CRUD refers Create, Read, Update, Delete
public interface ConferenceRepository extends CrudRepository<Conference, Integer> {

}
