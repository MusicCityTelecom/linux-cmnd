/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 *  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
 */
package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public interface WebMvcRegistrations {
    default public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return null;
    }

    default public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return null;
    }

    default public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
        return null;
    }
}

