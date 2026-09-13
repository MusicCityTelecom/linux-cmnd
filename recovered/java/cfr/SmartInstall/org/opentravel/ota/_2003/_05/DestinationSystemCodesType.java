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
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DestinationSystemCodesType", propOrder={"destinationSystemCode"})
public class DestinationSystemCodesType {
    @XmlElement(name="DestinationSystemCode", required=true)
    protected List<DestinationSystemCode> destinationSystemCode;

    public List<DestinationSystemCode> getDestinationSystemCode() {
        if (this.destinationSystemCode == null) {
            this.destinationSystemCode = new ArrayList<DestinationSystemCode>();
        }
        return this.destinationSystemCode;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class DestinationSystemCode {
        @XmlValue
        protected String value;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

