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
@XmlType(name="ServiceRPHsType", propOrder={"serviceRPH"})
public class ServiceRPHsType {
    @XmlElement(name="ServiceRPH", required=true)
    protected List<ServiceRPH> serviceRPH;

    public List<ServiceRPH> getServiceRPH() {
        if (this.serviceRPH == null) {
            this.serviceRPH = new ArrayList<ServiceRPH>();
        }
        return this.serviceRPH;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ServiceRPH {
        @XmlAttribute(name="RPH")
        protected String rph;
        @XmlAttribute(name="IsPerRoom")
        protected Boolean isPerRoom;

        public String getRPH() {
            return this.rph;
        }

        public void setRPH(String value) {
            this.rph = value;
        }

        public Boolean isIsPerRoom() {
            return this.isPerRoom;
        }

        public void setIsPerRoom(Boolean value) {
            this.isPerRoom = value;
        }
    }
}

