/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CancelPenaltyType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CancelPenaltiesType", propOrder={"cancelPenalty"})
public class CancelPenaltiesType {
    @XmlElement(name="CancelPenalty")
    protected List<CancelPenaltyType> cancelPenalty;
    @XmlAttribute(name="CancelPolicyIndicator")
    protected Boolean cancelPolicyIndicator;

    public List<CancelPenaltyType> getCancelPenalty() {
        if (this.cancelPenalty == null) {
            this.cancelPenalty = new ArrayList<CancelPenaltyType>();
        }
        return this.cancelPenalty;
    }

    public Boolean isCancelPolicyIndicator() {
        return this.cancelPolicyIndicator;
    }

    public void setCancelPolicyIndicator(Boolean value) {
        this.cancelPolicyIndicator = value;
    }
}

