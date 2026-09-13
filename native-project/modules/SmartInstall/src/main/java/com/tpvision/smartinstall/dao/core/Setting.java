package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "setting")
public class Setting {
   public static final int UN_ASSIGNED = -1;
   public static final int CLONE_EMPTY = 0;
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "value")
   private String value;
   @Column(name = "platform")
   private String platform;
   @Column(name = "cloneRename")
   private String clonerename;
   @Column(name = "isDelete")
   private String isdelete;
   @Column(name = "type")
   private String type;
   @Column(name = "created_by")
   private String createdBy;
   @Column(name = "created_date")
   private Date createdDate;
   @Column(name = "last_updated_by")
   private String lastUpdatedBy;
   @Column(name = "last_updated_date")
   private Date lastUpdatedDate;
   @Column(name = "geoname_id")
   private String geonameId;
   @Column(name = "language")
   private String language;
   @Column(name = "androidApps")
   private String androidApps;
   @Column(name = "content")
   private String content;
   @Column(name = "channelPackageId")
   private int channelPackageId;
   @Column(name = "appPackageId")
   private int appPackageId;
   @Column(name = "cloneItemStatus")
   private String cloneItemStatus;
   @Column(name = "settingPackageId")
   private int settingPackageId;
   @Column(name = "bannersId")
   private int bannersId;
   @Column(name = "welcomeId")
   private int welcomeId;
   @Column(name = "uiCustomizationsId")
   private int uiCustomizationsId;
   @Column(name = "scheduleId")
   private int scheduleId;
   @Transient
   private boolean lastUpdateDateModified = false;

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

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getPlatform() {
      return this.platform;
   }

   public void setPlatform(String platform) {
      this.platform = platform;
   }

   public String getClonerename() {
      return this.clonerename;
   }

   public void setClonerename(String clonerename) {
      this.clonerename = clonerename;
   }

   public String getIsdelete() {
      return this.isdelete;
   }

   public void setIsdelete(String isdelete) {
      this.isdelete = isdelete;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
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
      this.lastUpdateDateModified = true;
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public String getGeonameId() {
      return this.geonameId;
   }

   public void setGeonameId(String geonameId) {
      this.geonameId = geonameId;
   }

   public String getLanguage() {
      return this.language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   public String getAndroidApps() {
      return this.androidApps;
   }

   public void setAndroidApps(String androidApps) {
      this.androidApps = androidApps;
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getCloneItemStatus() {
      return this.cloneItemStatus;
   }

   public void setCloneItemStatus(String cloneItemStatus) {
      this.cloneItemStatus = cloneItemStatus;
   }

   public int getChannelPackageId() {
      return this.channelPackageId;
   }

   public void setChannelPackageId(int channelPackageId) {
      this.channelPackageId = channelPackageId;
   }

   public int getAppPackageId() {
      return this.appPackageId;
   }

   public void setAppPackageId(int appPackageId) {
      this.appPackageId = appPackageId;
   }

   public int getSettingPackageId() {
      return this.settingPackageId;
   }

   public void setSettingPackageId(int settingPackageId) {
      this.settingPackageId = settingPackageId;
   }

   public int getBannersId() {
      return this.bannersId;
   }

   public void setBannersId(int bannersId) {
      this.bannersId = bannersId;
   }

   public int getWelcomeId() {
      return this.welcomeId;
   }

   public void setWelcomeId(int welcomeId) {
      this.welcomeId = welcomeId;
   }

   public int getUiCustomizationsId() {
      return this.uiCustomizationsId;
   }

   public void setUiCustomizationsId(int uiCustomizationsId) {
      this.uiCustomizationsId = uiCustomizationsId;
   }

   public int getScheduleId() {
      return this.scheduleId;
   }

   public void setScheduleId(int scheduleId) {
      this.scheduleId = scheduleId;
   }

   public boolean isLastUpdateDateModified() {
      return this.lastUpdateDateModified;
   }
}
