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
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.ExtrasType;
import org.opentravel.ota._2003._05.ItineraryItemRequestType;
import org.opentravel.ota._2003._05.URLType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PackageType", propOrder={"url", "companyName", "dateRange", "itineraryItems", "extras"})
public class PackageType {
    @XmlElement(name="URL")
    protected URLType url;
    @XmlElement(name="CompanyName")
    protected CompanyNameType companyName;
    @XmlElement(name="DateRange")
    protected DateRange dateRange;
    @XmlElement(name="ItineraryItems")
    protected ItineraryItems itineraryItems;
    @XmlElement(name="Extras")
    protected Extras extras;
    @XmlAttribute(name="Type")
    protected String type;
    @XmlAttribute(name="TravelCode")
    protected String travelCode;
    @XmlAttribute(name="TourCode")
    protected String tourCode;
    @XmlAttribute(name="BoardCode")
    protected String boardCode;
    @XmlAttribute(name="PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name="FreeChildrenQuantity")
    protected Integer freeChildrenQuantity;
    @XmlAttribute(name="BrandCode")
    protected String brandCode;
    @XmlAttribute(name="ProductCode")
    protected String productCode;
    @XmlAttribute(name="ID")
    protected String id;

    public URLType getURL() {
        return this.url;
    }

    public void setURL(URLType value) {
        this.url = value;
    }

    public CompanyNameType getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(CompanyNameType value) {
        this.companyName = value;
    }

    public DateRange getDateRange() {
        return this.dateRange;
    }

    public void setDateRange(DateRange value) {
        this.dateRange = value;
    }

    public ItineraryItems getItineraryItems() {
        return this.itineraryItems;
    }

    public void setItineraryItems(ItineraryItems value) {
        this.itineraryItems = value;
    }

    public Extras getExtras() {
        return this.extras;
    }

    public void setExtras(Extras value) {
        this.extras = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getTravelCode() {
        return this.travelCode;
    }

    public void setTravelCode(String value) {
        this.travelCode = value;
    }

    public String getTourCode() {
        return this.tourCode;
    }

    public void setTourCode(String value) {
        this.tourCode = value;
    }

    public String getBoardCode() {
        return this.boardCode;
    }

    public void setBoardCode(String value) {
        this.boardCode = value;
    }

    public String getPromotionCode() {
        return this.promotionCode;
    }

    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    public Integer getFreeChildrenQuantity() {
        return this.freeChildrenQuantity;
    }

    public void setFreeChildrenQuantity(Integer value) {
        this.freeChildrenQuantity = value;
    }

    public String getBrandCode() {
        return this.brandCode;
    }

    public void setBrandCode(String value) {
        this.brandCode = value;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String value) {
        this.productCode = value;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String value) {
        this.id = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"itineraryItem"})
    public static class ItineraryItems {
        @XmlElement(name="ItineraryItem", required=true)
        protected List<ItineraryItemRequestType> itineraryItem;

        public List<ItineraryItemRequestType> getItineraryItem() {
            if (this.itineraryItem == null) {
                this.itineraryItem = new ArrayList<ItineraryItemRequestType>();
            }
            return this.itineraryItem;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"extra"})
    public static class Extras {
        @XmlElement(name="Extra", required=true)
        protected List<ExtrasType> extra;

        public List<ExtrasType> getExtra() {
            if (this.extra == null) {
                this.extra = new ArrayList<ExtrasType>();
            }
            return this.extra;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DateRange {
        @XmlAttribute(name="Start")
        protected String start;
        @XmlAttribute(name="Duration")
        protected String duration;
        @XmlAttribute(name="End")
        protected String end;

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

