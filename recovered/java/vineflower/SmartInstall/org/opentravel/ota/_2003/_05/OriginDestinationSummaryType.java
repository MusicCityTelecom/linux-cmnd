package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginDestinationSummaryType", propOrder = {"originLocation", "destinationLocation", "departureDateTime", "arrivalDateTime", "trainInfo"})
public class OriginDestinationSummaryType {
   @XmlElement(name = "OriginLocation", required = true)
   protected LocationType originLocation;
   @XmlElement(name = "DestinationLocation", required = true)
   protected LocationType destinationLocation;
   @XmlElement(name = "DepartureDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar departureDateTime;
   @XmlElement(name = "ArrivalDateTime")
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar arrivalDateTime;
   @XmlElement(name = "TrainInfo")
   protected List<TrainInfoType> trainInfo;

   public LocationType getOriginLocation() {
      return this.originLocation;
   }

   public void setOriginLocation(LocationType value) {
      this.originLocation = value;
   }

   public LocationType getDestinationLocation() {
      return this.destinationLocation;
   }

   public void setDestinationLocation(LocationType value) {
      this.destinationLocation = value;
   }

   public XMLGregorianCalendar getDepartureDateTime() {
      return this.departureDateTime;
   }

   public void setDepartureDateTime(XMLGregorianCalendar value) {
      this.departureDateTime = value;
   }

   public XMLGregorianCalendar getArrivalDateTime() {
      return this.arrivalDateTime;
   }

   public void setArrivalDateTime(XMLGregorianCalendar value) {
      this.arrivalDateTime = value;
   }

   public List<TrainInfoType> getTrainInfo() {
      if (this.trainInfo == null) {
         this.trainInfo = new ArrayList<>();
      }

      return this.trainInfo;
   }
}
