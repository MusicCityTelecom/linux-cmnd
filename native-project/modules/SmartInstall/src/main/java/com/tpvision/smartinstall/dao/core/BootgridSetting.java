package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bootgrid_dropmenu")
public class BootgridSetting {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "user")
   private String user;
   @Column(name = "grid_id")
   private String gridid;
   @Column(name = "invisible_column_id")
   private String invisibleColumnId;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUser() {
      return this.user;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public String getGridid() {
      return this.gridid;
   }

   public void setGridid(String gridid) {
      this.gridid = gridid;
   }

   public String getInvisibleColumnId() {
      return this.invisibleColumnId;
   }

   public void setInvisibleColumnId(String invisibleColumnId) {
      this.invisibleColumnId = invisibleColumnId;
   }
}
