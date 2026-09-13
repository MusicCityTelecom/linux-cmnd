/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CoverageTextType;
import org.opentravel.ota._2003._05.FormattedTextTextType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CoverageDetailsType")
public class CoverageDetailsType
extends FormattedTextTextType {
    @XmlAttribute(name="CoverageTextType", required=true)
    protected CoverageTextType coverageTextType;

    public CoverageTextType getCoverageTextType() {
        return this.coverageTextType;
    }

    public void setCoverageTextType(CoverageTextType value) {
        this.coverageTextType = value;
    }
}

