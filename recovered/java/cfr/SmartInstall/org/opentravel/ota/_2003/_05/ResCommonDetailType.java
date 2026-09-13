/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.opentravel.ota._2003._05.CancelPenaltiesType;
import org.opentravel.ota._2003._05.CommentType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.FeesType;
import org.opentravel.ota._2003._05.GuaranteeType;
import org.opentravel.ota._2003._05.GuestCountType;
import org.opentravel.ota._2003._05.MembershipType;
import org.opentravel.ota._2003._05.RequiredPaymentsType;
import org.opentravel.ota._2003._05.ResGlobalInfoType;
import org.opentravel.ota._2003._05.ResGuestRPHsType;
import org.opentravel.ota._2003._05.SpecialRequestType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResCommonDetailType", propOrder={"guestCounts", "timeSpan", "resGuestRPHs", "memberships", "comments", "specialRequests", "guarantee", "depositPayments", "cancelPenalties", "fees", "total"})
@XmlSeeAlso(value={ResGlobalInfoType.class})
public class ResCommonDetailType {
    @XmlElement(name="GuestCounts")
    protected GuestCountType guestCounts;
    @XmlElement(name="TimeSpan")
    protected TimeSpan timeSpan;
    @XmlElement(name="ResGuestRPHs")
    protected ResGuestRPHsType resGuestRPHs;
    @XmlElement(name="Memberships")
    protected MembershipType memberships;
    @XmlElement(name="Comments")
    protected CommentType comments;
    @XmlElement(name="SpecialRequests")
    protected SpecialRequestType specialRequests;
    @XmlElement(name="Guarantee")
    protected GuaranteeType guarantee;
    @XmlElement(name="DepositPayments")
    protected RequiredPaymentsType depositPayments;
    @XmlElement(name="CancelPenalties")
    protected CancelPenaltiesType cancelPenalties;
    @XmlElement(name="Fees")
    protected FeesType fees;
    @XmlElement(name="Total")
    protected TotalType total;

    public GuestCountType getGuestCounts() {
        return this.guestCounts;
    }

    public void setGuestCounts(GuestCountType value) {
        this.guestCounts = value;
    }

    public TimeSpan getTimeSpan() {
        return this.timeSpan;
    }

    public void setTimeSpan(TimeSpan value) {
        this.timeSpan = value;
    }

    public ResGuestRPHsType getResGuestRPHs() {
        return this.resGuestRPHs;
    }

    public void setResGuestRPHs(ResGuestRPHsType value) {
        this.resGuestRPHs = value;
    }

    public MembershipType getMemberships() {
        return this.memberships;
    }

    public void setMemberships(MembershipType value) {
        this.memberships = value;
    }

    public CommentType getComments() {
        return this.comments;
    }

    public void setComments(CommentType value) {
        this.comments = value;
    }

    public SpecialRequestType getSpecialRequests() {
        return this.specialRequests;
    }

    public void setSpecialRequests(SpecialRequestType value) {
        this.specialRequests = value;
    }

    public GuaranteeType getGuarantee() {
        return this.guarantee;
    }

    public void setGuarantee(GuaranteeType value) {
        this.guarantee = value;
    }

    public RequiredPaymentsType getDepositPayments() {
        return this.depositPayments;
    }

    public void setDepositPayments(RequiredPaymentsType value) {
        this.depositPayments = value;
    }

    public CancelPenaltiesType getCancelPenalties() {
        return this.cancelPenalties;
    }

    public void setCancelPenalties(CancelPenaltiesType value) {
        this.cancelPenalties = value;
    }

    public FeesType getFees() {
        return this.fees;
    }

    public void setFees(FeesType value) {
        this.fees = value;
    }

    public TotalType getTotal() {
        return this.total;
    }

    public void setTotal(TotalType value) {
        this.total = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TimeSpan
    extends DateTimeSpanType {
        @XmlAttribute(name="Increment")
        protected Duration increment;

        public Duration getIncrement() {
            return this.increment;
        }

        public void setIncrement(Duration value) {
            this.increment = value;
        }
    }
}

