/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.PkgReservation;
import org.opentravel.ota._2003._05.PricedItineraryType;
import org.opentravel.ota._2003._05.TicketingInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TicketingInfoRS_Type")
@XmlSeeAlso(value={PricedItineraryType.TicketingInfo.class, PkgReservation.TicketingInfo.class})
public class TicketingInfoRSType
extends TicketingInfoType {
    @XmlAttribute(name="eTicketNumber")
    protected String eTicketNumber;

    public String getETicketNumber() {
        return this.eTicketNumber;
    }

    public void setETicketNumber(String value) {
        this.eTicketNumber = value;
    }
}

