/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.apereo.cas.web.flow;

import java.util.Collection;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowLoginContextProvider;
import org.springframework.web.servlet.HandlerInterceptor;

public interface CasWebflowExecutionPlan {
    public static final String BEAN_NAME = "casWebflowExecutionPlan";

    public void registerWebflowLoginContextProvider(CasWebflowLoginContextProvider var1);

    public void registerWebflowConfigurer(CasWebflowConfigurer var1);

    public void registerWebflowInterceptor(HandlerInterceptor var1);

    public Collection<CasWebflowConfigurer> getWebflowConfigurers();

    public Collection<HandlerInterceptor> getWebflowInterceptors();

    public Collection<CasWebflowLoginContextProvider> getWebflowLoginContextProviders();

    public void execute();
}

