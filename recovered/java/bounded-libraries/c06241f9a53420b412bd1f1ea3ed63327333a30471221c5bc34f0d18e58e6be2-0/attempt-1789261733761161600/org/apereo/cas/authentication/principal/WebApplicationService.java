/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.validation.ValidationResponseType
 */
package org.apereo.cas.authentication.principal;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.validation.ValidationResponseType;

public interface WebApplicationService
extends Service {
    public static final String BEAN_NAME_FACTORY = "webApplicationServiceFactory";

    public String getArtifactId();

    public String getSource();

    public ValidationResponseType getFormat();

    public boolean isLoggedOutAlready();

    public void setLoggedOutAlready(boolean var1);
}

