package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smartui")
public class Smartui {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "created_by")
   private String createdBy;
   @Column(name = "created_date")
   private Date createdDate;
   @Column(name = "last_update_by")
   private String lastUpdateBy;
   @Column(name = "last_update_date")
   private Date lastUpdateDate;
   @Column(name = "value")
   private byte[] value;
   @Column(name = "type")
   private String type = "UUI";
   @Column(name = "isDelete")
   private String isdelete = "N";
   @Column(name = "Rename_website")
   private String renameWebsite;
   @Column(name = "version")
   private String version;

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

   public String getLastUpdateBy() {
      return this.lastUpdateBy;
   }

   public void setLastUpdateBy(String lastUpdateBy) {
      this.lastUpdateBy = lastUpdateBy;
   }

   public Date getLastUpdateDate() {
      return this.lastUpdateDate;
   }

   public void setLastUpdateDate(Date lastUpdateDate) {
      this.lastUpdateDate = lastUpdateDate;
   }

   public byte[] getValue() {
      return this.value;
   }

   public void setValue(byte[] value) {
      this.value = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getIsdelete() {
      return this.isdelete;
   }

   public void setIsdelete(String isdelete) {
      this.isdelete = isdelete;
   }

   public String getRenameWebsite() {
      return this.renameWebsite;
   }

   public void setRenameWebsite(String renameWebsite) {
      this.renameWebsite = renameWebsite;
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }
}
