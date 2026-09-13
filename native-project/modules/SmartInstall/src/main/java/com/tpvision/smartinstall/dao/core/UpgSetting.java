package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "upg_setting")
public class UpgSetting {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "platform")
   private String platform;
   @Column(name = "version")
   private String version;
   @Column(name = "ipversion")
   private String ipversion;
   @Column(name = "upgRename")
   private String upgrename;
   @Column(name = "created_by")
   private String createdBy;
   @Column(name = "created_date")
   private Date createdDate;
   @Column(name = "last_updated_by")
   private String lastUpdatedBy;
   @Column(name = "last_updated_date")
   private Date lastUpdatedDate;
   @Column(name = "upg_played_on")
   private Date upgPlayedOn;
   @Column(name = "upg_played_by")
   private String upgPlayedBy;
   @Column(name = "isDelete")
   private String isdelete;

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

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public String getIpversion() {
      return this.ipversion;
   }

   public void setIpversion(String ipversion) {
      this.ipversion = ipversion;
   }

   public String getUpgrename() {
      return this.upgrename;
   }

   public void setUpgrename(String upgrename) {
      this.upgrename = upgrename;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public Date getCreatedDate() {
      return this.createdDate;
   }

   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
   }

   public String getLastUpdatedBy() {
      return this.lastUpdatedBy;
   }

   public void setLastUpdatedBy(String lastUpdatedBy) {
      this.lastUpdatedBy = lastUpdatedBy;
   }

   public Date getLastUpdatedDate() {
      return this.lastUpdatedDate;
   }

   public void setLastUpdatedDate(Date lastUpdatedDate) {
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public Date getUpgPlayedOn() {
      return this.upgPlayedOn;
   }

   public void setUpgPlayedOn(Date upgPlayedOn) {
      this.upgPlayedOn = upgPlayedOn;
   }

   public String getUpgPlayedBy() {
      return this.upgPlayedBy;
   }

   public void setUpgPlayedBy(String upgPlayedBy) {
      this.upgPlayedBy = upgPlayedBy;
   }

   public String getIsdelete() {
      return this.isdelete;
   }

   public void setIsdelete(String isdelete) {
      this.isdelete = isdelete;
   }
}
