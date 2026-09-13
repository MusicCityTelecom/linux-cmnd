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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.FlightLegType;
import org.opentravel.ota._2003._05.OtherServiceInfoType;
import org.opentravel.ota._2003._05.SeatRequestType;
import org.opentravel.ota._2003._05.SpecialRemarkType;
import org.opentravel.ota._2003._05.SpecialServiceRequestType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SpecialReqDetailsType", propOrder={"seatRequests", "specialServiceRequests", "otherServiceInformations", "remarks", "specialRemarks"})
public class SpecialReqDetailsType {
    @XmlElement(name="SeatRequests")
    protected SeatRequests seatRequests;
    @XmlElement(name="SpecialServiceRequests")
    protected SpecialServiceRequests specialServiceRequests;
    @XmlElement(name="OtherServiceInformations")
    protected OtherServiceInformations otherServiceInformations;
    @XmlElement(name="Remarks")
    protected Remarks remarks;
    @XmlElement(name="SpecialRemarks")
    protected SpecialRemarks specialRemarks;

    public SeatRequests getSeatRequests() {
        return this.seatRequests;
    }

    public void setSeatRequests(SeatRequests value) {
        this.seatRequests = value;
    }

    public SpecialServiceRequests getSpecialServiceRequests() {
        return this.specialServiceRequests;
    }

    public void setSpecialServiceRequests(SpecialServiceRequests value) {
        this.specialServiceRequests = value;
    }

    public OtherServiceInformations getOtherServiceInformations() {
        return this.otherServiceInformations;
    }

    public void setOtherServiceInformations(OtherServiceInformations value) {
        this.otherServiceInformations = value;
    }

    public Remarks getRemarks() {
        return this.remarks;
    }

    public void setRemarks(Remarks value) {
        this.remarks = value;
    }

    public SpecialRemarks getSpecialRemarks() {
        return this.specialRemarks;
    }

    public void setSpecialRemarks(SpecialRemarks value) {
        this.specialRemarks = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"specialServiceRequest"})
    public static class SpecialServiceRequests {
        @XmlElement(name="SpecialServiceRequest", required=true)
        protected List<SpecialServiceRequest> specialServiceRequest;

        public List<SpecialServiceRequest> getSpecialServiceRequest() {
            if (this.specialServiceRequest == null) {
                this.specialServiceRequest = new ArrayList<SpecialServiceRequest>();
            }
            return this.specialServiceRequest;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"flightLeg"})
        public static class SpecialServiceRequest
        extends SpecialServiceRequestType {
            @XmlElement(name="FlightLeg")
            protected FlightLegType flightLeg;
            @XmlAttribute(name="TravelerRefNumberRPHList")
            protected List<String> travelerRefNumberRPHList;
            @XmlAttribute(name="FlightRefNumberRPHList")
            protected List<String> flightRefNumberRPHList;
            @XmlAttribute(name="BirthDate")
            @XmlSchemaType(name="date")
            protected XMLGregorianCalendar birthDate;

            public FlightLegType getFlightLeg() {
                return this.flightLeg;
            }

            public void setFlightLeg(FlightLegType value) {
                this.flightLeg = value;
            }

            public List<String> getTravelerRefNumberRPHList() {
                if (this.travelerRefNumberRPHList == null) {
                    this.travelerRefNumberRPHList = new ArrayList<String>();
                }
                return this.travelerRefNumberRPHList;
            }

            public List<String> getFlightRefNumberRPHList() {
                if (this.flightRefNumberRPHList == null) {
                    this.flightRefNumberRPHList = new ArrayList<String>();
                }
                return this.flightRefNumberRPHList;
            }

            public XMLGregorianCalendar getBirthDate() {
                return this.birthDate;
            }

            public void setBirthDate(XMLGregorianCalendar value) {
                this.birthDate = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"specialRemark"})
    public static class SpecialRemarks {
        @XmlElement(name="SpecialRemark", required=true)
        protected List<SpecialRemark> specialRemark;

        public List<SpecialRemark> getSpecialRemark() {
            if (this.specialRemark == null) {
                this.specialRemark = new ArrayList<SpecialRemark>();
            }
            return this.specialRemark;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"flightLeg"})
        public static class SpecialRemark
        extends SpecialRemarkType {
            @XmlElement(name="FlightLeg")
            protected FlightLegType flightLeg;
            @XmlAttribute(name="Operation")
            protected ActionType operation;
            @XmlAttribute(name="RPH")
            protected String rph;

            public FlightLegType getFlightLeg() {
                return this.flightLeg;
            }

            public void setFlightLeg(FlightLegType value) {
                this.flightLeg = value;
            }

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"seatRequest"})
    public static class SeatRequests {
        @XmlElement(name="SeatRequest", required=true)
        protected List<SeatRequest> seatRequest;

        public List<SeatRequest> getSeatRequest() {
            if (this.seatRequest == null) {
                this.seatRequest = new ArrayList<SeatRequest>();
            }
            return this.seatRequest;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class SeatRequest
        extends SeatRequestType {
            @XmlAttribute(name="TravelerRefNumberRPHList")
            protected List<String> travelerRefNumberRPHList;
            @XmlAttribute(name="FlightRefNumberRPHList")
            protected List<String> flightRefNumberRPHList;
            @XmlAttribute(name="PartialSeatingInd")
            protected Boolean partialSeatingInd;

            public List<String> getTravelerRefNumberRPHList() {
                if (this.travelerRefNumberRPHList == null) {
                    this.travelerRefNumberRPHList = new ArrayList<String>();
                }
                return this.travelerRefNumberRPHList;
            }

            public List<String> getFlightRefNumberRPHList() {
                if (this.flightRefNumberRPHList == null) {
                    this.flightRefNumberRPHList = new ArrayList<String>();
                }
                return this.flightRefNumberRPHList;
            }

            public Boolean isPartialSeatingInd() {
                return this.partialSeatingInd;
            }

            public void setPartialSeatingInd(Boolean value) {
                this.partialSeatingInd = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"remark"})
    public static class Remarks {
        @XmlElement(name="Remark", required=true)
        protected List<Remark> remark;

        public List<Remark> getRemark() {
            if (this.remark == null) {
                this.remark = new ArrayList<Remark>();
            }
            return this.remark;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"value"})
        public static class Remark {
            @XmlValue
            protected String value;
            @XmlAttribute(name="RPH")
            protected String rph;
            @XmlAttribute(name="Operation")
            protected ActionType operation;

            public String getValue() {
                return this.value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"otherServiceInformation"})
    public static class OtherServiceInformations {
        @XmlElement(name="OtherServiceInformation", required=true)
        protected List<OtherServiceInformation> otherServiceInformation;

        public List<OtherServiceInformation> getOtherServiceInformation() {
            if (this.otherServiceInformation == null) {
                this.otherServiceInformation = new ArrayList<OtherServiceInformation>();
            }
            return this.otherServiceInformation;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class OtherServiceInformation
        extends OtherServiceInfoType {
            @XmlAttribute(name="RPH")
            protected String rph;
            @XmlAttribute(name="Operation")
            protected ActionType operation;

            public String getRPH() {
                return this.rph;
            }

            public void setRPH(String value) {
                this.rph = value;
            }

            public ActionType getOperation() {
                return this.operation;
            }

            public void setOperation(ActionType value) {
                this.operation = value;
            }
        }
    }
}

