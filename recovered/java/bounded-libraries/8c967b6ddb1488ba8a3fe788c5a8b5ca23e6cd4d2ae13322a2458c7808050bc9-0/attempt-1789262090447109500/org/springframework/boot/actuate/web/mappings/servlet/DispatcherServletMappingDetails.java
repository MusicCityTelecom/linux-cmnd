/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import org.springframework.boot.actuate.web.mappings.HandlerMethodDescription;
import org.springframework.boot.actuate.web.mappings.servlet.RequestMappingConditionsDescription;

public class DispatcherServletMappingDetails {
    private HandlerMethodDescription handlerMethod;
    private RequestMappingConditionsDescription requestMappingConditions;

    public HandlerMethodDescription getHandlerMethod() {
        return this.handlerMethod;
    }

    void setHandlerMethod(HandlerMethodDescription handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public RequestMappingConditionsDescription getRequestMappingConditions() {
        return this.requestMappingConditions;
    }

    void setRequestMappingConditions(RequestMappingConditionsDescription requestMappingConditions) {
        this.requestMappingConditions = requestMappingConditions;
    }
}

