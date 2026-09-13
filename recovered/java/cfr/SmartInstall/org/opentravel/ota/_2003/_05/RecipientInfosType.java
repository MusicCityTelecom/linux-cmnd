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
import org.opentravel.ota._2003._05.ContactPersonType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecipientInfosType", propOrder={"recipientInfo"})
public class RecipientInfosType {
    @XmlElement(name="RecipientInfo", required=true)
    protected List<RecipientInfo> recipientInfo;

    public List<RecipientInfo> getRecipientInfo() {
        if (this.recipientInfo == null) {
            this.recipientInfo = new ArrayList<RecipientInfo>();
        }
        return this.recipientInfo;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"reservationID", "shippingInfo", "comments"})
    public static class RecipientInfo
    extends ContactPersonType {
        @XmlElement(name="ReservationID")
        protected List<UniqueIDType> reservationID;
        @XmlElement(name="ShippingInfo")
        protected ShippingInfo shippingInfo;
        @XmlElement(name="Comments")
        protected Comments comments;

        public List<UniqueIDType> getReservationID() {
            if (this.reservationID == null) {
                this.reservationID = new ArrayList<UniqueIDType>();
            }
            return this.reservationID;
        }

        public ShippingInfo getShippingInfo() {
            return this.shippingInfo;
        }

        public void setShippingInfo(ShippingInfo value) {
            this.shippingInfo = value;
        }

        public Comments getComments() {
            return this.comments;
        }

        public void setComments(Comments value) {
            this.comments = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class ShippingInfo {
            @XmlAttribute(name="ShippingType")
            protected String shippingType;
            @XmlAttribute(name="ShippingCarrier")
            protected String shippingCarrier;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public String getShippingType() {
                return this.shippingType;
            }

            public void setShippingType(String value) {
                this.shippingType = value;
            }

            public String getShippingCarrier() {
                return this.shippingCarrier;
            }

            public void setShippingCarrier(String value) {
                this.shippingCarrier = value;
            }

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

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"comment"})
        public static class Comments {
            @XmlElement(name="Comment", required=true)
            protected List<ParagraphType> comment;

            public List<ParagraphType> getComment() {
                if (this.comment == null) {
                    this.comment = new ArrayList<ParagraphType>();
                }
                return this.comment;
            }
        }
    }
}

