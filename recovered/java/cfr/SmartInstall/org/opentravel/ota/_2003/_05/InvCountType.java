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
import org.opentravel.ota._2003._05.BaseInvCountType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InvCountType", propOrder={"inventory", "uniqueID"})
public class InvCountType {
    @XmlElement(name="Inventory", required=true)
    protected List<BaseInvCountType> inventory;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlAttribute(name="ChainCode")
    protected String chainCode;
    @XmlAttribute(name="BrandCode")
    protected String brandCode;
    @XmlAttribute(name="HotelCode")
    protected String hotelCode;
    @XmlAttribute(name="HotelCityCode")
    protected String hotelCityCode;
    @XmlAttribute(name="HotelName")
    protected String hotelName;
    @XmlAttribute(name="HotelCodeContext")
    protected String hotelCodeContext;
    @XmlAttribute(name="ChainName")
    protected String chainName;
    @XmlAttribute(name="BrandName")
    protected String brandName;
    @XmlAttribute(name="AreaID")
    protected String areaID;

    public List<BaseInvCountType> getInventory() {
        if (this.inventory == null) {
            this.inventory = new ArrayList<BaseInvCountType>();
        }
        return this.inventory;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public String getChainCode() {
        return this.chainCode;
    }

    public void setChainCode(String value) {
        this.chainCode = value;
    }

    public String getBrandCode() {
        return this.brandCode;
    }

    public void setBrandCode(String value) {
        this.brandCode = value;
    }

    public String getHotelCode() {
        return this.hotelCode;
    }

    public void setHotelCode(String value) {
        this.hotelCode = value;
    }

    public String getHotelCityCode() {
        return this.hotelCityCode;
    }

    public void setHotelCityCode(String value) {
        this.hotelCityCode = value;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public void setHotelName(String value) {
        this.hotelName = value;
    }

    public String getHotelCodeContext() {
        return this.hotelCodeContext;
    }

    public void setHotelCodeContext(String value) {
        this.hotelCodeContext = value;
    }

    public String getChainName() {
        return this.chainName;
    }

    public void setChainName(String value) {
        this.chainName = value;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String value) {
        this.brandName = value;
    }

    public String getAreaID() {
        return this.areaID;
    }

    public void setAreaID(String value) {
        this.areaID = value;
    }
}

