/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sendgrid.Client
 *  com.sendgrid.SendGrid
 *  com.sendgrid.SendGridAPI
 *  org.apache.http.HttpHost
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 */
package org.springframework.boot.autoconfigure.sendgrid;

import com.sendgrid.Client;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridAPI;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.sendgrid.SendGridProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(value={SendGrid.class})
@ConditionalOnProperty(prefix="spring.sendgrid", value={"api-key"})
@EnableConfigurationProperties(value={SendGridProperties.class})
public class SendGridAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={SendGridAPI.class})
    public SendGrid sendGrid(SendGridProperties properties) {
        if (properties.isProxyConfigured()) {
            HttpHost proxy = new HttpHost(properties.getProxy().getHost(), properties.getProxy().getPort().intValue());
            return new SendGrid(properties.getApiKey(), new Client(HttpClientBuilder.create().setProxy(proxy).build()));
        }
        return new SendGrid(properties.getApiKey());
    }
}

