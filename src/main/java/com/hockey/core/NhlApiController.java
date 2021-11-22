package com.hockey.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hockey.core.dao.ConferenceRepository;
import com.hockey.core.dao.DivisionRepository;
import com.hockey.core.dao.GameRepository;
import com.hockey.core.dao.TeamRepository;
import com.hockey.core.model.Conference;
import com.hockey.core.model.Division;
import com.hockey.core.model.Game;
import com.hockey.core.model.Team;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class NhlApiController {
	Logger logger = Logger.getLogger(NhlApiController.class);

	@Autowired
	ConferenceRepository conferenceRepository;

	@Autowired
	DivisionRepository divisionRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	GameRepository gameRepository;

	@RequestMapping(value = "/api/load/conference", method = { RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public String loadDB() {

		logger.debug("loadDB has been reached");

//    DbHandler dbHandler = new DbHandler();
//	dbHandler.loadAllDb();
		logger.debug("loadAllDb has been reached");

		try {

			URL url = new URL("https://statsapi.web.nhl.com/api/v1/teams");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String myString;
			int count = 0;
			String dataLine = "";

			conferenceRepository.truncateConference();
			divisionRepository.truncateDivision();
			teamRepository.truncateTeam();

			logger.debug("Output from Server .... \n");
			while ((myString = br.readLine()) != null) {
				count = count + 1;
				dataLine = dataLine + myString;

				logger.debug(count + "   " + myString);
			}

			JSONObject myJsonString = new JSONObject(dataLine);
			Set<Integer> conferenceIds = new HashSet<Integer>();
			Set<Integer> divisionIds = new HashSet<Integer>();
			Set<Integer> teamIds = new HashSet<Integer>();

			JSONArray teamsArray = myJsonString.getJSONArray("teams");
			for (int i = 0; i < teamsArray.length(); i++) {
				if (!conferenceIds.contains(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"))) {
					conferenceIds.add(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
					Conference nConf = new Conference();
					nConf.setConfId(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
					nConf.setName(teamsArray.getJSONObject(i).getJSONObject("conference").getString("name"));
					logger.debug("CONF ID " + nConf.getConfId());
					logger.debug("CONF Name " + nConf.getName());
					logger.debug("CONF " + nConf);
					conferenceRepository.save(nConf);
				}

				if (!divisionIds.contains(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"))) {
					divisionIds.add(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
					Division nDiv = new Division();
					nDiv.setDivId(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
					nDiv.setName(teamsArray.getJSONObject(i).getJSONObject("division").getString("name"));
					nDiv.setNameShort(teamsArray.getJSONObject(i).getJSONObject("division").getString("nameShort"));
					logger.debug("DIV ID " + nDiv.getDivId());
					logger.debug("DIV Name " + nDiv.getName());
					logger.debug("DIV Name Short " + nDiv.getNameShort());
					logger.debug("DIV " + nDiv);
					divisionRepository.save(nDiv);
				}

				if (!teamIds.contains(teamsArray.getJSONObject(i).getInt("id"))) {
					teamIds.add(teamsArray.getJSONObject(i).getInt("id"));
					Team nTeam = new Team();
					nTeam.setTeamId(teamsArray.getJSONObject(i).getInt("id"));
					nTeam.setName(teamsArray.getJSONObject(i).getString("name"));
					nTeam.setTimeZone(teamsArray.getJSONObject(i).getJSONObject("venue").getJSONObject("timeZone")
							.getString("tz"));
					nTeam.setAbbreviation(teamsArray.getJSONObject(i).getString("abbreviation"));
					nTeam.setTeamName(teamsArray.getJSONObject(i).getString("teamName"));
					nTeam.setLocationName(teamsArray.getJSONObject(i).getString("locationName"));
					nTeam.setDivId(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
					nTeam.setConfId(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
					nTeam.setFranchiseId(teamsArray.getJSONObject(i).getJSONObject("franchise").getInt("franchiseId"));
					logger.debug("TEAM ID " + nTeam.getConfId());
					logger.debug("TEAM Name " + nTeam.getName());
					logger.debug("TEAM " + nTeam);
					teamRepository.save(nTeam);
				}

			}

			logger.debug(conferenceIds);
			conn.disconnect();

		} catch (IOException e) {

			e.printStackTrace();

		}
		//
		return "Saved";
	}

//        second api call 
//             urllib.request.urlopen('https://statsapi.web.nhl.com/api/v1/schedule?startDate=2021-10-01&endDate=2020-04-30')
	@RequestMapping(value = "/api/load/game", method = { RequestMethod.POST, RequestMethod.GET })
	@Transactional
	public String loadSchedule() {

		logger.debug("loadSchedule has been reached");
		try {
			// URL url2 = new
			// URL("https://statsapi.web.nhl.com/api/v1/schedule?startDate=2021-10-01&endDate=2021-04-30");
			URL url2 = new URL("https://statsapi.web.nhl.com/api/v1/schedule?season=20212022");
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Accept", "application/json");

			if (conn2.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn2.getResponseCode());
			}

			BufferedReader br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));

			String myString2;
			int count = 0;
			String dataLine2 = "";

			gameRepository.truncateGame();

			logger.debug("Output2 from Server .... \n");
			while ((myString2 = br2.readLine()) != null) {
				count = count + 1;
				dataLine2 = dataLine2 + myString2;
				// if (count == 100) {
				// // breaking the loop
				// break;
				// }

				logger.debug(count + "   " + myString2);
			}
//	     Output2 from Server .... 
//
//	     1   {
//	     2     "copyright" : "NHL and the NHL Shield are registered trademarks of the National Hockey League. NHL and NHL team marks are the property of the NHL and its teams. © NHL 2021. All Rights Reserved.",
//	     3     "totalItems" : 0,
//	     4     "totalEvents" : 0,
//	     5     "totalGames" : 0,
//	     6     "totalMatches" : 0,
//	     7     "metaData" : {
//	     8       "timeStamp" : "20210828_223908"
//	     9     },
//	     10     "wait" : 10,
//	     11     "dates" : [ ]
//	     12   }	     

			JSONObject myJsonString2 = new JSONObject(dataLine2);

			JSONArray datesArray = myJsonString2.getJSONArray("dates");
			Date aDate;
			for (int d = 0; d < datesArray.length(); d++) {
				logger.debug("DATE " + datesArray.getJSONObject(d).getString("date"));

				JSONArray gamesArray = datesArray.getJSONObject(d).getJSONArray("games");
				logger.debug("gamesArray " + gamesArray);
				for (int i = 0; i < gamesArray.length(); i++) {
					Game nGame = new Game();
					nGame.setGamePk(gamesArray.getJSONObject(i).getInt("gamePk"));
					nGame.setGameType(gamesArray.getJSONObject(i).getString("gameType"));

//					nGame.setGameDate(gamesArray.getJSONObject(i).getString("gameDate"));
//
//                  convert Game Date to EST time before adding to database...needed when searching later by Game Date  
//
					String strDate = gamesArray.getJSONObject(i).getString("gameDate");

					DateFormat inDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					inDF.setTimeZone(TimeZone.getTimeZone("UTC"));

					try {
						aDate = inDF.parse(strDate);

						DateFormat outDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						outDF.setTimeZone(TimeZone.getTimeZone("EST"));

						nGame.setGameDate(outDF.format(aDate));
						logger.debug("print Date...." + nGame.getGameDate());

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					nGame.setTeamAwayTotal(
							gamesArray.getJSONObject(i).getJSONObject("teams").getJSONObject("away").getInt("score"));
					nGame.setTeamAwayId(gamesArray.getJSONObject(i).getJSONObject("teams").getJSONObject("away")
							.getJSONObject("team").getInt("id"));
					nGame.setTeamHomeTotal(
							gamesArray.getJSONObject(i).getJSONObject("teams").getJSONObject("home").getInt("score"));
					nGame.setTeamHomeId(gamesArray.getJSONObject(i).getJSONObject("teams").getJSONObject("home")
							.getJSONObject("team").getInt("id"));
					nGame.setTeamAwayP1(0);
					nGame.setTeamAwayP2(0);
					nGame.setTeamAwayP3(0);
					nGame.setTeamAwayOT(0);
					nGame.setTeamAwayTotal(0);
					nGame.setTeamHomeP1(0);
					nGame.setTeamHomeP2(0);
					nGame.setTeamHomeP3(0);
					nGame.setTeamHomeOT(0);
					nGame.setTeamHomeTotal(0);
					nGame.setGameShootout(false);
					nGame.setTeamAwayOTsog(0);
					nGame.setTeamAwayP1sog(0);
					nGame.setTeamAwayP2sog(0);
					nGame.setTeamAwayP3sog(0);
					nGame.setTeamHomeOTsog(0);
					nGame.setTeamHomeP1sog(0);
					nGame.setTeamHomeP2sog(0);
					nGame.setTeamHomeP3sog(0);
					nGame.setTeamHomeTOTsog(0);
					nGame.setTeamAwayTOTsog(0);
					logger.debug("GAME ID " + nGame.getGamePk());
					logger.debug("GAME TYPE " + nGame.getGameType());
					logger.debug("GAME DATE " + nGame.getGameDate());

					gameRepository.save(nGame);

				}

			}

			conn2.disconnect();

		} catch (IOException e) {

			e.printStackTrace();

		}

//
		return "Saved";
	}

//  third api call 
//  urllib.request.urlopen('https://statsapi.web.nhl.com/api/v1/game/2021010001/linescore')  (gamepk)
	@RequestMapping(value = "/api/update/game/updateDate={inputDate}", method = { RequestMethod.POST,
			RequestMethod.GET })
	@Transactional
	public String updateGame(@PathVariable("inputDate") String inputDate) {

		logger.debug("updateGame has been reached");

		String selectDate = inputDate + "%";
		logger.debug("selectDate = " + selectDate);

		List<Game> rs = null;
		rs = gameRepository.findByGameDate(selectDate);
		logger.debug("rs ..." + rs);

		// URL url3 = new
		for (ListIterator<Game> iter = rs.listIterator(); iter.hasNext();) {
			Game gameFromDb = iter.next();
			String selectPk = gameFromDb.getGamePk().toString();
			logger.debug("gamePk...." + selectPk);
			updateGameLine(selectPk, gameFromDb);

		}
		return "Saved";
	}

	public String updateGameLine(String selectPk, Game gameFromDb) {

		try {

			URL url3 = new URL("https://statsapi.web.nhl.com/api/v1/game/" + selectPk + "/linescore");
			HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
			conn3.setRequestMethod("GET");
			conn3.setRequestProperty("Accept", "application/json");

			if (conn3.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn3.getResponseCode());
			}

			BufferedReader br3 = new BufferedReader(new InputStreamReader((conn3.getInputStream())));

			String myString3;
			int count = 0;
			String dataLine3 = "";

			while ((myString3 = br3.readLine()) != null) {
				count = count + 1;
				dataLine3 = dataLine3 + myString3;
				// if (count == 100) {
				// // breaking the loop
				// break;
				// }

				logger.debug(count + "   " + myString3);
			}
//Output3 from Server .... 
//	"copyright" : "NHL and the NHL Shield are registered trademarks of the National Hockey League. NHL and NHL team marks are the property of the NHL and its teams. © NHL 2021. All Rights Reserved.",
//	  "currentPeriod" : 3,
//	  "currentPeriodOrdinal" : "3rd",
//	  "currentPeriodTimeRemaining" : "Final",
//	  "periods" : [ {
//	    "periodType" : "REGULAR",
//	    "startTime" : "2021-09-25T23:10:13Z",
//	    "endTime" : "2021-09-25T23:38:54Z",
//	    "num" : 1,
//	    "ordinalNum" : "1st",
//	    "home" : {
//	      "goals" : 1,
//	      "shotsOnGoal" : 9,
//	      "rinkSide" : "right"
//	    },
//	    "away" : {
//	      "goals" : 0,
//	      "shotsOnGoal" : 5,
//	      "rinkSide" : "left"
//	    }
//	  }, {
//
			JSONObject myJsonString3 = new JSONObject(dataLine3);

			String timeRemaining = myJsonString3.getString("currentPeriodTimeRemaining");
			logger.debug("time Remaining " + timeRemaining);

			if (timeRemaining.equals("Final")) {

				JSONArray periodsArray = myJsonString3.getJSONArray("periods");
				logger.debug("periodsArray " + periodsArray);
				logger.debug("myJsonString3 " + myJsonString3);
				gameFromDb.setTeamAwayTotal(myJsonString3.getJSONObject("teams").getJSONObject("away").getInt("goals"));
				gameFromDb.setTeamAwayTOTsog(myJsonString3.getJSONObject("teams").getJSONObject("away").getInt("shotsOnGoal"));
				gameFromDb.setTeamHomeTotal(myJsonString3.getJSONObject("teams").getJSONObject("home").getInt("goals"));
				gameFromDb.setTeamHomeTOTsog(myJsonString3.getJSONObject("teams").getJSONObject("home").getInt("shotsOnGoal"));
				gameFromDb.setTeamAwayP1(periodsArray.getJSONObject(0).getJSONObject("away").getInt("goals"));
				gameFromDb.setTeamAwayP2(periodsArray.getJSONObject(1).getJSONObject("away").getInt("goals"));
				gameFromDb.setTeamAwayP3(periodsArray.getJSONObject(2).getJSONObject("away").getInt("goals"));
				gameFromDb.setTeamAwayOT(0);
				gameFromDb.setTeamAwayP1sog(periodsArray.getJSONObject(0).getJSONObject("away").getInt("shotsOnGoal"));
				gameFromDb.setTeamAwayP2sog(periodsArray.getJSONObject(1).getJSONObject("away").getInt("shotsOnGoal"));
				gameFromDb.setTeamAwayP3sog(periodsArray.getJSONObject(2).getJSONObject("away").getInt("shotsOnGoal"));
				gameFromDb.setTeamAwayOTsog(0);
				gameFromDb.setTeamHomeP1(periodsArray.getJSONObject(0).getJSONObject("home").getInt("goals"));
				gameFromDb.setTeamHomeP2(periodsArray.getJSONObject(1).getJSONObject("home").getInt("goals"));
				gameFromDb.setTeamHomeP3(periodsArray.getJSONObject(2).getJSONObject("home").getInt("goals"));
				gameFromDb.setTeamHomeOT(0);
				gameFromDb.setTeamHomeP1sog(periodsArray.getJSONObject(0).getJSONObject("home").getInt("shotsOnGoal"));
				gameFromDb.setTeamHomeP2sog(periodsArray.getJSONObject(1).getJSONObject("home").getInt("shotsOnGoal"));
				gameFromDb.setTeamHomeP3sog(periodsArray.getJSONObject(2).getJSONObject("home").getInt("shotsOnGoal"));
				gameFromDb.setTeamHomeOTsog(0);
				gameFromDb.setGameShootout(myJsonString3.getBoolean("hasShootout"));
				logger.debug("setGameShootout " + myJsonString3.getBoolean("hasShootout"));

				if (myJsonString3.getInt("currentPeriod") == 4) {
					gameFromDb.setTeamAwayOT(periodsArray.getJSONObject(3).getJSONObject("away").getInt("goals"));
					gameFromDb.setTeamHomeOT(periodsArray.getJSONObject(3).getJSONObject("home").getInt("goals"));
					gameFromDb.setTeamAwayOTsog(periodsArray.getJSONObject(3).getJSONObject("away").getInt("shotsOnGoal"));
					gameFromDb.setTeamHomeOTsog(periodsArray.getJSONObject(3).getJSONObject("home").getInt("shotsOnGoal"));
				}
				;
				logger.debug("gameFromDb .... " + gameFromDb.getGameDate() + " " + gameFromDb.getGamePk());
				gameRepository.save(gameFromDb);

				conn3.disconnect();
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

//
		return "Saved";

	}

}