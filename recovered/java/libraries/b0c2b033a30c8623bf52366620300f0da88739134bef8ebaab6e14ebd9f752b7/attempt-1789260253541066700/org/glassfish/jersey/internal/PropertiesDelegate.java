/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.Collection;

public interface PropertiesDelegate {
    public Object getProperty(String var1);

    public Collection<String> getPropertyNames();

    public void setProperty(String var1, Object var2);

    public void removeProperty(String var1);
}

