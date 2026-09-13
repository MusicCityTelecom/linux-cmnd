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
@XmlType(
   name = "RestaurantType",
   propOrder = {"multimediaDescriptions", "relativePosition", "operationSchedules", "infoCodes", "cuisineCodes", "descriptiveText"}
)
@XmlSeeAlso(RestaurantsType.Restaurant.class)
public class RestaurantType {
   @XmlElement(name = "MultimediaDescriptions")
   protected RestaurantType.MultimediaDescriptions multimediaDescriptions;
   @XmlElement(name = "RelativePosition")
   protected RelativePositionType relativePosition;
   @XmlElement(name = "OperationSchedules")
   protected OperationSchedulesPlusChargeType operationSchedules;
   @XmlElement(name = "InfoCodes")
   protected RestaurantType.InfoCodes infoCodes;
   @XmlElement(name = "CuisineCodes")
   protected RestaurantType.CuisineCodes cuisineCodes;
   @XmlElement(name = "DescriptiveText")
   protected String descriptiveText;
   @XmlAttribute(name = "RestaurantName")
   protected String restaurantName;
   @XmlAttribute(name = "MaxSeatingCapacity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger maxSeatingCapacity;
   @XmlAttribute(name = "MaxSingleParty")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger maxSingleParty;
   @XmlAttribute(name = "InvCode")
   protected String invCode;
   @XmlAttribute(name = "OfferBreakfast")
   protected Boolean offerBreakfast;
   @XmlAttribute(name = "OfferLunch")
   protected Boolean offerLunch;
   @XmlAttribute(name = "OfferDinner")
   protected Boolean offerDinner;
   @XmlAttribute(name = "OfferBrunch")
   protected Boolean offerBrunch;
   @XmlAttribute(name = "ProximityCode")
   protected String proximityCode;
   @XmlAttribute(name = "Sort")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger sort;
   @XmlAttribute(name = "ReservationReqInd")
   protected Boolean reservationReqInd;
   @XmlAttribute(name = "ID")
   protected String id;

   public RestaurantType.MultimediaDescriptions getMultimediaDescriptions() {
      return this.multimediaDescriptions;
   }

   public void setMultimediaDescriptions(RestaurantType.MultimediaDescriptions value) {
      this.multimediaDescriptions = value;
   }

   public RelativePositionType getRelativePosition() {
      return this.relativePosition;
   }

   public void setRelativePosition(RelativePositionType value) {
      this.relativePosition = value;
   }

   public OperationSchedulesPlusChargeType getOperationSchedules() {
      return this.operationSchedules;
   }

   public void setOperationSchedules(OperationSchedulesPlusChargeType value) {
      this.operationSchedules = value;
   }

   public RestaurantType.InfoCodes getInfoCodes() {
      return this.infoCodes;
   }

   public void setInfoCodes(RestaurantType.InfoCodes value) {
      this.infoCodes = value;
   }

   public RestaurantType.CuisineCodes getCuisineCodes() {
      return this.cuisineCodes;
   }

   public void setCuisineCodes(RestaurantType.CuisineCodes value) {
      this.cuisineCodes = value;
   }

   public String getDescriptiveText() {
      return this.descriptiveText;
   }

   public void setDescriptiveText(String value) {
      this.descriptiveText = value;
   }

   public String getRestaurantName() {
      return this.restaurantName;
   }

   public void setRestaurantName(String value) {
      this.restaurantName = value;
   }

   public BigInteger getMaxSeatingCapacity() {
      return this.maxSeatingCapacity;
   }

   public void setMaxSeatingCapacity(BigInteger value) {
      this.maxSeatingCapacity = value;
   }

   public BigInteger getMaxSingleParty() {
      return this.maxSingleParty;
   }

   public void setMaxSingleParty(BigInteger value) {
      this.maxSingleParty = value;
   }

   public String getInvCode() {
      return this.invCode;
   }

   public void setInvCode(String value) {
      this.invCode = value;
   }

   public Boolean isOfferBreakfast() {
      return this.offerBreakfast;
   }

   public void setOfferBreakfast(Boolean value) {
      this.offerBreakfast = value;
   }

   public Boolean isOfferLunch() {
      return this.offerLunch;
   }

   public void setOfferLunch(Boolean value) {
      this.offerLunch = value;
   }

   public Boolean isOfferDinner() {
      return this.offerDinner;
   }

   public void setOfferDinner(Boolean value) {
      this.offerDinner = value;
   }

   public Boolean isOfferBrunch() {
      return this.offerBrunch;
   }

   public void setOfferBrunch(Boolean value) {
      this.offerBrunch = value;
   }

   public String getProximityCode() {
      return this.proximityCode;
   }

   public void setProximityCode(String value) {
      this.proximityCode = value;
   }

   public BigInteger getSort() {
      return this.sort;
   }

   public void setSort(BigInteger value) {
      this.sort = value;
   }

   public Boolean isReservationReqInd() {
      return this.reservationReqInd;
   }

   public void setReservationReqInd(Boolean value) {
      this.reservationReqInd = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "cuisineCode")
   public static class CuisineCodes {
      @XmlElement(name = "CuisineCode", required = true)
      protected List<RestaurantType.CuisineCodes.CuisineCode> cuisineCode;

      public List<RestaurantType.CuisineCodes.CuisineCode> getCuisineCode() {
         if (this.cuisineCode == null) {
            this.cuisineCode = new ArrayList<>();
         }

         return this.cuisineCode;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class CuisineCode {
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "IsMain")
         protected Boolean isMain;
         @XmlAttribute(name = "ExistsCode")
         protected String existsCode;
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

         public Boolean isIsMain() {
            return this.isMain;
         }

         public void setIsMain(Boolean value) {
            this.isMain = value;
         }

         public String getExistsCode() {
            return this.existsCode;
         }

         public void setExistsCode(String value) {
            this.existsCode = value;
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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "infoCode")
   public static class InfoCodes {
      @XmlElement(name = "InfoCode", required = true)
      protected List<RestaurantType.InfoCodes.InfoCode> infoCode;

      public List<RestaurantType.InfoCodes.InfoCode> getInfoCode() {
         if (this.infoCode == null) {
            this.infoCode = new ArrayList<>();
         }

         return this.infoCode;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class InfoCode {
         @XmlAttribute(name = "Name")
         protected String name;
         @XmlAttribute(name = "Code")
         protected String code;
         @XmlAttribute(name = "CodeDetail")
         protected String codeDetail;
         @XmlAttribute(name = "Removal")
         protected Boolean removal;

         public String getName() {
            return this.name;
         }

         public void setName(String value) {
            this.name = value;
         }

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
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class MultimediaDescriptions extends MultimediaDescriptionsType {
      @XmlAttribute(name = "Attire")
      protected String attire;

      public String getAttire() {
         return this.attire;
      }

      public void setAttire(String value) {
         this.attire = value;
      }
   }
}
