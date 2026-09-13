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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.ContactsType;
import org.opentravel.ota._2003._05.DonationType;
import org.opentravel.ota._2003._05.OrganizationType;
import org.opentravel.ota._2003._05.TravelClubType;
import org.opentravel.ota._2003._05.VerificationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PersonNameType", propOrder={"namePrefix", "givenName", "middleName", "surnamePrefix", "surname", "nameSuffix", "nameTitle", "document"})
@XmlSeeAlso(value={DonationType.DonorInfo.Name.class, OrganizationType.OrgMemberName.class, TravelClubType.ClubMemberName.class, VerificationType.PersonName.class, ContactsType.Name.class})
public class PersonNameType {
    @XmlElement(name="NamePrefix")
    protected List<String> namePrefix;
    @XmlElement(name="GivenName")
    protected List<String> givenName;
    @XmlElement(name="MiddleName")
    protected List<String> middleName;
    @XmlElement(name="SurnamePrefix")
    protected String surnamePrefix;
    @XmlElement(name="Surname", required=true)
    protected String surname;
    @XmlElement(name="NameSuffix")
    protected List<String> nameSuffix;
    @XmlElement(name="NameTitle")
    protected List<String> nameTitle;
    @XmlElement(name="Document")
    protected Document document;
    @XmlAttribute(name="NameType")
    protected String nameType;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public List<String> getNamePrefix() {
        if (this.namePrefix == null) {
            this.namePrefix = new ArrayList<String>();
        }
        return this.namePrefix;
    }

    public List<String> getGivenName() {
        if (this.givenName == null) {
            this.givenName = new ArrayList<String>();
        }
        return this.givenName;
    }

    public List<String> getMiddleName() {
        if (this.middleName == null) {
            this.middleName = new ArrayList<String>();
        }
        return this.middleName;
    }

    public String getSurnamePrefix() {
        return this.surnamePrefix;
    }

    public void setSurnamePrefix(String value) {
        this.surnamePrefix = value;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String value) {
        this.surname = value;
    }

    public List<String> getNameSuffix() {
        if (this.nameSuffix == null) {
            this.nameSuffix = new ArrayList<String>();
        }
        return this.nameSuffix;
    }

    public List<String> getNameTitle() {
        if (this.nameTitle == null) {
            this.nameTitle = new ArrayList<String>();
        }
        return this.nameTitle;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document value) {
        this.document = value;
    }

    public String getNameType() {
        return this.nameType;
    }

    public void setNameType(String value) {
        this.nameType = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Document {
        @XmlAttribute(name="DocID")
        protected String docID;
        @XmlAttribute(name="DocType")
        protected String docType;

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
    }
}

