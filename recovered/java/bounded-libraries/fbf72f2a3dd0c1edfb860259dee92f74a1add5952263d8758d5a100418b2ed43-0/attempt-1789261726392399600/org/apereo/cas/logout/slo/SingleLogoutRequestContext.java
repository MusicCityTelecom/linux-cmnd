/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 */
package org.apereo.cas.logout.slo;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.LogoutRequestStatus;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceLogoutType;

public interface SingleLogoutRequestContext
extends Serializable {
    public LogoutRequestStatus getStatus();

    public void setStatus(LogoutRequestStatus var1);

    public String getTicketId();

    public WebApplicationService getService();

    public URL getLogoutUrl();

    public RegisteredService getRegisteredService();

    public SingleLogoutExecutionRequest getExecutionRequest();

    public RegisteredServiceLogoutType getLogoutType();

    public Map<String, String> getProperties();
}

