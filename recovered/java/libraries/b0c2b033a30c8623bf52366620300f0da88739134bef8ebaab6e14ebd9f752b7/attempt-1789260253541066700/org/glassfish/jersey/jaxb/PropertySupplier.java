/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb;

import java.util.Map;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface PropertySupplier {
    public boolean isFor(Class<?> var1);

    public Map<String, Object> getProperties();
}

