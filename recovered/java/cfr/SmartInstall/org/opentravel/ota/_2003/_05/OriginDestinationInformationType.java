/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ConnectionType;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.TravelDateTimeType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OriginDestinationInformationType", propOrder={"originLocation", "destinationLocation", "connectionLocations"})
public class OriginDestinationInformationType
extends TravelDateTimeType {
    @XmlElement(name="OriginLocation", required=true)
    protected OriginLocation originLocation;
    @XmlElement(name="DestinationLocation", required=true)
    protected DestinationLocation destinationLocation;
    @XmlElement(name="ConnectionLocations")
    protected ConnectionType connectionLocations;

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

    public ConnectionType getConnectionLocations() {
        return this.connectionLocations;
    }

    public void setConnectionLocations(ConnectionType value) {
        this.connectionLocations = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OriginLocation
    extends LocationType {
        @XmlAttribute(name="MultiAirportCityInd")
        protected Boolean multiAirportCityInd;
        @XmlAttribute(name="AlternateLocationInd")
        protected Boolean alternateLocationInd;

        public Boolean isMultiAirportCityInd() {
            return this.multiAirportCityInd;
        }

        public void setMultiAirportCityInd(Boolean value) {
            this.multiAirportCityInd = value;
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
        @XmlAttribute(name="MultiAirportCityInd")
        protected Boolean multiAirportCityInd;
        @XmlAttribute(name="AlternateLocationInd")
        protected Boolean alternateLocationInd;

        public Boolean isMultiAirportCityInd() {
            return this.multiAirportCityInd;
        }

        public void setMultiAirportCityInd(Boolean value) {
            this.multiAirportCityInd = value;
        }

        public Boolean isAlternateLocationInd() {
            return this.alternateLocationInd;
        }

        public void setAlternateLocationInd(Boolean value) {
            this.alternateLocationInd = value;
        }
    }
}

