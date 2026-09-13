/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.BookingRulesType;
import org.opentravel.ota._2003._05.HotelReservationIDsType;
import org.opentravel.ota._2003._05.ProfilesType;
import org.opentravel.ota._2003._05.ResCommonDetailType;
import org.opentravel.ota._2003._05.RoutingHopType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResGlobalInfoType", propOrder={"hotelReservationIDs", "routingHops", "profiles", "bookingRules"})
public class ResGlobalInfoType
extends ResCommonDetailType {
    @XmlElement(name="HotelReservationIDs")
    protected HotelReservationIDsType hotelReservationIDs;
    @XmlElement(name="RoutingHops")
    protected RoutingHopType routingHops;
    @XmlElement(name="Profiles")
    protected ProfilesType profiles;
    @XmlElement(name="BookingRules")
    protected BookingRulesType bookingRules;

    public HotelReservationIDsType getHotelReservationIDs() {
        return this.hotelReservationIDs;
    }

    public void setHotelReservationIDs(HotelReservationIDsType value) {
        this.hotelReservationIDs = value;
    }

    public RoutingHopType getRoutingHops() {
        return this.routingHops;
    }

    public void setRoutingHops(RoutingHopType value) {
        this.routingHops = value;
    }

    public ProfilesType getProfiles() {
        return this.profiles;
    }

    public void setProfiles(ProfilesType value) {
        this.profiles = value;
    }

    public BookingRulesType getBookingRules() {
        return this.bookingRules;
    }

    public void setBookingRules(BookingRulesType value) {
        this.bookingRules = value;
    }
}

