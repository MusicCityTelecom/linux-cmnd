/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRegistration
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collection;
import javax.servlet.ServletRegistration;
import org.springframework.boot.actuate.web.mappings.servlet.RegistrationMappingDescription;

public class ServletRegistrationMappingDescription
extends RegistrationMappingDescription<ServletRegistration> {
    public ServletRegistrationMappingDescription(ServletRegistration servletRegistration) {
        super(servletRegistration);
    }

    public Collection<String> getMappings() {
        return ((ServletRegistration)this.getRegistration()).getMappings();
    }
}

