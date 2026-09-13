package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "siconfig")
public class SIConfig {
   public static final String WEATHER_SERVICE_ENABLED = "enable";
   public static final int REFRESH_RATE_DEFAULT = 30;
   public static final int REFRESH_RATE_MIN = 15;
   @Id
   @Column(name = "id")
   private int id;
   @Column(name = "weather_service")
   private String weatherService;
   @Column(name = "refresh_rate")
   private String refreshRate;
   @Column(name = "database_version")
   private String databaseVerison;
   @Column(name = "cmnd_ip")
   private String cmndIp;
   @Column(name = "refresh_token")
   private String refreshToken;
   @Column(name = "access_token")
   private String accessToken;
   @Column(name = "expire_time")
   private Date expireTime;
   @Column(name = "reset_key")
   private String resetKey;
   @Column(name = "ca_password")
   private String caPassword;
   @Column(name = "secret_key")
   private String secretKey;
   @Column(name = "si_name")
   private String siName;
   @Column(name = "si_address")
   private String siAddress;
   @Column(name = "si_support")
   private String siSupport;
   @Column(name = "future_checkin")
   private String futureCheckIn;
   @Column(name = "checkout_time")
   private String checkoutTime;
   @Column(name = "api_license")
   private String apiLicense;
   @Column(name = "license_data")
   private String licenseData;
   @Column(name = "mychoice_apikey")
   private String mychoiceApikey;
   @Column(name = "support_language")
   private String supportLanguage;
   @Column(name = "default_language")
   private String defaultLanguage;
   @Column(name = "support_roomtype")
   private String supportRoomtype;
   @Column(name = "room_type")
   private String roomType;

   public boolean isWeatherServiceEnabled() {
      return null != this.weatherService && this.weatherService.equals("enable");
   }

   public int getRealRefreshRate() {
      int targetRate = 30;
      if (null != this.refreshRate) {
         targetRate = Integer.valueOf(this.refreshRate);
         if (targetRate < 15) {
            targetRate = 15;
         }
      }

      return targetRate;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getWeatherService() {
      return this.weatherService;
   }

   public void setWeatherService(String weatherService) {
      this.weatherService = weatherService;
   }

   public String getRefreshRate() {
      return this.refreshRate;
   }

   public void setRefreshRate(String refreshRate) {
      this.refreshRate = refreshRate;
   }

   public String getDatabaseVerison() {
      return this.databaseVerison;
   }

   public void setDatabaseVerison(String databaseVerison) {
      this.databaseVerison = databaseVerison;
   }

   public String getCmndIp() {
      return this.cmndIp;
   }

   public void setCmndIp(String cmndIp) {
      this.cmndIp = cmndIp;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public Date getExpireTime() {
      return this.expireTime;
   }

   public void setExpireTime(Date expireTime) {
      this.expireTime = expireTime;
   }

   public String getResetKey() {
      return this.resetKey;
   }

   public void setResetKey(String resetKey) {
      this.resetKey = resetKey;
   }

   public String getCaPassword() {
      return this.caPassword;
   }

   public void setCaPassword(String caPassword) {
      this.caPassword = caPassword;
   }

   public String getSecretKey() {
      return this.secretKey;
   }

   public void setSecretKey(String secretKey) {
      this.secretKey = secretKey;
   }

   public String getSiName() {
      return this.siName;
   }

   public void setSiName(String siName) {
      this.siName = siName;
   }

   public String getSiAddress() {
      return this.siAddress;
   }

   public void setSiAddress(String siAddress) {
      this.siAddress = siAddress;
   }

   public String getSiSupport() {
      return this.siSupport;
   }

   public void setSiSupport(String siSupport) {
      this.siSupport = siSupport;
   }

   public String getCheckoutTime() {
      return this.checkoutTime;
   }

   public void setCheckoutTime(String checkoutTime) {
      this.checkoutTime = checkoutTime;
   }

   public String getApiLicense() {
      return this.apiLicense;
   }

   public void setApiLicense(String apiLicense) {
      this.apiLicense = apiLicense;
   }

   public String getFutureCheckIn() {
      return this.futureCheckIn;
   }

   public void setFutureCheckIn(String futureCheckIn) {
      this.futureCheckIn = futureCheckIn;
   }

   public String getMychoiceApikey() {
      return this.mychoiceApikey;
   }

   public void setMychoiceApikey(String mychoiceApikey) {
      this.mychoiceApikey = mychoiceApikey;
   }

   public String getSupportLanguage() {
      return this.supportLanguage;
   }

   public void setSupportLanguage(String supportLanguage) {
      this.supportLanguage = supportLanguage;
   }

   public String getDefaultLanguage() {
      return this.defaultLanguage;
   }

   public void setDefaultLanguage(String defaultLanguage) {
      this.defaultLanguage = defaultLanguage;
   }

   public String getRoomType() {
      return this.roomType;
   }

   public void setRoomType(String roomType) {
      this.roomType = roomType;
   }

   public String getSupportRoomtype() {
      return this.supportRoomtype;
   }

   public void setSupportRoomtype(String supportRoomtype) {
      this.supportRoomtype = supportRoomtype;
   }

   public String getLicenseData() {
      return this.licenseData;
   }

   public void setLicenseData(String licenseData) {
      this.licenseData = licenseData;
   }
}
