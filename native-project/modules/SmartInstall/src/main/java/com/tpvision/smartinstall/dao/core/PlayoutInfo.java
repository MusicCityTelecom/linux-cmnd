package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "playout_info")
public class PlayoutInfo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "playoutId")
   private int playoutId;
   @Column(name = "rooms")
   private String rooms;
   @Column(name = "name")
   private String name;
   @Column(name = "type")
   private String type;
   @Column(name = "platform")
   private String platform;
   @Column(name = "version")
   private String version;
   @Column(name = "schedule")
   private String schedule;
   @Column(name = "source")
   private String source;
   @Column(name = "connectId")
   private int connectId;
   @Column(name = "status")
   private int status;
   @Column(name = "isclone")
   private String isclone;
   @Column(name = "filepath")
   private String filePath;

   public int getPlayoutId() {
      return this.playoutId;
   }

   public void setPlayoutId(int playoutId) {
      this.playoutId = playoutId;
   }

   public String getRooms() {
      return this.rooms;
   }

   public void setRooms(String rooms) {
      this.rooms = rooms;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
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

   public String getSchedule() {
      return this.schedule;
   }

   public void setSchedule(String schedule) {
      this.schedule = schedule;
   }

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public int getConnectId() {
      return this.connectId;
   }

   public void setConnectId(int connectId) {
      this.connectId = connectId;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public String getIsclone() {
      return this.isclone;
   }

   public void setIsclone(String isclone) {
      this.isclone = isclone;
   }

   public String getFilePath() {
      return this.filePath;
   }

   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }
}
