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
@XmlType(name="RoomLocationPrefType", propOrder={"value"})
public class RoomLocationPrefType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="RoomLocationType")
    protected String roomLocationType;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRoomLocationType() {
        return this.roomLocationType;
    }

    public void setRoomLocationType(String value) {
        this.roomLocationType = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

