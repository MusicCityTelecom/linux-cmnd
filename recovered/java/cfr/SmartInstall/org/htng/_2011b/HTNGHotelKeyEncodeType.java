/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGBasicOrSuiteRoomType;
import org.htng._2011b.HTNGMagneticStripeType;
import org.htng._2011b.HTNGSmartCardDataType;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_HotelKeyEncodeType", propOrder={"encoder", "magneticData", "smartCardData", "timeSpan", "accessAreas", "roomType"})
public class HTNGHotelKeyEncodeType {
    @XmlElement(name="Encoder")
    protected UniqueIDType encoder;
    @XmlElement(name="MagneticData")
    protected HTNGMagneticStripeType magneticData;
    @XmlElement(name="SmartCardData")
    protected HTNGSmartCardDataType smartCardData;
    @XmlElement(name="TimeSpan", required=true)
    protected DateTimeSpanType timeSpan;
    @XmlElement(name="AccessAreas")
    protected AccessAreas accessAreas;
    @XmlElement(name="RoomType", required=true)
    protected HTNGBasicOrSuiteRoomType roomType;
    @XmlAttribute(name="ReturnTrackData", required=true)
    protected boolean returnTrackData;
    @XmlAttribute(name="Quantity")
    protected Integer quantity;
    @XmlAttribute(name="KeyType", required=true)
    protected ActionType keyType;

    public UniqueIDType getEncoder() {
        return this.encoder;
    }

    public void setEncoder(UniqueIDType value) {
        this.encoder = value;
    }

    public HTNGMagneticStripeType getMagneticData() {
        return this.magneticData;
    }

    public void setMagneticData(HTNGMagneticStripeType value) {
        this.magneticData = value;
    }

    public HTNGSmartCardDataType getSmartCardData() {
        return this.smartCardData;
    }

    public void setSmartCardData(HTNGSmartCardDataType value) {
        this.smartCardData = value;
    }

    public DateTimeSpanType getTimeSpan() {
        return this.timeSpan;
    }

    public void setTimeSpan(DateTimeSpanType value) {
        this.timeSpan = value;
    }

    public AccessAreas getAccessAreas() {
        return this.accessAreas;
    }

    public void setAccessAreas(AccessAreas value) {
        this.accessAreas = value;
    }

    public HTNGBasicOrSuiteRoomType getRoomType() {
        return this.roomType;
    }

    public void setRoomType(HTNGBasicOrSuiteRoomType value) {
        this.roomType = value;
    }

    public boolean isReturnTrackData() {
        return this.returnTrackData;
    }

    public void setReturnTrackData(boolean value) {
        this.returnTrackData = value;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    public ActionType getKeyType() {
        return this.keyType;
    }

    public void setKeyType(ActionType value) {
        this.keyType = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"accessArea"})
    public static class AccessAreas {
        @XmlElement(name="AccessArea", required=true)
        protected List<UniqueIDType> accessArea;

        public List<UniqueIDType> getAccessArea() {
            if (this.accessArea == null) {
                this.accessArea = new ArrayList<UniqueIDType>();
            }
            return this.accessArea;
        }
    }
}

