/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.util.function.Supplier;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.util.PropertiesHelper;

abstract class AbstractXmlFactory<T>
implements Supplier<T> {
    private final Configuration config;

    protected AbstractXmlFactory(Configuration config) {
        this.config = config;
    }

    boolean isXmlSecurityDisabled() {
        return PropertiesHelper.isProperty(this.config.getProperty("jersey.config.xml.security.disable"));
    }
}

