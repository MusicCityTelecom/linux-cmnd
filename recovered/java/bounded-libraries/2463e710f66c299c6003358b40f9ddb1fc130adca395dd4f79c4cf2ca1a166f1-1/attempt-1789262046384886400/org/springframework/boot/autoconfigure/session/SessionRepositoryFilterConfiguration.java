/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.session.web.http.SessionRepositoryFilter
 */
package org.springframework.boot.autoconfigure.session;

import java.util.EnumSet;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration(proxyBeanMethods=false)
@ConditionalOnBean(value={SessionRepositoryFilter.class})
@EnableConfigurationProperties(value={SessionProperties.class})
class SessionRepositoryFilterConfiguration {
    SessionRepositoryFilterConfiguration() {
    }

    @Bean
    FilterRegistrationBean<SessionRepositoryFilter<?>> sessionRepositoryFilterRegistration(SessionProperties sessionProperties, SessionRepositoryFilter<?> filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter, new ServletRegistrationBean[0]);
        registration.setDispatcherTypes(this.getDispatcherTypes(sessionProperties));
        registration.setOrder(sessionProperties.getServlet().getFilterOrder());
        return registration;
    }

    private EnumSet<DispatcherType> getDispatcherTypes(SessionProperties sessionProperties) {
        SessionProperties.Servlet servletProperties = sessionProperties.getServlet();
        if (servletProperties.getFilterDispatcherTypes() == null) {
            return null;
        }
        return servletProperties.getFilterDispatcherTypes().stream().map(type -> DispatcherType.valueOf((String)type.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(DispatcherType.class)));
    }
}

