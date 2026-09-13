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
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.OperationSchedulesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TransportationType", propOrder={"transportation"})
public class TransportationType {
    @XmlElement(name="Transportation", required=true)
    protected List<Transportation> transportation;

    public List<Transportation> getTransportation() {
        if (this.transportation == null) {
            this.transportation = new ArrayList<Transportation>();
        }
        return this.transportation;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"multimediaDescriptions", "operationSchedules", "descriptiveText"})
    public static class Transportation {
        @XmlElement(name="MultimediaDescriptions")
        protected MultimediaDescriptionsType multimediaDescriptions;
        @XmlElement(name="OperationSchedules")
        protected OperationSchedulesType operationSchedules;
        @XmlElement(name="DescriptiveText")
        protected String descriptiveText;
        @XmlAttribute(name="NotificationRequired")
        protected String notificationRequired;
        @XmlAttribute(name="TransportationCode")
        protected String transportationCode;
        @XmlAttribute(name="ChargeUnit")
        protected String chargeUnit;
        @XmlAttribute(name="Included")
        protected Boolean included;
        @XmlAttribute(name="Description")
        protected String description;
        @XmlAttribute(name="TypicalTravelTime")
        protected String typicalTravelTime;
        @XmlAttribute(name="ExistsCode")
        protected String existsCode;
        @XmlAttribute(name="CodeDetail")
        protected String codeDetail;
        @XmlAttribute(name="Removal")
        protected Boolean removal;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;
        @XmlAttribute(name="ID")
        protected String id;

        public MultimediaDescriptionsType getMultimediaDescriptions() {
            return this.multimediaDescriptions;
        }

        public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
            this.multimediaDescriptions = value;
        }

        public OperationSchedulesType getOperationSchedules() {
            return this.operationSchedules;
        }

        public void setOperationSchedules(OperationSchedulesType value) {
            this.operationSchedules = value;
        }

        public String getDescriptiveText() {
            return this.descriptiveText;
        }

        public void setDescriptiveText(String value) {
            this.descriptiveText = value;
        }

        public String getNotificationRequired() {
            return this.notificationRequired;
        }

        public void setNotificationRequired(String value) {
            this.notificationRequired = value;
        }

        public String getTransportationCode() {
            return this.transportationCode;
        }

        public void setTransportationCode(String value) {
            this.transportationCode = value;
        }

        public String getChargeUnit() {
            return this.chargeUnit;
        }

        public void setChargeUnit(String value) {
            this.chargeUnit = value;
        }

        public Boolean isIncluded() {
            return this.included;
        }

        public void setIncluded(Boolean value) {
            this.included = value;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String value) {
            this.description = value;
        }

        public String getTypicalTravelTime() {
            return this.typicalTravelTime;
        }

        public void setTypicalTravelTime(String value) {
            this.typicalTravelTime = value;
        }

        public String getExistsCode() {
            return this.existsCode;
        }

        public void setExistsCode(String value) {
            this.existsCode = value;
        }

        public String getCodeDetail() {
            return this.codeDetail;
        }

        public void setCodeDetail(String value) {
            this.codeDetail = value;
        }

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
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

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

