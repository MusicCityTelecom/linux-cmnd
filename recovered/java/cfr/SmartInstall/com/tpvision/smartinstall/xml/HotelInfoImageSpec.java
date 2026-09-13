/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.Type;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"type"})
@XmlRootElement(name="hotelInfoImageSpec")
public class HotelInfoImageSpec {
    @XmlElement(required=true)
    protected List<Type> type;

    public List<Type> getType() {
        if (null == this.type) {
            this.type = new ArrayList<Type>();
        }
        return this.type;
    }
}

