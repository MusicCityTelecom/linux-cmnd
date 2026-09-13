package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialReqDetailsType", propOrder = {"seatRequests", "specialServiceRequests", "otherServiceInformations", "remarks", "specialRemarks"})
public class SpecialReqDetailsType {
   @XmlElement(name = "SeatRequests")
   protected SpecialReqDetailsType.SeatRequests seatRequests;
   @XmlElement(name = "SpecialServiceRequests")
   protected SpecialReqDetailsType.SpecialServiceRequests specialServiceRequests;
   @XmlElement(name = "OtherServiceInformations")
   protected SpecialReqDetailsType.OtherServiceInformations otherServiceInformations;
   @XmlElement(name = "Remarks")
   protected SpecialReqDetailsType.Remarks remarks;
   @XmlElement(name = "SpecialRemarks")
   protected SpecialReqDetailsType.SpecialRemarks specialRemarks;

   public SpecialReqDetailsType.SeatRequests getSeatRequests() {
      return this.seatRequests;
   }

   public void setSeatRequests(SpecialReqDetailsType.SeatRequests value) {
      this.seatRequests = value;
   }

   public SpecialReqDetailsType.SpecialServiceRequests getSpecialServiceRequests() {
      return this.specialServiceRequests;
   }

   public void setSpecialServiceRequests(SpecialReqDetailsType.SpecialServiceRequests value) {
      this.specialServiceRequests = value;
   }

   public SpecialReqDetailsType.OtherServiceInformations getOtherServiceInformations() {
      return this.otherServiceInformations;
   }

   public void setOtherServiceInformations(SpecialReqDetailsType.OtherServiceInformations value) {
      this.otherServiceInformations = value;
   }

   public SpecialReqDetailsType.Remarks getRemarks() {
      return this.remarks;
   }

   public void setRemarks(SpecialReqDetailsType.Remarks value) {
      this.remarks = value;
   }

   public SpecialReqDetailsType.SpecialRemarks getSpecialRemarks() {
      return this.specialRemarks;
   }

   public void setSpecialRemarks(SpecialReqDetailsType.SpecialRemarks value) {
      this.specialRemarks = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "otherServiceInformation")
   public static class OtherServiceInformations {
      @XmlElement(name = "OtherServiceInformation", required = true)
      protected List<SpecialReqDetailsType.OtherServiceInformations.OtherServiceInformation> otherServiceInformation;

      public List<SpecialReqDetailsType.OtherServiceInformations.OtherServiceInformation> getOtherServiceInformation() {
         if (this.otherServiceInformation == null) {
            this.otherServiceInformation = new ArrayList<>();
         }

         return this.otherServiceInformation;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class OtherServiceInformation extends OtherServiceInfoType {
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "Operation")
         protected ActionType operation;

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }

         public ActionType getOperation() {
            return this.operation;
         }

         public void setOperation(ActionType value) {
            this.operation = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "remark")
   public static class Remarks {
      @XmlElement(name = "Remark", required = true)
      protected List<SpecialReqDetailsType.Remarks.Remark> remark;

      public List<SpecialReqDetailsType.Remarks.Remark> getRemark() {
         if (this.remark == null) {
            this.remark = new ArrayList<>();
         }

         return this.remark;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class Remark {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "RPH")
         protected String rph;
         @XmlAttribute(name = "Operation")
         protected ActionType operation;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }

         public ActionType getOperation() {
            return this.operation;
         }

         public void setOperation(ActionType value) {
            this.operation = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "seatRequest")
   public static class SeatRequests {
      @XmlElement(name = "SeatRequest", required = true)
      protected List<SpecialReqDetailsType.SeatRequests.SeatRequest> seatRequest;

