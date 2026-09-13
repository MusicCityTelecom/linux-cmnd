package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "settingpackage")
public class SettingPackage {
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
   @Column(name = "lastEdit")
   private String lastEdit;
   @Column(name = "term_and_conditions")
   private String termAndConditions;
   @Column(name = "htv_tls_psk_key")
   private String htvTlsPskKey;
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

   public String getLastEdit() {
      return this.lastEdit;
   }

   public void setLastEdit(String lastEdit) {
      this.lastEditModified = true;
      this.lastEdit = lastEdit;
   }

   public String getTermAndConditions() {
      return this.termAndConditions;
   }

   public void setTermAndConditions(String termAndConditions) {
      this.termAndConditions = termAndConditions;
   }

   public String getHtvTlsPskKey() {
      return this.htvTlsPskKey;
   }

   public void setHtvTlsPskKey(String htvTlsPskKey) {
      this.htvTlsPskKey = htvTlsPskKey;
   }

   public boolean isLastEditModified() {
      return this.lastEditModified;
   }
}
