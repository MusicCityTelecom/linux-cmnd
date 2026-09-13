/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.validation;

import java.util.Collection;
import org.apereo.cas.validation.ServiceTicketValidationAuthorizer;

public interface ServiceTicketValidationAuthorizersExecutionPlan {
    public void registerAuthorizer(ServiceTicketValidationAuthorizer var1);

    public Collection<ServiceTicketValidationAuthorizer> getAuthorizers();
}

