package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "banners")
public class Banners {
   public static final String BANNDER_TYPE_SURVEY = "Survey";
   public static final String BANNDER_TYPE_EMERGENCY = "Emergency";
   public static final String BANNDER_TYPE_COMMERCIAL = "Commercial";
   public static final String BANNDER_TYPE_CUSTOM = "Custom";
   public static final String BANNER_CONTENT_STAR = "Star";
   public static final String BANNER_CONTENT_EVACUATION = "Evacuation";
   public static final String BANNER_CONTENT_ALERT = "Firealert";
   public static final String BANNER_CONTENT_CUSTOM = "Custom";
   public static final String BANNER_SURVEY_DEFALT_TITLE = "How would you rate your stay?";
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "platform")
   private String platform;
   @Column(name = "type")
   private String type;
   @Column(name = "content")
   private String content;
   @Column(name = "schedule")
   private String schedule;
   @Column(name = "triggers")
   private String triggers;
   @Column(name = "lastEdit")
   private String lastEdit;
   @Column(name = "response")
   private String response;
   @Column(name = "date")
   private String date;
   @Column(name = "origin")
   private String origin;
   @Transient
   private boolean lastEditModified = false;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPlatform() {
      return this.platform;
   }

   public void setPlatform(String platform) {
      this.platform = platform;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getSchedule() {
      return this.schedule;
   }

   public void setSchedule(String schedule) {
      this.schedule = schedule;
   }

   public String getTriggers() {
      return this.triggers;
   }

   public void setTriggers(String triggers) {
      this.triggers = triggers;
   }

   public String getLastEdit() {
      return this.lastEdit;
   }

   public void setLastEdit(String lastEdit) {
      this.lastEditModified = true;
      this.lastEdit = lastEdit;
   }

   public String getResponse() {
      return this.response;
   }

   public void setResponse(String response) {
      this.response = response;
   }

   public String getDate() {
      return this.date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getOrigin() {
      return this.origin;
   }

   public void setOrigin(String origin) {
      this.origin = origin;
   }

   public boolean isLastEditModified() {
      return this.lastEditModified;
   }
}
