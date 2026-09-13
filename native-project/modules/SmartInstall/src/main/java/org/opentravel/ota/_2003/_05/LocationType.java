package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationType", propOrder = "value")
@XmlSeeAlso(
   {
         ConnectionType.ConnectionLocation.class,
         OriginDestinationInformationType.OriginLocation.class,
         OriginDestinationInformationType.DestinationLocation.class,
         AirportPrefType.class,
         VehicleAvailCoreType.VendorLocation.class,
         VehicleAvailCoreType.DropOffLocation.class,
         VehicleLocationAdditionalDetailsType.OneWayDropLocations.OneWayDropLocation.class,
         VerificationType.StartLocation.class,
         VerificationType.EndLocation.class,
         SailingBaseType.DeparturePort.class,
         SailingBaseType.ArrivalPort.class,
         VehicleRentalCoreType.PickUpLocation.class,
         VehicleRentalCoreType.ReturnLocation.class,
         RailConnectionType.ConnectionLocation.class,
         RailOriginDestinationInformationType.OriginLocation.class,
         RailOriginDestinationInformationType.DestinationLocation.class,
         StationType.class,
         ItemSearchCriterionType.CodeRef.class
   }
)
public class LocationType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "LocationCode")
   protected String locationCode;
   @XmlAttribute(name = "CodeContext")
   protected String codeContext;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getLocationCode() {
      return this.locationCode;
   }

   public void setLocationCode(String value) {
      this.locationCode = value;
   }

   public String getCodeContext() {
      return this.codeContext;
   }

   public void setCodeContext(String value) {
      this.codeContext = value;
   }
}
