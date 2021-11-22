package com.hockey.core.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer gamePk;
	private String gameType;
	private String gameDate;
	private Integer teamAwayId;
	private Integer teamAwayP1;
	private Integer teamAwayP2;
	private Integer teamAwayP3;
	private Integer teamAwayOT;
	private Integer teamAwayTotal;
	private Integer teamHomeId;
	private Integer teamHomeP1;
	private Integer teamHomeP2;
	private Integer teamHomeP3;
	private Integer teamHomeOT;
	private Integer teamHomeTotal;
	private boolean gameShootout;
	private Integer teamAwayOTsog;
	
	private Integer teamAwayP1sog;
	private Integer teamAwayP2sog;
	private Integer teamAwayP3sog;
	private Integer teamHomeOTsog;
	private Integer teamHomeP1sog;
	private Integer teamHomeP2sog;
	private Integer teamHomeP3sog;
	private Integer teamAwayTOTsog;
	private Integer teamHomeTOTsog;
	
	public Game() {
	}


	public Game(Integer id, Integer gamePk, String gameType, String gameDate, Integer teamAwayId, Integer teamAwayP1,
			Integer teamAwayP2, Integer teamAwayP3, Integer teamAwayOT, Integer teamAwayTotal, Integer teamHomeId,
			Integer teamHomeP1, Integer teamHomeP2, Integer teamHomeP3, Integer teamHomeOT, Integer teamHomeTotal, Boolean gameShootout,
			Integer teamAwayOTsog, Integer teamAwayP1sog, Integer teamAwayP2sog, Integer teamAwayP3sog, Integer teamHomeOTsog, 
			Integer teamHomeP1sog, Integer teamHomeP2sog, Integer teamHomeP3sog, Integer teamAwayTOTsog, Integer teamHomeTOTsog) {
		super();
		this.id = id;
		this.gamePk = gamePk;
		this.gameType = gameType;
		this.gameDate = gameDate;
		this.teamAwayId = teamAwayId;
		this.teamAwayP1 = teamAwayP1;
		this.teamAwayP2 = teamAwayP2;
		this.teamAwayP3 = teamAwayP3;
		this.teamAwayOT = teamAwayOT;
		this.teamAwayTotal = teamAwayTotal;
		this.teamHomeId = teamHomeId;
		this.teamHomeP1 = teamHomeP1;
		this.teamHomeP2 = teamHomeP2;
		this.teamHomeP3 = teamHomeP3;
		this.teamHomeOT = teamHomeOT;
		this.teamHomeTotal = teamHomeTotal;
		this.gameShootout = gameShootout;
		this.teamAwayOTsog = teamAwayOTsog;
		this.teamAwayP1sog = teamAwayP1sog;
		this.teamAwayP2sog = teamAwayP2sog;
		this.teamAwayP3sog = teamAwayP3sog;
		this.teamHomeOTsog = teamHomeOTsog;
		this.teamHomeP1sog = teamHomeP1sog;
		this.teamHomeP2sog = teamHomeP2sog;
		this.teamHomeP3sog = teamHomeP3sog;
		this.teamAwayTOTsog = teamAwayTOTsog;
		this.teamHomeTOTsog = teamHomeTOTsog;		
	}

	

	@Override
	public String toString() {
		return "Game [id=" + id + ", gamePk=" + gamePk + ", gameType=" + gameType + ", gameDate=" + gameDate
				+ ", teamAwayId=" + teamAwayId + ", teamAwayP1=" + teamAwayP1 + ", teamAwayP2=" + teamAwayP2
				+ ", teamAwayP3=" + teamAwayP3 + ", teamAwayOT=" + teamAwayOT + ", teamAwayTotal=" + teamAwayTotal
				+ ", teamHomeId=" + teamHomeId + ", teamHomeP1=" + teamHomeP1 + ", teamHomeP2=" + teamHomeP2
				+ ", teamHomeP3=" + teamHomeP3 + ", teamHomeOT=" + teamHomeOT + ", teamHomeTotal=" + teamHomeTotal
				+ ", gameShootout=" + gameShootout + ", teamAwayOTsog=" + teamAwayOTsog + ", teamAwayP1sog="
				+ teamAwayP1sog + ", teamAwayP2sog=" + teamAwayP2sog + ", teamAwayP3sog=" + teamAwayP3sog
				+ ", teamHomeOTsog=" + teamHomeOTsog + ", teamHomeP1sog=" + teamHomeP1sog + ", teamHomeP2sog="
				+ teamHomeP2sog + ", teamHomeP3sog=" + teamHomeP3sog + ", teamAwayTOTsog=" + teamAwayTOTsog
				+ ", teamHomeTOTsog=" + teamHomeTOTsog + ", getId()=" + getId() + ", getGamePk()=" + getGamePk()
				+ ", getGameType()=" + getGameType() + ", getGameDate()=" + getGameDate() + ", getTeamAwayId()="
				+ getTeamAwayId() + ", getTeamAwayP1()=" + getTeamAwayP1() + ", getTeamAwayP2()=" + getTeamAwayP2()
				+ ", getTeamAwayP3()=" + getTeamAwayP3() + ", getTeamAwayOT()=" + getTeamAwayOT()
				+ ", getTeamAwayTotal()=" + getTeamAwayTotal() + ", getTeamHomeId()=" + getTeamHomeId()
				+ ", getTeamHomeP1()=" + getTeamHomeP1() + ", getTeamHomeP2()=" + getTeamHomeP2() + ", getTeamHomeP3()="
				+ getTeamHomeP3() + ", getTeamHomeOT()=" + getTeamHomeOT() + ", getTeamHomeTotal()="
				+ getTeamHomeTotal() + ", getGameShootout()=" + getGameShootout() + ", getTeamAwayOTsog()="
				+ getTeamAwayOTsog() + ", getTeamAwayP1sog()=" + getTeamAwayP1sog() + ", getTeamAwayP2sog()="
				+ getTeamAwayP2sog() + ", getTeamAwayP3sog()=" + getTeamAwayP3sog() + ", getTeamHomeOTsog()="
				+ getTeamHomeOTsog() + ", getTeamHomeP1sog()=" + getTeamHomeP1sog() + ", getTeamHomeP2sog()="
				+ getTeamHomeP2sog() + ", getTeamHomeP3sog()=" + getTeamHomeP3sog() + ", getTeamAwayTOTsog()="
				+ getTeamAwayTOTsog() + ", getTeamHomeTOTsog()=" + getTeamHomeTOTsog() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGamePk() {
		return gamePk;
	}

	public void setGamePk(Integer gamePk) {
		this.gamePk = gamePk;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	public Integer getTeamAwayId() {
		return teamAwayId;
	}

	public void setTeamAwayId(Integer teamAwayId) {
		this.teamAwayId = teamAwayId;
	}

	public Integer getTeamAwayP1() {
		return teamAwayP1;
	}

	public void setTeamAwayP1(Integer teamAwayP1) {
		this.teamAwayP1 = teamAwayP1;
	}

	public Integer getTeamAwayP2() {
		return teamAwayP2;
	}

	public void setTeamAwayP2(Integer teamAwayP2) {
		this.teamAwayP2 = teamAwayP2;
	}

	public Integer getTeamAwayP3() {
		return teamAwayP3;
	}

	public void setTeamAwayP3(Integer teamAwayP3) {
		this.teamAwayP3 = teamAwayP3;
	}

	public Integer getTeamAwayOT() {
		return teamAwayOT;
	}

	public void setTeamAwayOT(Integer teamAwayOT) {
		this.teamAwayOT = teamAwayOT;
	}

	public Integer getTeamAwayTotal() {
		return teamAwayTotal;
	}

	public void setTeamAwayTotal(Integer teamAwayTotal) {
		this.teamAwayTotal = teamAwayTotal;
	}

	public Integer getTeamHomeId() {
		return teamHomeId;
	}

	public void setTeamHomeId(Integer teamHomeId) {
		this.teamHomeId = teamHomeId;
	}

	public Integer getTeamHomeP1() {
		return teamHomeP1;
	}

	public void setTeamHomeP1(Integer teamHomeP1) {
		this.teamHomeP1 = teamHomeP1;
	}

	public Integer getTeamHomeP2() {
		return teamHomeP2;
	}

	public void setTeamHomeP2(Integer teamHomeP2) {
		this.teamHomeP2 = teamHomeP2;
	}

	public Integer getTeamHomeP3() {
		return teamHomeP3;
	}

	public void setTeamHomeP3(Integer teamHomeP3) {
		this.teamHomeP3 = teamHomeP3;
	}

	public Integer getTeamHomeOT() {
		return teamHomeOT;
	}

	public void setTeamHomeOT(Integer teamHomeOT) {
		this.teamHomeOT = teamHomeOT;
	}
	
	public Integer getTeamHomeTotal() {
		return teamHomeTotal;
	}

	public void setTeamHomeTotal(Integer teamHomeTotal) {
		this.teamHomeTotal = teamHomeTotal;
	}

	public Boolean getGameShootout() {
		return gameShootout;
	}

	public void setGameShootout(boolean gameShootout) {
		this.gameShootout = gameShootout;
	}
	
	public Integer getTeamAwayOTsog() {
		return teamAwayOTsog;
	}


	public void setTeamAwayOTsog(Integer teamAwayOTsog) {
		this.teamAwayOTsog = teamAwayOTsog;
	}


	public Integer getTeamAwayP1sog() {
		return teamAwayP1sog;
	}


	public void setTeamAwayP1sog(Integer teamAwayP1sog) {
		this.teamAwayP1sog = teamAwayP1sog;
	}


	public Integer getTeamAwayP2sog() {
		return teamAwayP2sog;
	}


	public void setTeamAwayP2sog(Integer teamAwayP2sog) {
		this.teamAwayP2sog = teamAwayP2sog;
	}


	public Integer getTeamAwayP3sog() {
		return teamAwayP3sog;
	}


	public void setTeamAwayP3sog(Integer teamAwayP3sog) {
		this.teamAwayP3sog = teamAwayP3sog;
	}


	public Integer getTeamHomeOTsog() {
		return teamHomeOTsog;
	}


	public void setTeamHomeOTsog(Integer teamHomeOTsog) {
		this.teamHomeOTsog = teamHomeOTsog;
	}


	public Integer getTeamHomeP1sog() {
		return teamHomeP1sog;
	}


	public void setTeamHomeP1sog(Integer teamHomeP1sog) {
		this.teamHomeP1sog = teamHomeP1sog;
	}


	public Integer getTeamHomeP2sog() {
		return teamHomeP2sog;
	}


	public void setTeamHomeP2sog(Integer teamHomeP2sog) {
		this.teamHomeP2sog = teamHomeP2sog;
	}


	public Integer getTeamHomeP3sog() {
		return teamHomeP3sog;
	}


	public void setTeamHomeP3sog(Integer teamHomeP3sog) {
		this.teamHomeP3sog = teamHomeP3sog;
	}


	public Integer getTeamAwayTOTsog() {
		return teamAwayTOTsog;
	}


	public void setTeamAwayTOTsog(Integer teamAwayTOTsog) {
		this.teamAwayTOTsog = teamAwayTOTsog;
	}


	public Integer getTeamHomeTOTsog() {
		return teamHomeTOTsog;
	}


	public void setTeamHomeTOTsog(Integer teamHomeTOTsog) {
		this.teamHomeTOTsog = teamHomeTOTsog;
	}


}
