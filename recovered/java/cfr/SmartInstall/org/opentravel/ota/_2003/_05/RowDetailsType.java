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
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RowDetailsType", propOrder={"airSeats", "airRowCharacteristics"})
public class RowDetailsType {
    @XmlElement(name="AirSeats", required=true)
    protected AirSeats airSeats;
    @XmlElement(name="AirRowCharacteristics", required=true)
    protected AirRowCharacteristics airRowCharacteristics;
    @XmlAttribute(name="MaxNumberOfSeats")
    protected Integer maxNumberOfSeats;
    @XmlAttribute(name="RowNumber")
    protected BigInteger rowNumber;
    @XmlAttribute(name="AirBookDesigCode")
    protected String airBookDesigCode;
    @XmlAttribute(name="RowSequenceNumber")
    protected BigInteger rowSequenceNumber;

    public AirSeats getAirSeats() {
        return this.airSeats;
    }

    public void setAirSeats(AirSeats value) {
        this.airSeats = value;
    }

    public AirRowCharacteristics getAirRowCharacteristics() {
        return this.airRowCharacteristics;
    }

    public void setAirRowCharacteristics(AirRowCharacteristics value) {
        this.airRowCharacteristics = value;
    }

    public Integer getMaxNumberOfSeats() {
        return this.maxNumberOfSeats;
    }

    public void setMaxNumberOfSeats(Integer value) {
        this.maxNumberOfSeats = value;
    }

    public BigInteger getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(BigInteger value) {
        this.rowNumber = value;
    }

    public String getAirBookDesigCode() {
        return this.airBookDesigCode;
    }

    public void setAirBookDesigCode(String value) {
        this.airBookDesigCode = value;
    }

    public BigInteger getRowSequenceNumber() {
        return this.rowSequenceNumber;
    }

    public void setRowSequenceNumber(BigInteger value) {
        this.rowSequenceNumber = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"airSeat"})
    public static class AirSeats {
        @XmlElement(name="AirSeat", required=true)
        protected List<AirSeat> airSeat;

        public List<AirSeat> getAirSeat() {
            if (this.airSeat == null) {
                this.airSeat = new ArrayList<AirSeat>();
            }
            return this.airSeat;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class AirSeat {
            @XmlAttribute(name="RPH")
            protected String rph;
            @XmlAttribute(name="SeatAvailability")
            protected String seatAvailability;
            @XmlAttribute(name="SeatNumber")
            protected String seatNumber;
            @XmlAttribute(name="SeatCharacteristics", required=true)
            protected List<String> seatCharacteristics;
            @XmlAttribute(name="AirBookDesigCode")
            protected String airBookDesigCode;
            @XmlAttribute(name="SeatSequenceNumber")
            protected BigInteger seatSequenceNumber;

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }

            public String getSeatAvailability() {
                return this.seatAvailability;
            }

            public void setSeatAvailability(String value) {
                this.seatAvailability = value;
            }

            public String getSeatNumber() {
                return this.seatNumber;
            }

            public void setSeatNumber(String value) {
                this.seatNumber = value;
            }

            public List<String> getSeatCharacteristics() {
                if (this.seatCharacteristics == null) {
                    this.seatCharacteristics = new ArrayList<String>();
                }
                return this.seatCharacteristics;
            }

            public String getAirBookDesigCode() {
                return this.airBookDesigCode;
            }

            public void setAirBookDesigCode(String value) {
                this.airBookDesigCode = value;
            }

            public BigInteger getSeatSequenceNumber() {
                return this.seatSequenceNumber;
            }

            public void setSeatSequenceNumber(BigInteger value) {
                this.seatSequenceNumber = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AirRowCharacteristics {
        @XmlAttribute(name="CharacteristicList", required=true)
        protected List<String> characteristicList;

        public List<String> getCharacteristicList() {
            if (this.characteristicList == null) {
                this.characteristicList = new ArrayList<String>();
            }
            return this.characteristicList;
        }
    }
}

