package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "channelpackage")
public class ChannelPackage {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "platform")
   private String platform;
   @Column(name = "value")
   private String value;
   @Column(name = "numberOfChs")
   private int numberOfChs;
   @Column(name = "lastEdit")
   private String lastEdit;
   @Column(name = "createdBy")
   private String createdBy = "admin";
   @Column(name = "configName")
   private String configName;
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

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public int getNumberOfChs() {
      return this.numberOfChs;
   }

   public void setNumberOfChs(int numberOfChs) {
      this.numberOfChs = numberOfChs;
   }

   public String getLastEdit() {
      return this.lastEdit;
   }

   public void setLastEdit(String lastEdit) {
      this.lastEditModified = true;
      this.lastEdit = lastEdit;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public String getConfigName() {
      return this.configName;
   }

   public void setConfigName(String configName) {
      this.configName = configName;
   }

   public boolean isLastEditModified() {
      return this.lastEditModified;
   }
}
