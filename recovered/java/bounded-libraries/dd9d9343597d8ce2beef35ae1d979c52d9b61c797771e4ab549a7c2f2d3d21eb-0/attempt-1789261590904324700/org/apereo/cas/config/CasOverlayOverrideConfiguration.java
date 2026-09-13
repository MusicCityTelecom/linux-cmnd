/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ScopedProxyMode
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package org.apereo.cas.config;

import java.util.HashMap;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.support.TpvHttpsLoginCheckHandlerInterceptorAdapter;
import org.apereo.cas.web.support.TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration(value="CasOverlayOverrideConfiguration", proxyBeanMethods=false)
public class CasOverlayOverrideConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @ConditionalOnMissingBean(name={"tpvWebflowExecutionPlanConfigurer"})
    public CasWebflowExecutionPlanConfigurer tpvWebflowExecutionPlanConfigurer() {
        return plan -> {
            plan.registerWebflowInterceptor((HandlerInterceptor)this.tpvHttpsLoginCheckHandlerInterceptorAdapter());
            plan.registerWebflowInterceptor((HandlerInterceptor)this.tpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter());
        };
    }

    public TpvHttpsLoginCheckHandlerInterceptorAdapter tpvHttpsLoginCheckHandlerInterceptorAdapter() {
        TpvHttpsLoginCheckHandlerInterceptorAdapter adapter = new TpvHttpsLoginCheckHandlerInterceptorAdapter();
        adapter.setForceHttps(true);
        HashMap<String, String> portRedirectMap = new HashMap<String, String>();
        portRedirectMap.put("http", "https");
        portRedirectMap.put("8080", "8443");
        portRedirectMap.put("8082", "8444");
        adapter.setPortRedirectMap(portRedirectMap);
        return adapter;
    }

    public TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter tpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter() {
        TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter adapter = new TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter();
        adapter.setErrorMaxCount(5);
        adapter.setLockMinutes(15);
        return adapter;
    }
}

