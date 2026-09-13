/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.services;

import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.UnauthorizedServiceException;

public class UnauthorizedServiceForPrincipalException
extends UnauthorizedServiceException {
    private static final long serialVersionUID = 8909291297815558561L;
    private static final String CODE = "service.not.authorized.missing.attr";
    private final RegisteredService registeredService;
    private final String principalId;
    private final Map<String, List<Object>> attributes;

    public UnauthorizedServiceForPrincipalException(String message, RegisteredService registeredService, String principalId, Map<String, List<Object>> attributes) {
        super(CODE, message);
        this.registeredService = registeredService;
        this.principalId = principalId;
        this.attributes = attributes;
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }

    @Generated
    public String getPrincipalId() {
        return this.principalId;
    }

    @Generated
    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }
}

