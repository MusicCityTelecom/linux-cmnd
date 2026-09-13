/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterRegistration
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collection;
import javax.servlet.FilterRegistration;
import org.springframework.boot.actuate.web.mappings.servlet.RegistrationMappingDescription;

public class FilterRegistrationMappingDescription
extends RegistrationMappingDescription<FilterRegistration> {
    public FilterRegistrationMappingDescription(FilterRegistration filterRegistration) {
        super(filterRegistration);
    }

    public Collection<String> getServletNameMappings() {
        return ((FilterRegistration)this.getRegistration()).getServletNameMappings();
    }

    public Collection<String> getUrlPatternMappings() {
        return ((FilterRegistration)this.getRegistration()).getUrlPatternMappings();
    }
}

