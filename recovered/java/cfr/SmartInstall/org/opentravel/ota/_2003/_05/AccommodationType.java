/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AccommodationCategoryType;
import org.opentravel.ota._2003._05.AccommodationClass;
import org.opentravel.ota._2003._05.BerthAccommodationType;
import org.opentravel.ota._2003._05.CompartmentType;
import org.opentravel.ota._2003._05.SeatAccommodationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccommodationType", propOrder={"seat", "berth", "clazz", "compartment"})
@XmlSeeAlso(value={AccommodationCategoryType.Accommodation.class})
public class AccommodationType {
    @XmlElement(name="Seat")
    @XmlSchemaType(name="NMTOKEN")
    protected SeatAccommodationType seat;
    @XmlElement(name="Berth")
    @XmlSchemaType(name="NMTOKEN")
    protected BerthAccommodationType berth;
    @XmlElement(name="Class")
    protected AccommodationClass clazz;
    @XmlElement(name="Compartment")
    protected CompartmentType compartment;

    public SeatAccommodationType getSeat() {
        return this.seat;
    }

    public void setSeat(SeatAccommodationType value) {
        this.seat = value;
    }

    public BerthAccommodationType getBerth() {
        return this.berth;
    }

    public void setBerth(BerthAccommodationType value) {
        this.berth = value;
    }

    public AccommodationClass getClazz() {
        return this.clazz;
    }

    public void setClazz(AccommodationClass value) {
        this.clazz = value;
    }

    public CompartmentType getCompartment() {
        return this.compartment;
    }

    public void setCompartment(CompartmentType value) {
        this.compartment = value;
    }
}

