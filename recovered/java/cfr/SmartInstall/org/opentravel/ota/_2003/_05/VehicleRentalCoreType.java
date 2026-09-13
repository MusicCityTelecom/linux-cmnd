/*
 * Decompiled with CFR 0.152.
 */
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
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.DistanceUnitNameType;
import org.opentravel.ota._2003._05.ItineraryItemRequestType;
import org.opentravel.ota._2003._05.ItineraryItemResponseType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.VehicleRentalTransactionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleRentalCoreType", propOrder={"pickUpLocation", "returnLocation"})
@XmlSeeAlso(value={VehicleRentalTransactionType.PickUpReturnDetails.class, ItineraryItemRequestType.RentalCar.class, ItineraryItemResponseType.RentalCar.class})
public class VehicleRentalCoreType {
    @XmlElement(name="PickUpLocation")
    protected List<PickUpLocation> pickUpLocation;
    @XmlElement(name="ReturnLocation")
    protected ReturnLocation returnLocation;
    @XmlAttribute(name="PickUpDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar pickUpDateTime;
    @XmlAttribute(name="ReturnDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar returnDateTime;
    @XmlAttribute(name="StartChargesDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar startChargesDateTime;
    @XmlAttribute(name="StopChargesDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar stopChargesDateTime;
    @XmlAttribute(name="OneWayIndicator")
    protected Boolean oneWayIndicator;
    @XmlAttribute(name="MultiIslandRentalDays")
    protected Integer multiIslandRentalDays;
    @XmlAttribute(name="Quantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger quantity;
    @XmlAttribute(name="DistUnitName")
    protected DistanceUnitNameType distUnitName;

    public List<PickUpLocation> getPickUpLocation() {
        if (this.pickUpLocation == null) {
            this.pickUpLocation = new ArrayList<PickUpLocation>();
        }
        return this.pickUpLocation;
    }

    public ReturnLocation getReturnLocation() {
        return this.returnLocation;
    }

    public void setReturnLocation(ReturnLocation value) {
        this.returnLocation = value;
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

    public XMLGregorianCalendar getStartChargesDateTime() {
        return this.startChargesDateTime;
    }

    public void setStartChargesDateTime(XMLGregorianCalendar value) {
        this.startChargesDateTime = value;
    }

    public XMLGregorianCalendar getStopChargesDateTime() {
        return this.stopChargesDateTime;
    }

    public void setStopChargesDateTime(XMLGregorianCalendar value) {
        this.stopChargesDateTime = value;
    }

    public Boolean isOneWayIndicator() {
        return this.oneWayIndicator;
    }

    public void setOneWayIndicator(Boolean value) {
        this.oneWayIndicator = value;
    }

    public Integer getMultiIslandRentalDays() {
        return this.multiIslandRentalDays;
    }

    public void setMultiIslandRentalDays(Integer value) {
        this.multiIslandRentalDays = value;
    }

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger value) {
        this.quantity = value;
    }

    public DistanceUnitNameType getDistUnitName() {
        return this.distUnitName;
    }

    public void setDistUnitName(DistanceUnitNameType value) {
        this.distUnitName = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ReturnLocation
    extends LocationType {
        @XmlAttribute(name="ExtendedLocationCode")
        protected String extendedLocationCode;
        @XmlAttribute(name="CounterLocation")
        protected String counterLocation;

        public String getExtendedLocationCode() {
            return this.extendedLocationCode;
        }

        public void setExtendedLocationCode(String value) {
            this.extendedLocationCode = value;
        }

        public String getCounterLocation() {
            return this.counterLocation;
        }

        public void setCounterLocation(String value) {
            this.counterLocation = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PickUpLocation
    extends LocationType {
        @XmlAttribute(name="ExtendedLocationCode")
        protected String extendedLocationCode;
        @XmlAttribute(name="CounterLocation")
        protected String counterLocation;

        public String getExtendedLocationCode() {
            return this.extendedLocationCode;
        }

        public void setExtendedLocationCode(String value) {
            this.extendedLocationCode = value;
        }

        public String getCounterLocation() {
            return this.counterLocation;
        }

        public void setCounterLocation(String value) {
            this.counterLocation = value;
        }
    }
}

