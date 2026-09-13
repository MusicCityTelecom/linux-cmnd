/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@JsonFilter(value="GroovyAuthenticationExceptionsProperties")
@RequiresModule(name="cas-server-core-authentication", automated=true)
public class GroovyAuthenticationExceptionsProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -1385347572099983874L;
}

