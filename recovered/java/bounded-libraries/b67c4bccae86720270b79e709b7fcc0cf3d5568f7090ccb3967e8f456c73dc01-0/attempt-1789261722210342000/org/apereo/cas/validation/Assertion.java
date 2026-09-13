/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.validation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;

public interface Assertion
extends Serializable {
    public Authentication getPrimaryAuthentication();

    public Authentication getOriginalAuthentication();

    public List<Authentication> getChainedAuthentications();

    public boolean isFromNewLogin();

    public WebApplicationService getService();

    public RegisteredService getRegisteredService();

    public Map<String, Serializable> getContext();
}

