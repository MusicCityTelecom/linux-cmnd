/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.RebateType;
import org.opentravel.ota._2003._05.RoutingHopType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.WrittenConfInstType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelReservationsType", propOrder={"hotelReservation", "routingHops", "writtenConfInst", "tpaExtensions"})
public class HotelReservationsType {
    @XmlElement(name="HotelReservation", required=true)
    protected List<HotelReservation> hotelReservation;
    @XmlElement(name="RoutingHops")
    protected RoutingHopType routingHops;
    @XmlElement(name="WrittenConfInst")
    protected WrittenConfInstType writtenConfInst;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public List<HotelReservation> getHotelReservation() {
        if (this.hotelReservation == null) {
            this.hotelReservation = new ArrayList<HotelReservation>();
        }
        return this.hotelReservation;
    }

    public RoutingHopType getRoutingHops() {
        return this.routingHops;
    }

    public void setRoutingHops(RoutingHopType value) {
        this.routingHops = value;
    }

    public WrittenConfInstType getWrittenConfInst() {
        return this.writtenConfInst;
    }

    public void setWrittenConfInst(WrittenConfInstType value) {
        this.writtenConfInst = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"rebatePrograms"})
    public static class HotelReservation
    extends HotelReservationType {
        @XmlElement(name="RebatePrograms")
        protected RebatePrograms rebatePrograms;

        public RebatePrograms getRebatePrograms() {
            return this.rebatePrograms;
        }

        public void setRebatePrograms(RebatePrograms value) {
            this.rebatePrograms = value;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"rebateProgram"})
        public static class RebatePrograms {
            @XmlElement(name="RebateProgram")
            protected List<RebateType> rebateProgram;

            public List<RebateType> getRebateProgram() {
                if (this.rebateProgram == null) {
                    this.rebateProgram = new ArrayList<RebateType>();
                }
                return this.rebateProgram;
            }
        }
    }
}

