/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.BookingRulesType;
import org.opentravel.ota._2003._05.CommentType;
import org.opentravel.ota._2003._05.MembershipType;
import org.opentravel.ota._2003._05.ResGuestRPHsType;
import org.opentravel.ota._2003._05.RoomStayType;
import org.opentravel.ota._2003._05.ServiceRPHsType;
import org.opentravel.ota._2003._05.SpecialRequestType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomStaysType", propOrder={"roomStay"})
public class RoomStaysType {
    @XmlElement(name="RoomStay", required=true)
    protected List<RoomStay> roomStay;

    public List<RoomStay> getRoomStay() {
        if (this.roomStay == null) {
            this.roomStay = new ArrayList<RoomStay>();
        }
        return this.roomStay;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"resGuestRPHs", "memberships", "comments", "specialRequests", "serviceRPHs", "reference", "bookingRules"})
    public static class RoomStay
    extends RoomStayType {
        @XmlElement(name="ResGuestRPHs")
        protected ResGuestRPHsType resGuestRPHs;
        @XmlElement(name="Memberships")
        protected MembershipType memberships;
        @XmlElement(name="Comments")
        protected CommentType comments;
        @XmlElement(name="SpecialRequests")
        protected SpecialRequestType specialRequests;
        @XmlElement(name="ServiceRPHs")
        protected ServiceRPHsType serviceRPHs;
        @XmlElement(name="Reference")
        protected Reference reference;
        @XmlElement(name="BookingRules")
        protected BookingRulesType bookingRules;
        @XmlAttribute(name="IndexNumber")
        protected Integer indexNumber;

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

        public ServiceRPHsType getServiceRPHs() {
            return this.serviceRPHs;
        }

        public void setServiceRPHs(ServiceRPHsType value) {
            this.serviceRPHs = value;
        }

        public Reference getReference() {
            return this.reference;
        }

        public void setReference(Reference value) {
            this.reference = value;
        }

        public BookingRulesType getBookingRules() {
            return this.bookingRules;
        }

        public void setBookingRules(BookingRulesType value) {
            this.bookingRules = value;
        }

        public Integer getIndexNumber() {
            return this.indexNumber;
        }

        public void setIndexNumber(Integer value) {
            this.indexNumber = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Reference
        extends UniqueIDType {
            @XmlAttribute(name="DateTime")
            @XmlSchemaType(name="dateTime")
            protected XMLGregorianCalendar dateTime;

            public XMLGregorianCalendar getDateTime() {
                return this.dateTime;
            }

            public void setDateTime(XMLGregorianCalendar value) {
                this.dateTime = value;
            }
        }
    }
}

