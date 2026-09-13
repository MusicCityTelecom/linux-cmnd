package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "VehicleReservationSummaryType",
   propOrder = {"confID", "pickUpLocation", "returnLocation", "personName", "vehicle", "vendor", "tpaExtensions"}
)
public class VehicleReservationSummaryType {
   @XmlElement(name = "ConfID", required = true)
   protected List<VehicleReservationSummaryType.ConfID> confID;
   @XmlElement(name = "PickUpLocation")
   protected LocationType pickUpLocation;
   @XmlElement(name = "ReturnLocation")
   protected LocationType returnLocation;
   @XmlElement(name = "PersonName")
   protected PersonNameType personName;
   @XmlElement(name = "Vehicle")
   protected VehicleType vehicle;
   @XmlElement(name = "Vendor")
   protected CompanyNameType vendor;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "PickUpDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar pickUpDateTime;
   @XmlAttribute(name = "ReturnDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar returnDateTime;
   @XmlAttribute(name = "ReservationStatus")
   protected String reservationStatus;
   @XmlAttribute(name = "CreateDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar createDateTime;
   @XmlAttribute(name = "CreatorID")
   protected String creatorID;
   @XmlAttribute(name = "LastModifyDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar lastModifyDateTime;
   @XmlAttribute(name = "LastModifierID")
   protected String lastModifierID;
   @XmlAttribute(name = "PurgeDate")
   @XmlSchemaType(name = "date")
   protected XMLGregorianCalendar purgeDate;

   public List<VehicleReservationSummaryType.ConfID> getConfID() {
      if (this.confID == null) {
         this.confID = new ArrayList<>();
      }

      return this.confID;
   }

   public LocationType getPickUpLocation() {
      return this.pickUpLocation;
   }

   public void setPickUpLocation(LocationType value) {
      this.pickUpLocation = value;
   }

   public LocationType getReturnLocation() {
      return this.returnLocation;
   }

   public void setReturnLocation(LocationType value) {
      this.returnLocation = value;
   }

   public PersonNameType getPersonName() {
      return this.personName;
   }

   public void setPersonName(PersonNameType value) {
      this.personName = value;
   }

   public VehicleType getVehicle() {
      return this.vehicle;
   }

   public void setVehicle(VehicleType value) {
      this.vehicle = value;
   }

   public CompanyNameType getVendor() {
      return this.vendor;
   }

   public void setVendor(CompanyNameType value) {
      this.vendor = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public XMLGregorianCalendar getPickUpDateTime() {
      return this.pickUpDateTime;
   }

   public void setPickUpDateTime(XMLGregorianCalendar value) {
      this.pickUpDateTime = value;
   }

   public XMLGregorianCalendar getReturnDateTime() {
      return this.returnDateTime;
   }

   public void setReturnDateTime(XMLGregorianCalendar value) {
      this.returnDateTime = value;
   }

   public String getReservationStatus() {
      return this.reservationStatus;
   }

   public void setReservationStatus(String value) {
      this.reservationStatus = value;
   }

   public XMLGregorianCalendar getCreateDateTime() {
      return this.createDateTime;
   }

   public void setCreateDateTime(XMLGregorianCalendar value) {
      this.createDateTime = value;
   }

   public String getCreatorID() {
      return this.creatorID;
   }

   public void setCreatorID(String value) {
      this.creatorID = value;
   }

   public XMLGregorianCalendar getLastModifyDateTime() {
      return this.lastModifyDateTime;
   }

   public void setLastModifyDateTime(XMLGregorianCalendar value) {
      this.lastModifyDateTime = value;
   }

   public String getLastModifierID() {
      return this.lastModifierID;
   }

   public void setLastModifierID(String value) {
      this.lastModifierID = value;
   }

   public XMLGregorianCalendar getPurgeDate() {
      return this.purgeDate;
   }

   public void setPurgeDate(XMLGregorianCalendar value) {
      this.purgeDate = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class ConfID extends UniqueIDType {
      @XmlAttribute(name = "Status")
      protected String status;

      public String getStatus() {
         return this.status;
      }

      public void setStatus(String value) {
         this.status = value;
      }
   }
}
