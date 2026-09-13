/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.context.request.WebRequest
 */
package org.springframework.boot.web.servlet.error;

import java.util.Collections;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.web.context.request.WebRequest;

public interface ErrorAttributes {
    public static final String ERROR_ATTRIBUTE = ErrorAttributes.class.getName() + ".error";

    default public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return Collections.emptyMap();
    }

    public Throwable getError(WebRequest var1);
}

