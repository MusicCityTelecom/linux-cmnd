/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-interrupt-webflow")
@JsonFilter(value="RestfulInterruptProperties")
public class RestfulInterruptProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 1833594332973137011L;
}

