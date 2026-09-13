/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.web.view;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="RestfulViewProperties")
public class RestfulViewProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -8102345678378393382L;
}

