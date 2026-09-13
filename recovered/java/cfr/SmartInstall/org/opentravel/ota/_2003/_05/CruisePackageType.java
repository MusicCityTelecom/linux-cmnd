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
import org.opentravel.ota._2003._05.CruiseGuestDetailType;
import org.opentravel.ota._2003._05.LocationGeneralType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CruisePackageType", propOrder={"location"})
@XmlSeeAlso(value={CruiseGuestDetailType.SelectedPackages.SelectedPackage.class})
public class CruisePackageType {
    @XmlElement(name="Location")
    protected List<Location> location;
    @XmlAttribute(name="PackageTypeCode", required=true)
    protected String packageTypeCode;
    @XmlAttribute(name="Status")
    protected String status;
    @XmlAttribute(name="CruisePackageCode")
    protected String cruisePackageCode;
    @XmlAttribute(name="InclusiveIndicator")
    protected Boolean inclusiveIndicator;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public List<Location> getLocation() {
        if (this.location == null) {
            this.location = new ArrayList<Location>();
        }
        return this.location;
    }

    public String getPackageTypeCode() {
        return this.packageTypeCode;
    }

    public void setPackageTypeCode(String value) {
        this.packageTypeCode = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getCruisePackageCode() {
        return this.cruisePackageCode;
    }

    public void setCruisePackageCode(String value) {
        this.cruisePackageCode = value;
    }

    public Boolean isInclusiveIndicator() {
        return this.inclusiveIndicator;
    }

    public void setInclusiveIndicator(Boolean value) {
        this.inclusiveIndicator = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"information"})
    public static class Location
    extends LocationGeneralType {
        @XmlElement(name="Information")
        protected ParagraphType information;
        @XmlAttribute(name="LocationName")
        protected String locationName;
        @XmlAttribute(name="LocationCode")
        protected String locationCode;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

        public ParagraphType getInformation() {
            return this.information;
        }

        public void setInformation(ParagraphType value) {
            this.information = value;
        }

        public String getLocationName() {
            return this.locationName;
        }

        public void setLocationName(String value) {
            this.locationName = value;
        }

        public String getLocationCode() {
            return this.locationCode;
        }

        public void setLocationCode(String value) {
            this.locationCode = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }

        public String getStart() {
            return this.start;
        }

        public void setStart(String value) {
            this.start = value;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String value) {
            this.duration = value;
        }

        public String getEnd() {
            return this.end;
        }

        public void setEnd(String value) {
            this.end = value;
        }
    }
}

