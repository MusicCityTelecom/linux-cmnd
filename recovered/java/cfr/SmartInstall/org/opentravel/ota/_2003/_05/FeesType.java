/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.FeeType;
import org.opentravel.ota._2003._05.GuestRoomType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FeesType", propOrder={"fee"})
@XmlSeeAlso(value={GuestRoomType.RoomLevelFees.class})
public class FeesType {
    @XmlElement(name="Fee", required=true)
    protected List<FeeType> fee;

    public List<FeeType> getFee() {
        if (this.fee == null) {
            this.fee = new ArrayList<FeeType>();
        }
        return this.fee;
    }
}

