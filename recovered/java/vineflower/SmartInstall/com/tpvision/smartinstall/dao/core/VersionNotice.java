package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "version_notice")
public class VersionNotice {
   public static final int NOTICE_TYPE_DIALOG = 0;
   public static final int NOTICE_TYPE_EMAIL = 1;
   public static final int ENTITY_TYPE_CMND_VERSION = 0;
   public static final int ENTITY_TYPE_RECEPTION_VERSION = 1;
   public static final int ENTITY_TYPE_FIRMWARE_VERSION = 2;
   public static final String CMND_ENTIY_ID_FOR_CMND_TYPE = "CMND";
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "notice_type")
   private int noticeType;
   @Column(name = "notice_admin")
   private String noticeAdmin;
   @Column(name = "entity_type")
   private int entityType;
   @Column(name = "entity_id")
   private String entityId;
   @Column(name = "notice_version")
   private String noticeVersion;
   @Column(name = "notice_time")
   private Date noticeTime;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getNoticeType() {
      return this.noticeType;
   }

   public void setNoticeType(int noticeType) {
      this.noticeType = noticeType;
   }

   public String getNoticeAdmin() {
      return this.noticeAdmin;
   }

   public void setNoticeAdmin(String noticeAdmin) {
      this.noticeAdmin = noticeAdmin;
   }

   public int getEntityType() {
      return this.entityType;
   }

   public void setEntityType(int entityType) {
      this.entityType = entityType;
   }

   public String getEntityId() {
      return this.entityId;
   }

   public void setEntityId(String entityId) {
      this.entityId = entityId;
   }

   public String getNoticeVersion() {
      return this.noticeVersion;
   }

   public void setNoticeVersion(String noticeVersion) {
      this.noticeVersion = noticeVersion;
   }

   public Date getNoticeTime() {
      return this.noticeTime;
   }

   public void setNoticeTime(Date noticeTime) {
      this.noticeTime = noticeTime;
   }
}
