/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.BookedTrainSegmentType;
import org.opentravel.ota._2003._05.ClassCodeType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.StationDetailsType;
import org.opentravel.ota._2003._05.TrainInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrainSegmentType", propOrder={"departureStation", "arrivalStation", "marketingCompany", "operatingCompany", "equipment", "trainInfo"})
@XmlSeeAlso(value={BookedTrainSegmentType.class})
public class TrainSegmentType {
    @XmlElement(name="DepartureStation", required=true)
    protected StationDetailsType departureStation;
    @XmlElement(name="ArrivalStation", required=true)
    protected StationDetailsType arrivalStation;
    @XmlElement(name="MarketingCompany", required=true)
    protected CompanyNameType marketingCompany;
    @XmlElement(name="OperatingCompany")
    protected CompanyNameType operatingCompany;
    @XmlElement(name="Equipment")
    protected ClassCodeType equipment;
    @XmlElement(name="TrainInfo")
    protected TrainInfoType trainInfo;
    @XmlAttribute(name="DepartureDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar departureDateTime;
    @XmlAttribute(name="ArrivalDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlAttribute(name="StopQuantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger stopQuantity;
    @XmlAttribute(name="JourneyDuration")
    protected Duration journeyDuration;
    @XmlAttribute(name="CrossBorderInd")
    protected Boolean crossBorderInd;

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

    public CompanyNameType getMarketingCompany() {
        return this.marketingCompany;
    }

    public void setMarketingCompany(CompanyNameType value) {
        this.marketingCompany = value;
    }

    public CompanyNameType getOperatingCompany() {
        return this.operatingCompany;
    }

    public void setOperatingCompany(CompanyNameType value) {
        this.operatingCompany = value;
    }

    public ClassCodeType getEquipment() {
        return this.equipment;
    }

    public void setEquipment(ClassCodeType value) {
        this.equipment = value;
    }

    public TrainInfoType getTrainInfo() {
        return this.trainInfo;
    }

    public void setTrainInfo(TrainInfoType value) {
        this.trainInfo = value;
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

    public BigInteger getStopQuantity() {
        return this.stopQuantity;
    }

    public void setStopQuantity(BigInteger value) {
        this.stopQuantity = value;
    }

    public Duration getJourneyDuration() {
        return this.journeyDuration;
    }

    public void setJourneyDuration(Duration value) {
        this.journeyDuration = value;
    }

    public Boolean isCrossBorderInd() {
        return this.crossBorderInd;
    }

    public void setCrossBorderInd(Boolean value) {
        this.crossBorderInd = value;
    }
}

