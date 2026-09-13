/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.htng._2011b.HTNGComponentRoomType;
import org.htng._2011b.HTNGProfileMessageStatusType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"pos", "uniqueID", "propertyInfo", "room", "tpaExtensions"})
@XmlRootElement(name="HTNG_ProfileMessageRQ")
public class HTNGProfileMessageRQ {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name="PropertyInfo", required=true)
    protected PropertyInfo propertyInfo;
    @XmlElement(name="Room")
    protected HTNGComponentRoomType room;
    @XmlElement(name="TPA_Extensions", namespace="http://www.opentravel.org/OTA/2003/05")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="ProfileMessageStatus")
    protected HTNGProfileMessageStatusType profileMessageStatus;
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

    public POSType getPOS() {
        return this.pos;
    }

    public void setPOS(POSType value) {
        this.pos = value;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public PropertyInfo getPropertyInfo() {
        return this.propertyInfo;
    }

    public void setPropertyInfo(PropertyInfo value) {
        this.propertyInfo = value;
    }

    public HTNGComponentRoomType getRoom() {
        return this.room;
    }

    public void setRoom(HTNGComponentRoomType value) {
        this.room = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public HTNGProfileMessageStatusType getProfileMessageStatus() {
        return this.profileMessageStatus;
    }

    public void setProfileMessageStatus(HTNGProfileMessageStatusType value) {
        this.profileMessageStatus = value;
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
    @XmlType(name="")
    public static class PropertyInfo {
        @XmlAttribute(name="ChainCode")
        protected String chainCode;
        @XmlAttribute(name="BrandCode")
        protected String brandCode;
        @XmlAttribute(name="HotelCode")
        protected String hotelCode;
        @XmlAttribute(name="HotelCityCode")
        protected String hotelCityCode;
        @XmlAttribute(name="HotelName")
        protected String hotelName;
        @XmlAttribute(name="HotelCodeContext")
        protected String hotelCodeContext;
        @XmlAttribute(name="ChainName")
        protected String chainName;
        @XmlAttribute(name="BrandName")
        protected String brandName;
        @XmlAttribute(name="AreaID")
        protected String areaID;

        public String getChainCode() {
            return this.chainCode;
        }

        public void setChainCode(String value) {
            this.chainCode = value;
        }

        public String getBrandCode() {
            return this.brandCode;
        }

        public void setBrandCode(String value) {
            this.brandCode = value;
        }

        public String getHotelCode() {
            return this.hotelCode;
        }

        public void setHotelCode(String value) {
            this.hotelCode = value;
        }

        public String getHotelCityCode() {
            return this.hotelCityCode;
        }

        public void setHotelCityCode(String value) {
            this.hotelCityCode = value;
        }

        public String getHotelName() {
            return this.hotelName;
        }

        public void setHotelName(String value) {
            this.hotelName = value;
        }

        public String getHotelCodeContext() {
            return this.hotelCodeContext;
        }

        public void setHotelCodeContext(String value) {
            this.hotelCodeContext = value;
        }

        public String getChainName() {
            return this.chainName;
        }

        public void setChainName(String value) {
            this.chainName = value;
        }

        public String getBrandName() {
            return this.brandName;
        }

        public void setBrandName(String value) {
            this.brandName = value;
        }

        public String getAreaID() {
            return this.areaID;
        }

        public void setAreaID(String value) {
            this.areaID = value;
        }
    }
}

