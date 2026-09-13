/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.PreferLevelType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SeatingPrefType", propOrder={"value"})
public class SeatingPrefType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="SeatDirection")
    protected String seatDirection;
    @XmlAttribute(name="SeatLocation")
    protected String seatLocation;
    @XmlAttribute(name="SeatPosition")
    protected String seatPosition;
    @XmlAttribute(name="SeatRow")
    protected String seatRow;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSeatDirection() {
        return this.seatDirection;
    }

    public void setSeatDirection(String value) {
        this.seatDirection = value;
    }

    public String getSeatLocation() {
        return this.seatLocation;
    }

    public void setSeatLocation(String value) {
        this.seatLocation = value;
    }

    public String getSeatPosition() {
        return this.seatPosition;
    }

    public void setSeatPosition(String value) {
        this.seatPosition = value;
    }

    public String getSeatRow() {
        return this.seatRow;
    }

    public void setSeatRow(String value) {
        this.seatRow = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

