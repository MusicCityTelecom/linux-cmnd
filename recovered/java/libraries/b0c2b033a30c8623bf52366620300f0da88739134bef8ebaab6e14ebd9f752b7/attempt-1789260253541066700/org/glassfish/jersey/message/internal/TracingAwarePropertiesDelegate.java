/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.Collection;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.message.internal.TracingLogger;

public final class TracingAwarePropertiesDelegate
implements PropertiesDelegate {
    private final PropertiesDelegate propertiesDelegate;
    private TracingLogger tracingLogger;

    public TracingAwarePropertiesDelegate(PropertiesDelegate propertiesDelegate) {
        this.propertiesDelegate = propertiesDelegate;
    }

    @Override
    public void removeProperty(String name) {
        if (TracingLogger.PROPERTY_NAME.equals(name)) {
            this.tracingLogger = null;
        }
        this.propertiesDelegate.removeProperty(name);
    }

    @Override
    public void setProperty(String name, Object object) {
        if (TracingLogger.PROPERTY_NAME.equals(name)) {
            this.tracingLogger = (TracingLogger)object;
        }
        this.propertiesDelegate.setProperty(name, object);
    }

    @Override
    public Object getProperty(String name) {
        if (this.tracingLogger != null && TracingLogger.PROPERTY_NAME.equals(name)) {
            return this.tracingLogger;
        }
        return this.propertiesDelegate.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.propertiesDelegate.getPropertyNames();
    }
}

