/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.CertificationType;
import org.opentravel.ota._2003._05.CommissionInfoType;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.FareCodeOptionType;
import org.opentravel.ota._2003._05.PTCFareBreakdownType;
import org.opentravel.ota._2003._05.TicketingInfoType;
import org.opentravel.ota._2003._05.WarningType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FreeTextType", propOrder={"value"})
@XmlSeeAlso(value={ErrorType.class, WarningType.class, PTCFareBreakdownType.Endorsements.Endorsement.class, CertificationType.class, CommissionInfoType.class, FareCodeOptionType.FareRemark.class, TicketingInfoType.TicketAdvisory.class})
public class FreeTextType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="Language")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String language;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }
}

