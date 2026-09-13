/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.FreeTextType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_EventNotificationHeaderType", propOrder={"subscriptionProducerMessages"})
public class HTNGEventNotificationHeaderType {
    @XmlElement(name="SubscriptionProducerMessages")
    protected SubscriptionProducerMessages subscriptionProducerMessages;
    @XmlAttribute(name="SubscriptionEventType", required=true)
    protected String subscriptionEventType;
    @XmlAttribute(name="SubscriptionEventTypeID", required=true)
    protected String subscriptionEventTypeID;
    @XmlAttribute(name="ProducerReason", required=true)
    protected String producerReason;
    @XmlAttribute(name="ConsumerSubscriptionID", required=true)
    protected String consumerSubscriptionID;
    @XmlAttribute(name="TerminationDateTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar terminationDateTime;

    public SubscriptionProducerMessages getSubscriptionProducerMessages() {
        return this.subscriptionProducerMessages;
    }

    public void setSubscriptionProducerMessages(SubscriptionProducerMessages value) {
        this.subscriptionProducerMessages = value;
    }

    public String getSubscriptionEventType() {
        return this.subscriptionEventType;
    }

    public void setSubscriptionEventType(String value) {
        this.subscriptionEventType = value;
    }

    public String getSubscriptionEventTypeID() {
        return this.subscriptionEventTypeID;
    }

    public void setSubscriptionEventTypeID(String value) {
        this.subscriptionEventTypeID = value;
    }

    public String getProducerReason() {
        return this.producerReason;
    }

    public void setProducerReason(String value) {
        this.producerReason = value;
    }

    public String getConsumerSubscriptionID() {
        return this.consumerSubscriptionID;
    }

    public void setConsumerSubscriptionID(String value) {
        this.consumerSubscriptionID = value;
    }

    public XMLGregorianCalendar getTerminationDateTime() {
        return this.terminationDateTime;
    }

    public void setTerminationDateTime(XMLGregorianCalendar value) {
        this.terminationDateTime = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"subscriptionProducerMessage"})
    public static class SubscriptionProducerMessages {
        @XmlElement(name="SubscriptionProducerMessage", required=true)
        protected List<FreeTextType> subscriptionProducerMessage;

        public List<FreeTextType> getSubscriptionProducerMessage() {
            if (this.subscriptionProducerMessage == null) {
                this.subscriptionProducerMessage = new ArrayList<FreeTextType>();
            }
            return this.subscriptionProducerMessage;
        }
    }
}

