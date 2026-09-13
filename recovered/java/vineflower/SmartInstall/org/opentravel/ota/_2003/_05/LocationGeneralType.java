package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationGeneralType", propOrder = {"cityName", "stateProv", "countryName"})
@XmlSeeAlso(CruisePackageType.Location.class)
public class LocationGeneralType {
   @XmlElement(name = "CityName")
   protected String cityName;
   @XmlElement(name = "StateProv")
   protected StateProvType stateProv;
   @XmlElement(name = "CountryName")
   protected CountryNameType countryName;

   public String getCityName() {
      return this.cityName;
   }

   public void setCityName(String value) {
      this.cityName = value;
   }

   public StateProvType getStateProv() {
      return this.stateProv;
   }

   public void setStateProv(StateProvType value) {
      this.stateProv = value;
   }

   public CountryNameType getCountryName() {
      return this.countryName;
   }

   public void setCountryName(CountryNameType value) {
      this.countryName = value;
   }
}
