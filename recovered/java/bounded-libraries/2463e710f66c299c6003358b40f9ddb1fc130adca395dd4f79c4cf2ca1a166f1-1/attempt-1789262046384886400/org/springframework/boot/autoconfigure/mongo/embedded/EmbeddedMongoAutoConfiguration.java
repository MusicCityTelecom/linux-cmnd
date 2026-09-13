/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClientSettings
 *  com.mongodb.client.MongoClient
 *  com.mongodb.reactivestreams.client.MongoClient
 *  de.flapdoodle.embed.mongo.MongodExecutable
 *  de.flapdoodle.embed.mongo.MongodStarter
 *  de.flapdoodle.embed.mongo.config.Defaults
 *  de.flapdoodle.embed.mongo.config.ImmutableMongodConfig$Builder
 *  de.flapdoodle.embed.mongo.config.MongodConfig
 *  de.flapdoodle.embed.mongo.config.Net
 *  de.flapdoodle.embed.mongo.config.Storage
 *  de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion
 *  de.flapdoodle.embed.mongo.distribution.Version
 *  de.flapdoodle.embed.mongo.distribution.Versions
 *  de.flapdoodle.embed.mongo.packageresolver.Command
 *  de.flapdoodle.embed.process.config.ExecutableProcessConfig
 *  de.flapdoodle.embed.process.config.RuntimeConfig
 *  de.flapdoodle.embed.process.config.process.ImmutableProcessOutput
 *  de.flapdoodle.embed.process.config.process.ProcessOutput
 *  de.flapdoodle.embed.process.config.store.DownloadConfig
 *  de.flapdoodle.embed.process.config.store.ImmutableDownloadConfig
 *  de.flapdoodle.embed.process.config.store.ImmutableDownloadConfig$Builder
 *  de.flapdoodle.embed.process.distribution.Version
 *  de.flapdoodle.embed.process.distribution.Version$GenericVersion
 *  de.flapdoodle.embed.process.io.Processors
 *  de.flapdoodle.embed.process.io.Slf4jLevel
 *  de.flapdoodle.embed.process.io.StreamProcessor
 *  de.flapdoodle.embed.process.io.progress.ProgressListener
 *  de.flapdoodle.embed.process.io.progress.Slf4jProgressListener
 *  de.flapdoodle.embed.process.runtime.Network
 *  de.flapdoodle.embed.process.store.ExtractedArtifactStore
 *  de.flapdoodle.embed.process.store.IArtifactStore
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.env.MapPropertySource
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.data.mongodb.core.MongoClientFactoryBean
 *  org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.mongo.embedded;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.Defaults;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.distribution.Versions;
import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.process.config.ExecutableProcessConfig;
import de.flapdoodle.embed.process.config.RuntimeConfig;
import de.flapdoodle.embed.process.config.process.ImmutableProcessOutput;
import de.flapdoodle.embed.process.config.process.ProcessOutput;
import de.flapdoodle.embed.process.config.store.DownloadConfig;
import de.flapdoodle.embed.process.config.store.ImmutableDownloadConfig;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.io.Slf4jLevel;
import de.flapdoodle.embed.process.io.StreamProcessor;
import de.flapdoodle.embed.process.io.progress.ProgressListener;
import de.flapdoodle.embed.process.io.progress.Slf4jProgressListener;
import de.flapdoodle.embed.process.runtime.Network;
import de.flapdoodle.embed.process.store.ExtractedArtifactStore;
import de.flapdoodle.embed.process.store.IArtifactStore;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoClientDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.data.mongo.ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.embedded.DownloadConfigBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;
import org.springframework.util.Assert;

@AutoConfiguration(before={MongoAutoConfiguration.class})
@EnableConfigurationProperties(value={MongoProperties.class, EmbeddedMongoProperties.class})
@ConditionalOnClass(value={MongoClientSettings.class, MongodStarter.class})
@Import(value={EmbeddedMongoClientDependsOnBeanFactoryPostProcessor.class, EmbeddedReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor.class})
public class EmbeddedMongoAutoConfiguration {
    private static final byte[] IP4_LOOPBACK_ADDRESS = new byte[]{127, 0, 0, 1};
    private static final byte[] IP6_LOOPBACK_ADDRESS = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
    private final MongoProperties properties;

    public EmbeddedMongoAutoConfiguration(MongoProperties properties) {
        this.properties = properties;
    }

    @Bean(initMethod="start", destroyMethod="stop")
    @ConditionalOnMissingBean
    public MongodExecutable embeddedMongoServer(MongodConfig mongodConfig, RuntimeConfig runtimeConfig, ApplicationContext context) {
        Integer configuredPort = this.properties.getPort();
        if (configuredPort == null || configuredPort == 0) {
            this.setEmbeddedPort(context, mongodConfig.net().getPort());
        }
        MongodStarter mongodStarter = this.getMongodStarter(runtimeConfig);
        return (MongodExecutable)mongodStarter.prepare((ExecutableProcessConfig)mongodConfig);
    }

