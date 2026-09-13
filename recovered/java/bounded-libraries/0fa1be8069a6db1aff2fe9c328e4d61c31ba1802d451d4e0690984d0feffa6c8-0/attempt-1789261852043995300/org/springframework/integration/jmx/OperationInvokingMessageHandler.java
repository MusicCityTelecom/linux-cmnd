/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.IntegrationPatternType
 *  org.springframework.integration.handler.AbstractReplyProducingMessageHandler
 *  org.springframework.integration.util.ClassUtils
 *  org.springframework.jmx.support.ObjectNameManager
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandlingException
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.jmx;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.JMException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.util.ClassUtils;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class OperationInvokingMessageHandler
extends AbstractReplyProducingMessageHandler {
    private final MBeanServerConnection server;
    private ObjectName defaultObjectName;
    private String operationName;
    private boolean expectReply = true;

    public OperationInvokingMessageHandler(MBeanServerConnection server) {
        Assert.notNull((Object)server, (String)"MBeanServer is required.");
        this.server = server;
    }

    public void setObjectName(String objectName) {
        try {
            if (objectName != null) {
                this.defaultObjectName = ObjectNameManager.getInstance((String)objectName);
            }
        }
        catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public void setExpectReply(boolean expectReply) {
        this.expectReply = expectReply;
    }

    public String getComponentType() {
        return this.expectReply ? "jmx:operation-invoking-outbound-gateway" : "jmx:operation-invoking-channel-adapter";
    }

    public IntegrationPatternType getIntegrationPatternType() {
        return this.expectReply ? super.getIntegrationPatternType() : IntegrationPatternType.outbound_channel_adapter;
    }

    protected Object handleRequestMessage(Message<?> requestMessage) {
        ObjectName objectName = this.resolveObjectName(requestMessage);
        String operation = this.resolveOperationName(requestMessage);
        Map<String, Object> paramsFromMessage = this.resolveParameters(requestMessage);
        try {
            Object result = this.invokeOperation(requestMessage, objectName, operation, paramsFromMessage);
            if (!this.expectReply && result != null) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn((CharSequence)("This component doesn't expect a reply. The MBean operation '" + operation + "' result '" + result + "' for '" + objectName + "' is ignored."));
                }
                return null;
            }
            return result;
        }
        catch (JMException ex) {
            throw new MessageHandlingException(requestMessage, "failed to invoke JMX operation '" + operation + "' on MBean [" + objectName + "] with " + paramsFromMessage.size() + " parameters [" + paramsFromMessage + "] in the [" + (Object)((Object)this) + ']', (Throwable)ex);
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private Object invokeOperation(Message<?> requestMessage, ObjectName objectName, String operation, Map<String, Object> paramsFromMessage) throws JMException, IOException {
        MBeanInfo mbeanInfo = this.server.getMBeanInfo(objectName);
        MBeanOperationInfo[] opInfoArray = mbeanInfo.getOperations();
        boolean hasNoArgOption = false;
        for (MBeanOperationInfo opInfo : opInfoArray) {
            String[] signature;
            Object[] values;
            int index;
            if (!operation.equals(opInfo.getName())) continue;
            MBeanParameterInfo[] paramInfoArray = opInfo.getSignature();
            if (paramInfoArray.length == 0) {
                hasNoArgOption = true;
            }
            if (paramInfoArray.length != paramsFromMessage.size() || (index = this.populateValuesAndSignature(paramsFromMessage, paramInfoArray, values = new Object[paramInfoArray.length], signature = new String[paramInfoArray.length])) != paramInfoArray.length) continue;
            return this.server.invoke(objectName, operation, values, signature);
        }
        if (hasNoArgOption) {
            return this.server.invoke(objectName, operation, null, null);
        }
        throw new MessagingException(requestMessage, "failed to find JMX operation '" + operation + "' on MBean [" + objectName + "] of type [" + mbeanInfo.getClassName() + "] with " + paramsFromMessage.size() + " parameters: " + paramsFromMessage);
    }

    private int populateValuesAndSignature(Map<String, Object> paramsFromMessage, MBeanParameterInfo[] paramInfoArray, Object[] values, String[] signature) {
        int index = 0;
        for (MBeanParameterInfo paramInfo : paramInfoArray) {
            Object value = paramsFromMessage.get(paramInfo.getName());
            if (value == null) {
                value = paramsFromMessage.get("p" + (index + 1));
            }
            if (value == null || !this.valueTypeMatchesParameterType(value, paramInfo)) continue;
            values[index] = value;
            signature[index] = paramInfo.getType();
            ++index;
        }
        return index;
    }

    private boolean valueTypeMatchesParameterType(Object value, MBeanParameterInfo paramInfo) {
        Class<?> valueClass = value.getClass();
        if (valueClass.getName().equals(paramInfo.getType())) {
            return true;
        }
        Class primitiveType = ClassUtils.resolvePrimitiveType(valueClass);
        return primitiveType != null && primitiveType.getName().equals(paramInfo.getType());
    }

    private ObjectName resolveObjectName(Message<?> message) {
        ObjectName objectName;
        Object objectNameHeader = message.getHeaders().get((Object)"jmx_objectName");
        if (objectNameHeader != null) {
            try {
                objectName = ObjectNameManager.getInstance((Object)objectNameHeader);
            }
            catch (MalformedObjectNameException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            objectName = this.defaultObjectName;
        }
        Assert.notNull((Object)objectName, (String)"Failed to resolve ObjectName: either from headers or 'defaultObjectName'.");
        return objectName;
    }

    private String resolveOperationName(Message<?> message) {
        String operation = (String)message.getHeaders().get((Object)"jmx_operationName", String.class);
        if (operation == null) {
            operation = this.operationName;
        }
        Assert.notNull((Object)operation, (String)"Failed to resolve operation name.");
        return operation;
    }

    private Map<String, Object> resolveParameters(Message<?> message) {
        Object payload = message.getPayload();
        Map<String, Object> map = payload instanceof Map ? (Map<String, Object>)payload : (payload instanceof List ? this.createParameterMapFromList((List)payload) : (payload.getClass().isArray() ? this.createParameterMapFromList(Arrays.asList(ObjectUtils.toObjectArray((Object)payload))) : this.createParameterMapFromList(Collections.singletonList(payload))));
        return map;
    }

    private Map<String, Object> createParameterMapFromList(List<?> parameters) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < parameters.size(); ++i) {
            map.put("p" + (i + 1), parameters.get(i));
        }
        return map;
    }
}

