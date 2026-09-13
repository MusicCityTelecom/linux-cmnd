/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-core")
@JsonFilter(value="GroovyAcceptableUsagePolicyProperties")
public class GroovyAcceptableUsagePolicyProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 9164227843747126083L;
}

