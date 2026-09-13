/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.unboundid.ldap.listener.InMemoryDirectoryServer
 *  com.unboundid.ldap.listener.InMemoryDirectoryServerConfig
 *  com.unboundid.ldap.listener.InMemoryListenerConfig
 *  com.unboundid.ldap.sdk.LDAPException
 *  com.unboundid.ldap.sdk.schema.Schema
 *  com.unboundid.ldif.LDIFReader
 *  javax.annotation.PreDestroy
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.DependsOn
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.type.AnnotatedTypeMetadata
 *  org.springframework.ldap.core.ContextSource
 *  org.springframework.ldap.core.support.LdapContextSource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.ldap.embedded;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.ldif.LDIFReader;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.StringUtils;

@AutoConfiguration(before={LdapAutoConfiguration.class})
@EnableConfigurationProperties(value={LdapProperties.class, EmbeddedLdapProperties.class})
@ConditionalOnClass(value={InMemoryDirectoryServer.class})
@Conditional(value={EmbeddedLdapCondition.class})
public class EmbeddedLdapAutoConfiguration {
    private static final String PROPERTY_SOURCE_NAME = "ldap.ports";
    private final EmbeddedLdapProperties embeddedProperties;
    private InMemoryDirectoryServer server;

    public EmbeddedLdapAutoConfiguration(EmbeddedLdapProperties embeddedProperties) {
        this.embeddedProperties = embeddedProperties;
    }

    @Bean
    public InMemoryDirectoryServer directoryServer(ApplicationContext applicationContext) throws LDAPException {
        String[] baseDn = StringUtils.toStringArray(this.embeddedProperties.getBaseDn());
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(baseDn);
        if (this.embeddedProperties.getCredential().isAvailable()) {
            config.addAdditionalBindCredentials(this.embeddedProperties.getCredential().getUsername(), this.embeddedProperties.getCredential().getPassword());
        }
        this.setSchema(config);
        InMemoryListenerConfig listenerConfig = InMemoryListenerConfig.createLDAPConfig((String)"LDAP", (int)this.embeddedProperties.getPort());
        config.setListenerConfigs(new InMemoryListenerConfig[]{listenerConfig});
        this.server = new InMemoryDirectoryServer(config);
        this.importLdif(applicationContext);
        this.server.startListening();
        this.setPortProperty(applicationContext, this.server.getListenPort());
        return this.server;
    }

    private void setSchema(InMemoryDirectoryServerConfig config) {
        if (!this.embeddedProperties.getValidation().isEnabled()) {
            config.setSchema(null);
            return;
        }
        Resource schema = this.embeddedProperties.getValidation().getSchema();
        if (schema != null) {
            this.setSchema(config, schema);
        }
    }

    private void setSchema(InMemoryDirectoryServerConfig config, Resource resource) {
        try {
            Schema defaultSchema = Schema.getDefaultStandardSchema();
            Schema schema = Schema.getSchema((InputStream)resource.getInputStream());
            config.setSchema(Schema.mergeSchemas((Schema[])new Schema[]{defaultSchema, schema}));
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to load schema " + resource.getDescription(), ex);
        }
    }

    private void importLdif(ApplicationContext applicationContext) {
        block15: {
            String location = this.embeddedProperties.getLdif();
            if (StringUtils.hasText((String)location)) {
                try {
                    Resource resource = applicationContext.getResource(location);
                    if (!resource.exists()) break block15;
                    try (InputStream inputStream = resource.getInputStream();){
                        this.server.importFromLDIF(true, new LDIFReader(inputStream));
                    }
                }
                catch (Exception ex) {
                    throw new IllegalStateException("Unable to load LDIF " + location, ex);
                }
            }
        }
    }

    private void setPortProperty(ApplicationContext context, int port) {
        if (context instanceof ConfigurableApplicationContext) {
            MutablePropertySources sources = ((ConfigurableApplicationContext)context).getEnvironment().getPropertySources();
            this.getLdapPorts(sources).put("local.ldap.port", port);
        }
        if (context.getParent() != null) {
            this.setPortProperty(context.getParent(), port);
        }
    }

    private Map<String, Object> getLdapPorts(MutablePropertySources sources) {
        PropertySource propertySource = sources.get(PROPERTY_SOURCE_NAME);
        if (propertySource == null) {
            propertySource = new MapPropertySource(PROPERTY_SOURCE_NAME, new HashMap());
            sources.addFirst(propertySource);
        }
        return (Map)propertySource.getSource();
    }

    @PreDestroy
    public void close() {
        if (this.server != null) {
            this.server.shutDown(true);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ContextSource.class})
    static class EmbeddedLdapContextConfiguration {
        EmbeddedLdapContextConfiguration() {
        }

        @Bean
        @DependsOn(value={"directoryServer"})
        @ConditionalOnMissingBean
        LdapContextSource ldapContextSource(Environment environment, LdapProperties properties, EmbeddedLdapProperties embeddedProperties) {
            LdapContextSource source = new LdapContextSource();
            if (embeddedProperties.getCredential().isAvailable()) {
                source.setUserDn(embeddedProperties.getCredential().getUsername());
                source.setPassword(embeddedProperties.getCredential().getPassword());
            }
            source.setUrls(properties.determineUrls(environment));
            return source;
        }
    }

    static class EmbeddedLdapCondition
    extends SpringBootCondition {
        private static final Bindable<List<String>> STRING_LIST = Bindable.listOf(String.class);

        EmbeddedLdapCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Embedded LDAP", new Object[0]);
            Environment environment = context.getEnvironment();
            if (environment != null && !((List)Binder.get((Environment)environment).bind("spring.ldap.embedded.base-dn", STRING_LIST).orElseGet(Collections::emptyList)).isEmpty()) {
                return ConditionOutcome.match(message.because("Found base-dn property"));
            }
            return ConditionOutcome.noMatch(message.because("No base-dn property found"));
        }
    }
}

