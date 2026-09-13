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
import org.opentravel.ota._2003._05.DistanceUnitNameType;
import org.opentravel.ota._2003._05.FormattedTextType;
import org.opentravel.ota._2003._05.LocationDetailVehicleInfoType;
import org.opentravel.ota._2003._05.VehicleType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleLocationVehiclesType", propOrder={"vehicleInfos", "vehicle"})
public class VehicleLocationVehiclesType {
    @XmlElement(name="VehicleInfos")
    protected VehicleInfos vehicleInfos;
    @XmlElement(name="Vehicle")
    protected List<Vehicle> vehicle;

    public VehicleInfos getVehicleInfos() {
        return this.vehicleInfos;
    }

    public void setVehicleInfos(VehicleInfos value) {
        this.vehicleInfos = value;
    }

    public List<Vehicle> getVehicle() {
        if (this.vehicle == null) {
            this.vehicle = new ArrayList<Vehicle>();
        }
        return this.vehicle;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"vehicleInfo"})
    public static class VehicleInfos {
        @XmlElement(name="VehicleInfo", required=true)
        protected List<VehicleInfo> vehicleInfo;

        public List<VehicleInfo> getVehicleInfo() {
            if (this.vehicleInfo == null) {
                this.vehicleInfo = new ArrayList<VehicleInfo>();
            }
            return this.vehicleInfo;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class VehicleInfo
        extends FormattedTextType {
            @XmlAttribute(name="Type", required=true)
            protected LocationDetailVehicleInfoType type;

            public LocationDetailVehicleInfoType getType() {
                return this.type;
            }

            public void setType(LocationDetailVehicleInfoType value) {
                this.type = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"text"})
    public static class Vehicle
    extends VehicleType {
        @XmlElement(name="Text")
        protected List<FormattedTextType> text;
        @XmlAttribute(name="IsConfirmableInd")
        protected Boolean isConfirmableInd;
        @XmlAttribute(name="DistanceUnit")
        protected DistanceUnitNameType distanceUnit;
        @XmlAttribute(name="DistancePerFuelUnit")
        protected Integer distancePerFuelUnit;

        public List<FormattedTextType> getText() {
            if (this.text == null) {
                this.text = new ArrayList<FormattedTextType>();
            }
            return this.text;
        }

        public Boolean isIsConfirmableInd() {
            return this.isConfirmableInd;
        }

        public void setIsConfirmableInd(Boolean value) {
            this.isConfirmableInd = value;
        }

        public DistanceUnitNameType getDistanceUnit() {
            return this.distanceUnit;
        }

        public void setDistanceUnit(DistanceUnitNameType value) {
            this.distanceUnit = value;
        }

        public Integer getDistancePerFuelUnit() {
            return this.distancePerFuelUnit;
        }

        public void setDistancePerFuelUnit(Integer value) {
            this.distancePerFuelUnit = value;
        }
    }
}

