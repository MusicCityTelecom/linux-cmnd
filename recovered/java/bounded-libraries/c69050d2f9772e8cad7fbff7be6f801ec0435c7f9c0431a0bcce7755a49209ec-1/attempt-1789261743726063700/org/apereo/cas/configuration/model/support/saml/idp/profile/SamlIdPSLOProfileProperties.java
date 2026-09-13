/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.saml.idp.profile;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.saml.idp.profile.SamlIdPBaseProfileProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPSLOProfileProperties")
public class SamlIdPSLOProfileProperties
extends SamlIdPBaseProfileProperties {
    private static final long serialVersionUID = -8100516679034234656L;
}