      public List<SpecialReqDetailsType.SeatRequests.SeatRequest> getSeatRequest() {
         if (this.seatRequest == null) {
            this.seatRequest = new ArrayList<>();
         }

         return this.seatRequest;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class SeatRequest extends SeatRequestType {
         @XmlAttribute(name = "TravelerRefNumberRPHList")
         protected List<String> travelerRefNumberRPHList;
         @XmlAttribute(name = "FlightRefNumberRPHList")
         protected List<String> flightRefNumberRPHList;
         @XmlAttribute(name = "PartialSeatingInd")
         protected Boolean partialSeatingInd;

         public List<String> getTravelerRefNumberRPHList() {
            if (this.travelerRefNumberRPHList == null) {
               this.travelerRefNumberRPHList = new ArrayList<>();
            }

            return this.travelerRefNumberRPHList;
         }

         public List<String> getFlightRefNumberRPHList() {
            if (this.flightRefNumberRPHList == null) {
               this.flightRefNumberRPHList = new ArrayList<>();
            }

            return this.flightRefNumberRPHList;
         }

         public Boolean isPartialSeatingInd() {
            return this.partialSeatingInd;
         }

         public void setPartialSeatingInd(Boolean value) {
            this.partialSeatingInd = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "specialRemark")
   public static class SpecialRemarks {
      @XmlElement(name = "SpecialRemark", required = true)
      protected List<SpecialReqDetailsType.SpecialRemarks.SpecialRemark> specialRemark;

      public List<SpecialReqDetailsType.SpecialRemarks.SpecialRemark> getSpecialRemark() {
         if (this.specialRemark == null) {
            this.specialRemark = new ArrayList<>();
         }

         return this.specialRemark;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "flightLeg")
      public static class SpecialRemark extends SpecialRemarkType {
         @XmlElement(name = "FlightLeg")
         protected FlightLegType flightLeg;
         @XmlAttribute(name = "Operation")
         protected ActionType operation;
         @XmlAttribute(name = "RPH")
         protected String rph;

         public FlightLegType getFlightLeg() {
            return this.flightLeg;
         }

         public void setFlightLeg(FlightLegType value) {
            this.flightLeg = value;
         }

         public ActionType getOperation() {
            return this.operation;
         }

         public void setOperation(ActionType value) {
            this.operation = value;
         }

         public String getRPH() {
            return this.rph;
         }

         public void setRPH(String value) {
            this.rph = value;
         }
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "specialServiceRequest")
   public static class SpecialServiceRequests {
      @XmlElement(name = "SpecialServiceRequest", required = true)
      protected List<SpecialReqDetailsType.SpecialServiceRequests.SpecialServiceRequest> specialServiceRequest;

      public List<SpecialReqDetailsType.SpecialServiceRequests.SpecialServiceRequest> getSpecialServiceRequest() {
         if (this.specialServiceRequest == null) {
            this.specialServiceRequest = new ArrayList<>();
         }

         return this.specialServiceRequest;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "flightLeg")
      public static class SpecialServiceRequest extends SpecialServiceRequestType {
         @XmlElement(name = "FlightLeg")
         protected FlightLegType flightLeg;
         @XmlAttribute(name = "TravelerRefNumberRPHList")
         protected List<String> travelerRefNumberRPHList;
         @XmlAttribute(name = "FlightRefNumberRPHList")
         protected List<String> flightRefNumberRPHList;
         @XmlAttribute(name = "BirthDate")
         @XmlSchemaType(name = "date")
         protected XMLGregorianCalendar birthDate;

         public FlightLegType getFlightLeg() {
            return this.flightLeg;
         }

         public void setFlightLeg(FlightLegType value) {
            this.flightLeg = value;
         }

         public List<String> getTravelerRefNumberRPHList() {
            if (this.travelerRefNumberRPHList == null) {
               this.travelerRefNumberRPHList = new ArrayList<>();
            }

            return this.travelerRefNumberRPHList;
         }

         public List<String> getFlightRefNumberRPHList() {
            if (this.flightRefNumberRPHList == null) {
               this.flightRefNumberRPHList = new ArrayList<>();
            }

            return this.flightRefNumberRPHList;
         }

         public XMLGregorianCalendar getBirthDate() {
            return this.birthDate;
         }

         public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
         }
      }
   }
}
