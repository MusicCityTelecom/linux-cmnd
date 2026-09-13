/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NamePrefType", propOrder={"uniqueID", "personName"})
public class NamePrefType {
    @XmlElement(name="UniqueID", required=true)
    protected UniqueIDType uniqueID;
    @XmlElement(name="PersonName", required=true)
    protected PersonNameType personName;
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public PersonNameType getPersonName() {
        return this.personName;
    }

    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

