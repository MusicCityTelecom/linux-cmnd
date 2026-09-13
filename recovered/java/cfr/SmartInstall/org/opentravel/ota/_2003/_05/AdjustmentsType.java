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
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AdjustmentsType", propOrder={"adjustment"})
public class AdjustmentsType {
    @XmlElement(name="Adjustment", required=true)
    protected List<Adjustment> adjustment;
    @XmlAttribute(name="RequestID")
    protected String requestID;

    public List<Adjustment> getAdjustment() {
        if (this.adjustment == null) {
            this.adjustment = new ArrayList<Adjustment>();
        }
        return this.adjustment;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String value) {
        this.requestID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Adjustment {
        @XmlAttribute(name="ReservationOriginatorCode")
        protected String reservationOriginatorCode;
        @XmlAttribute(name="ConfirmationID")
        protected String confirmationID;
        @XmlAttribute(name="ReservationID")
        protected String reservationID;
        @XmlAttribute(name="RoomInventoryCode")
        protected String roomInventoryCode;
        @XmlAttribute(name="AdjustReason")
        protected String adjustReason;
        @XmlAttribute(name="Sequence")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger sequence;
        @XmlAttribute(name="InvValue")
        protected BigInteger invValue;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;
        @XmlAttribute(name="PromotionCode")
        protected String promotionCode;
        @XmlAttribute(name="PromotionVendorCode")
        protected List<String> promotionVendorCode;

        public String getReservationOriginatorCode() {
            return this.reservationOriginatorCode;
        }

        public void setReservationOriginatorCode(String value) {
            this.reservationOriginatorCode = value;
        }

        public String getConfirmationID() {
            return this.confirmationID;
        }

        public void setConfirmationID(String value) {
            this.confirmationID = value;
        }

        public String getReservationID() {
            return this.reservationID;
        }

        public void setReservationID(String value) {
            this.reservationID = value;
        }

        public String getRoomInventoryCode() {
            return this.roomInventoryCode;
        }

        public void setRoomInventoryCode(String value) {
            this.roomInventoryCode = value;
        }

        public String getAdjustReason() {
            return this.adjustReason;
        }

        public void setAdjustReason(String value) {
            this.adjustReason = value;
        }

        public BigInteger getSequence() {
            return this.sequence;
        }

        public void setSequence(BigInteger value) {
            this.sequence = value;
        }

        public BigInteger getInvValue() {
            return this.invValue;
        }

        public void setInvValue(BigInteger value) {
            this.invValue = value;
        }

        public String getStart() {
            return this.start;
        }

        public void setStart(String value) {
            this.start = value;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String value) {
            this.duration = value;
        }

        public String getEnd() {
            return this.end;
        }

        public void setEnd(String value) {
            this.end = value;
        }

        public String getPromotionCode() {
            return this.promotionCode;
        }

        public void setPromotionCode(String value) {
            this.promotionCode = value;
        }

        public List<String> getPromotionVendorCode() {
            if (this.promotionVendorCode == null) {
                this.promotionVendorCode = new ArrayList<String>();
            }
            return this.promotionVendorCode;
        }
    }
}

