/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGRequestBaseType;
import org.htng._2011b.HTNGRoomElementType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"room", "tpaExtensions"})
@XmlRootElement(name="HTNG_HotelRoomStatusUpdateNotifRQ")
public class HTNGHotelRoomStatusUpdateNotifRQ
extends HTNGRequestBaseType {
    @XmlElement(name="Room", required=true)
    protected HTNGRoomElementType room;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public HTNGRoomElementType getRoom() {
        return this.room;
    }

    public void setRoom(HTNGRoomElementType value) {
        this.room = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }
}

