/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.reactive.function.server.ServerRequest
 *  org.springframework.web.server.ServerWebExchange
 */
package org.springframework.boot.web.reactive.error;

import java.util.Collections;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

public interface ErrorAttributes {
    public static final String ERROR_ATTRIBUTE = ErrorAttributes.class.getName() + ".error";

    default public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        return Collections.emptyMap();
    }

    public Throwable getError(ServerRequest var1);

    public void storeErrorInformation(Throwable var1, ServerWebExchange var2);
}

