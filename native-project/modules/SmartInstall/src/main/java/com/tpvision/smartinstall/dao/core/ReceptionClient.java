package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reception_client")
public class ReceptionClient {
   public static final int STATUS_VERSION_CHECK_YES = 1;
   public static final int STATUS_VERSION_CHECK_NO = 0;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "client_id")
   private String clientId;
   @Column(name = "current_version")
   private String currentVersion;
   @Column(name = "add_time")
   private Date addTime;
   @Column(name = "status")
   private int status;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getClientId() {
      return this.clientId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getCurrentVersion() {
      return this.currentVersion;
   }

   public void setCurrentVersion(String currentVersion) {
      this.currentVersion = currentVersion;
   }

   public Date getAddTime() {
      return this.addTime;
   }

   public void setAddTime(Date addTime) {
      this.addTime = addTime;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }
}
