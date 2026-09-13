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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelReservationIDsType", propOrder={"hotelReservationID"})
public class HotelReservationIDsType {
    @XmlElement(name="HotelReservationID", required=true)
    protected List<HotelReservationID> hotelReservationID;

    public List<HotelReservationID> getHotelReservationID() {
        if (this.hotelReservationID == null) {
            this.hotelReservationID = new ArrayList<HotelReservationID>();
        }
        return this.hotelReservationID;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class HotelReservationID {
        @XmlAttribute(name="ResID_Type")
        protected String resIDType;
        @XmlAttribute(name="ResID_Value")
        protected String resIDValue;
        @XmlAttribute(name="ResID_Source")
        protected String resIDSource;
        @XmlAttribute(name="ResID_SourceContext")
        protected String resIDSourceContext;
        @XmlAttribute(name="ResID_Date")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar resIDDate;
        @XmlAttribute(name="ForGuest")
        protected Boolean forGuest;
        @XmlAttribute(name="ResGuestRPH")
        protected String resGuestRPH;
        @XmlAttribute(name="CancelOriginatorCode")
        protected String cancelOriginatorCode;
        @XmlAttribute(name="CancellationDate")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar cancellationDate;
        @XmlAttribute(name="HotelReservationID_RPH")
        protected String hotelReservationIDRPH;

        public String getResIDType() {
            return this.resIDType;
        }

        public void setResIDType(String value) {
            this.resIDType = value;
        }

        public String getResIDValue() {
            return this.resIDValue;
        }

        public void setResIDValue(String value) {
            this.resIDValue = value;
        }

        public String getResIDSource() {
            return this.resIDSource;
        }

        public void setResIDSource(String value) {
            this.resIDSource = value;
        }

        public String getResIDSourceContext() {
            return this.resIDSourceContext;
        }

        public void setResIDSourceContext(String value) {
            this.resIDSourceContext = value;
        }

        public XMLGregorianCalendar getResIDDate() {
            return this.resIDDate;
        }

        public void setResIDDate(XMLGregorianCalendar value) {
            this.resIDDate = value;
        }

        public Boolean isForGuest() {
            return this.forGuest;
        }

        public void setForGuest(Boolean value) {
            this.forGuest = value;
        }

        public String getResGuestRPH() {
            return this.resGuestRPH;
        }

        public void setResGuestRPH(String value) {
            this.resGuestRPH = value;
        }

        public String getCancelOriginatorCode() {
            return this.cancelOriginatorCode;
        }

        public void setCancelOriginatorCode(String value) {
            this.cancelOriginatorCode = value;
        }

        public XMLGregorianCalendar getCancellationDate() {
            return this.cancellationDate;
        }

        public void setCancellationDate(XMLGregorianCalendar value) {
            this.cancellationDate = value;
        }

        public String getHotelReservationIDRPH() {
            return this.hotelReservationIDRPH;
        }

        public void setHotelReservationIDRPH(String value) {
            this.hotelReservationIDRPH = value;
        }
    }
}

