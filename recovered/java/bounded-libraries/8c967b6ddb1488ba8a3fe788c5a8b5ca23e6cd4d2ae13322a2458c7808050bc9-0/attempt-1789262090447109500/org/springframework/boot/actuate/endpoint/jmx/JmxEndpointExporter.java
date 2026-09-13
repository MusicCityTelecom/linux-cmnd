/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.jmx.JmxException
 *  org.springframework.jmx.export.MBeanExportException
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.jmx;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.endpoint.jmx.EndpointMBean;
import org.springframework.boot.actuate.endpoint.jmx.EndpointObjectNameFactory;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationResponseMapper;
import org.springframework.jmx.JmxException;
import org.springframework.jmx.export.MBeanExportException;
import org.springframework.util.Assert;

public class JmxEndpointExporter
implements InitializingBean,
DisposableBean,
BeanClassLoaderAware {
    private static final Log logger = LogFactory.getLog(JmxEndpointExporter.class);
    private ClassLoader classLoader;
    private final MBeanServer mBeanServer;
    private final EndpointObjectNameFactory objectNameFactory;
    private final JmxOperationResponseMapper responseMapper;
    private final Collection<ExposableJmxEndpoint> endpoints;
    private Collection<ObjectName> registered;

    public JmxEndpointExporter(MBeanServer mBeanServer, EndpointObjectNameFactory objectNameFactory, JmxOperationResponseMapper responseMapper, Collection<? extends ExposableJmxEndpoint> endpoints) {
        Assert.notNull((Object)mBeanServer, (String)"MBeanServer must not be null");
        Assert.notNull((Object)objectNameFactory, (String)"ObjectNameFactory must not be null");
        Assert.notNull((Object)responseMapper, (String)"ResponseMapper must not be null");
        Assert.notNull(endpoints, (String)"Endpoints must not be null");
        this.mBeanServer = mBeanServer;
        this.objectNameFactory = objectNameFactory;
        this.responseMapper = responseMapper;
        this.endpoints = Collections.unmodifiableCollection(endpoints);
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void afterPropertiesSet() {
        this.registered = this.register();
    }

    public void destroy() throws Exception {
        this.unregister(this.registered);
    }

    private Collection<ObjectName> register() {
        return this.endpoints.stream().map(this::register).collect(Collectors.toList());
    }

    private ObjectName register(ExposableJmxEndpoint endpoint) {
        Assert.notNull((Object)endpoint, (String)"Endpoint must not be null");
        try {
            ObjectName name = this.objectNameFactory.getObjectName(endpoint);
            EndpointMBean mbean = new EndpointMBean(this.responseMapper, this.classLoader, endpoint);
            this.mBeanServer.registerMBean(mbean, name);
            return name;
        }
        catch (MalformedObjectNameException ex) {
            throw new IllegalStateException("Invalid ObjectName for " + this.getEndpointDescription(endpoint), ex);
        }
        catch (Exception ex) {
            throw new MBeanExportException("Failed to register MBean for " + this.getEndpointDescription(endpoint), (Throwable)ex);
        }
    }

    private void unregister(Collection<ObjectName> objectNames) {
        objectNames.forEach(this::unregister);
    }

    private void unregister(ObjectName objectName) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug((Object)("Unregister endpoint with ObjectName '" + objectName + "' from the JMX domain"));
            }
            this.mBeanServer.unregisterMBean(objectName);
        }
        catch (InstanceNotFoundException instanceNotFoundException) {
        }
        catch (MBeanRegistrationException ex) {
            throw new JmxException("Failed to unregister MBean with ObjectName '" + objectName + "'", (Throwable)ex);
        }
    }

    private String getEndpointDescription(ExposableJmxEndpoint endpoint) {
        return "endpoint '" + endpoint.getEndpointId() + "'";
    }
}

