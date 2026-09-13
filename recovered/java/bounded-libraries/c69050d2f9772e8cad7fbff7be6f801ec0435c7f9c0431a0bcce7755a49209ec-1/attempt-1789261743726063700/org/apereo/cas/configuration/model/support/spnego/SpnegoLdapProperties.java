/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.spnego;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-spnego-webflow")
@JsonFilter(value="SpnegoLdapProperties")
public class SpnegoLdapProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -8835216200501334936L;
}

