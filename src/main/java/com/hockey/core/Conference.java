package com.hockey.core;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
public class Conference {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private Integer confId;
  private String name;

  protected Conference() {}

  public Conference(Integer confId, String name) {
    this.confId = confId;
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format(
        "Conference[id=%d, confId='%s', name='%s']",
        id, confId, name);
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getConfId() {
	    return confId;
	  }

  public void setConfId(Integer confId) {
	    this.confId = confId;
	  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
