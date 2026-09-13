/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.wsfed;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-wsfederation-webflow")
@JsonFilter(value="GroovyWsFederationDelegationProperties")
public class GroovyWsFederationDelegationProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8079027843747126083L;
}

