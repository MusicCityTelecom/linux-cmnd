/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.jooq.lambda.Unchecked
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.ModelAndView
 */
package org.apereo.cas.util.spring;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RefreshableHandlerInterceptor
implements HandlerInterceptor {
    private ObjectProvider<? extends HandlerInterceptor> delegate;
    private Supplier<List<? extends HandlerInterceptor>> delegateSupplier;

    public RefreshableHandlerInterceptor(ObjectProvider<? extends HandlerInterceptor> delegate) {
        this.delegate = delegate;
    }

    public RefreshableHandlerInterceptor(Supplier<List<? extends HandlerInterceptor>> delegate) {
        this.delegateSupplier = delegate;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.getHandlerInterceptors().stream().allMatch(Unchecked.predicate(i -> i.preHandle(request, response, handler)));
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        this.getHandlerInterceptors().forEach(Unchecked.consumer(i -> i.postHandle(request, response, handler, modelAndView)));
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.getHandlerInterceptors().forEach(Unchecked.consumer(i -> i.afterCompletion(request, response, handler, ex)));
    }

    private List<? extends HandlerInterceptor> getHandlerInterceptors() {
        if (this.delegate != null) {
            return Optional.ofNullable((HandlerInterceptor)this.delegate.getIfAvailable()).map(List::of).orElseGet(List::of);
        }
        return this.delegateSupplier.get();
    }
}

