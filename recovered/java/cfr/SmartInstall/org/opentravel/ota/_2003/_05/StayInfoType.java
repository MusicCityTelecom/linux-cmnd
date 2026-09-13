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
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.LoyaltyPointsAccrualsType;
import org.opentravel.ota._2003._05.RevenueCategoriesType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StayInfoType", propOrder={"loyaltyPointsAccruals", "revenueCategories", "reservationID", "hotelReservation"})
public class StayInfoType {
    @XmlElement(name="LoyaltyPointsAccruals")
    protected LoyaltyPointsAccrualsType loyaltyPointsAccruals;
    @XmlElement(name="RevenueCategories")
    protected RevenueCategoriesType revenueCategories;
    @XmlElement(name="ReservationID")
    protected UniqueIDType reservationID;
    @XmlElement(name="HotelReservation")
    protected HotelReservationType hotelReservation;
    @XmlAttribute(name="SequenceNumber")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger sequenceNumber;
    @XmlAttribute(name="RoomStayRPH")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger roomStayRPH;

    public LoyaltyPointsAccrualsType getLoyaltyPointsAccruals() {
        return this.loyaltyPointsAccruals;
    }

    public void setLoyaltyPointsAccruals(LoyaltyPointsAccrualsType value) {
        this.loyaltyPointsAccruals = value;
    }

    public RevenueCategoriesType getRevenueCategories() {
        return this.revenueCategories;
    }

    public void setRevenueCategories(RevenueCategoriesType value) {
        this.revenueCategories = value;
    }

    public UniqueIDType getReservationID() {
        return this.reservationID;
    }

    public void setReservationID(UniqueIDType value) {
        this.reservationID = value;
    }

    public HotelReservationType getHotelReservation() {
        return this.hotelReservation;
    }

    public void setHotelReservation(HotelReservationType value) {
        this.hotelReservation = value;
    }

    public BigInteger getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(BigInteger value) {
        this.sequenceNumber = value;
    }

    public BigInteger getRoomStayRPH() {
        return this.roomStayRPH;
    }

    public void setRoomStayRPH(BigInteger value) {
        this.roomStayRPH = value;
    }
}

