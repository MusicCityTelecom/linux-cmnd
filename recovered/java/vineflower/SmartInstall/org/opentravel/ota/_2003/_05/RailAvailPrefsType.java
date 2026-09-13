package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RailAvailPrefsType", propOrder = {"operatorPref", "transportModes", "railAmenities", "discountType", "classCodes", "accommodationCategory"})
public class RailAvailPrefsType {
   @XmlElement(name = "OperatorPref")
   protected List<CompanyNamePrefType> operatorPref;
   @XmlElement(name = "TransportModes")
   protected List<RailAvailPrefsType.TransportModes> transportModes;
   @XmlElement(name = "RailAmenities")
   protected RailAmenityType railAmenities;
   @XmlElement(name = "DiscountType")
   protected List<RailAvailPrefsType.DiscountType> discountType;
   @XmlElement(name = "ClassCodes")
   protected List<ClassCodeType> classCodes;
   @XmlElement(name = "AccommodationCategory")
   protected AccommodationCategoryType accommodationCategory;

   public List<CompanyNamePrefType> getOperatorPref() {
      if (this.operatorPref == null) {
         this.operatorPref = new ArrayList<>();
      }

      return this.operatorPref;
   }

   public List<RailAvailPrefsType.TransportModes> getTransportModes() {
      if (this.transportModes == null) {
         this.transportModes = new ArrayList<>();
      }

      return this.transportModes;
   }

   public RailAmenityType getRailAmenities() {
      return this.railAmenities;
   }

   public void setRailAmenities(RailAmenityType value) {
      this.railAmenities = value;
   }

   public List<RailAvailPrefsType.DiscountType> getDiscountType() {
      if (this.discountType == null) {
         this.discountType = new ArrayList<>();
      }

      return this.discountType;
   }

   public List<ClassCodeType> getClassCodes() {
      if (this.classCodes == null) {
         this.classCodes = new ArrayList<>();
      }

      return this.classCodes;
   }

   public AccommodationCategoryType getAccommodationCategory() {
      return this.accommodationCategory;
   }

   public void setAccommodationCategory(AccommodationCategoryType value) {
      this.accommodationCategory = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class DiscountType {
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class TransportModes {
      @XmlAttribute(name = "Code", required = true)
      protected String code;
      @XmlAttribute(name = "CodeContext")
      protected String codeContext;
      @XmlAttribute(name = "Quantity")
      @XmlSchemaType(name = "nonNegativeInteger")
      protected BigInteger quantity;
      @XmlAttribute(name = "PreferLevel")
      protected PreferLevelType preferLevel;

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

      public BigInteger getQuantity() {
         return this.quantity;
      }

      public void setQuantity(BigInteger value) {
         this.quantity = value;
      }

      public PreferLevelType getPreferLevel() {
         return this.preferLevel;
      }

      public void setPreferLevel(PreferLevelType value) {
         this.preferLevel = value;
      }
   }
}
