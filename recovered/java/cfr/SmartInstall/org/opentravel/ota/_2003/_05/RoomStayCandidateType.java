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
import org.opentravel.ota._2003._05.AvailRequestSegmentsType;
import org.opentravel.ota._2003._05.GuestCountType;
import org.opentravel.ota._2003._05.OTAHotelAvailGetRQ;
import org.opentravel.ota._2003._05.OTAHotelInvCountRQ;
import org.opentravel.ota._2003._05.PkgRoomInventoryType;
import org.opentravel.ota._2003._05.RoomAmenityPrefType;
import org.opentravel.ota._2003._05.RoomProfileType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RoomStayCandidateType", propOrder={"guestCounts", "roomAmenity"})
@XmlSeeAlso(value={OTAHotelInvCountRQ.HotelInvCountRequests.HotelInvCountRequest.RoomTypeCandidates.RoomTypeCandidate.class, OTAHotelAvailGetRQ.HotelAvailRequests.HotelAvailRequest.RoomTypeCandidates.RoomTypeCandidate.class, PkgRoomInventoryType.class, RoomProfileType.class, AvailRequestSegmentsType.AvailRequestSegment.RoomStayCandidates.RoomStayCandidate.class})
public class RoomStayCandidateType {
    @XmlElement(name="GuestCounts")
    protected GuestCountType guestCounts;
    @XmlElement(name="RoomAmenity")
    protected List<RoomAmenityPrefType> roomAmenity;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="RatePlanCandidateRPH")
    protected String ratePlanCandidateRPH;
    @XmlAttribute(name="BookingCode")
    protected String bookingCode;
    @XmlAttribute(name="RoomType")
    protected String roomType;
    @XmlAttribute(name="RoomTypeCode")
    protected String roomTypeCode;
    @XmlAttribute(name="RoomCategory")
    protected String roomCategory;
    @XmlAttribute(name="RoomID")
    protected String roomID;
    @XmlAttribute(name="Floor")
    protected Integer floor;
    @XmlAttribute(name="InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name="RoomLocationCode")
    protected String roomLocationCode;
    @XmlAttribute(name="RoomViewCode")
    protected String roomViewCode;
    @XmlAttribute(name="BedTypeCode")
    protected List<String> bedTypeCode;
    @XmlAttribute(name="NonSmoking")
    protected Boolean nonSmoking;
    @XmlAttribute(name="Configuration")
    protected String configuration;
    @XmlAttribute(name="SizeMeasurement")
    protected String sizeMeasurement;
    @XmlAttribute(name="Quantity")
    protected Integer quantity;
    @XmlAttribute(name="Composite")
    protected Boolean composite;
    @XmlAttribute(name="RoomClassificationCode")
    protected String roomClassificationCode;
    @XmlAttribute(name="RoomArchitectureCode")
    protected String roomArchitectureCode;
    @XmlAttribute(name="RoomGender")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String roomGender;
    @XmlAttribute(name="SharedRoomInd")
    protected Boolean sharedRoomInd;
    @XmlAttribute(name="PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name="PromotionVendorCode")
    protected List<String> promotionVendorCode;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;

    public GuestCountType getGuestCounts() {
        return this.guestCounts;
    }

    public void setGuestCounts(GuestCountType value) {
        this.guestCounts = value;
    }

    public List<RoomAmenityPrefType> getRoomAmenity() {
        if (this.roomAmenity == null) {
            this.roomAmenity = new ArrayList<RoomAmenityPrefType>();
        }
        return this.roomAmenity;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public String getRatePlanCandidateRPH() {
        return this.ratePlanCandidateRPH;
    }

    public void setRatePlanCandidateRPH(String value) {
        this.ratePlanCandidateRPH = value;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setBookingCode(String value) {
        this.bookingCode = value;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public void setRoomType(String value) {
        this.roomType = value;
    }

    public String getRoomTypeCode() {
        return this.roomTypeCode;
    }

    public void setRoomTypeCode(String value) {
        this.roomTypeCode = value;
    }

    public String getRoomCategory() {
        return this.roomCategory;
    }

    public void setRoomCategory(String value) {
        this.roomCategory = value;
    }

    public String getRoomID() {
        return this.roomID;
    }

    public void setRoomID(String value) {
        this.roomID = value;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public void setFloor(Integer value) {
        this.floor = value;
    }

    public String getInvBlockCode() {
        return this.invBlockCode;
    }

    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    public String getRoomLocationCode() {
        return this.roomLocationCode;
    }

    public void setRoomLocationCode(String value) {
        this.roomLocationCode = value;
    }

    public String getRoomViewCode() {
        return this.roomViewCode;
    }

    public void setRoomViewCode(String value) {
        this.roomViewCode = value;
    }

    public List<String> getBedTypeCode() {
        if (this.bedTypeCode == null) {
            this.bedTypeCode = new ArrayList<String>();
        }
        return this.bedTypeCode;
    }

    public Boolean isNonSmoking() {
        return this.nonSmoking;
    }

    public void setNonSmoking(Boolean value) {
        this.nonSmoking = value;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String value) {
        this.configuration = value;
    }

    public String getSizeMeasurement() {
        return this.sizeMeasurement;
    }

    public void setSizeMeasurement(String value) {
        this.sizeMeasurement = value;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer value) {
        this.quantity = value;
    }

    public Boolean isComposite() {
        return this.composite;
    }

    public void setComposite(Boolean value) {
        this.composite = value;
    }

    public String getRoomClassificationCode() {
        return this.roomClassificationCode;
    }

    public void setRoomClassificationCode(String value) {
        this.roomClassificationCode = value;
    }

    public String getRoomArchitectureCode() {
        return this.roomArchitectureCode;
    }

    public void setRoomArchitectureCode(String value) {
        this.roomArchitectureCode = value;
    }

    public String getRoomGender() {
        return this.roomGender;
    }

    public void setRoomGender(String value) {
        this.roomGender = value;
    }

    public Boolean isSharedRoomInd() {
        return this.sharedRoomInd;
    }

    public void setSharedRoomInd(Boolean value) {
        this.sharedRoomInd = value;
    }

    public String getPromotionCode() {
        return this.promotionCode;
    }

    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    public List<String> getPromotionVendorCode() {
        if (this.promotionVendorCode == null) {
            this.promotionVendorCode = new ArrayList<String>();
        }
        return this.promotionVendorCode;
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
}

