/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CostingItemType;
import org.opentravel.ota._2003._05.GuestCountType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomPriceType", propOrder={"guestCounts", "itemPrice", "profilePrice"})
public class RoomPriceType {
    @XmlElement(name="GuestCounts")
    protected List<GuestCountType> guestCounts;
    @XmlElement(name="ItemPrice")
    protected List<CostingItemType> itemPrice;
    @XmlElement(name="ProfilePrice")
    protected ProfilePrice profilePrice;
    @XmlAttribute(name="RoomRPH")
    protected String roomRPH;
    @XmlAttribute(name="Code")
    protected String code;

    public List<GuestCountType> getGuestCounts() {
        if (this.guestCounts == null) {
            this.guestCounts = new ArrayList<GuestCountType>();
        }
        return this.guestCounts;
    }

    public List<CostingItemType> getItemPrice() {
        if (this.itemPrice == null) {
            this.itemPrice = new ArrayList<CostingItemType>();
        }
        return this.itemPrice;
    }

    public ProfilePrice getProfilePrice() {
        return this.profilePrice;
    }

    public void setProfilePrice(ProfilePrice value) {
        this.profilePrice = value;
    }

    public String getRoomRPH() {
        return this.roomRPH;
    }

    public void setRoomRPH(String value) {
        this.roomRPH = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ProfilePrice {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }
}

