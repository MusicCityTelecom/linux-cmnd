/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties")
public class Pac4jDelegatedAuthenticationDiscoverySelectionJsonProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -2261947621312270068L;
}

