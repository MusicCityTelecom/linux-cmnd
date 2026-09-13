/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.util.concurrent.ConcurrentHashMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.sf.ehcache.management.service.impl;

import java.lang.management.ManagementFactory;
import java.util.Map;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.StandardMBean;
import net.sf.ehcache.management.service.impl.RemoteAgentEndpointImplMBean;
import net.sf.ehcache.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.management.l1bridge.AbstractRemoteAgentEndpointImpl;

public class RemoteAgentEndpointImpl
extends AbstractRemoteAgentEndpointImpl
implements RemoteAgentEndpointImplMBean {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteAgentEndpointImpl.class);
    public static final String AGENCY = "Ehcache";
    public static final String MBEAN_NAME_PREFIX = "net.sf.ehcache:type=" + IDENTIFIER + ",agency=" + "Ehcache";
    private final ThreadLocal<String> requestClusterUUID = new ThreadLocal();
    private final Map<String, ObjectName> objectNames = new ConcurrentHashMap();

    protected boolean isTsaSecured() {
        return false;
    }

    public String getRequestClusterUUID() {
        return this.requestClusterUUID.get();
    }

    public boolean isTsaBridged() {
        return this.getRequestClusterUUID() != null;
    }

    public void registerMBean(final String clientUUID) {
        ObjectName objectName;
        if (clientUUID == null) {
            throw new NullPointerException("clientUUID cannot be null");
        }
        try {
            objectName = new ObjectName(MBEAN_NAME_PREFIX + ",node=" + clientUUID);
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            platformMBeanServer.registerMBean(new StandardMBean(this, RemoteAgentEndpointImplMBean.class){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
                    try {
                        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                        try {
                            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                            RemoteAgentEndpointImpl.this.requestClusterUUID.set(clientUUID);
                            Object object = super.invoke(actionName, params, signature);
                            Thread.currentThread().setContextClassLoader(contextClassLoader);
                            return object;
                        }
                        catch (Throwable throwable) {
                            Thread.currentThread().setContextClassLoader(contextClassLoader);
                            throw throwable;
                        }
                    }
                    finally {
                        RemoteAgentEndpointImpl.this.requestClusterUUID.remove();
                    }
                }
            }, objectName);
        }
        catch (Exception e) {
            LOG.warn("Error registering RemoteAgentEndpointImpl MBean with UUID: " + clientUUID, (Throwable)e);
            objectName = null;
        }
        this.objectNames.put(clientUUID, objectName);
    }

    public void unregisterMBean(String clientUUID) {
        ObjectName objectName = this.objectNames.remove(clientUUID);
        if (objectName == null) {
            return;
        }
        try {
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            platformMBeanServer.unregisterMBean(objectName);
        }
        catch (Exception e) {
            LOG.warn("Error unregistering RemoteAgentEndpointImpl MBean : " + objectName, (Throwable)e);
        }
    }

    @Override
    public String getVersion() {
        return this.getClass().getPackage().getImplementationVersion();
    }

    @Override
    public String getAgency() {
        return AGENCY;
    }
}

