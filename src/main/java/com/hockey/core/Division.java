package com.hockey.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Division {

	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Integer id;
      private Integer divId; 
	  private String name;
	  private String nameShort;

	  protected Division() {}

	  public Division(Integer divId, String name, String nameShort) {
	    this.divId = divId;
	    this.name = name;
	    this.nameShort = nameShort;
	  }

	  @Override
	  public String toString() {
	    return String.format(
	        "Division[id=%d, divId='%s', name='%s', nameShort='%s']",
	        id, divId, name, nameShort);
	  }
	  
	  public Integer getId() {
	    return id;
	  }

	  public void setId(Integer id) {
	    this.id = id;
	  }

	  public Integer getDivId() {
		    return divId;
	  }

		  public void setDivId(Integer divId) {
		    this.divId = divId;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getNameShort() {
	    return nameShort;
	  }

	  public void setNameShort(String nameShort) {
	    this.nameShort = nameShort;
	  }
	}