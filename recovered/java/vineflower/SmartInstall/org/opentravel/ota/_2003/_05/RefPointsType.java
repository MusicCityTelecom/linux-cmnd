package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RefPointsType", propOrder = "refPoint")
@XmlSeeAlso(AreaInfoType.RefPoints.class)
public class RefPointsType {
   @XmlElement(name = "RefPoint", required = true)
   protected List<RefPointsType.RefPoint> refPoint;

   public List<RefPointsType.RefPoint> getRefPoint() {
      if (this.refPoint == null) {
         this.refPoint = new ArrayList<>();
      }

      return this.refPoint;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"multimediaDescriptions", "descriptiveText"})
   public static class RefPoint extends RelativePositionType {
      @XmlElement(name = "MultimediaDescriptions")
      protected MultimediaDescriptionsType multimediaDescriptions;
      @XmlElement(name = "DescriptiveText")
      protected String descriptiveText;
      @XmlAttribute(name = "RefPointCategoryCode")
      protected String refPointCategoryCode;
      @XmlAttribute(name = "Proximity")
      protected String proximity;
      @XmlAttribute(name = "CityCode")
      protected String cityCode;
      @XmlAttribute(name = "RefPointName")
      protected String refPointName;
      @XmlAttribute(name = "ExistsCode")
      protected String existsCode;
      @XmlAttribute(name = "StateProv")
      protected String stateProv;
      @XmlAttribute(name = "CountryCode")
      protected String countryCode;
      @XmlAttribute(name = "ID")
      protected String id;
      @XmlAttribute(name = "Latitude")
      protected String latitude;
      @XmlAttribute(name = "Longitude")
      protected String longitude;
      @XmlAttribute(name = "Altitude")
      protected String altitude;
      @XmlAttribute(name = "AltitudeUnitOfMeasureCode")
      protected String altitudeUnitOfMeasureCode;
      @XmlAttribute(name = "PositionAccuracy")
      protected String positionAccuracy;
      @XmlAttribute(name = "URI")
      @XmlSchemaType(name = "anyURI")
      protected String uri;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "CodeDetail")
      protected String codeDetail;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;

      public MultimediaDescriptionsType getMultimediaDescriptions() {
         return this.multimediaDescriptions;
      }

      public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
         this.multimediaDescriptions = value;
      }

      public String getDescriptiveText() {
         return this.descriptiveText;
      }

      public void setDescriptiveText(String value) {
         this.descriptiveText = value;
      }

      public String getRefPointCategoryCode() {
         return this.refPointCategoryCode;
      }

      public void setRefPointCategoryCode(String value) {
         this.refPointCategoryCode = value;
      }

      public String getProximity() {
         return this.proximity;
      }

      public void setProximity(String value) {
         this.proximity = value;
      }

      public String getCityCode() {
         return this.cityCode;
      }

      public void setCityCode(String value) {
         this.cityCode = value;
      }

      public String getRefPointName() {
         return this.refPointName;
      }

      public void setRefPointName(String value) {
         this.refPointName = value;
      }

      public String getExistsCode() {
         return this.existsCode;
      }

      public void setExistsCode(String value) {
         this.existsCode = value;
      }

      public String getStateProv() {
         return this.stateProv;
      }

      public void setStateProv(String value) {
         this.stateProv = value;
      }

      public String getCountryCode() {
         return this.countryCode;
      }

      public void setCountryCode(String value) {
         this.countryCode = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      public String getLatitude() {
         return this.latitude;
      }

      public void setLatitude(String value) {
         this.latitude = value;
      }

      public String getLongitude() {
         return this.longitude;
      }

      public void setLongitude(String value) {
         this.longitude = value;
      }

      public String getAltitude() {
         return this.altitude;
      }

      public void setAltitude(String value) {
         this.altitude = value;
      }

      public String getAltitudeUnitOfMeasureCode() {
         return this.altitudeUnitOfMeasureCode;
      }

      public void setAltitudeUnitOfMeasureCode(String value) {
         this.altitudeUnitOfMeasureCode = value;
      }

      public String getPositionAccuracy() {
         return this.positionAccuracy;
      }

      public void setPositionAccuracy(String value) {
         this.positionAccuracy = value;
      }

      public String getURI() {
         return this.uri;
      }

      public void setURI(String value) {
         this.uri = value;
      }

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getCodeContext() {
         return this.codeContext;
      }

      public void setCodeContext(String value) {
         this.codeContext = value;
      }

      public String getCodeDetail() {
         return this.codeDetail;
      }

      public void setCodeDetail(String value) {
         this.codeDetail = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }
   }
}
