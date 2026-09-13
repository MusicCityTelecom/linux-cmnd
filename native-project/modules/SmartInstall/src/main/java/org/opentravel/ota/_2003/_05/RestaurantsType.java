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
@XmlType(name = "RestaurantsType", propOrder = {"restaurant", "srvcInfoCodes"})
public class RestaurantsType {
   @XmlElement(name = "Restaurant")
   protected List<RestaurantsType.Restaurant> restaurant;
   @XmlElement(name = "SrvcInfoCodes")
   protected RestaurantsType.SrvcInfoCodes srvcInfoCodes;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public List<RestaurantsType.Restaurant> getRestaurant() {
      if (this.restaurant == null) {
         this.restaurant = new ArrayList<>();
      }

      return this.restaurant;
   }

   public RestaurantsType.SrvcInfoCodes getSrvcInfoCodes() {
      return this.srvcInfoCodes;
   }

   public void setSrvcInfoCodes(RestaurantsType.SrvcInfoCodes value) {
      this.srvcInfoCodes = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"features", "contactInfos", "srvcInfoCodes"})
   public static class Restaurant extends RestaurantType {
      @XmlElement(name = "Features")
      protected FeaturesType features;
      @XmlElement(name = "ContactInfos")
      protected ContactInfosType contactInfos;
      @XmlElement(name = "SrvcInfoCodes")
      protected RestaurantsType.Restaurant.SrvcInfoCodes srvcInfoCodes;
      @XmlAttribute(name = "AwardsRPH")
      protected String awardsRPH;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;

      public FeaturesType getFeatures() {
         return this.features;
      }

      public void setFeatures(FeaturesType value) {
         this.features = value;
      }

      public ContactInfosType getContactInfos() {
         return this.contactInfos;
      }

      public void setContactInfos(ContactInfosType value) {
         this.contactInfos = value;
      }

      public RestaurantsType.Restaurant.SrvcInfoCodes getSrvcInfoCodes() {
         return this.srvcInfoCodes;
      }

      public void setSrvcInfoCodes(RestaurantsType.Restaurant.SrvcInfoCodes value) {
         this.srvcInfoCodes = value;
      }

      public String getAwardsRPH() {
         return this.awardsRPH;
      }

      public void setAwardsRPH(String value) {
         this.awardsRPH = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "srvcInfoCode")
      public static class SrvcInfoCodes {
         @XmlElement(name = "SrvcInfoCode", required = true)
         protected List<RestaurantsType.Restaurant.SrvcInfoCodes.SrvcInfoCode> srvcInfoCode;

         public List<RestaurantsType.Restaurant.SrvcInfoCodes.SrvcInfoCode> getSrvcInfoCode() {
            if (this.srvcInfoCode == null) {
               this.srvcInfoCode = new ArrayList<>();
            }

            return this.srvcInfoCode;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class SrvcInfoCode {
            @XmlAttribute(name = "Code")
            protected String code;
            @XmlAttribute(name = "Quantity")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger quantity;
            @XmlAttribute(name = "CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name = "Removal")
            protected Boolean removal;

            public String getCode() {
               return this.code;
            }

            public void setCode(String value) {
               this.code = value;
            }

            public BigInteger getQuantity() {
               return this.quantity;
            }

            public void setQuantity(BigInteger value) {
               this.quantity = value;
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
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "srvcInfoCode")
   public static class SrvcInfoCodes {
      @XmlElement(name = "SrvcInfoCode", required = true)
      protected List<RestaurantsType.SrvcInfoCodes.SrvcInfoCode> srvcInfoCode;

      public List<RestaurantsType.SrvcInfoCodes.SrvcInfoCode> getSrvcInfoCode() {
         if (this.srvcInfoCode == null) {
            this.srvcInfoCode = new ArrayList<>();
         }

         return this.srvcInfoCode;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class SrvcInfoCode {
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Removal")
         protected Boolean removal;
         @XmlAttribute(name = "Quantity")
         @XmlSchemaType(name = "nonNegativeInteger")
         protected BigInteger quantity;

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
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

         public BigInteger getQuantity() {
            return this.quantity;
         }

         public void setQuantity(BigInteger value) {
            this.quantity = value;
         }
      }
   }
}
