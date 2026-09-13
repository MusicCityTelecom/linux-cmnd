/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.services;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.BaseRestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-rest-service-registry")
@JsonFilter(value="RestfulServiceRegistryProperties")
public class RestfulServiceRegistryProperties
extends BaseRestEndpointProperties {
    private static final long serialVersionUID = 7086088180957285517L;
}

