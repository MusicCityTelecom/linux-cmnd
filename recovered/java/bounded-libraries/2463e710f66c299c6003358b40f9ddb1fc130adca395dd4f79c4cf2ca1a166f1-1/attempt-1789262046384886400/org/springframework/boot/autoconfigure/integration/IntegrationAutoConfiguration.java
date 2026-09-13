/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.RSocket
 *  io.rsocket.transport.netty.server.TcpServerTransport
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException
 *  org.springframework.boot.task.TaskSchedulerBuilder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.integration.config.EnableIntegration
 *  org.springframework.integration.config.EnableIntegrationManagement
 *  org.springframework.integration.config.IntegrationManagementConfigurer
 *  org.springframework.integration.context.IntegrationProperties
 *  org.springframework.integration.gateway.GatewayProxyFactoryBean
 *  org.springframework.integration.jdbc.store.JdbcMessageStore
 *  org.springframework.integration.jmx.config.EnableIntegrationMBeanExport
 *  org.springframework.integration.monitor.IntegrationMBeanExporter
 *  org.springframework.integration.rsocket.ClientRSocketConnector
 *  org.springframework.integration.rsocket.IntegrationRSocketEndpoint
 *  org.springframework.integration.rsocket.ServerRSocketConnector
 *  org.springframework.integration.rsocket.ServerRSocketMessageHandler
 *  org.springframework.integration.rsocket.outbound.RSocketOutboundGateway
 *  org.springframework.integration.scheduling.PollerMetadata
 *  org.springframework.messaging.rsocket.RSocketRequester
 *  org.springframework.messaging.rsocket.RSocketStrategies
 *  org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 *  org.springframework.scheduling.support.CronTrigger
 *  org.springframework.scheduling.support.PeriodicTrigger
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.integration;

