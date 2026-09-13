/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.CancelRuleType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CancelInfoRSType", propOrder={"cancelRules", "uniqueID"})
public class CancelInfoRSType {
    @XmlElement(name="CancelRules")
    protected CancelRules cancelRules;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;

    public CancelRules getCancelRules() {
        return this.cancelRules;
    }

    public void setCancelRules(CancelRules value) {
        this.cancelRules = value;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"cancelRule"})
    public static class CancelRules {
        @XmlElement(name="CancelRule", required=true)
        protected List<CancelRuleType> cancelRule;

        public List<CancelRuleType> getCancelRule() {
            if (this.cancelRule == null) {
                this.cancelRule = new ArrayList<CancelRuleType>();
            }
            return this.cancelRule;
        }
    }
}

