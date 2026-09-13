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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TravelerRPHs", propOrder={"travelerRPH"})
public class TravelerRPHs {
    @XmlElement(name="TravelerRPH", required=true)
    protected List<TravelerRPH> travelerRPH;

    public List<TravelerRPH> getTravelerRPH() {
        if (this.travelerRPH == null) {
            this.travelerRPH = new ArrayList<TravelerRPH>();
        }
        return this.travelerRPH;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TravelerRPH {
        @XmlAttribute(name="RPH", required=true)
        protected String rph;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }
    }
}

