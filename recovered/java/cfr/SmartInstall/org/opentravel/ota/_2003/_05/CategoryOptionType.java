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
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CategoryLocationType;
import org.opentravel.ota._2003._05.PriceInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CategoryOptionType", propOrder={"priceInfos", "dining"})
public class CategoryOptionType {
    @XmlElement(name="PriceInfos")
    protected PriceInfos priceInfos;
    @XmlElement(name="Dining")
    protected List<Dining> dining;
    @XmlAttribute(name="Status")
    protected String status;
    @XmlAttribute(name="HeldIndicator")
    protected Boolean heldIndicator;
    @XmlAttribute(name="CategoryLocation")
    protected CategoryLocationType categoryLocation;
    @XmlAttribute(name="MaxOccupancy")
    protected Integer maxOccupancy;
    @XmlAttribute(name="ListOfCategoryQualifierCodes")
    protected List<String> listOfCategoryQualifierCodes;
    @XmlAttribute(name="AvailableGroupAllocationQty")
    protected Integer availableGroupAllocationQty;
    @XmlAttribute(name="FareCode")
    protected String fareCode;
    @XmlAttribute(name="GroupCode")
    protected String groupCode;
    @XmlAttribute(name="BerthedCategoryCode")
    protected String berthedCategoryCode;
    @XmlAttribute(name="PricedCategoryCode")
    protected String pricedCategoryCode;

    public PriceInfos getPriceInfos() {
        return this.priceInfos;
    }

    public void setPriceInfos(PriceInfos value) {
        this.priceInfos = value;
    }

    public List<Dining> getDining() {
        if (this.dining == null) {
            this.dining = new ArrayList<Dining>();
        }
        return this.dining;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public Boolean isHeldIndicator() {
        return this.heldIndicator;
    }

    public void setHeldIndicator(Boolean value) {
        this.heldIndicator = value;
    }

    public CategoryLocationType getCategoryLocation() {
        return this.categoryLocation;
    }

    public void setCategoryLocation(CategoryLocationType value) {
        this.categoryLocation = value;
    }

    public Integer getMaxOccupancy() {
        return this.maxOccupancy;
    }

    public void setMaxOccupancy(Integer value) {
        this.maxOccupancy = value;
    }

    public List<String> getListOfCategoryQualifierCodes() {
        if (this.listOfCategoryQualifierCodes == null) {
            this.listOfCategoryQualifierCodes = new ArrayList<String>();
        }
        return this.listOfCategoryQualifierCodes;
    }

    public Integer getAvailableGroupAllocationQty() {
        return this.availableGroupAllocationQty;
    }

    public void setAvailableGroupAllocationQty(Integer value) {
        this.availableGroupAllocationQty = value;
    }

    public String getFareCode() {
        return this.fareCode;
    }

    public void setFareCode(String value) {
        this.fareCode = value;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String value) {
        this.groupCode = value;
    }

    public String getBerthedCategoryCode() {
        return this.berthedCategoryCode;
    }

    public void setBerthedCategoryCode(String value) {
        this.berthedCategoryCode = value;
    }

    public String getPricedCategoryCode() {
        return this.pricedCategoryCode;
    }

    public void setPricedCategoryCode(String value) {
        this.pricedCategoryCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"priceInfo"})
    public static class PriceInfos {
        @XmlElement(name="PriceInfo", required=true)
        protected List<PriceInfo> priceInfo;

        public List<PriceInfo> getPriceInfo() {
            if (this.priceInfo == null) {
                this.priceInfo = new ArrayList<PriceInfo>();
            }
            return this.priceInfo;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class PriceInfo
        extends PriceInfoType {
            @XmlAttribute(name="FareCode")
            protected String fareCode;
            @XmlAttribute(name="GroupCode")
            protected String groupCode;

            public String getFareCode() {
                return this.fareCode;
            }

            public void setFareCode(String value) {
                this.fareCode = value;
            }

            public String getGroupCode() {
                return this.groupCode;
            }

            public void setGroupCode(String value) {
                this.groupCode = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Dining {
        @XmlAttribute(name="Sitting", required=true)
        protected String sitting;
        @XmlAttribute(name="Status")
        protected String status;
        @XmlAttribute(name="Occupancy")
        protected Integer occupancy;

        public String getSitting() {
            return this.sitting;
        }

        public void setSitting(String value) {
            this.sitting = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public Integer getOccupancy() {
            return this.occupancy;
        }

        public void setOccupancy(Integer value) {
            this.occupancy = value;
        }
    }
}

