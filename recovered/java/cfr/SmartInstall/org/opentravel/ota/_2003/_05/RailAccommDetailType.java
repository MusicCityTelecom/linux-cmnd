/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AccommodationClass;
import org.opentravel.ota._2003._05.AccommodationServiceType;
import org.opentravel.ota._2003._05.BerthDetailType;
import org.opentravel.ota._2003._05.CompartmentPositionType;
import org.opentravel.ota._2003._05.CompartmentType;
import org.opentravel.ota._2003._05.DeckType;
import org.opentravel.ota._2003._05.SeatDetailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailAccommDetailType", propOrder={"seat", "berth", "clazz", "compartment", "car"})
@XmlSeeAlso(value={AccommodationServiceType.AccommodationDetail.class})
public class RailAccommDetailType {
    @XmlElement(name="Seat")
    protected SeatDetailType seat;
    @XmlElement(name="Berth")
    protected BerthDetailType berth;
    @XmlElement(name="Class")
    protected AccommodationClass clazz;
    @XmlElement(name="Compartment")
    protected Compartment compartment;
    @XmlElement(name="Car")
    protected Car car;
    @XmlAttribute(name="Deck")
    protected DeckType deck;

    public SeatDetailType getSeat() {
        return this.seat;
    }

    public void setSeat(SeatDetailType value) {
        this.seat = value;
    }

    public BerthDetailType getBerth() {
        return this.berth;
    }

    public void setBerth(BerthDetailType value) {
        this.berth = value;
    }

    public AccommodationClass getClazz() {
        return this.clazz;
    }

    public void setClazz(AccommodationClass value) {
        this.clazz = value;
    }

    public Compartment getCompartment() {
        return this.compartment;
    }

    public void setCompartment(Compartment value) {
        this.compartment = value;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car value) {
        this.car = value;
    }

    public DeckType getDeck() {
        return this.deck;
    }

    public void setDeck(DeckType value) {
        this.deck = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Compartment
    extends CompartmentType {
        @XmlAttribute(name="Number")
        protected String number;
        @XmlAttribute(name="Position")
        protected CompartmentPositionType position;

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String value) {
            this.number = value;
        }

        public CompartmentPositionType getPosition() {
            return this.position;
        }

        public void setPosition(CompartmentPositionType value) {
            this.position = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Car {
        @XmlAttribute(name="Number")
        protected String number;

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String value) {
            this.number = value;
        }
    }
}

