/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
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
import org.opentravel.ota._2003._05.CategoryLocationType;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.SailingCategoryInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CabinOptionType", propOrder={"cabinConfiguration", "measurementInfo", "remark"})
@XmlSeeAlso(value={SailingCategoryInfoType.SelectedCategory.SelectedCabin.class})
public class CabinOptionType {
    @XmlElement(name="CabinConfiguration")
    protected List<CabinConfiguration> cabinConfiguration;
    @XmlElement(name="MeasurementInfo")
    protected List<MeasurementInfo> measurementInfo;
    @XmlElement(name="Remark")
    protected FreeTextType remark;
    @XmlAttribute(name="Status", required=true)
    protected String status;
    @XmlAttribute(name="CategoryLocation")
    protected CategoryLocationType categoryLocation;
    @XmlAttribute(name="ShipSide")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shipSide;
    @XmlAttribute(name="PositionInShip")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String positionInShip;
    @XmlAttribute(name="BedType")
    protected String bedType;
    @XmlAttribute(name="ReleaseDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar releaseDateTime;
    @XmlAttribute(name="CabinCategoryStatusCode")
    protected String cabinCategoryStatusCode;
    @XmlAttribute(name="CabinCategoryCode")
    protected String cabinCategoryCode;
    @XmlAttribute(name="CabinRanking")
    protected Integer cabinRanking;
    @XmlAttribute(name="ConnectingCabinIndicator")
    protected Boolean connectingCabinIndicator;
    @XmlAttribute(name="ConnectingCabinNumber")
    protected String connectingCabinNumber;
    @XmlAttribute(name="DeckNumber")
    protected String deckNumber;
    @XmlAttribute(name="DeckName")
    protected String deckName;
    @XmlAttribute(name="CabinNumber", required=true)
    protected String cabinNumber;
    @XmlAttribute(name="MaxOccupancy")
    protected Integer maxOccupancy;
    @XmlAttribute(name="DeclineIndicator")
    protected Boolean declineIndicator;
    @XmlAttribute(name="HeldIndicator")
    protected Boolean heldIndicator;

    public List<CabinConfiguration> getCabinConfiguration() {
        if (this.cabinConfiguration == null) {
            this.cabinConfiguration = new ArrayList<CabinConfiguration>();
        }
        return this.cabinConfiguration;
    }

    public List<MeasurementInfo> getMeasurementInfo() {
        if (this.measurementInfo == null) {
            this.measurementInfo = new ArrayList<MeasurementInfo>();
        }
        return this.measurementInfo;
    }

    public FreeTextType getRemark() {
        return this.remark;
    }

    public void setRemark(FreeTextType value) {
        this.remark = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public CategoryLocationType getCategoryLocation() {
        return this.categoryLocation;
    }

    public void setCategoryLocation(CategoryLocationType value) {
        this.categoryLocation = value;
    }

    public String getShipSide() {
        return this.shipSide;
    }

    public void setShipSide(String value) {
        this.shipSide = value;
    }

    public String getPositionInShip() {
        return this.positionInShip;
    }

    public void setPositionInShip(String value) {
        this.positionInShip = value;
    }

    public String getBedType() {
        return this.bedType;
    }

    public void setBedType(String value) {
        this.bedType = value;
    }

    public XMLGregorianCalendar getReleaseDateTime() {
        return this.releaseDateTime;
    }

    public void setReleaseDateTime(XMLGregorianCalendar value) {
        this.releaseDateTime = value;
    }

    public String getCabinCategoryStatusCode() {
        return this.cabinCategoryStatusCode;
    }

    public void setCabinCategoryStatusCode(String value) {
        this.cabinCategoryStatusCode = value;
    }

    public String getCabinCategoryCode() {
        return this.cabinCategoryCode;
    }

    public void setCabinCategoryCode(String value) {
        this.cabinCategoryCode = value;
    }

    public Integer getCabinRanking() {
        return this.cabinRanking;
    }

    public void setCabinRanking(Integer value) {
        this.cabinRanking = value;
    }

    public Boolean isConnectingCabinIndicator() {
        return this.connectingCabinIndicator;
    }

    public void setConnectingCabinIndicator(Boolean value) {
        this.connectingCabinIndicator = value;
    }

    public String getConnectingCabinNumber() {
        return this.connectingCabinNumber;
    }

    public void setConnectingCabinNumber(String value) {
        this.connectingCabinNumber = value;
    }

    public String getDeckNumber() {
        return this.deckNumber;
    }

    public void setDeckNumber(String value) {
        this.deckNumber = value;
    }

    public String getDeckName() {
        return this.deckName;
    }

    public void setDeckName(String value) {
        this.deckName = value;
    }

    public String getCabinNumber() {
        return this.cabinNumber;
    }

    public void setCabinNumber(String value) {
        this.cabinNumber = value;
    }

    public Integer getMaxOccupancy() {
        return this.maxOccupancy;
    }

    public void setMaxOccupancy(Integer value) {
        this.maxOccupancy = value;
    }

    public Boolean isDeclineIndicator() {
        return this.declineIndicator;
    }

    public void setDeclineIndicator(Boolean value) {
        this.declineIndicator = value;
    }

    public Boolean isHeldIndicator() {
        return this.heldIndicator;
    }

    public void setHeldIndicator(Boolean value) {
        this.heldIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class MeasurementInfo {
        @XmlAttribute(name="DimensionInfo")
        protected String dimensionInfo;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="Name")
        protected String name;
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public String getDimensionInfo() {
            return this.dimensionInfo;
        }

        public void setDimensionInfo(String value) {
            this.dimensionInfo = value;
        }

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

        public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
        }

        public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

        public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CabinConfiguration {
        @XmlAttribute(name="BedConfigurationCode")
        protected String bedConfigurationCode;

        public String getBedConfigurationCode() {
            return this.bedConfigurationCode;
        }

        public void setBedConfigurationCode(String value) {
            this.bedConfigurationCode = value;
        }
    }
}

