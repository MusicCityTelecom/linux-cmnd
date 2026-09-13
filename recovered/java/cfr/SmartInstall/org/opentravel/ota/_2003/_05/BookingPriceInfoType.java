/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AirItineraryPricingInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BookingPriceInfoType")
public class BookingPriceInfoType
extends AirItineraryPricingInfoType {
    @XmlAttribute(name="RepriceRequired")
    protected Boolean repriceRequired;

    public Boolean isRepriceRequired() {
        return this.repriceRequired;
    }

    public void setRepriceRequired(Boolean value) {
        this.repriceRequired = value;
    }
}

