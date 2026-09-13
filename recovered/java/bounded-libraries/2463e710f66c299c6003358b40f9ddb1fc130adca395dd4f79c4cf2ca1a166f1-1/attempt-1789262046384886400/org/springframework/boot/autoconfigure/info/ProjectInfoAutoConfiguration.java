/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.info.BuildProperties
 *  org.springframework.boot.info.GitProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.EncodedResource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.info;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotatedTypeMetadata;

@AutoConfiguration
@EnableConfigurationProperties(value={ProjectInfoProperties.class})
public class ProjectInfoAutoConfiguration {
    private final ProjectInfoProperties properties;

    public ProjectInfoAutoConfiguration(ProjectInfoProperties properties) {
        this.properties = properties;
    }

    @Conditional(value={GitResourceAvailableCondition.class})
    @ConditionalOnMissingBean
    @Bean
    public GitProperties gitProperties() throws Exception {
        return new GitProperties(this.loadFrom(this.properties.getGit().getLocation(), "git", this.properties.getGit().getEncoding()));
    }

    @ConditionalOnResource(resources={"${spring.info.build.location:classpath:META-INF/build-info.properties}"})
    @ConditionalOnMissingBean
    @Bean
    public BuildProperties buildProperties() throws Exception {
        return new BuildProperties(this.loadFrom(this.properties.getBuild().getLocation(), "build", this.properties.getBuild().getEncoding()));
    }

    protected Properties loadFrom(Resource location, String prefix, Charset encoding) throws IOException {
        prefix = prefix.endsWith(".") ? prefix : prefix + ".";
        Properties source = this.loadSource(location, encoding);
        Properties target = new Properties();
        for (String key : source.stringPropertyNames()) {
            if (!key.startsWith(prefix)) continue;
            target.put(key.substring(prefix.length()), source.get(key));
        }
        return target;
    }

    private Properties loadSource(Resource location, Charset encoding) throws IOException {
        if (encoding != null) {
            return PropertiesLoaderUtils.loadProperties((EncodedResource)new EncodedResource(location, encoding));
        }
        return PropertiesLoaderUtils.loadProperties((Resource)location);
    }

    static class GitResourceAvailableCondition
    extends SpringBootCondition {
        GitResourceAvailableCondition() {
        }

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ResourceLoader loader = context.getResourceLoader();
            Environment environment = context.getEnvironment();
            String location = environment.getProperty("spring.info.git.location");
            if (location == null) {
                location = "classpath:git.properties";
            }
            ConditionMessage.Builder message = ConditionMessage.forCondition("GitResource", new Object[0]);
            if (loader.getResource(location).exists()) {
                return ConditionOutcome.match(message.found("git info at").items(location));
            }
            return ConditionOutcome.noMatch(message.didNotFind("git info at").items(location));
        }
    }
}

