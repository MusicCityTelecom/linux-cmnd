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
import org.opentravel.ota._2003._05.GuestType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CruiseBookingInfoType", propOrder={"bookingPrices", "paymentSchedule", "guestPrices", "policyInfo"})
public class CruiseBookingInfoType {
    @XmlElement(name="BookingPrices")
    protected BookingPrices bookingPrices;
    @XmlElement(name="PaymentSchedule")
    protected PaymentSchedule paymentSchedule;
    @XmlElement(name="GuestPrices")
    protected GuestPrices guestPrices;
    @XmlElement(name="PolicyInfo")
    protected List<ParagraphType> policyInfo;

    public BookingPrices getBookingPrices() {
        return this.bookingPrices;
    }

    public void setBookingPrices(BookingPrices value) {
        this.bookingPrices = value;
    }

    public PaymentSchedule getPaymentSchedule() {
        return this.paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule value) {
        this.paymentSchedule = value;
    }

    public GuestPrices getGuestPrices() {
        return this.guestPrices;
    }

    public void setGuestPrices(GuestPrices value) {
        this.guestPrices = value;
    }

    public List<ParagraphType> getPolicyInfo() {
        if (this.policyInfo == null) {
            this.policyInfo = new ArrayList<ParagraphType>();
        }
        return this.policyInfo;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"payment"})
    public static class PaymentSchedule {
        @XmlElement(name="Payment", required=true)
        protected List<Payment> payment;

        public List<Payment> getPayment() {
            if (this.payment == null) {
                this.payment = new ArrayList<Payment>();
            }
            return this.payment;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Payment {
            @XmlAttribute(name="PaymentNumber", required=true)
            protected int paymentNumber;
            @XmlAttribute(name="DueDate", required=true)
            protected String dueDate;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public int getPaymentNumber() {
                return this.paymentNumber;
            }

            public void setPaymentNumber(int value) {
                this.paymentNumber = value;
            }

            public String getDueDate() {
                return this.dueDate;
            }

            public void setDueDate(String value) {
                this.dueDate = value;
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
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"guestPrice"})
    public static class GuestPrices {
        @XmlElement(name="GuestPrice", required=true)
        protected List<GuestPrice> guestPrice;

        public List<GuestPrice> getGuestPrice() {
            if (this.guestPrice == null) {
                this.guestPrice = new ArrayList<GuestPrice>();
            }
            return this.guestPrice;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"priceInfos"})
        public static class GuestPrice
        extends GuestType {
            @XmlElement(name="PriceInfos", required=true)
            protected PriceInfos priceInfos;

            public PriceInfos getPriceInfos() {
                return this.priceInfos;
            }

            public void setPriceInfos(PriceInfos value) {
                this.priceInfos = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"priceInfo"})
            public static class PriceInfos {
                @XmlElement(name="PriceInfo", required=true)
                protected List<PriceInfo> priceInfo;

                public List<PriceInfo> getPriceInfo() {
                    if (this.priceInfo == null) {
                        this.priceInfo = new ArrayList<PriceInfo>();
                    }
                    return this.priceInfo;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class PriceInfo {
                    @XmlAttribute(name="PriceTypeCode", required=true)
                    protected String priceTypeCode;
                    @XmlAttribute(name="Amount")
                    protected BigDecimal amount;
                    @XmlAttribute(name="RestrictedIndicator")
                    protected Boolean restrictedIndicator;
                    @XmlAttribute(name="CodeDetail")
                    protected String codeDetail;
                    @XmlAttribute(name="Percent")
                    protected BigDecimal percent;

                    public String getPriceTypeCode() {
                        return this.priceTypeCode;
                    }

                    public void setPriceTypeCode(String value) {
                        this.priceTypeCode = value;
                    }

                    public BigDecimal getAmount() {
                        return this.amount;
                    }

                    public void setAmount(BigDecimal value) {
                        this.amount = value;
                    }

                    public Boolean isRestrictedIndicator() {
                        return this.restrictedIndicator;
                    }

                    public void setRestrictedIndicator(Boolean value) {
                        this.restrictedIndicator = value;
                    }

                    public String getCodeDetail() {
                        return this.codeDetail;
                    }

                    public void setCodeDetail(String value) {
                        this.codeDetail = value;
                    }

                    public BigDecimal getPercent() {
                        return this.percent;
                    }

                    public void setPercent(BigDecimal value) {
                        this.percent = value;
                    }
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"bookingPrice"})
    public static class BookingPrices {
        @XmlElement(name="BookingPrice", required=true)
        protected List<BookingPrice> bookingPrice;

        public List<BookingPrice> getBookingPrice() {
            if (this.bookingPrice == null) {
                this.bookingPrice = new ArrayList<BookingPrice>();
            }
            return this.bookingPrice;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class BookingPrice {
            @XmlAttribute(name="PriceTypeCode", required=true)
            protected String priceTypeCode;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="RestrictedIndicator")
            protected Boolean restrictedIndicator;
            @XmlAttribute(name="CodeDetail")
            protected String codeDetail;
            @XmlAttribute(name="Percent")
            protected BigDecimal percent;

            public String getPriceTypeCode() {
                return this.priceTypeCode;
            }

            public void setPriceTypeCode(String value) {
                this.priceTypeCode = value;
            }

            public BigDecimal getAmount() {
                return this.amount;
            }

            public void setAmount(BigDecimal value) {
                this.amount = value;
            }

            public Boolean isRestrictedIndicator() {
                return this.restrictedIndicator;
            }

            public void setRestrictedIndicator(Boolean value) {
                this.restrictedIndicator = value;
            }

            public String getCodeDetail() {
                return this.codeDetail;
            }

            public void setCodeDetail(String value) {
                this.codeDetail = value;
            }

            public BigDecimal getPercent() {
                return this.percent;
            }

            public void setPercent(BigDecimal value) {
                this.percent = value;
            }
        }
    }
}

