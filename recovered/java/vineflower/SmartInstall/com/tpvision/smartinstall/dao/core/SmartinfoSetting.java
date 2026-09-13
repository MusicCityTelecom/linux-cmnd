package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smartinfo_setting")
public class SmartinfoSetting {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "setting_id")
   private int settingId;
   @Column(name = "smartui_id")
   private int smartuiId;
   @Column(name = "created_by")
   private String createdBy;
   @Column(name = "last_updated_by")
   private String lastUpdatedBy;
   @Column(name = "created_date")
   private Date createdDate;
   @Column(name = "last_updated_date")
   private Date lastUpdatedDate;
   @Column(name = "templateId")
   private Integer templateid;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getSettingId() {
      return this.settingId;
   }

   public void setSettingId(int settingId) {
      this.settingId = settingId;
   }

   public int getSmartuiId() {
      return this.smartuiId;
   }

   public void setSmartuiId(int smartuiId) {
      this.smartuiId = smartuiId;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public String getLastUpdatedBy() {
      return this.lastUpdatedBy;
   }

   public void setLastUpdatedBy(String lastUpdatedBy) {
      this.lastUpdatedBy = lastUpdatedBy;
   }

   public Date getCreatedDate() {
      return this.createdDate;
   }

   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
   }

   public Date getLastUpdatedDate() {
      return this.lastUpdatedDate;
   }

   public void setLastUpdatedDate(Date lastUpdatedDate) {
      this.lastUpdatedDate = lastUpdatedDate;
   }

   public Integer getTemplateid() {
      return this.templateid;
   }

   public void setTemplateid(Integer templateid) {
      this.templateid = templateid;
   }
}
