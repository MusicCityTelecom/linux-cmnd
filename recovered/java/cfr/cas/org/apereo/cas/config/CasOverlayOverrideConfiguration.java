/*
 * Decompiled with CFR 0.152.
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

@Configuration(value="CasOverlayOverrideConfiguration", proxyBeanMethods=false)
public class CasOverlayOverrideConfiguration {
    @Bean
    @RefreshScope(proxyMode=ScopedProxyMode.DEFAULT)
    @ConditionalOnMissingBean(name={"tpvWebflowExecutionPlanConfigurer"})
    public CasWebflowExecutionPlanConfigurer tpvWebflowExecutionPlanConfigurer() {
        return plan -> {
            plan.registerWebflowInterceptor(this.tpvHttpsLoginCheckHandlerInterceptorAdapter());
            plan.registerWebflowInterceptor(this.tpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter());
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

