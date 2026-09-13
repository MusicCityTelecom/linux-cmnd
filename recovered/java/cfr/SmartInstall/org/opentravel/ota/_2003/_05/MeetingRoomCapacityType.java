/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.FeeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MeetingRoomCapacityType", propOrder={"occupancy"})
public class MeetingRoomCapacityType {
    @XmlElement(name="Occupancy")
    protected Occupancy occupancy;
    @XmlAttribute(name="MeetingRoomFormatCode")
    protected String meetingRoomFormatCode;

    public Occupancy getOccupancy() {
        return this.occupancy;
    }

    public void setOccupancy(Occupancy value) {
        this.occupancy = value;
    }

    public String getMeetingRoomFormatCode() {
        return this.meetingRoomFormatCode;
    }

    public void setMeetingRoomFormatCode(String value) {
        this.meetingRoomFormatCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"minRoomCharge"})
    public static class Occupancy {
        @XmlElement(name="MinRoomCharge")
        protected FeeType minRoomCharge;
        @XmlAttribute(name="MinOccupancy")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger minOccupancy;
        @XmlAttribute(name="MaxOccupancy")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger maxOccupancy;
        @XmlAttribute(name="StandardOccupancy")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger standardOccupancy;

        public FeeType getMinRoomCharge() {
            return this.minRoomCharge;
        }

        public void setMinRoomCharge(FeeType value) {
            this.minRoomCharge = value;
        }

        public BigInteger getMinOccupancy() {
            return this.minOccupancy;
        }

        public void setMinOccupancy(BigInteger value) {
            this.minOccupancy = value;
        }

        public BigInteger getMaxOccupancy() {
            return this.maxOccupancy;
        }

        public void setMaxOccupancy(BigInteger value) {
            this.maxOccupancy = value;
        }

        public BigInteger getStandardOccupancy() {
            return this.standardOccupancy;
        }

        public void setStandardOccupancy(BigInteger value) {
            this.standardOccupancy = value;
        }
    }
}

