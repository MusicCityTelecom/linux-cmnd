package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weather_forecast")
public class Weather {
   public static final int WEATHER_STATUS_SUCESS = 1;
   public static final int WEATHER_STATUS_FAIL = 0;
   @Id
   @Column(name = "cityId")
   private String cityId;
   @Column(name = "lang")
   private String lang;
   @Column(name = "forecasts")
   private String forecasts;
   @Column(name = "fetchTime")
   private String fetchTime;
   @Column(name = "lastFetchTime")
   private String lastFetchTime;
   @Column(name = "errorMsg")
   private String errorMsg;
   @Column(name = "status")
   private int status;

   public String getCityId() {
      return this.cityId;
   }

   public void setCityId(String cityId) {
      this.cityId = cityId;
   }

   public String getLang() {
      return this.lang;
   }

   public void setLang(String lang) {
      this.lang = lang;
   }

   public String getForecasts() {
      return this.forecasts;
   }

   public void setForecasts(String forecast) {
      this.forecasts = forecast;
   }

   public String getFetchTime() {
      return this.fetchTime;
   }

   public void setFetchTime(String fetchTime) {
      this.fetchTime = fetchTime;
   }

   public String getLastFetchTime() {
      return this.lastFetchTime;
   }

   public void setLastFetchTime(String lastFetchTime) {
      this.lastFetchTime = lastFetchTime;
   }

   public String getErrorMsg() {
      return this.errorMsg;
   }

   public void setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }
}
