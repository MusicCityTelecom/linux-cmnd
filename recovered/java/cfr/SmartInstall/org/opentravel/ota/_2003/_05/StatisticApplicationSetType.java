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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StatisticApplicationSetType", propOrder={"statisticCodes", "revenueCategorySummaries", "countCategorySummaries", "reportSummaries"})
public class StatisticApplicationSetType {
    @XmlElement(name="StatisticCodes")
    protected StatisticCodes statisticCodes;
    @XmlElement(name="RevenueCategorySummaries")
    protected RevenueCategorySummaries revenueCategorySummaries;
    @XmlElement(name="CountCategorySummaries")
    protected CountCategorySummaries countCategorySummaries;
    @XmlElement(name="ReportSummaries")
    protected ReportSummaries reportSummaries;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public StatisticCodes getStatisticCodes() {
        return this.statisticCodes;
    }

    public void setStatisticCodes(StatisticCodes value) {
        this.statisticCodes = value;
    }

    public RevenueCategorySummaries getRevenueCategorySummaries() {
        return this.revenueCategorySummaries;
    }

    public void setRevenueCategorySummaries(RevenueCategorySummaries value) {
        this.revenueCategorySummaries = value;
    }

    public CountCategorySummaries getCountCategorySummaries() {
        return this.countCategorySummaries;
    }

    public void setCountCategorySummaries(CountCategorySummaries value) {
        this.countCategorySummaries = value;
    }

    public ReportSummaries getReportSummaries() {
        return this.reportSummaries;
    }

    public void setReportSummaries(ReportSummaries value) {
        this.reportSummaries = value;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"statisticCode"})
    public static class StatisticCodes {
        @XmlElement(name="StatisticCode", required=true)
        protected List<StatisticCode> statisticCode;

        public List<StatisticCode> getStatisticCode() {
            if (this.statisticCode == null) {
                this.statisticCode = new ArrayList<StatisticCode>();
            }
            return this.statisticCode;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class StatisticCode {
            @XmlAttribute(name="StatCode")
            protected String statCode;
            @XmlAttribute(name="StatCategoryCode")
            protected String statCategoryCode;

            public String getStatCode() {
                return this.statCode;
            }

            public void setStatCode(String value) {
                this.statCode = value;
            }

            public String getStatCategoryCode() {
                return this.statCategoryCode;
            }

            public void setStatCategoryCode(String value) {
                this.statCategoryCode = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"revenueCategorySummary"})
    public static class RevenueCategorySummaries {
        @XmlElement(name="RevenueCategorySummary", required=true)
        protected List<RevenueCategorySummary> revenueCategorySummary;

        public List<RevenueCategorySummary> getRevenueCategorySummary() {
            if (this.revenueCategorySummary == null) {
                this.revenueCategorySummary = new ArrayList<RevenueCategorySummary>();
            }
            return this.revenueCategorySummary;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class RevenueCategorySummary {
            @XmlAttribute(name="RevenueCategoryCode")
            protected String revenueCategoryCode;
            @XmlAttribute(name="Amount")
            protected BigDecimal amount;
            @XmlAttribute(name="CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name="DecimalPlaces")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger decimalPlaces;

            public String getRevenueCategoryCode() {
                return this.revenueCategoryCode;
            }

            public void setRevenueCategoryCode(String value) {
                this.revenueCategoryCode = value;
            }

            public BigDecimal getAmount() {
                return this.amount;
            }

            public void setAmount(BigDecimal value) {
                this.amount = value;
            }

            public String getCurrencyCode() {
                return this.currencyCode;
            }

            public void setCurrencyCode(String value) {
                this.currencyCode = value;
            }

            public BigInteger getDecimalPlaces() {
                return this.decimalPlaces;
            }

            public void setDecimalPlaces(BigInteger value) {
                this.decimalPlaces = value;
            }
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"reportSummary"})
    public static class ReportSummaries {
        @XmlElement(name="ReportSummary", required=true)
        protected List<ParagraphType> reportSummary;

        public List<ParagraphType> getReportSummary() {
            if (this.reportSummary == null) {
                this.reportSummary = new ArrayList<ParagraphType>();
            }
            return this.reportSummary;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"countCategorySummary"})
    public static class CountCategorySummaries {
        @XmlElement(name="CountCategorySummary", required=true)
        protected List<CountCategorySummary> countCategorySummary;

        public List<CountCategorySummary> getCountCategorySummary() {
            if (this.countCategorySummary == null) {
                this.countCategorySummary = new ArrayList<CountCategorySummary>();
            }
            return this.countCategorySummary;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class CountCategorySummary {
            @XmlAttribute(name="SummaryCount")
            @XmlSchemaType(name="nonNegativeInteger")
            protected BigInteger summaryCount;
            @XmlAttribute(name="CountCategoryCode")
            protected String countCategoryCode;

            public BigInteger getSummaryCount() {
                return this.summaryCount;
            }

            public void setSummaryCount(BigInteger value) {
                this.summaryCount = value;
            }

            public String getCountCategoryCode() {
                return this.countCategoryCode;
            }

            public void setCountCategoryCode(String value) {
                this.countCategoryCode = value;
            }
        }
    }
}

