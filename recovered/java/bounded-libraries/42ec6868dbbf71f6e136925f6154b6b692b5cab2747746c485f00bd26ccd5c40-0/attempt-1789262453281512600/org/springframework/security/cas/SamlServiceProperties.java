/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.cas;

import org.springframework.security.cas.ServiceProperties;

public final class SamlServiceProperties
extends ServiceProperties {
    public static final String DEFAULT_SAML_ARTIFACT_PARAMETER = "SAMLart";
    public static final String DEFAULT_SAML_SERVICE_PARAMETER = "TARGET";

    public SamlServiceProperties() {
        super.setArtifactParameter(DEFAULT_SAML_ARTIFACT_PARAMETER);
        super.setServiceParameter(DEFAULT_SAML_SERVICE_PARAMETER);
    }
}

