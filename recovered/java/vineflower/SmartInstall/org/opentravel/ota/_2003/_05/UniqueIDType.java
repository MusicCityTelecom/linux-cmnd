package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGAuthorizationType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UniqueID_Type", propOrder = "companyName")
@XmlSeeAlso(
   {
         HTNGAuthorizationType.BookingReferenceID.class,
         OTAHotelAvailRS.RoomStays.RoomStay.Reference.class,
         OTAReadRQ.ReadRequests.HotelReadRequest.UserID.class,
         OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID.class,
         SourceType.RequestorID.class,
         AirReservationType.BookingReferenceID.class,
         AuthorizationType.BookingReferenceID.class,
         VehicleAvailCoreType.Reference.class,
         VehicleReservationRQAdditionalInfoType.Reference.class,
         VehicleReservationSummaryType.ConfID.class,
         VehicleSegmentCoreType.ConfID.class,
         ProfileType.UserID.class,
         RoomStaysType.RoomStay.Reference.class,
         ReservationIDType.class
   }
)
public class UniqueIDType {
   @XmlElement(name = "CompanyName")
   protected CompanyNameType companyName;
   @XmlAttribute(name = "URL")
   @XmlSchemaType(name = "anyURI")
   protected String url;
   @XmlAttribute(name = "Type", required = true)
   protected String type;
   @XmlAttribute(name = "Instance")
   protected String instance;
   @XmlAttribute(name = "ID_Context")
   protected String idContext;
   @XmlAttribute(name = "ID", required = true)
   protected String id;

   public CompanyNameType getCompanyName() {
      return this.companyName;
   }

   public void setCompanyName(CompanyNameType value) {
      this.companyName = value;
   }

   public String getURL() {
      return this.url;
   }

   public void setURL(String value) {
      this.url = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getInstance() {
      return this.instance;
   }

   public void setInstance(String value) {
      this.instance = value;
   }

   public String getIDContext() {
      return this.idContext;
   }

   public void setIDContext(String value) {
      this.idContext = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }
}
