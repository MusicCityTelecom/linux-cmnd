/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CruiseGuestDetailType;
import org.opentravel.ota._2003._05.CruiseGuestInfoType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RelatedTravelerType", propOrder={"uniqueID", "personName"})
@XmlSeeAlso(value={CruiseGuestDetailType.LinkedTraveler.class, CruiseGuestInfoType.LinkedBookings.LinkedBooking.class})
public class RelatedTravelerType {
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name="PersonName")
    protected PersonNameType personName;
    @XmlAttribute(name="Relation")
    protected String relation;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;
    @XmlAttribute(name="BirthDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar birthDate;

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public PersonNameType getPersonName() {
        return this.personName;
    }

    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }

    public XMLGregorianCalendar getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }
}

