/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.htng._2011b.HTNGKeyValueItemsType;
import org.htng._2011b.HTNGQueryResultListType;
import org.htng._2011b.HTNGResultFormatType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"requestorID", "queries"})
@XmlRootElement(name="HTNG_StatisticsRQ")
public class HTNGStatisticsRQ {
    @XmlElement(name="RequestorID", required=true)
    protected UniqueIDType requestorID;
    @XmlElement(name="Queries", required=true)
    protected Queries queries;
    @XmlAttribute(name="EchoToken")
    protected String echoToken;
    @XmlAttribute(name="TimeStamp")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name="Target")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name="TargetName")
    protected String targetName;
    @XmlAttribute(name="Version", required=true)
    protected BigDecimal version;
    @XmlAttribute(name="TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name="SequenceNmbr")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name="TransactionStatusCode")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name="RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name="CorrelationID")
    protected String correlationID;
    @XmlAttribute(name="AltLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String altLangID;
    @XmlAttribute(name="PrimaryLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String primaryLangID;

    public UniqueIDType getRequestorID() {
        return this.requestorID;
    }

    public void setRequestorID(UniqueIDType value) {
        this.requestorID = value;
    }

    public Queries getQueries() {
        return this.queries;
    }

    public void setQueries(Queries value) {
        this.queries = value;
    }

    public String getEchoToken() {
        return this.echoToken;
    }

    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    public XMLGregorianCalendar getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String value) {
        this.target = value;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetName(String value) {
        this.targetName = value;
    }

    public BigDecimal getVersion() {
        return this.version;
    }

    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    public String getTransactionIdentifier() {
        return this.transactionIdentifier;
    }

    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    public BigInteger getSequenceNmbr() {
        return this.sequenceNmbr;
    }

    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    public String getTransactionStatusCode() {
        return this.transactionStatusCode;
    }

    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    public Boolean isRetransmissionIndicator() {
        return this.retransmissionIndicator;
    }

    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    public String getCorrelationID() {
        return this.correlationID;
    }

    public void setCorrelationID(String value) {
        this.correlationID = value;
    }

    public String getAltLangID() {
        return this.altLangID;
    }

    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    public String getPrimaryLangID() {
        return this.primaryLangID;
    }

    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"query"})
    public static class Queries {
        @XmlElement(name="Query", required=true)
        protected List<Query> query;

        public List<Query> getQuery() {
            if (this.query == null) {
                this.query = new ArrayList<Query>();
            }
            return this.query;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"requestParameters", "responseParameters"})
        public static class Query {
            @XmlElement(name="RequestParameters", required=true)
            protected HTNGKeyValueItemsType requestParameters;
            @XmlElement(name="ResponseParameters", required=true)
            protected ResponseParameters responseParameters;
            @XmlAttribute(name="StoredQueryName")
            protected String storedQueryName;
            @XmlAttribute(name="QueryTrackingID", required=true)
            protected String queryTrackingID;

            public HTNGKeyValueItemsType getRequestParameters() {
                return this.requestParameters;
            }

            public void setRequestParameters(HTNGKeyValueItemsType value) {
                this.requestParameters = value;
            }

            public ResponseParameters getResponseParameters() {
                return this.responseParameters;
            }

            public void setResponseParameters(ResponseParameters value) {
                this.responseParameters = value;
            }

            public String getStoredQueryName() {
                return this.storedQueryName;
            }

            public void setStoredQueryName(String value) {
                this.storedQueryName = value;
            }

            public String getQueryTrackingID() {
                return this.queryTrackingID;
            }

            public void setQueryTrackingID(String value) {
                this.queryTrackingID = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class ResponseParameters {
                @XmlAttribute(name="ResultFormat")
                protected HTNGResultFormatType resultFormat;
                @XmlAttribute(name="Size")
                protected BigInteger size;
                @XmlAttribute(name="UnitOfMeasure")
                protected HTNGQueryResultListType unitOfMeasure;

                public HTNGResultFormatType getResultFormat() {
                    if (this.resultFormat == null) {
                        return HTNGResultFormatType.XML;
                    }
                    return this.resultFormat;
                }

                public void setResultFormat(HTNGResultFormatType value) {
                    this.resultFormat = value;
                }

                public BigInteger getSize() {
                    if (this.size == null) {
                        return new BigInteger("1");
                    }
                    return this.size;
                }

                public void setSize(BigInteger value) {
                    this.size = value;
                }

                public HTNGQueryResultListType getUnitOfMeasure() {
                    if (this.unitOfMeasure == null) {
                        return HTNGQueryResultListType.ALL_ASCENDING;
                    }
                    return this.unitOfMeasure;
                }

                public void setUnitOfMeasure(HTNGQueryResultListType value) {
                    this.unitOfMeasure = value;
                }
            }
        }
    }
}