import io.rsocket.RSocket;
import io.rsocket.transport.netty.server.TcpServerTransport;
import java.time.Duration;
import javax.management.MBeanServer;
import javax.sql.DataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfigurationScanRegistrar;
import org.springframework.boot.autoconfigure.integration.IntegrationDataSourceInitializer;
import org.springframework.boot.autoconfigure.integration.IntegrationDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxProperties;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.OnDatabaseInitializationCondition;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.config.IntegrationManagementConfigurer;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.jdbc.store.JdbcMessageStore;
import org.springframework.integration.jmx.config.EnableIntegrationMBeanExport;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.integration.rsocket.ClientRSocketConnector;
import org.springframework.integration.rsocket.IntegrationRSocketEndpoint;
import org.springframework.integration.rsocket.ServerRSocketConnector;
import org.springframework.integration.rsocket.ServerRSocketMessageHandler;
import org.springframework.integration.rsocket.outbound.RSocketOutboundGateway;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.util.StringUtils;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, JmxAutoConfiguration.class, TaskSchedulingAutoConfiguration.class})
@ConditionalOnClass(value={EnableIntegration.class})
@EnableConfigurationProperties(value={IntegrationProperties.class, JmxProperties.class})
public class IntegrationAutoConfiguration {
    @Bean(name={"integrationGlobalProperties"})
    @ConditionalOnMissingBean(name={"integrationGlobalProperties"})
    public static org.springframework.integration.context.IntegrationProperties integrationGlobalProperties(IntegrationProperties properties) {
        org.springframework.integration.context.IntegrationProperties integrationProperties = new org.springframework.integration.context.IntegrationProperties();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from((Object)properties.getChannel().isAutoCreate()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setChannelsAutoCreate(arg_0));
        map.from((Object)properties.getChannel().getMaxUnicastSubscribers()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setChannelsMaxUnicastSubscribers(arg_0));
        map.from((Object)properties.getChannel().getMaxBroadcastSubscribers()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setChannelsMaxBroadcastSubscribers(arg_0));
        map.from((Object)properties.getError().isRequireSubscribers()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setErrorChannelRequireSubscribers(arg_0));
        map.from((Object)properties.getError().isIgnoreFailures()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setErrorChannelIgnoreFailures(arg_0));
        map.from((Object)properties.getEndpoint().isThrowExceptionOnLateReply()).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setMessagingTemplateThrowExceptionOnLateReply(arg_0));
        map.from(properties.getEndpoint().getReadOnlyHeaders()).as(StringUtils::toStringArray).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setReadOnlyHeaders(arg_0));
        map.from(properties.getEndpoint().getNoAutoStartup()).as(StringUtils::toStringArray).to(arg_0 -> ((org.springframework.integration.context.IntegrationProperties)integrationProperties).setNoAutoStartupEndpoints(arg_0));
        return integrationProperties;
    }

    static class OnIntegrationDatasourceInitializationCondition
    extends OnDatabaseInitializationCondition {
        OnIntegrationDatasourceInitializationCondition() {
            super("Integration", "spring.integration.jdbc.initialize-schema");
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={IntegrationRSocketEndpoint.class, RSocketRequester.class, RSocket.class})
    @Conditional(value={AnyRSocketChannelAdapterAvailable.class})
    protected static class IntegrationRSocketConfiguration {
        protected IntegrationRSocketConfiguration() {
        }

        @Configuration(proxyBeanMethods=false)
        protected static class IntegrationRSocketClientConfiguration {
            protected IntegrationRSocketClientConfiguration() {
            }

            @Bean
            @ConditionalOnMissingBean
            @Conditional(value={RemoteRSocketServerAddressConfigured.class})
            public ClientRSocketConnector clientRSocketConnector(IntegrationProperties integrationProperties, RSocketStrategies rSocketStrategies) {
                IntegrationProperties.RSocket.Client client = integrationProperties.getRsocket().getClient();
                ClientRSocketConnector clientRSocketConnector = client.getUri() != null ? new ClientRSocketConnector(client.getUri()) : new ClientRSocketConnector(client.getHost(), client.getPort().intValue());
                clientRSocketConnector.setRSocketStrategies(rSocketStrategies);
                return clientRSocketConnector;
            }

            static class RemoteRSocketServerAddressConfigured
            extends AnyNestedCondition {
                RemoteRSocketServerAddressConfigured() {
                    super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
                }

                @ConditionalOnProperty(prefix="spring.integration.rsocket.client", name={"host", "port"})
                static class TcpAddressConfigured {
                    TcpAddressConfigured() {
                    }
                }

                @ConditionalOnProperty(prefix="spring.integration.rsocket.client", name={"uri"})
                static class WebSocketAddressConfigured {
                    WebSocketAddressConfigured() {
                    }
                }
            }
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnClass(value={TcpServerTransport.class})
        @AutoConfigureBefore(value={RSocketMessagingAutoConfiguration.class})
        protected static class IntegrationRSocketServerConfiguration {
            protected IntegrationRSocketServerConfiguration() {
            }

            @Bean
            @ConditionalOnMissingBean(value={ServerRSocketMessageHandler.class})
            public RSocketMessageHandler serverRSocketMessageHandler(RSocketStrategies rSocketStrategies, IntegrationProperties integrationProperties) {
                ServerRSocketMessageHandler messageHandler = new ServerRSocketMessageHandler(integrationProperties.getRsocket().getServer().isMessageMappingEnabled());
                messageHandler.setRSocketStrategies(rSocketStrategies);
                return messageHandler;
            }

            @Bean
            @ConditionalOnMissingBean
            public ServerRSocketConnector serverRSocketConnector(ServerRSocketMessageHandler messageHandler) {
                return new ServerRSocketConnector(messageHandler);
            }
        }

        static class AnyRSocketChannelAdapterAvailable
        extends AnyNestedCondition {
            AnyRSocketChannelAdapterAvailable() {
                super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
            }

            @ConditionalOnBean(value={RSocketOutboundGateway.class})
            static class RSocketOutboundGatewayAvailable {
                RSocketOutboundGatewayAvailable() {
                }
            }

            @ConditionalOnBean(value={IntegrationRSocketEndpoint.class})
            static class IntegrationRSocketEndpointAvailable {
                IntegrationRSocketEndpointAvailable() {
                }
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JdbcMessageStore.class})
    @ConditionalOnSingleCandidate(value=DataSource.class)
    @Conditional(value={OnIntegrationDatasourceInitializationCondition.class})
    protected static class IntegrationJdbcConfiguration {
        protected IntegrationJdbcConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={IntegrationDataSourceScriptDatabaseInitializer.class, IntegrationDataSourceInitializer.class})
        public IntegrationDataSourceScriptDatabaseInitializer integrationDataSourceInitializer(DataSource dataSource, IntegrationProperties properties) {
            return new IntegrationDataSourceScriptDatabaseInitializer(dataSource, properties.getJdbc());
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={GatewayProxyFactoryBean.class})
    @Import(value={IntegrationAutoConfigurationScanRegistrar.class})
    protected static class IntegrationComponentScanConfiguration {
        protected IntegrationComponentScanConfiguration() {
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={EnableIntegrationManagement.class})
    @ConditionalOnMissingBean(value={IntegrationManagementConfigurer.class}, name={"integrationManagementConfigurer"}, search=SearchStrategy.CURRENT)
    protected static class IntegrationManagementConfiguration {
        protected IntegrationManagementConfiguration() {
        }

        @Configuration(proxyBeanMethods=false)
        @EnableIntegrationManagement(defaultLoggingEnabled="${spring.integration.management.default-logging-enabled:true}")
        protected static class EnableIntegrationManagementConfiguration {
            protected EnableIntegrationManagementConfiguration() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={EnableIntegrationMBeanExport.class})
    @ConditionalOnMissingBean(value={IntegrationMBeanExporter.class}, search=SearchStrategy.CURRENT)
    @ConditionalOnBean(value={MBeanServer.class})
    @ConditionalOnProperty(prefix="spring.jmx", name={"enabled"}, havingValue="true", matchIfMissing=true)
    protected static class IntegrationJmxConfiguration {
        protected IntegrationJmxConfiguration() {
        }

        @Bean
        public IntegrationMBeanExporter integrationMbeanExporter(BeanFactory beanFactory, JmxProperties properties) {
            IntegrationMBeanExporter exporter = new IntegrationMBeanExporter();
            String defaultDomain = properties.getDefaultDomain();
            if (StringUtils.hasLength((String)defaultDomain)) {
                exporter.setDefaultDomain(defaultDomain);
            }
            exporter.setServer((MBeanServer)beanFactory.getBean(properties.getServer(), MBeanServer.class));
            return exporter;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={TaskSchedulerBuilder.class})
    @ConditionalOnMissingBean(name={"taskScheduler"})
    protected static class IntegrationTaskSchedulerConfiguration {
        protected IntegrationTaskSchedulerConfiguration() {
        }

        @Bean(name={"taskScheduler"})
        public ThreadPoolTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
            return builder.build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @EnableIntegration
    protected static class IntegrationConfiguration {
        protected IntegrationConfiguration() {
        }

        @Bean(value={"org.springframework.integration.context.defaultPollerMetadata"})
        @ConditionalOnMissingBean(name={"org.springframework.integration.context.defaultPollerMetadata"})
        public PollerMetadata defaultPollerMetadata(IntegrationProperties integrationProperties) {
            IntegrationProperties.Poller poller = integrationProperties.getPoller();
            MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleNonNullValuesIn(entries -> {
                entries.put("spring.integration.poller.cron", StringUtils.hasText((String)poller.getCron()) ? poller.getCron() : null);
                entries.put("spring.integration.poller.fixed-delay", poller.getFixedDelay());
                entries.put("spring.integration.poller.fixed-rate", poller.getFixedRate());
            });
            PollerMetadata pollerMetadata = new PollerMetadata();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(poller::getMaxMessagesPerPoll).to(arg_0 -> ((PollerMetadata)pollerMetadata).setMaxMessagesPerPoll(arg_0));
            map.from(poller::getReceiveTimeout).as(Duration::toMillis).to(arg_0 -> ((PollerMetadata)pollerMetadata).setReceiveTimeout(arg_0));
            map.from((Object)poller).as(this::asTrigger).to(arg_0 -> ((PollerMetadata)pollerMetadata).setTrigger(arg_0));
            return pollerMetadata;
        }

        private Trigger asTrigger(IntegrationProperties.Poller poller) {
            if (StringUtils.hasText((String)poller.getCron())) {
                return new CronTrigger(poller.getCron());
            }
            if (poller.getFixedDelay() != null) {
                return this.createPeriodicTrigger(poller.getFixedDelay(), poller.getInitialDelay(), false);
            }
            if (poller.getFixedRate() != null) {
                return this.createPeriodicTrigger(poller.getFixedRate(), poller.getInitialDelay(), true);
            }
            return null;
        }

        private Trigger createPeriodicTrigger(Duration period, Duration initialDelay, boolean fixedRate) {
            PeriodicTrigger trigger = new PeriodicTrigger(period.toMillis());
            if (initialDelay != null) {
                trigger.setInitialDelay(initialDelay.toMillis());
            }
            trigger.setFixedRate(fixedRate);
            return trigger;
        }
    }
}

