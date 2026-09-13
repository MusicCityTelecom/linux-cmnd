/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ShipInfoType", propOrder={"ship", "shipLength", "shipVoltage"})
public class ShipInfoType {
    @XmlElement(name="Ship")
    protected Ship ship;
    @XmlElement(name="ShipLength")
    protected ShipLength shipLength;
    @XmlElement(name="ShipVoltage")
    protected ShipVoltage shipVoltage;
    @XmlAttribute(name="VendorCode")
    protected String vendorCode;
    @XmlAttribute(name="VendorName")
    protected String vendorName;
    @XmlAttribute(name="ShipCode")
    protected String shipCode;
    @XmlAttribute(name="ShipName")
    protected String shipName;
    @XmlAttribute(name="VendorCodeContext")
    protected String vendorCodeContext;

    public Ship getShip() {
        return this.ship;
    }

    public void setShip(Ship value) {
        this.ship = value;
    }

    public ShipLength getShipLength() {
        return this.shipLength;
    }

    public void setShipLength(ShipLength value) {
        this.shipLength = value;
    }

    public ShipVoltage getShipVoltage() {
        return this.shipVoltage;
    }

    public void setShipVoltage(ShipVoltage value) {
        this.shipVoltage = value;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public void setVendorCode(String value) {
        this.vendorCode = value;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String value) {
        this.vendorName = value;
    }

    public String getShipCode() {
        return this.shipCode;
    }

    public void setShipCode(String value) {
        this.shipCode = value;
    }

    public String getShipName() {
        return this.shipName;
    }

    public void setShipName(String value) {
        this.shipName = value;
    }

    public String getVendorCodeContext() {
        return this.vendorCodeContext;
    }

    public void setVendorCodeContext(String value) {
        this.vendorCodeContext = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ShipVoltage {
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
        }

        public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

        public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ShipLength {
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
        }

        public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

        public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Ship {
        @XmlAttribute(name="StabilizedIndicator")
        protected Boolean stabilizedIndicator;
        @XmlAttribute(name="RegistrationCountryCode")
        protected String registrationCountryCode;
        @XmlAttribute(name="RestaurantQuantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger restaurantQuantity;
        @XmlAttribute(name="ElevatorQuantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger elevatorQuantity;
        @XmlAttribute(name="MaxCrewQuantity")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger maxCrewQuantity;
        @XmlAttribute(name="MaxGuestQuantity")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger maxGuestQuantity;
        @XmlAttribute(name="CruisingSpeed")
        protected BigDecimal cruisingSpeed;
        @XmlAttribute(name="MaxSpeed")
        protected BigDecimal maxSpeed;
        @XmlAttribute(name="InsideCabinQuantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger insideCabinQuantity;
        @XmlAttribute(name="OutsideCabinQuantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger outsideCabinQuantity;
        @XmlAttribute(name="InauguralDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar inauguralDate;
        @XmlAttribute(name="RefurbishedDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar refurbishedDate;
        @XmlAttribute(name="BuiltDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar builtDate;
        @XmlAttribute(name="NextRefurbishDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar nextRefurbishDate;

        public Boolean isStabilizedIndicator() {
            return this.stabilizedIndicator;
        }

        public void setStabilizedIndicator(Boolean value) {
            this.stabilizedIndicator = value;
        }

        public String getRegistrationCountryCode() {
            return this.registrationCountryCode;
        }

        public void setRegistrationCountryCode(String value) {
            this.registrationCountryCode = value;
        }

        public BigInteger getRestaurantQuantity() {
            return this.restaurantQuantity;
        }

        public void setRestaurantQuantity(BigInteger value) {
            this.restaurantQuantity = value;
        }

        public BigInteger getElevatorQuantity() {
            return this.elevatorQuantity;
        }

        public void setElevatorQuantity(BigInteger value) {
            this.elevatorQuantity = value;
        }

        public BigInteger getMaxCrewQuantity() {
            return this.maxCrewQuantity;
        }

        public void setMaxCrewQuantity(BigInteger value) {
            this.maxCrewQuantity = value;
        }

        public BigInteger getMaxGuestQuantity() {
            return this.maxGuestQuantity;
        }

        public void setMaxGuestQuantity(BigInteger value) {
            this.maxGuestQuantity = value;
        }

        public BigDecimal getCruisingSpeed() {
            return this.cruisingSpeed;
        }

        public void setCruisingSpeed(BigDecimal value) {
            this.cruisingSpeed = value;
        }

        public BigDecimal getMaxSpeed() {
            return this.maxSpeed;
        }

        public void setMaxSpeed(BigDecimal value) {
            this.maxSpeed = value;
        }

        public BigInteger getInsideCabinQuantity() {
            return this.insideCabinQuantity;
        }

        public void setInsideCabinQuantity(BigInteger value) {
            this.insideCabinQuantity = value;
        }

        public BigInteger getOutsideCabinQuantity() {
            return this.outsideCabinQuantity;
        }

        public void setOutsideCabinQuantity(BigInteger value) {
            this.outsideCabinQuantity = value;
        }

        public XMLGregorianCalendar getInauguralDate() {
            return this.inauguralDate;
        }

        public void setInauguralDate(XMLGregorianCalendar value) {
            this.inauguralDate = value;
        }

        public XMLGregorianCalendar getRefurbishedDate() {
            return this.refurbishedDate;
        }

        public void setRefurbishedDate(XMLGregorianCalendar value) {
            this.refurbishedDate = value;
        }

        public XMLGregorianCalendar getBuiltDate() {
            return this.builtDate;
        }

        public void setBuiltDate(XMLGregorianCalendar value) {
            this.builtDate = value;
        }

        public XMLGregorianCalendar getNextRefurbishDate() {
            return this.nextRefurbishDate;
        }

        public void setNextRefurbishDate(XMLGregorianCalendar value) {
            this.nextRefurbishDate = value;
        }
    }
}

