/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.resource.spi.ResourceAdapter
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeanWrapper
 */
package org.springframework.jms.listener.endpoint;

import javax.resource.spi.ResourceAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;
import org.springframework.jms.listener.endpoint.StandardJmsActivationSpecFactory;

public class DefaultJmsActivationSpecFactory
extends StandardJmsActivationSpecFactory {
    private static final String RESOURCE_ADAPTER_SUFFIX = "ResourceAdapter";
    private static final String RESOURCE_ADAPTER_IMPL_SUFFIX = "ResourceAdapterImpl";
    private static final String ACTIVATION_SPEC_SUFFIX = "ActivationSpec";
    private static final String ACTIVATION_SPEC_IMPL_SUFFIX = "ActivationSpecImpl";
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected Class<?> determineActivationSpecClass(ResourceAdapter adapter) {
        String specClassName;
        String adapterClassName;
        block13: {
            String providerName;
            adapterClassName = adapter.getClass().getName();
            if (adapterClassName.endsWith(RESOURCE_ADAPTER_SUFFIX)) {
                providerName = adapterClassName.substring(0, adapterClassName.length() - RESOURCE_ADAPTER_SUFFIX.length());
                specClassName = providerName + ACTIVATION_SPEC_SUFFIX;
                try {
                    return adapter.getClass().getClassLoader().loadClass(specClassName);
                }
                catch (ClassNotFoundException ex) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug((Object)("No default <Provider>ActivationSpec class found: " + specClassName));
                    }
                    break block13;
                }
            }
            if (adapterClassName.endsWith(RESOURCE_ADAPTER_IMPL_SUFFIX)) {
                providerName = adapterClassName.substring(0, adapterClassName.length() - RESOURCE_ADAPTER_IMPL_SUFFIX.length());
                specClassName = providerName + ACTIVATION_SPEC_IMPL_SUFFIX;
                try {
                    return adapter.getClass().getClassLoader().loadClass(specClassName);
                }
                catch (ClassNotFoundException ex) {
                    if (!this.logger.isDebugEnabled()) break block13;
                    this.logger.debug((Object)("No default <Provider>ActivationSpecImpl class found: " + specClassName));
                }
            }
        }
        String providerPackage = adapterClassName.substring(0, adapterClassName.lastIndexOf(46) + 1);
        specClassName = providerPackage + ACTIVATION_SPEC_IMPL_SUFFIX;
        try {
            return adapter.getClass().getClassLoader().loadClass(specClassName);
        }
        catch (ClassNotFoundException ex) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("No default ActivationSpecImpl class found in provider package: " + specClassName));
            }
            specClassName = providerPackage + "inbound." + ACTIVATION_SPEC_IMPL_SUFFIX;
            try {
                return adapter.getClass().getClassLoader().loadClass(specClassName);
            }
            catch (ClassNotFoundException ex2) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("No default ActivationSpecImpl class found in inbound subpackage: " + specClassName));
                }
                throw new IllegalStateException("No ActivationSpec class defined - specify the 'activationSpecClass' property or override the 'determineActivationSpecClass' method");
            }
        }
    }

    @Override
    protected void populateActivationSpecProperties(BeanWrapper bw, JmsActivationSpecConfig config) {
        super.populateActivationSpecProperties(bw, config);
        if (config.getMaxConcurrency() > 0) {
            if (bw.isWritableProperty("maxSessions")) {
                bw.setPropertyValue("maxSessions", (Object)Integer.toString(config.getMaxConcurrency()));
            } else if (bw.isWritableProperty("maxNumberOfWorks")) {
                bw.setPropertyValue("maxNumberOfWorks", (Object)Integer.toString(config.getMaxConcurrency()));
            } else if (bw.isWritableProperty("maxConcurrency")) {
                bw.setPropertyValue("maxConcurrency", (Object)Integer.toString(config.getMaxConcurrency()));
            }
        }
        if (config.getPrefetchSize() > 0) {
            if (bw.isWritableProperty("maxMessagesPerSessions")) {
                bw.setPropertyValue("maxMessagesPerSessions", (Object)Integer.toString(config.getPrefetchSize()));
            } else if (bw.isWritableProperty("maxMessages")) {
                bw.setPropertyValue("maxMessages", (Object)Integer.toString(config.getPrefetchSize()));
            } else if (bw.isWritableProperty("maxBatchSize")) {
                bw.setPropertyValue("maxBatchSize", (Object)Integer.toString(config.getPrefetchSize()));
            }
        }
    }

    @Override
    protected void applyAcknowledgeMode(BeanWrapper bw, int ackMode) {
        if (ackMode == 0 && bw.isWritableProperty("useRAManagedTransaction")) {
            bw.setPropertyValue("useRAManagedTransaction", (Object)"true");
        } else {
            super.applyAcknowledgeMode(bw, ackMode);
        }
    }
}

