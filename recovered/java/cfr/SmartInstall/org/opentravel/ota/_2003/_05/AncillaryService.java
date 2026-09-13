/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AncillaryServiceType;
import org.opentravel.ota._2003._05.PreferLevelType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="AncillaryService")
public class AncillaryService
extends AncillaryServiceType {
    @XmlAttribute(name="PreferLevel")
    protected PreferLevelType preferLevel;

    public PreferLevelType getPreferLevel() {
        return this.preferLevel;
    }

    public void setPreferLevel(PreferLevelType value) {
        this.preferLevel = value;
    }
}

