/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-webflow")
@JsonFilter(value="SurrogateJsonAuthenticationProperties")
public class SurrogateJsonAuthenticationProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 3599367681439517829L;
}

