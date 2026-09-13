package com.tpvision.smartinstall.util;

public class Location {
   private String country;
   private String city;
   private String geonameid;
   private String pin;
   private String addressLin1;
   private String addressLin2;
   private String hotelName;
   private String name;
   private String phoneNumber;
   private String phoneWebsite;

   public String getCountry() {
      return this.country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getCity() {
      return this.city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getGeonameid() {
      return this.geonameid;
   }

   public void setGeonameid(String geonameid) {
      this.geonameid = geonameid;
   }

   public String getPin() {
      return this.pin;
   }

   public void setPin(String pin) {
      this.pin = pin;
   }

   public String getAddressLin1() {
      return this.addressLin1;
   }

   public void setAddressLin1(String addressLin1) {
      this.addressLin1 = addressLin1;
   }

   public String getAddressLin2() {
      return this.addressLin2;
   }

   public void setAddressLin2(String addressLin2) {
      this.addressLin2 = addressLin2;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getPhoneWebsite() {
      return this.phoneWebsite;
   }

   public void setPhoneWebsite(String phoneWebsite) {
      this.phoneWebsite = phoneWebsite;
   }

   public Location() {
   }

   public Location(
      String hotelName,
      String country,
      String city,
      String geonameid,
      String pin,
      String addressLine1,
      String addressLine2,
      String name,
      String phoneNumber,
      String phoneWebsite
   ) {
      this.country = country;
      this.city = city;
      this.geonameid = geonameid;
      this.pin = pin;
      this.addressLin1 = addressLine1;
      this.addressLin2 = addressLine2;
      this.hotelName = hotelName;
      this.name = name;
      this.phoneNumber = phoneNumber;
      this.phoneWebsite = phoneWebsite;
   }

   public String getHotelName() {
      return this.hotelName;
   }

   public void setHotelName(String hotelName) {
      this.hotelName = hotelName;
   }

   public boolean isEmpty() {
      boolean ret = false;
      if (null == this.addressLin1 || null == this.city || null == this.country || null == this.hotelName) {
         ret = true;
      }

      return ret;
   }
}
