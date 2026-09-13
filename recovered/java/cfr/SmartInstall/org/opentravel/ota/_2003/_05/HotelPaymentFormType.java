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
import org.opentravel.ota._2003._05.HotelRoomListType;
import org.opentravel.ota._2003._05.PaymentFormType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelPaymentFormType", propOrder={"masterAccountUsage"})
@XmlSeeAlso(value={HotelRoomListType.Guests.Guest.GuaranteePayment.class})
public class HotelPaymentFormType
extends PaymentFormType {
    @XmlElement(name="MasterAccountUsage")
    protected MasterAccountUsage masterAccountUsage;

    public MasterAccountUsage getMasterAccountUsage() {
        return this.masterAccountUsage;
    }

    public void setMasterAccountUsage(MasterAccountUsage value) {
        this.masterAccountUsage = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MasterAccountUsage {
        @XmlAttribute(name="BillingType")
        protected String billingType;
        @XmlAttribute(name="SignFoodAndBev")
        protected Boolean signFoodAndBev;

        public String getBillingType() {
            return this.billingType;
        }

        public void setBillingType(String value) {
            this.billingType = value;
        }

        public Boolean isSignFoodAndBev() {
            return this.signFoodAndBev;
        }

        public void setSignFoodAndBev(Boolean value) {
            this.signFoodAndBev = value;
        }
    }
}

