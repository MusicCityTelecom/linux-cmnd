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
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.AddressType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.PaymentDetailType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FulfillmentType", propOrder={"paymentDetails", "deliveryAddress", "name", "receipt", "paymentText"})
public class FulfillmentType {
    @XmlElement(name="PaymentDetails")
    protected PaymentDetails paymentDetails;
    @XmlElement(name="DeliveryAddress")
    protected AddressType deliveryAddress;
    @XmlElement(name="Name")
    protected PersonNameType name;
    @XmlElement(name="Receipt")
    protected Receipt receipt;
    @XmlElement(name="PaymentText")
    protected List<PaymentText> paymentText;

    public PaymentDetails getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails value) {
        this.paymentDetails = value;
    }

    public AddressType getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(AddressType value) {
        this.deliveryAddress = value;
    }

    public PersonNameType getName() {
        return this.name;
    }

    public void setName(PersonNameType value) {
        this.name = value;
    }

    public Receipt getReceipt() {
        return this.receipt;
    }

    public void setReceipt(Receipt value) {
        this.receipt = value;
    }

    public List<PaymentText> getPaymentText() {
        if (this.paymentText == null) {
            this.paymentText = new ArrayList<PaymentText>();
        }
        return this.paymentText;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Receipt {
        @XmlAttribute(name="DistribType")
        protected String distribType;

        public String getDistribType() {
            return this.distribType;
        }

        public void setDistribType(String value) {
            this.distribType = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class PaymentText
    extends FormattedTextTextType {
        @XmlAttribute(name="Name")
        protected String name;

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"paymentDetail"})
    public static class PaymentDetails {
        @XmlElement(name="PaymentDetail", required=true)
        protected List<PaymentDetail> paymentDetail;

        public List<PaymentDetail> getPaymentDetail() {
            if (this.paymentDetail == null) {
                this.paymentDetail = new ArrayList<PaymentDetail>();
            }
            return this.paymentDetail;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class PaymentDetail
        extends PaymentDetailType {
            @XmlAttribute(name="Operation")
            protected ActionType operation;

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }
        }
    }
}

