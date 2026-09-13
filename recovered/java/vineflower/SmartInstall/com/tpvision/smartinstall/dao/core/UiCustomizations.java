package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "uicustomizations")
public class UiCustomizations {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "name")
   private String name;
   @Column(name = "value")
   private String value;
   @Column(name = "platform")
   private String platform;
   @Column(name = "lastEdit")
   private String lastEdit;
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

   public String getLastEdit() {
      return this.lastEdit;
   }

   public void setLastEdit(String lastEdit) {
      this.lastEditModified = true;
      this.lastEdit = lastEdit;
   }

   public boolean isLastEditModified() {
      return this.lastEditModified;
   }
}
