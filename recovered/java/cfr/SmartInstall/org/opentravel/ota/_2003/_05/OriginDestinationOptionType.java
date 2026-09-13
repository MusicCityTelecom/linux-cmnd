/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AirItineraryType;
import org.opentravel.ota._2003._05.BookFlightSegmentType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OriginDestinationOptionType", propOrder={"flightSegment"})
@XmlSeeAlso(value={AirItineraryType.OriginDestinationOptions.OriginDestinationOption.class})
public class OriginDestinationOptionType {
    @XmlElement(name="FlightSegment", required=true)
    protected List<FlightSegment> flightSegment;

    public List<FlightSegment> getFlightSegment() {
        if (this.flightSegment == null) {
            this.flightSegment = new ArrayList<FlightSegment>();
        }
        return this.flightSegment;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"tpaExtensions"})
    public static class FlightSegment
    extends BookFlightSegmentType {
        @XmlElement(name="TPA_Extensions")
        protected TPAExtensionsType tpaExtensions;

        public TPAExtensionsType getTPAExtensions() {
            return this.tpaExtensions;
        }

        public void setTPAExtensions(TPAExtensionsType value) {
            this.tpaExtensions = value;
        }
    }
}

