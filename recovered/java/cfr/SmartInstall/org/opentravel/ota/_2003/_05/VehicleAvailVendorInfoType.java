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
import org.opentravel.ota._2003._05.OffLocationServicePricedType;
import org.opentravel.ota._2003._05.PaymentRulesType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.VehicleLocationDetailsType;
import org.opentravel.ota._2003._05.VehicleTourInfoType;
import org.opentravel.ota._2003._05.VendorMessagesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleAvailVendorInfoType", propOrder={"vendorMessages", "offLocServices", "paymentRules", "locationDetails", "tourInfo", "tpaExtensions"})
public class VehicleAvailVendorInfoType {
    @XmlElement(name="VendorMessages")
    protected VendorMessagesType vendorMessages;
    @XmlElement(name="OffLocServices")
    protected List<OffLocationServicePricedType> offLocServices;
    @XmlElement(name="PaymentRules")
    protected PaymentRulesType paymentRules;
    @XmlElement(name="LocationDetails")
    protected List<VehicleLocationDetailsType> locationDetails;
    @XmlElement(name="TourInfo")
    protected TourInfo tourInfo;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public VendorMessagesType getVendorMessages() {
        return this.vendorMessages;
    }

    public void setVendorMessages(VendorMessagesType value) {
        this.vendorMessages = value;
    }

    public List<OffLocationServicePricedType> getOffLocServices() {
        if (this.offLocServices == null) {
            this.offLocServices = new ArrayList<OffLocationServicePricedType>();
        }
        return this.offLocServices;
    }

    public PaymentRulesType getPaymentRules() {
        return this.paymentRules;
    }

    public void setPaymentRules(PaymentRulesType value) {
        this.paymentRules = value;
    }

    public List<VehicleLocationDetailsType> getLocationDetails() {
        if (this.locationDetails == null) {
            this.locationDetails = new ArrayList<VehicleLocationDetailsType>();
        }
        return this.locationDetails;
    }

    public TourInfo getTourInfo() {
        return this.tourInfo;
    }

    public void setTourInfo(TourInfo value) {
        this.tourInfo = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TourInfo
    extends VehicleTourInfoType {
        @XmlAttribute(name="RPH")
        protected String rph;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }
    }
}

