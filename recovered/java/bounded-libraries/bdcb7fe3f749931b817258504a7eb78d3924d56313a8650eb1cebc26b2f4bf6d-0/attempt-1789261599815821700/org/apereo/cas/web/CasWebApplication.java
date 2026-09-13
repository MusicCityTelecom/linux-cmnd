/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.util.spring.boot.CasBanner
 *  org.springframework.boot.Banner
 *  org.springframework.boot.WebApplicationType
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
 *  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
 *  org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
 *  org.springframework.boot.builder.SpringApplicationBuilder
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.client.discovery.EnableDiscoveryClient
 *  org.springframework.context.annotation.EnableAspectJAutoProxy
 *  org.springframework.scheduling.annotation.EnableScheduling
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package org.apereo.cas.web;

import lombok.Generated;
import org.apereo.cas.CasEmbeddedContainerUtils;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.util.spring.boot.CasBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication(proxyBeanMethods=false, exclude={DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties(value={CasConfigurationProperties.class})
@EnableAspectJAutoProxy(proxyTargetClass=false)
@EnableTransactionManagement(proxyTargetClass=false)
@EnableScheduling
public class CasWebApplication {
    public static void main(String[] args) {
        CasEmbeddedContainerUtils.getLoggingInitialization().ifPresent(init -> init.setMainArguments(args));
        CasBanner banner = CasEmbeddedContainerUtils.getCasBannerInstance();
        new SpringApplicationBuilder(new Class[]{CasWebApplication.class}).banner((Banner)banner).web(WebApplicationType.SERVLET).logStartupInfo(true).applicationStartup(CasEmbeddedContainerUtils.getApplicationStartup()).run(args);
    }

    @Generated
    public CasWebApplication() {
    }
}

