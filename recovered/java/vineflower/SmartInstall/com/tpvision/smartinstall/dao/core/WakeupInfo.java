package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wakeup_info")
public class WakeupInfo {
   @Id
   @Column(name = "id")
   private int id;
   @Column(name = "roomid")
   private String roomid;
   @Column(name = "wakeupid")
   private String wakeupid;
   @Column(name = "wakeuptime")
   private String wakeuptime;
   @Column(name = "status")
   private int status;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getRoomId() {
      return this.roomid;
   }

   public void setRoomId(String roomid) {
      this.roomid = roomid;
   }

   public String getWakeupId() {
      return this.wakeupid;
   }

   public void setWakeupId(String wakeupid) {
      this.wakeupid = wakeupid;
   }

   public String getWakeupTime() {
      return this.wakeuptime;
   }

   public void setWakeupTime(String wakeuptime) {
      this.wakeuptime = wakeuptime;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }
}
