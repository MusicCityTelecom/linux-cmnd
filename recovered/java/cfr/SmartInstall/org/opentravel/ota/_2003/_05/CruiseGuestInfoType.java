/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.CruiseGuestDetailType;
import org.opentravel.ota._2003._05.PaymentDetailType;
import org.opentravel.ota._2003._05.RelatedTravelerType;
import org.opentravel.ota._2003._05.ReservationIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CruiseGuestInfoType", propOrder={"reservationID", "guestDetails", "linkedBookings", "paymentOptions", "cancellationPenalty"})
public class CruiseGuestInfoType {
    @XmlElement(name="ReservationID")
    protected List<ReservationIDType> reservationID;
    @XmlElement(name="GuestDetails", required=true)
    protected GuestDetails guestDetails;
    @XmlElement(name="LinkedBookings")
    protected LinkedBookings linkedBookings;
    @XmlElement(name="PaymentOptions")
    protected PaymentOptions paymentOptions;
    @XmlElement(name="CancellationPenalty")
    protected CancellationPenalty cancellationPenalty;

    public List<ReservationIDType> getReservationID() {
        if (this.reservationID == null) {
            this.reservationID = new ArrayList<ReservationIDType>();
        }
        return this.reservationID;
    }

    public GuestDetails getGuestDetails() {
        return this.guestDetails;
    }

    public void setGuestDetails(GuestDetails value) {
        this.guestDetails = value;
    }

    public LinkedBookings getLinkedBookings() {
        return this.linkedBookings;
    }

    public void setLinkedBookings(LinkedBookings value) {
        this.linkedBookings = value;
    }

    public PaymentOptions getPaymentOptions() {
        return this.paymentOptions;
    }

    public void setPaymentOptions(PaymentOptions value) {
        this.paymentOptions = value;
    }

    public CancellationPenalty getCancellationPenalty() {
        return this.cancellationPenalty;
    }

    public void setCancellationPenalty(CancellationPenalty value) {
        this.cancellationPenalty = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"paymentOption"})
    public static class PaymentOptions {
        @XmlElement(name="PaymentOption", required=true)
        protected List<PaymentOption> paymentOption;

        public List<PaymentOption> getPaymentOption() {
            if (this.paymentOption == null) {
                this.paymentOption = new ArrayList<PaymentOption>();
            }
            return this.paymentOption;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class PaymentOption
        extends PaymentDetailType {
            @XmlAttribute(name="ExtendedIndicator")
            protected Boolean extendedIndicator;
            @XmlAttribute(name="PaymentPurpose")
            @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
            protected String paymentPurpose;
            @XmlAttribute(name="ExtendedDepositDate")
            protected String extendedDepositDate;
            @XmlAttribute(name="ReferenceNumber")
            protected String referenceNumber;

            public Boolean isExtendedIndicator() {
                return this.extendedIndicator;
            }

            public void setExtendedIndicator(Boolean value) {
                this.extendedIndicator = value;
            }

            public String getPaymentPurpose() {
                return this.paymentPurpose;
            }

            public void setPaymentPurpose(String value) {
                this.paymentPurpose = value;
            }

            public String getExtendedDepositDate() {
                return this.extendedDepositDate;
            }

            public void setExtendedDepositDate(String value) {
                this.extendedDepositDate = value;
            }

            public String getReferenceNumber() {
                return this.referenceNumber;
            }

            public void setReferenceNumber(String value) {
                this.referenceNumber = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"linkedBooking"})
    public static class LinkedBookings {
        @XmlElement(name="LinkedBooking", required=true)
        protected List<LinkedBooking> linkedBooking;

        public List<LinkedBooking> getLinkedBooking() {
            if (this.linkedBooking == null) {
                this.linkedBooking = new ArrayList<LinkedBooking>();
            }
            return this.linkedBooking;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class LinkedBooking
        extends RelatedTravelerType {
            @XmlAttribute(name="LinkTypeCode")
            protected List<String> linkTypeCode;

            public List<String> getLinkTypeCode() {
                if (this.linkTypeCode == null) {
                    this.linkTypeCode = new ArrayList<String>();
                }
                return this.linkTypeCode;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"guestDetail"})
    public static class GuestDetails {
        @XmlElement(name="GuestDetail", required=true)
        protected List<CruiseGuestDetailType> guestDetail;

        public List<CruiseGuestDetailType> getGuestDetail() {
            if (this.guestDetail == null) {
                this.guestDetail = new ArrayList<CruiseGuestDetailType>();
            }
            return this.guestDetail;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CancellationPenalty {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }
    }
}

