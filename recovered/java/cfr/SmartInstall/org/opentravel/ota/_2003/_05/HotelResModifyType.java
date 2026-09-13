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
import org.opentravel.ota._2003._05.RoutingHopType;
import org.opentravel.ota._2003._05.VerificationType;
import org.opentravel.ota._2003._05.WrittenConfInstType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelResModifyType", propOrder={"hotelResModify", "routingHops", "writtenConfInst"})
public class HotelResModifyType {
    @XmlElement(name="HotelResModify", required=true)
    protected List<HotelResModify> hotelResModify;
    @XmlElement(name="RoutingHops")
    protected RoutingHopType routingHops;
    @XmlElement(name="WrittenConfInst")
    protected WrittenConfInstType writtenConfInst;

    public List<HotelResModify> getHotelResModify() {
        if (this.hotelResModify == null) {
            this.hotelResModify = new ArrayList<HotelResModify>();
        }
        return this.hotelResModify;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"verification"})
    public static class HotelResModify
    extends HotelReservationType {
        @XmlElement(name="Verification")
        protected List<Verification> verification;

        public List<Verification> getVerification() {
            if (this.verification == null) {
                this.verification = new ArrayList<Verification>();
            }
            return this.verification;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"currentStateReservation"})
        public static class Verification
        extends VerificationType {
            @XmlElement(name="CurrentStateReservation")
            protected HotelReservationType currentStateReservation;

            public HotelReservationType getCurrentStateReservation() {
                return this.currentStateReservation;
            }

            public void setCurrentStateReservation(HotelReservationType value) {
                this.currentStateReservation = value;
            }
        }
    }
}

