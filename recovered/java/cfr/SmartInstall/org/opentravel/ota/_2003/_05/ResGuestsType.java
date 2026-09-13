/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ResGuestType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ResGuestsType", propOrder={"resGuest"})
public class ResGuestsType {
    @XmlElement(name="ResGuest", required=true)
    protected List<ResGuestType> resGuest;

    public List<ResGuestType> getResGuest() {
        if (this.resGuest == null) {
            this.resGuest = new ArrayList<ResGuestType>();
        }
        return this.resGuest;
    }
}

