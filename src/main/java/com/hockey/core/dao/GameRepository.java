package com.hockey.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hockey.core.model.Game;


// This will be AUTO IMPLEMENTED by Spring into a Bean called conferenceRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
	@Modifying
	@Query (
			value = "truncate table game",
			nativeQuery = true
			)
	void truncateGame();

//	@Modifying
//	@Query("update game u set u.teamAwayP1 = ?1, u.teamAwayP2 = ?2, teamAwayP3 = ?3, teamAwayOT = ?4, teamAwayTotal = ?5, teamHomeP1 = ?6, , teamHomeP2 = ?7, , teamHomeP3 = ?8, teamHomeOT = ?9, teamHomTotal = ?10 where u.PkId = ?11")
//  void setScoresById(Integer teamAwayP1, Integer teamAwayP2, Integer teamAwayP3, Integer teamAwayOT, Integer teamAwayTotal, Integer teamHomeP1, Integer teamHomeP2, 
//	 Integer teamHomeP3, Integer teamHomeOT , Integer teamHomeTotal, Integer PkId);
      
     @Query(
    		 value = "select * from game WHERE game_pk = ?1", 
    		 nativeQuery = true
    	   )
	 Game findByGame(int pkid);

     @Query(
    		 value = "select * from game WHERE game_date like ?1", 
    		 nativeQuery = true
    	   )
	 List<Game> findByGameDate(String pkdate);

}
