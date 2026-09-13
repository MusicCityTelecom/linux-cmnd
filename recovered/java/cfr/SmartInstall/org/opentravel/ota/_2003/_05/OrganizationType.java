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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.OfficeLocationType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.TravelArrangerType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OrganizationType", propOrder={"orgMemberName", "orgName", "relatedOrgName", "travelArranger"})
public class OrganizationType {
    @XmlElement(name="OrgMemberName")
    protected OrgMemberName orgMemberName;
    @XmlElement(name="OrgName")
    protected CompanyNameType orgName;
    @XmlElement(name="RelatedOrgName")
    protected List<CompanyNameType> relatedOrgName;
    @XmlElement(name="TravelArranger")
    protected List<TravelArrangerType> travelArranger;
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
    @XmlAttribute(name="DefaultInd")
    protected Boolean defaultInd;
    @XmlAttribute(name="OfficeType")
    protected OfficeLocationType officeType;

    public OrgMemberName getOrgMemberName() {
        return this.orgMemberName;
    }

    public void setOrgMemberName(OrgMemberName value) {
        this.orgMemberName = value;
    }

    public CompanyNameType getOrgName() {
        return this.orgName;
    }

    public void setOrgName(CompanyNameType value) {
        this.orgName = value;
    }

    public List<CompanyNameType> getRelatedOrgName() {
        if (this.relatedOrgName == null) {
            this.relatedOrgName = new ArrayList<CompanyNameType>();
        }
        return this.relatedOrgName;
    }

    public List<TravelArrangerType> getTravelArranger() {
        if (this.travelArranger == null) {
            this.travelArranger = new ArrayList<TravelArrangerType>();
        }
        return this.travelArranger;
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

    public Boolean isDefaultInd() {
        return this.defaultInd;
    }

    public void setDefaultInd(Boolean value) {
        this.defaultInd = value;
    }

    public OfficeLocationType getOfficeType() {
        return this.officeType;
    }

    public void setOfficeType(OfficeLocationType value) {
        this.officeType = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OrgMemberName
    extends PersonNameType {
        @XmlAttribute(name="Level")
        protected String level;
        @XmlAttribute(name="Title")
        protected String title;
        @XmlAttribute(name="ID")
        protected String id;

        public String getLevel() {
            return this.level;
        }

        public void setLevel(String value) {
            this.level = value;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

