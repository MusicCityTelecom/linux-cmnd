/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.integration.endpoint.AbstractMessageSource
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.util.HashMap;
import java.util.Set;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.jmx.MBeanObjectConverter;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class MBeanTreePollingMessageSource
extends AbstractMessageSource<Object> {
    private volatile MBeanServerConnection server;
    private volatile ObjectName queryName = null;
    private volatile QueryExp queryExpression = ObjectName.WILDCARD;
    private final MBeanObjectConverter converter;

    public MBeanTreePollingMessageSource(MBeanObjectConverter converter) {
        this.converter = converter;
    }

    public String getComponentType() {
        return "jmx:tree-polling-channel-adapter";
    }

    protected Object doReceive() {
        Assert.notNull((Object)this.server, (String)"MBeanServer is required");
        try {
            HashMap<String, Object> beans = new HashMap<String, Object>();
            Set<ObjectInstance> results = this.server.queryMBeans(this.queryName, this.queryExpression);
            for (ObjectInstance instance : results) {
                Object result = this.converter.convert(this.server, instance);
                beans.put(instance.getObjectName().getCanonicalName(), result);
            }
            return beans;
        }
        catch (Exception e) {
            throw new MessagingException("Failed to retrieve tree snapshot", (Throwable)e);
        }
    }

    public void setServer(MBeanServerConnection server) {
        this.server = server;
    }

    public void setQueryName(String queryName) {
        Assert.notNull((Object)queryName, (String)"'queryName' must not be null");
        try {
            this.setQueryNameReference(ObjectName.getInstance(queryName));
        }
        catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setQueryNameReference(ObjectName queryName) {
        this.queryName = queryName;
    }

    public void setQueryExpression(String queryExpression) {
        Assert.notNull((Object)queryExpression, (String)"'queryExpression' must not be null");
        try {
            this.setQueryExpressionReference(ObjectName.getInstance(queryExpression));
        }
        catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setQueryExpressionReference(QueryExp queryExpression) {
        this.queryExpression = queryExpression;
    }
}

