/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.AbstractHeaderMapper
 *  org.springframework.util.StringUtils
 */
package org.springframework.jms.support;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import org.springframework.jms.support.JmsHeaderMapper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.AbstractHeaderMapper;
import org.springframework.util.StringUtils;

public class SimpleJmsHeaderMapper
extends AbstractHeaderMapper<Message>
implements JmsHeaderMapper {
    private static final Set<Class<?>> SUPPORTED_PROPERTY_TYPES = new HashSet<Class>(Arrays.asList(Boolean.class, Byte.class, Double.class, Float.class, Integer.class, Long.class, Short.class, String.class));

    public void fromHeaders(MessageHeaders headers, Message jmsMessage) {
        block16: {
            try {
                String jmsType;
                Destination jmsReplyTo;
                Object jmsCorrelationId = headers.get((Object)"jms_correlationId");
                if (jmsCorrelationId instanceof Number) {
                    jmsCorrelationId = jmsCorrelationId.toString();
                }
                if (jmsCorrelationId instanceof String) {
                    try {
                        jmsMessage.setJMSCorrelationID((String)jmsCorrelationId);
                    }
                    catch (Exception ex) {
                        this.logger.debug((Object)"Failed to set JMSCorrelationID - skipping", (Throwable)ex);
                    }
                }
                if ((jmsReplyTo = (Destination)this.getHeaderIfAvailable((Map)headers, "jms_replyTo", Destination.class)) != null) {
                    try {
                        jmsMessage.setJMSReplyTo(jmsReplyTo);
                    }
                    catch (Exception ex) {
                        this.logger.debug((Object)"Failed to set JMSReplyTo - skipping", (Throwable)ex);
                    }
                }
                if ((jmsType = (String)this.getHeaderIfAvailable((Map)headers, "jms_type", String.class)) != null) {
                    try {
                        jmsMessage.setJMSType(jmsType);
                    }
                    catch (Exception ex) {
                        this.logger.debug((Object)"Failed to set JMSType - skipping", (Throwable)ex);
                    }
                }
                for (Map.Entry entry : headers.entrySet()) {
                    Object value;
                    String headerName = (String)entry.getKey();
                    if (!StringUtils.hasText((String)headerName) || headerName.startsWith("jms_") || (value = entry.getValue()) == null || !SUPPORTED_PROPERTY_TYPES.contains(value.getClass())) continue;
                    try {
                        String propertyName = this.fromHeaderName(headerName);
                        jmsMessage.setObjectProperty(propertyName, value);
                    }
                    catch (Exception ex) {
                        if (headerName.startsWith("JMSX")) {
                            if (!this.logger.isTraceEnabled()) continue;
                            this.logger.trace((Object)("Skipping reserved header '" + headerName + "' since it cannot be set by client"));
                            continue;
                        }
                        if (!this.logger.isDebugEnabled()) continue;
                        this.logger.debug((Object)("Failed to map message header '" + headerName + "' to JMS property"), (Throwable)ex);
                    }
                }
            }
            catch (Exception ex) {
                if (!this.logger.isDebugEnabled()) break block16;
                this.logger.debug((Object)"Error occurred while mapping from MessageHeaders to JMS properties", (Throwable)ex);
            }
        }
    }

    public MessageHeaders toHeaders(Message jmsMessage) {
        HashMap<String, Object> headers;
        block31: {
            headers = new HashMap<String, Object>();
            try {
                try {
                    String correlationId = jmsMessage.getJMSCorrelationID();
                    if (correlationId != null) {
                        headers.put("jms_correlationId", correlationId);
                    }
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSCorrelationID property - skipping", (Throwable)ex);
                }
                try {
                    Destination destination = jmsMessage.getJMSDestination();
                    if (destination != null) {
                        headers.put("jms_destination", destination);
                    }
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSDestination property - skipping", (Throwable)ex);
                }
                try {
                    int deliveryMode = jmsMessage.getJMSDeliveryMode();
                    headers.put("jms_deliveryMode", deliveryMode);
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSDeliveryMode property - skipping", (Throwable)ex);
                }
                try {
                    long expiration = jmsMessage.getJMSExpiration();
                    headers.put("jms_expiration", expiration);
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSExpiration property - skipping", (Throwable)ex);
                }
                try {
                    String messageId = jmsMessage.getJMSMessageID();
                    if (messageId != null) {
                        headers.put("jms_messageId", messageId);
                    }
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSMessageID property - skipping", (Throwable)ex);
                }
                try {
                    headers.put("jms_priority", jmsMessage.getJMSPriority());
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSPriority property - skipping", (Throwable)ex);
                }
                try {
                    Destination replyTo = jmsMessage.getJMSReplyTo();
                    if (replyTo != null) {
                        headers.put("jms_replyTo", replyTo);
                    }
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSReplyTo property - skipping", (Throwable)ex);
                }
                try {
                    headers.put("jms_redelivered", jmsMessage.getJMSRedelivered());
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSRedelivered property - skipping", (Throwable)ex);
                }
                try {
                    String type = jmsMessage.getJMSType();
                    if (type != null) {
                        headers.put("jms_type", type);
                    }
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSType property - skipping", (Throwable)ex);
                }
                try {
                    headers.put("jms_timestamp", jmsMessage.getJMSTimestamp());
                }
                catch (Exception ex) {
                    this.logger.debug((Object)"Failed to read JMSTimestamp property - skipping", (Throwable)ex);
                }
                Enumeration jmsPropertyNames = jmsMessage.getPropertyNames();
                if (jmsPropertyNames != null) {
                    while (jmsPropertyNames.hasMoreElements()) {
                        String propertyName = jmsPropertyNames.nextElement().toString();
                        try {
                            String headerName = this.toHeaderName(propertyName);
                            headers.put(headerName, jmsMessage.getObjectProperty(propertyName));
                        }
                        catch (Exception ex) {
                            if (!this.logger.isDebugEnabled()) continue;
                            this.logger.debug((Object)("Error occurred while mapping JMS property '" + propertyName + "' to Message header"), (Throwable)ex);
                        }
                    }
                }
            }
            catch (JMSException ex) {
                if (!this.logger.isDebugEnabled()) break block31;
                this.logger.debug((Object)"Error occurred while mapping from JMS properties to MessageHeaders", (Throwable)ex);
            }
        }
        return new MessageHeaders(headers);
    }

    protected String fromHeaderName(String headerName) {
        if ("contentType".equals(headerName)) {
            return "content_type";
        }
        return super.fromHeaderName(headerName);
    }

    protected String toHeaderName(String propertyName) {
        if ("content_type".equals(propertyName)) {
            return "contentType";
        }
        return super.toHeaderName(propertyName);
    }
}

