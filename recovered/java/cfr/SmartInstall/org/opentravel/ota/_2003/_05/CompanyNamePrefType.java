/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AirSearchPrefsType;
import org.opentravel.ota._2003._05.AirlinePrefType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.VehicleAvailRQCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CompanyNamePrefType")
@XmlSeeAlso(value={AirlinePrefType.VendorPref.class, AirSearchPrefsType.VendorPref.class, VehicleAvailRQCoreType.VendorPrefs.VendorPref.class})
public class CompanyNamePrefType
extends CompanyNameType {
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

