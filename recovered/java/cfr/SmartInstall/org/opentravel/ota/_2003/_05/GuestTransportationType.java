/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.LocationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="GuestTransportationType", propOrder={"guestCity", "gatewayCity"})
public class GuestTransportationType {
    @XmlElement(name="GuestCity")
    protected LocationType guestCity;
    @XmlElement(name="GatewayCity")
    protected LocationType gatewayCity;
    @XmlAttribute(name="Mode", required=true)
    protected String mode;
    @XmlAttribute(name="Status")
    protected String status;

    public LocationType getGuestCity() {
        return this.guestCity;
    }

    public void setGuestCity(LocationType value) {
        this.guestCity = value;
    }

    public LocationType getGatewayCity() {
        return this.gatewayCity;
    }

    public void setGatewayCity(LocationType value) {
        this.gatewayCity = value;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String value) {
        this.mode = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }
}

