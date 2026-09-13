/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import java.util.Optional;
import org.apereo.cas.services.RegisteredService;

public interface RegisteredServiceCipherExecutor {
    public static final String CUSTOM_HEADER_REGISTERED_SERVICE_ID = RegisteredService.class.getName();
    public static final String DEFAULT_BEAN_NAME = "registeredServiceCipherExecutor";

    public String encode(String var1, Optional<RegisteredService> var2);

    default public String encode(String data) {
        return this.encode(data, Optional.empty());
    }

    public String decode(String var1, Optional<RegisteredService> var2);

    default public boolean isEnabled() {
        return true;
    }

    default public boolean supports(RegisteredService registeredService) {
        return true;
    }

    public static RegisteredServiceCipherExecutor noOp() {
        return new RegisteredServiceCipherExecutor(){

            @Override
            public String encode(String data, Optional<RegisteredService> service) {
                return data;
            }

            @Override
            public String decode(String data, Optional<RegisteredService> service) {
                return data;
            }

            @Override
            public boolean supports(RegisteredService registeredService) {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}

