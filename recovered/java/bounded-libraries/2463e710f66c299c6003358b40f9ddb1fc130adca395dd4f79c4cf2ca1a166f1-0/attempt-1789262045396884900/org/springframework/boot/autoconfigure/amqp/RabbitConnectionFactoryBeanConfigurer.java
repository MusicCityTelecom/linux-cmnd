/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.client.impl.CredentialsProvider
 *  com.rabbitmq.client.impl.CredentialsRefreshService
 *  org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.amqp;

import com.rabbitmq.client.impl.CredentialsProvider;
import com.rabbitmq.client.impl.CredentialsRefreshService;
import java.time.Duration;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

public class RabbitConnectionFactoryBeanConfigurer {
    private final RabbitProperties rabbitProperties;
    private final ResourceLoader resourceLoader;
    private CredentialsProvider credentialsProvider;
    private CredentialsRefreshService credentialsRefreshService;

    public RabbitConnectionFactoryBeanConfigurer(ResourceLoader resourceLoader, RabbitProperties properties) {
        this.resourceLoader = resourceLoader;
        this.rabbitProperties = properties;
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public void setCredentialsRefreshService(CredentialsRefreshService credentialsRefreshService) {
        this.credentialsRefreshService = credentialsRefreshService;
    }

    public void configure(RabbitConnectionFactoryBean factory) {
        Assert.notNull((Object)factory, (String)"RabbitConnectionFactoryBean must not be null");
        factory.setResourceLoader(this.resourceLoader);
        PropertyMapper map = PropertyMapper.get();
        map.from(this.rabbitProperties::determineHost).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setHost(arg_0));
        map.from(this.rabbitProperties::determinePort).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setPort(arg_0));
        map.from(this.rabbitProperties::determineUsername).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setUsername(arg_0));
        map.from(this.rabbitProperties::determinePassword).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setPassword(arg_0));
        map.from(this.rabbitProperties::determineVirtualHost).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setVirtualHost(arg_0));
        map.from(this.rabbitProperties::getRequestedHeartbeat).whenNonNull().asInt(Duration::getSeconds).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setRequestedHeartbeat(arg_0));
        map.from(this.rabbitProperties::getRequestedChannelMax).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setRequestedChannelMax(arg_0));
        RabbitProperties.Ssl ssl = this.rabbitProperties.getSsl();
        if (ssl.determineEnabled()) {
            factory.setUseSSL(true);
            map.from(ssl::getAlgorithm).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setSslAlgorithm(arg_0));
            map.from(ssl::getKeyStoreType).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setKeyStoreType(arg_0));
            map.from(ssl::getKeyStore).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setKeyStore(arg_0));
            map.from(ssl::getKeyStorePassword).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setKeyStorePassphrase(arg_0));
            map.from(ssl::getKeyStoreAlgorithm).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setKeyStoreAlgorithm(arg_0));
            map.from(ssl::getTrustStoreType).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setTrustStoreType(arg_0));
            map.from(ssl::getTrustStore).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setTrustStore(arg_0));
            map.from(ssl::getTrustStorePassword).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setTrustStorePassphrase(arg_0));
            map.from(ssl::getTrustStoreAlgorithm).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setTrustStoreAlgorithm(arg_0));
            map.from(ssl::isValidateServerCertificate).to(validate -> factory.setSkipServerCertificateValidation(validate == false));
            map.from(ssl::getVerifyHostname).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setEnableHostnameVerification(arg_0));
        }
        map.from(this.rabbitProperties::getConnectionTimeout).whenNonNull().asInt(Duration::toMillis).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setConnectionTimeout(arg_0));
        map.from(this.rabbitProperties::getChannelRpcTimeout).whenNonNull().asInt(Duration::toMillis).to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setChannelRpcTimeout(arg_0));
        map.from((Object)this.credentialsProvider).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setCredentialsProvider(arg_0));
        map.from((Object)this.credentialsRefreshService).whenNonNull().to(arg_0 -> ((RabbitConnectionFactoryBean)factory).setCredentialsRefreshService(arg_0));
    }
}

