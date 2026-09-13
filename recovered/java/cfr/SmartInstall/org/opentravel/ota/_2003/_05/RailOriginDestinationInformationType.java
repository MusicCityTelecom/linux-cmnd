/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.RailConnectionType;
import org.opentravel.ota._2003._05.TravelDateTimeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailOriginDestinationInformationType", propOrder={"originLocation", "destinationLocation", "connectionLocations"})
public class RailOriginDestinationInformationType
extends TravelDateTimeType {
    @XmlElement(name="OriginLocation", required=true)
    protected OriginLocation originLocation;
    @XmlElement(name="DestinationLocation", required=true)
    protected DestinationLocation destinationLocation;
    @XmlElement(name="ConnectionLocations")
    protected RailConnectionType connectionLocations;

    public OriginLocation getOriginLocation() {
        return this.originLocation;
    }

    public void setOriginLocation(OriginLocation value) {
        this.originLocation = value;
    }

    public DestinationLocation getDestinationLocation() {
        return this.destinationLocation;
    }

    public void setDestinationLocation(DestinationLocation value) {
        this.destinationLocation = value;
    }

    public RailConnectionType getConnectionLocations() {
        return this.connectionLocations;
    }

    public void setConnectionLocations(RailConnectionType value) {
        this.connectionLocations = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OriginLocation
    extends LocationType {
        @XmlAttribute(name="MultiCityStationInd")
        protected Boolean multiCityStationInd;
        @XmlAttribute(name="AlternateLocationInd")
        protected Boolean alternateLocationInd;

        public Boolean isMultiCityStationInd() {
            return this.multiCityStationInd;
        }

        public void setMultiCityStationInd(Boolean value) {
            this.multiCityStationInd = value;
        }

        public Boolean isAlternateLocationInd() {
            return this.alternateLocationInd;
        }

        public void setAlternateLocationInd(Boolean value) {
            this.alternateLocationInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class DestinationLocation
    extends LocationType {
        @XmlAttribute(name="MultiCityStationInd")
        protected Boolean multiCityStationInd;
        @XmlAttribute(name="AlternateLocationInd")
        protected Boolean alternateLocationInd;

        public Boolean isMultiCityStationInd() {
            return this.multiCityStationInd;
        }

        public void setMultiCityStationInd(Boolean value) {
            this.multiCityStationInd = value;
        }

        public Boolean isAlternateLocationInd() {
            return this.alternateLocationInd;
        }

        public void setAlternateLocationInd(Boolean value) {
            this.alternateLocationInd = value;
        }
    }
}

