package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
   public static final String ROLE_NAME_ADMIN = "ROLE_ADMIN";
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idrole")
   private int idrole;
   @Column(name = "name")
   private String name;

   public int getIdrole() {
      return this.idrole;
   }

   public void setIdrole(int idrole) {
      this.idrole = idrole;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
