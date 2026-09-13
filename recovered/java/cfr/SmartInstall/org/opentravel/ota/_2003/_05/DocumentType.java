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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AirTravelerType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DocumentType", propOrder={"docHolderName", "docHolderFormattedName", "docLimitations", "additionalPersonNames"})
@XmlSeeAlso(value={AirTravelerType.Document.class})
public class DocumentType {
    @XmlElement(name="DocHolderName")
    protected String docHolderName;
    @XmlElement(name="DocHolderFormattedName")
    protected PersonNameType docHolderFormattedName;
    @XmlElement(name="DocLimitations")
    protected List<String> docLimitations;
    @XmlElement(name="AdditionalPersonNames")
    protected AdditionalPersonNames additionalPersonNames;
    @XmlAttribute(name="DocIssueAuthority")
    protected String docIssueAuthority;
    @XmlAttribute(name="DocIssueLocation")
    protected String docIssueLocation;
    @XmlAttribute(name="DocID")
    protected String docID;
    @XmlAttribute(name="DocType")
    protected String docType;
    @XmlAttribute(name="DocIssueStateProv")
    protected String docIssueStateProv;
    @XmlAttribute(name="DocIssueCountry")
    protected String docIssueCountry;
    @XmlAttribute(name="BirthCountry")
    protected String birthCountry;
    @XmlAttribute(name="BirthPlace")
    protected String birthPlace;
    @XmlAttribute(name="DocHolderNationality")
    protected String docHolderNationality;
    @XmlAttribute(name="ContactName")
    protected String contactName;
    @XmlAttribute(name="HolderType")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String holderType;
    @XmlAttribute(name="Remark")
    protected String remark;
    @XmlAttribute(name="PostalCode")
    protected String postalCode;
    @XmlAttribute(name="BirthDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar birthDate;
    @XmlAttribute(name="Gender")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String gender;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;

    public String getDocHolderName() {
        return this.docHolderName;
    }

    public void setDocHolderName(String value) {
        this.docHolderName = value;
    }

    public PersonNameType getDocHolderFormattedName() {
        return this.docHolderFormattedName;
    }

    public void setDocHolderFormattedName(PersonNameType value) {
        this.docHolderFormattedName = value;
    }

    public List<String> getDocLimitations() {
        if (this.docLimitations == null) {
            this.docLimitations = new ArrayList<String>();
        }
        return this.docLimitations;
    }

    public AdditionalPersonNames getAdditionalPersonNames() {
        return this.additionalPersonNames;
    }

    public void setAdditionalPersonNames(AdditionalPersonNames value) {
        this.additionalPersonNames = value;
    }

    public String getDocIssueAuthority() {
        return this.docIssueAuthority;
    }

    public void setDocIssueAuthority(String value) {
        this.docIssueAuthority = value;
    }

    public String getDocIssueLocation() {
        return this.docIssueLocation;
    }

    public void setDocIssueLocation(String value) {
        this.docIssueLocation = value;
    }

    public String getDocID() {
        return this.docID;
    }

    public void setDocID(String value) {
        this.docID = value;
    }

    public String getDocType() {
        return this.docType;
    }

    public void setDocType(String value) {
        this.docType = value;
    }

    public String getDocIssueStateProv() {
        return this.docIssueStateProv;
    }

    public void setDocIssueStateProv(String value) {
        this.docIssueStateProv = value;
    }

    public String getDocIssueCountry() {
        return this.docIssueCountry;
    }

    public void setDocIssueCountry(String value) {
        this.docIssueCountry = value;
    }

    public String getBirthCountry() {
        return this.birthCountry;
    }

    public void setBirthCountry(String value) {
        this.birthCountry = value;
    }

    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(String value) {
        this.birthPlace = value;
    }

    public String getDocHolderNationality() {
        return this.docHolderNationality;
    }

    public void setDocHolderNationality(String value) {
        this.docHolderNationality = value;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String value) {
        this.contactName = value;
    }

    public String getHolderType() {
        return this.holderType;
    }

    public void setHolderType(String value) {
        this.holderType = value;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    public XMLGregorianCalendar getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String value) {
        this.gender = value;
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

    public XMLGregorianCalendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public XMLGregorianCalendar getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(XMLGregorianCalendar value) {
        this.expireDate = value;
    }

    public Boolean isExpireDateExclusiveIndicator() {
        return this.expireDateExclusiveIndicator;
    }

    public void setExpireDateExclusiveIndicator(Boolean value) {
        this.expireDateExclusiveIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"additionalPersonName"})
    public static class AdditionalPersonNames {
        @XmlElement(name="AdditionalPersonName", required=true)
        protected List<String> additionalPersonName;

        public List<String> getAdditionalPersonName() {
            if (this.additionalPersonName == null) {
                this.additionalPersonName = new ArrayList<String>();
            }
            return this.additionalPersonName;
        }
    }
}

