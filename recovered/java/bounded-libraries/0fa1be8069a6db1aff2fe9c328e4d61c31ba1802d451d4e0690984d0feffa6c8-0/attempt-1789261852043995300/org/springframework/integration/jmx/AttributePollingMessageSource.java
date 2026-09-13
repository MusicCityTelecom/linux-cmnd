/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.endpoint.AbstractMessageSource
 *  org.springframework.jmx.support.ObjectNameManager
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class AttributePollingMessageSource
extends AbstractMessageSource<Object> {
    private volatile ObjectName objectName;
    private volatile String attributeName;
    private volatile MBeanServerConnection server;

    public void setServer(MBeanServerConnection server) {
        this.server = server;
    }

    public void setObjectName(String objectName) {
        try {
            this.objectName = ObjectNameManager.getInstance((String)objectName);
        }
        catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getComponentType() {
        return "jmx:attribute-polling-channel-adapter";
    }

    protected Object doReceive() {
        Assert.notNull((Object)this.server, (String)"MBeanServer is required");
        Assert.notNull((Object)this.objectName, (String)"object name is required");
        Assert.notNull((Object)this.attributeName, (String)"attribute name is required");
        try {
            return this.server.getAttribute(this.objectName, this.attributeName);
        }
        catch (Exception e) {
            throw new MessagingException("failed to retrieve JMX attribute '" + this.attributeName + "' on MBean [" + this.objectName + "]", (Throwable)e);
        }
    }
}

