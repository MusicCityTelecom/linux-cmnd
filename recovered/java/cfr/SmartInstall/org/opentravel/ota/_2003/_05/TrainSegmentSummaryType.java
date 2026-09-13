/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.StationDetailsType;
import org.opentravel.ota._2003._05.TrainIdentificationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrainSegmentSummaryType", propOrder={"departureStation", "arrivalStation", "departureDateTime", "arrivalDateTime", "trainIdentification"})
public class TrainSegmentSummaryType {
    @XmlElement(name="DepartureStation", required=true)
    protected StationDetailsType departureStation;
    @XmlElement(name="ArrivalStation", required=true)
    protected StationDetailsType arrivalStation;
    @XmlElement(name="DepartureDateTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar departureDateTime;
    @XmlElement(name="ArrivalDateTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlElement(name="TrainIdentification", required=true)
    protected TrainIdentificationType trainIdentification;

    public StationDetailsType getDepartureStation() {
        return this.departureStation;
    }

    public void setDepartureStation(StationDetailsType value) {
        this.departureStation = value;
    }

    public StationDetailsType getArrivalStation() {
        return this.arrivalStation;
    }

    public void setArrivalStation(StationDetailsType value) {
        this.arrivalStation = value;
    }

    public XMLGregorianCalendar getDepartureDateTime() {
        return this.departureDateTime;
    }

    public void setDepartureDateTime(XMLGregorianCalendar value) {
        this.departureDateTime = value;
    }

    public XMLGregorianCalendar getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }

    public TrainIdentificationType getTrainIdentification() {
        return this.trainIdentification;
    }

    public void setTrainIdentification(TrainIdentificationType value) {
        this.trainIdentification = value;
    }
}

