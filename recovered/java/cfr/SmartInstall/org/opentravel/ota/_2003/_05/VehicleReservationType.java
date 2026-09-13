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
import org.opentravel.ota._2003._05.CustomerPrimaryAdditionalType;
import org.opentravel.ota._2003._05.VehicleSegmentAdditionalInfoType;
import org.opentravel.ota._2003._05.VehicleSegmentCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleReservationType", propOrder={"customer", "vehSegmentCore", "vehSegmentInfo"})
public class VehicleReservationType {
    @XmlElement(name="Customer")
    protected CustomerPrimaryAdditionalType customer;
    @XmlElement(name="VehSegmentCore", required=true)
    protected VehSegmentCore vehSegmentCore;
    @XmlElement(name="VehSegmentInfo")
    protected VehicleSegmentAdditionalInfoType vehSegmentInfo;
    @XmlAttribute(name="ReservationStatus")
    protected String reservationStatus;
    @XmlAttribute(name="CreateDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar createDateTime;
    @XmlAttribute(name="CreatorID")
    protected String creatorID;
    @XmlAttribute(name="LastModifyDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastModifyDateTime;
    @XmlAttribute(name="LastModifierID")
    protected String lastModifierID;
    @XmlAttribute(name="PurgeDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar purgeDate;

    public CustomerPrimaryAdditionalType getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerPrimaryAdditionalType value) {
        this.customer = value;
    }

    public VehSegmentCore getVehSegmentCore() {
        return this.vehSegmentCore;
    }

    public void setVehSegmentCore(VehSegmentCore value) {
        this.vehSegmentCore = value;
    }

    public VehicleSegmentAdditionalInfoType getVehSegmentInfo() {
        return this.vehSegmentInfo;
    }

    public void setVehSegmentInfo(VehicleSegmentAdditionalInfoType value) {
        this.vehSegmentInfo = value;
    }

    public String getReservationStatus() {
        return this.reservationStatus;
    }

    public void setReservationStatus(String value) {
        this.reservationStatus = value;
    }

    public XMLGregorianCalendar getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(XMLGregorianCalendar value) {
        this.createDateTime = value;
    }

    public String getCreatorID() {
        return this.creatorID;
    }

    public void setCreatorID(String value) {
        this.creatorID = value;
    }

    public XMLGregorianCalendar getLastModifyDateTime() {
        return this.lastModifyDateTime;
    }

    public void setLastModifyDateTime(XMLGregorianCalendar value) {
        this.lastModifyDateTime = value;
    }

    public String getLastModifierID() {
        return this.lastModifierID;
    }

    public void setLastModifierID(String value) {
        this.lastModifierID = value;
    }

    public XMLGregorianCalendar getPurgeDate() {
        return this.purgeDate;
    }

    public void setPurgeDate(XMLGregorianCalendar value) {
        this.purgeDate = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class VehSegmentCore
    extends VehicleSegmentCoreType {
        @XmlAttribute(name="OptionChangeAllowedIndicator")
        protected Boolean optionChangeAllowedIndicator;

        public Boolean isOptionChangeAllowedIndicator() {
            return this.optionChangeAllowedIndicator;
        }

        public void setOptionChangeAllowedIndicator(Boolean value) {
            this.optionChangeAllowedIndicator = value;
        }
    }
}