    private MongodStarter getMongodStarter(RuntimeConfig runtimeConfig) {
        if (runtimeConfig == null) {
            return MongodStarter.getDefaultInstance();
        }
        return MongodStarter.getInstance((RuntimeConfig)runtimeConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public MongodConfig embeddedMongoConfiguration(EmbeddedMongoProperties embeddedProperties) throws IOException {
        Integer configuredPort;
        ImmutableMongodConfig.Builder builder = MongodConfig.builder().version(this.determineVersion(embeddedProperties));
        EmbeddedMongoProperties.Storage storage = embeddedProperties.getStorage();
        if (storage != null) {
            String databaseDir = storage.getDatabaseDir();
            String replSetName = storage.getReplSetName();
            int oplogSize = storage.getOplogSize() != null ? (int)storage.getOplogSize().toMegabytes() : 0;
            builder.replication(new Storage(databaseDir, replSetName, oplogSize));
        }
        if ((configuredPort = this.properties.getPort()) != null && configuredPort > 0) {
            builder.net(new Net(this.getHost().getHostAddress(), configuredPort.intValue(), Network.localhostIsIPv6()));
        } else {
            builder.net(new Net(this.getHost().getHostAddress(), Network.freeServerPort((InetAddress)this.getHost()), Network.localhostIsIPv6()));
        }
        return builder.build();
    }

    private IFeatureAwareVersion determineVersion(EmbeddedMongoProperties embeddedProperties) {
        Assert.state((embeddedProperties.getVersion() != null ? 1 : 0) != 0, (String)"Set the spring.mongodb.embedded.version property or define your own MongodConfig bean to use embedded MongoDB");
        for (Version version : Version.values()) {
            if (!version.asInDownloadPath().equals(embeddedProperties.getVersion())) continue;
            return version;
        }
        return Versions.withFeatures((de.flapdoodle.embed.process.distribution.Version)this.createEmbeddedMongoVersion(embeddedProperties));
    }

    private Version.GenericVersion createEmbeddedMongoVersion(EmbeddedMongoProperties embeddedProperties) {
        return de.flapdoodle.embed.process.distribution.Version.of((String)embeddedProperties.getVersion());
    }

    private InetAddress getHost() throws UnknownHostException {
        if (this.properties.getHost() == null) {
            return InetAddress.getByAddress(Network.localhostIsIPv6() ? IP6_LOOPBACK_ADDRESS : IP4_LOOPBACK_ADDRESS);
        }
        return InetAddress.getByName(this.properties.getHost());
    }

    private void setEmbeddedPort(ApplicationContext context, int port) {
        this.setPortProperty(context, port);
    }

    private void setPortProperty(ApplicationContext currentContext, int port) {
        if (currentContext instanceof ConfigurableApplicationContext) {
            MutablePropertySources sources = ((ConfigurableApplicationContext)currentContext).getEnvironment().getPropertySources();
            this.getMongoPorts(sources).put("local.mongo.port", port);
        }
        if (currentContext.getParent() != null) {
            this.setPortProperty(currentContext.getParent(), port);
        }
    }

    private Map<String, Object> getMongoPorts(MutablePropertySources sources) {
        PropertySource propertySource = sources.get("mongo.ports");
        if (propertySource == null) {
            propertySource = new MapPropertySource("mongo.ports", new HashMap());
            sources.addFirst(propertySource);
        }
        return (Map)propertySource.getSource();
    }

    @ConditionalOnClass(value={com.mongodb.reactivestreams.client.MongoClient.class, ReactiveMongoClientFactoryBean.class})
    static class EmbeddedReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor
    extends ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor {
        EmbeddedReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor() {
            super(MongodExecutable.class);
        }
    }

    @ConditionalOnClass(value={MongoClient.class, MongoClientFactoryBean.class})
    static class EmbeddedMongoClientDependsOnBeanFactoryPostProcessor
    extends MongoClientDependsOnBeanFactoryPostProcessor {
        EmbeddedMongoClientDependsOnBeanFactoryPostProcessor() {
            super(MongodExecutable.class);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Logger.class})
    @ConditionalOnMissingBean(value={RuntimeConfig.class})
    static class RuntimeConfigConfiguration {
        RuntimeConfigConfiguration() {
        }

        @Bean
        RuntimeConfig embeddedMongoRuntimeConfig(ObjectProvider<DownloadConfigBuilderCustomizer> downloadConfigBuilderCustomizers) {
            Logger logger = LoggerFactory.getLogger((String)(this.getClass().getPackage().getName() + ".EmbeddedMongo"));
            ImmutableProcessOutput processOutput = ProcessOutput.builder().output(Processors.logTo((Logger)logger, (Slf4jLevel)Slf4jLevel.INFO)).error(Processors.logTo((Logger)logger, (Slf4jLevel)Slf4jLevel.ERROR)).commands(Processors.named((String)"[console>]", (StreamProcessor)Processors.logTo((Logger)logger, (Slf4jLevel)Slf4jLevel.DEBUG))).build();
            return Defaults.runtimeConfigFor((Command)Command.MongoD, (Logger)logger).processOutput((ProcessOutput)processOutput).artifactStore((IArtifactStore)this.getArtifactStore(logger, downloadConfigBuilderCustomizers.orderedStream())).isDaemonProcess(false).build();
        }

        private ExtractedArtifactStore getArtifactStore(Logger logger, Stream<DownloadConfigBuilderCustomizer> downloadConfigBuilderCustomizers) {
            ImmutableDownloadConfig.Builder downloadConfigBuilder = Defaults.downloadConfigFor((Command)Command.MongoD);
            downloadConfigBuilder.progressListener((ProgressListener)new Slf4jProgressListener(logger));
            downloadConfigBuilderCustomizers.forEach(customizer -> customizer.customize(downloadConfigBuilder));
            ImmutableDownloadConfig downloadConfig = downloadConfigBuilder.build();
            return Defaults.extractedArtifactStoreFor((Command)Command.MongoD).withDownloadConfig((DownloadConfig)downloadConfig);
        }
    }
}

