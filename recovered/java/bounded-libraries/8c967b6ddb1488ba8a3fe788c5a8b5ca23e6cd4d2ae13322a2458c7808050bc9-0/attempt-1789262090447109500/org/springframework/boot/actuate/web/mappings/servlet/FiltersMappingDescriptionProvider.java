/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.web.context.WebApplicationContext
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.web.mappings.MappingDescriptionProvider;
import org.springframework.boot.actuate.web.mappings.servlet.FilterRegistrationMappingDescription;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class FiltersMappingDescriptionProvider
implements MappingDescriptionProvider {
    @Override
    public List<FilterRegistrationMappingDescription> describeMappings(ApplicationContext context) {
        if (!(context instanceof WebApplicationContext)) {
            return Collections.emptyList();
        }
        return ((WebApplicationContext)context).getServletContext().getFilterRegistrations().values().stream().map(FilterRegistrationMappingDescription::new).collect(Collectors.toList());
    }

    @Override
    public String getMappingName() {
        return "servletFilters";
    }
}

