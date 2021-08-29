package com.hockey.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@ComponentScan
@RequestMapping(path="/nhl") // This means URL's start with /nhl (after Application path)
public class MainController {
  Logger logger = Logger.getLogger(MainController.class);
	
  @Autowired // This means to get the bean called conferenceRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private ConferenceRepository conferenceRepository;

  @Autowired // This means to get the bean called conferenceRepository
  // Which is auto-generated by Spring, we will use it to handle the data
private DivisionRepository divisionRepository;

  @Autowired // This means to get the bean called conferenceRepository
  // Which is auto-generated by Spring, we will use it to handle the data
private TeamRepository teamRepository;

  
  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewConference (@RequestParam String name) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
		
	logger.debug("addNewConference has been reached");
		
    Conference n = new Conference();
    n.setName(name);
    conferenceRepository.save(n);
    return "Saved";
  }
  @GetMapping(path="/dbload") 
  public @ResponseBody String loadDB () {
    	
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
	         throw new RuntimeException("Failed : HTTP error code : "
	                 + conn.getResponseCode());
	     }

	     BufferedReader br = new BufferedReader(new InputStreamReader(
	         (conn.getInputStream())));

	     String myString;
	     int count = 0;
	     String dataLine = "";
	    
	     System.out.println("Output from Server .... \n");
	     while ((myString = br.readLine()) != null) {
             count = count + 1;
             dataLine = dataLine + myString;  
	    	 
	    	 System.out.println(count + "   " + myString);
	     }
	     
	     JSONObject myJsonString = new JSONObject(dataLine);
		 Set<Integer> conferenceIds = new HashSet<Integer>();
		 Set<Integer> divisionIds = new HashSet<Integer>();
		 Set<Integer> teamIds = new HashSet<Integer>();

		 JSONArray teamsArray = myJsonString.getJSONArray("teams");
		 for (int i = 0; i < teamsArray.length(); i++)
		 {
			if (!conferenceIds.contains(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"))){
				conferenceIds.add(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
				Conference nConf = new Conference();	 
				nConf.setConfId(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
			    nConf.setName(teamsArray.getJSONObject(i).getJSONObject("conference").getString("name"));
			    System.out.println("CONF ID " + nConf.getConfId()); 
			    System.out.println("CONF Name " + nConf.getName());
			    System.out.println("CONF " + nConf);
			    conferenceRepository.save(nConf);
			 }

			if (!divisionIds.contains(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"))){
				divisionIds.add(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
				Division nDiv = new Division();	 
				nDiv.setDivId(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
			    nDiv.setName(teamsArray.getJSONObject(i).getJSONObject("division").getString("name"));
			    nDiv.setNameShort(teamsArray.getJSONObject(i).getJSONObject("division").getString("nameShort"));
			    System.out.println("DIV ID " + nDiv.getDivId()); 
			    System.out.println("DIV Name " + nDiv.getName());
			    System.out.println("DIV Name Short " + nDiv.getNameShort());
			    System.out.println("DIV " + nDiv);
			    divisionRepository.save(nDiv);
			 }
			
			if (!teamIds.contains(teamsArray.getJSONObject(i).getInt("id"))){
				teamIds.add(teamsArray.getJSONObject(i).getInt("id"));
				Team nTeam = new Team();	 
				nTeam.setTeamId(teamsArray.getJSONObject(i).getInt("id"));
			    nTeam.setName(teamsArray.getJSONObject(i).getString("name"));
			    nTeam.setTimeZone(teamsArray.getJSONObject(i).getJSONObject("venue").getJSONObject("timeZone").getString("tz"));
			    nTeam.setAbbreviation(teamsArray.getJSONObject(i).getString("abbreviation"));
			    nTeam.setTeamName(teamsArray.getJSONObject(i).getString("teamName"));
			    nTeam.setLocationName(teamsArray.getJSONObject(i).getString("locationName"));
			    nTeam.setDivId(teamsArray.getJSONObject(i).getJSONObject("division").getInt("id"));
			    nTeam.setConfId(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
			    nTeam.setFranchiseId(teamsArray.getJSONObject(i).getJSONObject("franchise").getInt("franchiseId"));
			    System.out.println("TEAM ID " + nTeam.getConfId()); 
			    System.out.println("TEAM Name " + nTeam.getName());
			    System.out.println("TEAM " + nTeam);
			    teamRepository.save(nTeam);
			 }

	     }

		 System.out.println(conferenceIds);  
	     conn.disconnect();

	     } catch (IOException e) {

	        e.printStackTrace();
		        
	     }
//        second api call 
//             urllib.request.urlopen('https://statsapi.web.nhl.com/api/v1/schedule?startDate=2021-10-01&endDate=2020-04-30')

	try {
	  //   URL url2 = new URL("https://statsapi.web.nhl.com/api/v1/schedule?startDate=2021-10-01&endDate=2021-04-30");
	     URL url2 = new URL("https://statsapi.web.nhl.com/api/v1/schedule");
	     HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
	     conn2.setRequestMethod("GET");
	     conn2.setRequestProperty("Accept", "application/json");

	     if (conn2.getResponseCode() != 200) {
	         throw new RuntimeException("Failed : HTTP error code : "
	                 + conn2.getResponseCode());
	     }

	     BufferedReader br2 = new BufferedReader(new InputStreamReader(
	         (conn2.getInputStream())));

	     String myString2;
	     int count = 0;
	     String dataLine2 = "";
	    
	     System.out.println("Output2 from Server .... \n");
	     while ((myString2 = br2.readLine()) != null) {
             count = count + 1;
             dataLine2 = dataLine2 + myString2;  
	    	 
	    	 System.out.println(count + "   " + myString2);
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
		 Set<Integer> scheduleIds = new HashSet<Integer>();

		 JSONArray gamesArray = myJsonString2.getJSONArray("teams");
		 for (int i = 0; i < gamesArray.length(); i++)
		 {
//			if (!conferenceIds.contains(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"))){
//				conferenceIds.add(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
//				Conference nConf = new Conference();	 
//				nConf.setConfId(teamsArray.getJSONObject(i).getJSONObject("conference").getInt("id"));
//			    nConf.setName(teamsArray.getJSONObject(i).getJSONObject("conference").getString("name"));
//			    System.out.println("CONF ID " + nConf.getConfId()); 
//			    System.out.println("CONF Name " + nConf.getName());
//			    System.out.println("CONF " + nConf);
//			    conferenceRepository.save(nConf);
//			 }
//

	     }

		 System.out.println(scheduleIds);  
	     conn2.disconnect();

	     } catch (IOException e) {

	        e.printStackTrace();
		        
	     }
	
//
	return "Saved";
  }
  @GetMapping(path="/all")
  public @ResponseBody Iterable<Conference> getAllConferences() {
    // This returns a JSON or XML with the users
	logger.debug("getAllConferences has been reached");
	return conferenceRepository.findAll();
  }
}