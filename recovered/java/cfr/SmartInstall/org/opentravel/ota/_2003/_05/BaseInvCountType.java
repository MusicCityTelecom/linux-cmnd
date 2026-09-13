/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import org.opentravel.ota._2003._05.StatusApplicationControlType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BaseInvCountType", propOrder={"statusApplicationControl", "invCounts", "offSell", "uniqueID"})
public class BaseInvCountType {
    @XmlElement(name="StatusApplicationControl")
    protected StatusApplicationControlType statusApplicationControl;
    @XmlElement(name="InvCounts")
    protected InvCounts invCounts;
    @XmlElement(name="OffSell")
    protected OffSell offSell;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;

    public StatusApplicationControlType getStatusApplicationControl() {
        return this.statusApplicationControl;
    }

    public void setStatusApplicationControl(StatusApplicationControlType value) {
        this.statusApplicationControl = value;
    }

    public InvCounts getInvCounts() {
        return this.invCounts;
    }

    public void setInvCounts(InvCounts value) {
        this.invCounts = value;
    }

    public OffSell getOffSell() {
        return this.offSell;
    }

    public void setOffSell(OffSell value) {
        this.offSell = value;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OffSell {
        @XmlAttribute(name="OffSellValueType")
        protected String offSellValueType;
        @XmlAttribute(name="OffSellValue")
        protected BigDecimal offSellValue;

        public String getOffSellValueType() {
            return this.offSellValueType;
        }

        public void setOffSellValueType(String value) {
            this.offSellValueType = value;
        }

        public BigDecimal getOffSellValue() {
            return this.offSellValue;
        }

        public void setOffSellValue(BigDecimal value) {
            this.offSellValue = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"invCount"})
    public static class InvCounts {
        @XmlElement(name="InvCount", required=true)
        protected List<InvCount> invCount;

        public List<InvCount> getInvCount() {
            if (this.invCount == null) {
                this.invCount = new ArrayList<InvCount>();
            }
            return this.invCount;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"invBlockCutoff"})
        public static class InvCount {
            @XmlElement(name="InvBlockCutoff")
            protected InvBlockCutoff invBlockCutoff;
            @XmlAttribute(name="CountType")
            protected String countType;
            @XmlAttribute(name="Count")
            protected BigInteger count;
            @XmlAttribute(name="AdjustReason")
            protected String adjustReason;
            @XmlAttribute(name="ActionType")
            protected String actionType;

            public InvBlockCutoff getInvBlockCutoff() {
                return this.invBlockCutoff;
            }

            public void setInvBlockCutoff(InvBlockCutoff value) {
                this.invBlockCutoff = value;
            }

            public String getCountType() {
                return this.countType;
            }

            public void setCountType(String value) {
                this.countType = value;
            }

            public BigInteger getCount() {
                return this.count;
            }

            public void setCount(BigInteger value) {
                this.count = value;
            }

            public String getAdjustReason() {
                return this.adjustReason;
            }

            public void setAdjustReason(String value) {
                this.adjustReason = value;
            }

            public String getActionType() {
                return this.actionType;
            }

            public void setActionType(String value) {
                this.actionType = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class InvBlockCutoff {
                @XmlAttribute(name="AbsoluteCutoff")
                protected String absoluteCutoff;
                @XmlAttribute(name="OffsetDuration")
                protected Duration offsetDuration;
                @XmlAttribute(name="OffsetCalculationMode")
                @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
                protected String offsetCalculationMode;

                public String getAbsoluteCutoff() {
                    return this.absoluteCutoff;
                }

                public void setAbsoluteCutoff(String value) {
                    this.absoluteCutoff = value;
                }

                public Duration getOffsetDuration() {
                    return this.offsetDuration;
                }

                public void setOffsetDuration(Duration value) {
                    this.offsetDuration = value;
                }

                public String getOffsetCalculationMode() {
                    return this.offsetCalculationMode;
                }

                public void setOffsetCalculationMode(String value) {
                    this.offsetCalculationMode = value;
                }
            }
        }
    }
}

