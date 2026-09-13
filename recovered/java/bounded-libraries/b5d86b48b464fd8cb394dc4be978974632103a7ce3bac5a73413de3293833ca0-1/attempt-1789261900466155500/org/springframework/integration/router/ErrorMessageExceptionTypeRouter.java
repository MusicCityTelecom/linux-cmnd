/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.router;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class ErrorMessageExceptionTypeRouter
extends AbstractMappingMessageRouter {
    private volatile Map<String, Class<?>> classNameMappings = new LinkedHashMap();
    private volatile boolean initialized;

    @Override
    @ManagedAttribute
    public void setChannelMappings(Map<String, String> channelMappings) {
        super.setChannelMappings(channelMappings);
        if (this.initialized) {
            this.populateClassNameMapping(channelMappings.keySet());
        }
    }

    private void populateClassNameMapping(Set<String> classNames) {
        LinkedHashMap newClassNameMappings = new LinkedHashMap();
        for (String className : classNames) {
            newClassNameMappings.put(className, this.resolveClassFromName(className));
        }
        this.classNameMappings = newClassNameMappings;
    }

    private Class<?> resolveClassFromName(String className) {
        try {
            Assert.state((this.getApplicationContext() != null ? 1 : 0) != 0, (String)"An ApplicationContext is required");
            return ClassUtils.forName((String)className, (ClassLoader)this.getApplicationContext().getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load class for channel mapping.", e);
        }
    }

    @Override
    @ManagedOperation
    public void setChannelMapping(String key, String channelName) {
        super.setChannelMapping(key, channelName);
        if (this.initialized) {
            LinkedHashMap newClassNameMappings = new LinkedHashMap(this.classNameMappings);
            newClassNameMappings.put(key, this.resolveClassFromName(key));
            this.classNameMappings = newClassNameMappings;
        }
    }

    @Override
    @ManagedOperation
    public void removeChannelMapping(String key) {
        super.removeChannelMapping(key);
        LinkedHashMap newClassNameMappings = new LinkedHashMap(this.classNameMappings);
        newClassNameMappings.remove(key);
        this.classNameMappings = newClassNameMappings;
    }

    @Override
    @ManagedOperation
    public void replaceChannelMappings(Properties channelMappings) {
        super.replaceChannelMappings(channelMappings);
        this.populateClassNameMapping(this.getChannelMappings().keySet());
    }

    @Override
    protected void onInit() {
        super.onInit();
        this.populateClassNameMapping(this.getChannelMappings().keySet());
        this.initialized = true;
    }

    @Override
    protected List<Object> getChannelKeys(Message<?> message) {
        String mostSpecificCause = null;
        Object payload = message.getPayload();
        if (payload instanceof Throwable) {
            for (Throwable cause = (Throwable)payload; cause != null; cause = cause.getCause()) {
                for (Map.Entry<String, Class<?>> entry : this.classNameMappings.entrySet()) {
                    String channelKey = entry.getKey();
                    Class<?> exceptionClass = entry.getValue();
                    if (!exceptionClass.isInstance(cause)) continue;
                    mostSpecificCause = channelKey;
                }
            }
        }
        return Collections.singletonList(mostSpecificCause);
    }
}

