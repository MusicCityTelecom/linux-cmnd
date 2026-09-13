/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AccommodationDetailType;
import org.opentravel.ota._2003._05.DestinationLevelType;
import org.opentravel.ota._2003._05.PropertyIdentityType;
import org.opentravel.ota._2003._05.SourceIdentificationType;
import org.opentravel.ota._2003._05.URLType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccommodationInfoType", propOrder={"property", "resort", "accommodationClass", "sourceIdentification", "contentInfo"})
@XmlSeeAlso(value={AccommodationDetailType.class})
public class AccommodationInfoType {
    @XmlElement(name="Property", required=true)
    protected PropertyIdentityType property;
    @XmlElement(name="Resort")
    protected Resort resort;
    @XmlElement(name="AccommodationClass")
    protected AccommodationClass accommodationClass;
    @XmlElement(name="SourceIdentification")
    protected SourceIdentificationType sourceIdentification;
    @XmlElement(name="ContentInfo")
    protected URLType contentInfo;
    @XmlAttribute(name="PackageID")
    protected String packageID;
    @XmlAttribute(name="MinChildAge")
    protected Integer minChildAge;
    @XmlAttribute(name="MaxChildAge")
    protected Integer maxChildAge;
    @XmlAttribute(name="BaseMealPlan")
    protected String baseMealPlan;

    public PropertyIdentityType getProperty() {
        return this.property;
    }

    public void setProperty(PropertyIdentityType value) {
        this.property = value;
    }

    public Resort getResort() {
        return this.resort;
    }

    public void setResort(Resort value) {
        this.resort = value;
    }

    public AccommodationClass getAccommodationClass() {
        return this.accommodationClass;
    }

    public void setAccommodationClass(AccommodationClass value) {
        this.accommodationClass = value;
    }

    public SourceIdentificationType getSourceIdentification() {
        return this.sourceIdentification;
    }

    public void setSourceIdentification(SourceIdentificationType value) {
        this.sourceIdentification = value;
    }

    public URLType getContentInfo() {
        return this.contentInfo;
    }

    public void setContentInfo(URLType value) {
        this.contentInfo = value;
    }

    public String getPackageID() {
        return this.packageID;
    }

    public void setPackageID(String value) {
        this.packageID = value;
    }

    public Integer getMinChildAge() {
        return this.minChildAge;
    }

    public void setMinChildAge(Integer value) {
        this.minChildAge = value;
    }

    public Integer getMaxChildAge() {
        return this.maxChildAge;
    }

    public void setMaxChildAge(Integer value) {
        this.maxChildAge = value;
    }

    public String getBaseMealPlan() {
        return this.baseMealPlan;
    }

    public void setBaseMealPlan(String value) {
        this.baseMealPlan = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Resort {
        @XmlAttribute(name="DestinationCode")
        protected String destinationCode;
        @XmlAttribute(name="DestinationLevel")
        protected DestinationLevelType destinationLevel;
        @XmlAttribute(name="DestinationName")
        protected String destinationName;
        @XmlAttribute(name="ResortCode")
        protected String resortCode;
        @XmlAttribute(name="ResortName")
        protected String resortName;

        public String getDestinationCode() {
            return this.destinationCode;
        }

        public void setDestinationCode(String value) {
            this.destinationCode = value;
        }

        public DestinationLevelType getDestinationLevel() {
            return this.destinationLevel;
        }

        public void setDestinationLevel(DestinationLevelType value) {
            this.destinationLevel = value;
        }

        public String getDestinationName() {
            return this.destinationName;
        }

        public void setDestinationName(String value) {
            this.destinationName = value;
        }

        public String getResortCode() {
            return this.resortCode;
        }

        public void setResortCode(String value) {
            this.resortCode = value;
        }

        public String getResortName() {
            return this.resortName;
        }

        public void setResortName(String value) {
            this.resortName = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AccommodationClass {
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="Name")
        protected String name;
        @XmlAttribute(name="NationalCode")
        protected String nationalCode;
        @XmlAttribute(name="OfficialName")
        protected String officialName;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getNationalCode() {
            return this.nationalCode;
        }

        public void setNationalCode(String value) {
            this.nationalCode = value;
        }

        public String getOfficialName() {
            return this.officialName;
        }

        public void setOfficialName(String value) {
            this.officialName = value;
        }
    }
}

