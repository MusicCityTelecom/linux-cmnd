/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TransportInfoType", propOrder={"transportInfo"})
public class TransportInfoType {
    @XmlElement(name="TransportInfo", required=true)
    protected TransportInfo transportInfo;

    public TransportInfo getTransportInfo() {
        return this.transportInfo;
    }

    public void setTransportInfo(TransportInfo value) {
        this.transportInfo = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TransportInfo {
        @XmlAttribute(name="Type")
        protected String type;
        @XmlAttribute(name="ID")
        protected String id;
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="Time")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar time;

        public String getType() {
            return this.type;
        }

        public void setType(String value) {
            this.type = value;
        }

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

        public XMLGregorianCalendar getTime() {
            return this.time;
        }

        public void setTime(XMLGregorianCalendar value) {
            this.time = value;
        }
    }
}

