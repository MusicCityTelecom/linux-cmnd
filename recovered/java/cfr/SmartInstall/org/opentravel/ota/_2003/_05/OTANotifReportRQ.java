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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AvailStatusMessageType;
import org.opentravel.ota._2003._05.HotelDescriptiveContentType;
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.MessageAcknowledgementType;
import org.opentravel.ota._2003._05.RateAmountMessageType;
import org.opentravel.ota._2003._05.VehicleResRSAdditionalInfoType;
import org.opentravel.ota._2003._05.VehicleResRSCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"notifDetails"})
@XmlRootElement(name="OTA_NotifReportRQ")
public class OTANotifReportRQ
extends MessageAcknowledgementType {
    @XmlElement(name="NotifDetails")
    protected NotifDetails notifDetails;

    public NotifDetails getNotifDetails() {
        return this.notifDetails;
    }

    public void setNotifDetails(NotifDetails value) {
        this.notifDetails = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"hotelNotifReport", "vehNotifReport"})
    public static class NotifDetails {
        @XmlElement(name="HotelNotifReport")
        protected HotelNotifReport hotelNotifReport;
        @XmlElement(name="VehNotifReport")
        protected VehNotifReport vehNotifReport;

        public HotelNotifReport getHotelNotifReport() {
            return this.hotelNotifReport;
        }

        public void setHotelNotifReport(HotelNotifReport value) {
            this.hotelNotifReport = value;
        }

        public VehNotifReport getVehNotifReport() {
            return this.vehNotifReport;
        }

        public void setVehNotifReport(VehNotifReport value) {
            this.vehNotifReport = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"vehRes"})
        public static class VehNotifReport {
            @XmlElement(name="VehRes")
            protected VehRes vehRes;

            public VehRes getVehRes() {
                return this.vehRes;
            }

            public void setVehRes(VehRes value) {
                this.vehRes = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"vehNotifReportRQCore", "vehNotifReportRQInfo"})
            public static class VehRes {
                @XmlElement(name="VehNotifReportRQCore", required=true)
                protected VehNotifReportRQCore vehNotifReportRQCore;
                @XmlElement(name="VehNotifReportRQInfo")
                protected VehicleResRSAdditionalInfoType vehNotifReportRQInfo;

                public VehNotifReportRQCore getVehNotifReportRQCore() {
                    return this.vehNotifReportRQCore;
                }

                public void setVehNotifReportRQCore(VehNotifReportRQCore value) {
                    this.vehNotifReportRQCore = value;
                }

                public VehicleResRSAdditionalInfoType getVehNotifReportRQInfo() {
                    return this.vehNotifReportRQInfo;
                }

                public void setVehNotifReportRQInfo(VehicleResRSAdditionalInfoType value) {
                    this.vehNotifReportRQInfo = value;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class VehNotifReportRQCore
                extends VehicleResRSCoreType {
                    @XmlAttribute(name="ReservationStatus")
                    protected String reservationStatus;

                    public String getReservationStatus() {
                        return this.reservationStatus;
                    }

                    public void setReservationStatus(String value) {
                        this.reservationStatus = value;
                    }
                }
            }
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"hotelReservations", "availStatusMessages", "rateAmountMessages", "hotelDescriptiveContents"})
        public static class HotelNotifReport {
            @XmlElement(name="HotelReservations")
            protected HotelReservations hotelReservations;
            @XmlElement(name="AvailStatusMessages")
            protected AvailStatusMessages availStatusMessages;
            @XmlElement(name="RateAmountMessages")
            protected RateAmountMessages rateAmountMessages;
            @XmlElement(name="HotelDescriptiveContents")
            protected HotelDescriptiveContents hotelDescriptiveContents;

            public HotelReservations getHotelReservations() {
                return this.hotelReservations;
            }

            public void setHotelReservations(HotelReservations value) {
                this.hotelReservations = value;
            }

            public AvailStatusMessages getAvailStatusMessages() {
                return this.availStatusMessages;
            }

            public void setAvailStatusMessages(AvailStatusMessages value) {
                this.availStatusMessages = value;
            }

            public RateAmountMessages getRateAmountMessages() {
                return this.rateAmountMessages;
            }

            public void setRateAmountMessages(RateAmountMessages value) {
                this.rateAmountMessages = value;
            }

            public HotelDescriptiveContents getHotelDescriptiveContents() {
                return this.hotelDescriptiveContents;
            }

            public void setHotelDescriptiveContents(HotelDescriptiveContents value) {
                this.hotelDescriptiveContents = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"rateAmountMessage"})
            public static class RateAmountMessages {
                @XmlElement(name="RateAmountMessage", required=true)
                protected List<RateAmountMessage> rateAmountMessage;
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

                public List<RateAmountMessage> getRateAmountMessage() {
                    if (this.rateAmountMessage == null) {
                        this.rateAmountMessage = new ArrayList<RateAmountMessage>();
                    }
                    return this.rateAmountMessage;
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

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class RateAmountMessage
                extends RateAmountMessageType {
                    @XmlAttribute(name="WarningRPH")
                    protected List<String> warningRPH;

                    public List<String> getWarningRPH() {
                        if (this.warningRPH == null) {
                            this.warningRPH = new ArrayList<String>();
                        }
                        return this.warningRPH;
                    }
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"hotelReservation"})
            public static class HotelReservations {
                @XmlElement(name="HotelReservation", required=true)
                protected List<HotelReservation> hotelReservation;

                public List<HotelReservation> getHotelReservation() {
                    if (this.hotelReservation == null) {
                        this.hotelReservation = new ArrayList<HotelReservation>();
                    }
                    return this.hotelReservation;
                }

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class HotelReservation
                extends HotelReservationType {
                    @XmlAttribute(name="WarningRPH")
                    protected List<String> warningRPH;

                    public List<String> getWarningRPH() {
                        if (this.warningRPH == null) {
                            this.warningRPH = new ArrayList<String>();
                        }
                        return this.warningRPH;
                    }
                }
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"hotelDescriptiveContent"})
            public static class HotelDescriptiveContents {
                @XmlElement(name="HotelDescriptiveContent", required=true)
                protected List<HotelDescriptiveContent> hotelDescriptiveContent;
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

                public List<HotelDescriptiveContent> getHotelDescriptiveContent() {
                    if (this.hotelDescriptiveContent == null) {
                        this.hotelDescriptiveContent = new ArrayList<HotelDescriptiveContent>();
                    }
                    return this.hotelDescriptiveContent;
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

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class HotelDescriptiveContent
                extends HotelDescriptiveContentType {
                    @XmlAttribute(name="WarningRPH")
                    protected List<String> warningRPH;
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

                    public List<String> getWarningRPH() {
                        if (this.warningRPH == null) {
                            this.warningRPH = new ArrayList<String>();
                        }
                        return this.warningRPH;
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
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="", propOrder={"availStatusMessage"})
            public static class AvailStatusMessages {
                @XmlElement(name="AvailStatusMessage", required=true)
                protected List<AvailStatusMessage> availStatusMessage;
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

                public List<AvailStatusMessage> getAvailStatusMessage() {
                    if (this.availStatusMessage == null) {
                        this.availStatusMessage = new ArrayList<AvailStatusMessage>();
                    }
                    return this.availStatusMessage;
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

                @XmlAccessorType(value=XmlAccessType.FIELD)
                @XmlType(name="")
                public static class AvailStatusMessage
                extends AvailStatusMessageType {
                    @XmlAttribute(name="WarningRPH")
                    protected List<String> warningRPH;

                    public List<String> getWarningRPH() {
                        if (this.warningRPH == null) {
                            this.warningRPH = new ArrayList<String>();
                        }
                        return this.warningRPH;
                    }
                }
            }
        }
    }
}

