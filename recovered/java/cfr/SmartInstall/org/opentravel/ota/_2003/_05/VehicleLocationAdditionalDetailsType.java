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
import org.opentravel.ota._2003._05.FormattedTextType;
import org.opentravel.ota._2003._05.LocationDetailShuttleInfoType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.OperationSchedulesType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.VehicleLocationInformationType;
import org.opentravel.ota._2003._05.VehicleWhereAtFacilityType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleLocationAdditionalDetailsType", propOrder={"vehRentLocInfos", "parkLocation", "counterLocation", "operationSchedules", "shuttle", "oneWayDropLocations", "tpaExtensions"})
public class VehicleLocationAdditionalDetailsType {
    @XmlElement(name="VehRentLocInfos")
    protected VehRentLocInfos vehRentLocInfos;
    @XmlElement(name="ParkLocation")
    protected VehicleWhereAtFacilityType parkLocation;
    @XmlElement(name="CounterLocation")
    protected VehicleWhereAtFacilityType counterLocation;
    @XmlElement(name="OperationSchedules")
    protected OperationSchedulesType operationSchedules;
    @XmlElement(name="Shuttle")
    protected Shuttle shuttle;
    @XmlElement(name="OneWayDropLocations")
    protected OneWayDropLocations oneWayDropLocations;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public VehRentLocInfos getVehRentLocInfos() {
        return this.vehRentLocInfos;
    }

    public void setVehRentLocInfos(VehRentLocInfos value) {
        this.vehRentLocInfos = value;
    }

    public VehicleWhereAtFacilityType getParkLocation() {
        return this.parkLocation;
    }

    public void setParkLocation(VehicleWhereAtFacilityType value) {
        this.parkLocation = value;
    }

    public VehicleWhereAtFacilityType getCounterLocation() {
        return this.counterLocation;
    }

    public void setCounterLocation(VehicleWhereAtFacilityType value) {
        this.counterLocation = value;
    }

    public OperationSchedulesType getOperationSchedules() {
        return this.operationSchedules;
    }

    public void setOperationSchedules(OperationSchedulesType value) {
        this.operationSchedules = value;
    }

    public Shuttle getShuttle() {
        return this.shuttle;
    }

    public void setShuttle(Shuttle value) {
        this.shuttle = value;
    }

    public OneWayDropLocations getOneWayDropLocations() {
        return this.oneWayDropLocations;
    }

    public void setOneWayDropLocations(OneWayDropLocations value) {
        this.oneWayDropLocations = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vehRentLocInfo"})
    public static class VehRentLocInfos {
        @XmlElement(name="VehRentLocInfo", required=true)
        protected List<VehicleLocationInformationType> vehRentLocInfo;

        public List<VehicleLocationInformationType> getVehRentLocInfo() {
            if (this.vehRentLocInfo == null) {
                this.vehRentLocInfo = new ArrayList<VehicleLocationInformationType>();
            }
            return this.vehRentLocInfo;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"shuttleInfos", "operationSchedules"})
    public static class Shuttle {
        @XmlElement(name="ShuttleInfos")
        protected ShuttleInfos shuttleInfos;
        @XmlElement(name="OperationSchedules")
        protected OperationSchedulesType operationSchedules;

        public ShuttleInfos getShuttleInfos() {
            return this.shuttleInfos;
        }

        public void setShuttleInfos(ShuttleInfos value) {
            this.shuttleInfos = value;
        }

        public OperationSchedulesType getOperationSchedules() {
            return this.operationSchedules;
        }

        public void setOperationSchedules(OperationSchedulesType value) {
            this.operationSchedules = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"shuttleInfo"})
        public static class ShuttleInfos {
            @XmlElement(name="ShuttleInfo", required=true)
            protected List<ShuttleInfo> shuttleInfo;

            public List<ShuttleInfo> getShuttleInfo() {
                if (this.shuttleInfo == null) {
                    this.shuttleInfo = new ArrayList<ShuttleInfo>();
                }
                return this.shuttleInfo;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class ShuttleInfo
            extends FormattedTextType {
                @XmlAttribute(name="Type", required=true)
                protected LocationDetailShuttleInfoType type;

                public LocationDetailShuttleInfoType getType() {
                    return this.type;
                }

                public void setType(LocationDetailShuttleInfoType value) {
                    this.type = value;
                }
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"oneWayDropLocation"})
    public static class OneWayDropLocations {
        @XmlElement(name="OneWayDropLocation", required=true)
        protected List<OneWayDropLocation> oneWayDropLocation;

        public List<OneWayDropLocation> getOneWayDropLocation() {
            if (this.oneWayDropLocation == null) {
                this.oneWayDropLocation = new ArrayList<OneWayDropLocation>();
            }
            return this.oneWayDropLocation;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class OneWayDropLocation
        extends LocationType {
            @XmlAttribute(name="ExtendedLocationCode")
            protected String extendedLocationCode;

            public String getExtendedLocationCode() {
                return this.extendedLocationCode;
            }

            public void setExtendedLocationCode(String value) {
                this.extendedLocationCode = value;
            }
        }
    }
}

