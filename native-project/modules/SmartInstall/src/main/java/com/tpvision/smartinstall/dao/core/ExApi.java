package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exapi")
public class ExApi {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;
   @Column(name = "api")
   private String api = "off";
   @Column(name = "apiurl")
   private String apiurl;
   @Column(name = "apikey")
   private String apikey;
   @Column(name = "apikeyname")
   private String apikeyname;

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getApi() {
      return this.api;
   }

   public void setApi(String api) {
      this.api = api;
   }

   public String getApiurl() {
      return this.apiurl;
   }

   public void setApiurl(String apiurl) {
      this.apiurl = apiurl;
   }

   public String getApikey() {
      return this.apikey;
   }

   public void setApikey(String apikey) {
      this.apikey = apikey;
   }

   public String getApikeyname() {
      return this.apikeyname;
   }

   public void setApikeyname(String apikeyname) {
      this.apikeyname = apikeyname;
   }
}
