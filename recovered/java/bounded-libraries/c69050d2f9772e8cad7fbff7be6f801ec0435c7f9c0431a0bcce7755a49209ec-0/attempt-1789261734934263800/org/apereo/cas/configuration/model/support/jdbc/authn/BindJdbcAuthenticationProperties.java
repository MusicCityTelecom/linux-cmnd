/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.jdbc.authn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jdbc-authentication")
@JsonFilter(value="BindJdbcAuthenticationProperties")
public class BindJdbcAuthenticationProperties
extends BaseJdbcAuthenticationProperties {
    private static final long serialVersionUID = 4268982716707687796L;
}

