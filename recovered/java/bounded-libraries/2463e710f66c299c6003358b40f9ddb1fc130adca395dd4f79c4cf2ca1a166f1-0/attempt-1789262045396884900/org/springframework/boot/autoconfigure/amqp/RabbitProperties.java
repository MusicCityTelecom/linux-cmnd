/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.core.AcknowledgeMode
 *  org.springframework.amqp.rabbit.connection.AbstractConnectionFactory$AddressShuffleMode
 *  org.springframework.amqp.rabbit.connection.CachingConnectionFactory$CacheMode
 *  org.springframework.amqp.rabbit.connection.CachingConnectionFactory$ConfirmType
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.convert.DurationUnit
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix="spring.rabbitmq")
public class RabbitProperties {
    private static final int DEFAULT_PORT = 5672;
    private static final int DEFAULT_PORT_SECURE = 5671;
    private static final int DEFAULT_STREAM_PORT = 5552;
    private String host = "localhost";
    private Integer port;
    private String username = "guest";
    private String password = "guest";
    private final Ssl ssl = new Ssl();
    private String virtualHost;
    private String addresses;
    private AbstractConnectionFactory.AddressShuffleMode addressShuffleMode = AbstractConnectionFactory.AddressShuffleMode.NONE;
    @DurationUnit(value=ChronoUnit.SECONDS)
    private Duration requestedHeartbeat;
    private int requestedChannelMax = 2047;
    private boolean publisherReturns;
    private CachingConnectionFactory.ConfirmType publisherConfirmType;
    private Duration connectionTimeout;
    private Duration channelRpcTimeout = Duration.ofMinutes(10L);
    private final Cache cache = new Cache();
    private final Listener listener = new Listener();
    private final Template template = new Template();
    private final Stream stream = new Stream();
    private List<Address> parsedAddresses;

    public String getHost() {
        return this.host;
    }

    public String determineHost() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.getHost();
        }
        return this.parsedAddresses.get(0).host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public int determinePort() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            Integer port = this.getPort();
            if (port != null) {
                return port;
            }
            return Optional.ofNullable(this.getSsl().getEnabled()).orElse(false) != false ? 5671 : 5672;
        }
        return this.parsedAddresses.get(0).port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddresses() {
        return this.addresses;
    }

    public String determineAddresses() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.host + ":" + this.determinePort();
        }
        ArrayList<String> addressStrings = new ArrayList<String>();
        for (Address parsedAddress : this.parsedAddresses) {
            addressStrings.add(parsedAddress.host + ":" + parsedAddress.port);
        }
        return StringUtils.collectionToCommaDelimitedString(addressStrings);
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
        this.parsedAddresses = this.parseAddresses(addresses);
    }

    private List<Address> parseAddresses(String addresses) {
        ArrayList<Address> parsedAddresses = new ArrayList<Address>();
        for (String address : StringUtils.commaDelimitedListToStringArray((String)addresses)) {
            parsedAddresses.add(new Address(address, Optional.ofNullable(this.getSsl().getEnabled()).orElse(false)));
        }
        return parsedAddresses;
    }

    public String getUsername() {
        return this.username;
    }

    public String determineUsername() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.username;
        }
        Address address = this.parsedAddresses.get(0);
        return address.username != null ? address.username : this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public String determinePassword() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.getPassword();
        }
        Address address = this.parsedAddresses.get(0);
        return address.password != null ? address.password : this.getPassword();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ssl getSsl() {
        return this.ssl;
    }

    public String getVirtualHost() {
        return this.virtualHost;
    }

    public String determineVirtualHost() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.getVirtualHost();
        }
        Address address = this.parsedAddresses.get(0);
        return address.virtualHost != null ? address.virtualHost : this.getVirtualHost();
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = StringUtils.hasText((String)virtualHost) ? virtualHost : "/";
    }

    public AbstractConnectionFactory.AddressShuffleMode getAddressShuffleMode() {
        return this.addressShuffleMode;
    }

    public void setAddressShuffleMode(AbstractConnectionFactory.AddressShuffleMode addressShuffleMode) {
        this.addressShuffleMode = addressShuffleMode;
    }

    public Duration getRequestedHeartbeat() {
        return this.requestedHeartbeat;
    }

    public void setRequestedHeartbeat(Duration requestedHeartbeat) {
        this.requestedHeartbeat = requestedHeartbeat;
    }

    public int getRequestedChannelMax() {
        return this.requestedChannelMax;
    }

    public void setRequestedChannelMax(int requestedChannelMax) {
        this.requestedChannelMax = requestedChannelMax;
    }

    public boolean isPublisherReturns() {
        return this.publisherReturns;
    }

    public void setPublisherReturns(boolean publisherReturns) {
        this.publisherReturns = publisherReturns;
    }

    public Duration getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setPublisherConfirmType(CachingConnectionFactory.ConfirmType publisherConfirmType) {
        this.publisherConfirmType = publisherConfirmType;
    }

    public CachingConnectionFactory.ConfirmType getPublisherConfirmType() {
        return this.publisherConfirmType;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Duration getChannelRpcTimeout() {
        return this.channelRpcTimeout;
    }

    public void setChannelRpcTimeout(Duration channelRpcTimeout) {
        this.channelRpcTimeout = channelRpcTimeout;
    }

    public Cache getCache() {
        return this.cache;
    }

    public Listener getListener() {
        return this.listener;
    }

    public Template getTemplate() {
        return this.template;
    }

    public Stream getStream() {
        return this.stream;
    }

    public static final class Stream {
        private String host = "localhost";
        private int port = 5552;
        private String username;
        private String password;
        private String name;

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return this.port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static final class Address {
        private static final String PREFIX_AMQP = "amqp://";
        private static final String PREFIX_AMQP_SECURE = "amqps://";
        private String host;
        private int port;
        private String username;
        private String password;
        private String virtualHost;
        private Boolean secureConnection;

        private Address(String input, boolean sslEnabled) {
            input = input.trim();
            input = this.trimPrefix(input);
            input = this.parseUsernameAndPassword(input);
            input = this.parseVirtualHost(input);
            this.parseHostAndPort(input, sslEnabled);
        }

        private String trimPrefix(String input) {
            if (input.startsWith(PREFIX_AMQP_SECURE)) {
                this.secureConnection = true;
                return input.substring(PREFIX_AMQP_SECURE.length());
            }
            if (input.startsWith(PREFIX_AMQP)) {
                this.secureConnection = false;
                return input.substring(PREFIX_AMQP.length());
            }
            return input;
        }

        private String parseUsernameAndPassword(String input) {
            if (input.contains("@")) {
                String[] split = StringUtils.split((String)input, (String)"@");
                String creds = split[0];
                input = split[1];
                split = StringUtils.split((String)creds, (String)":");
                this.username = split[0];
                if (split.length > 0) {
                    this.password = split[1];
                }
            }
            return input;
        }

        private String parseVirtualHost(String input) {
            int hostIndex = input.indexOf(47);
            if (hostIndex >= 0) {
                this.virtualHost = input.substring(hostIndex + 1);
                if (this.virtualHost.isEmpty()) {
                    this.virtualHost = "/";
                }
                input = input.substring(0, hostIndex);
            }
            return input;
        }

        private void parseHostAndPort(String input, boolean sslEnabled) {
            int bracketIndex = input.lastIndexOf(93);
            int colonIndex = input.lastIndexOf(58);
            if (colonIndex == -1 || colonIndex < bracketIndex) {
                this.host = input;
                this.port = this.determineSslEnabled(sslEnabled) ? 5671 : 5672;
            } else {
                this.host = input.substring(0, colonIndex);
                this.port = Integer.parseInt(input.substring(colonIndex + 1));
            }
        }

        private boolean determineSslEnabled(boolean sslEnabled) {
            return this.secureConnection != null ? this.secureConnection : sslEnabled;
        }
    }

    public static class ListenerRetry
    extends Retry {
        private boolean stateless = true;

        public boolean isStateless() {
            return this.stateless;
        }

        public void setStateless(boolean stateless) {
            this.stateless = stateless;
        }
    }

    public static class Retry {
        private boolean enabled;
        private int maxAttempts = 3;
        private Duration initialInterval = Duration.ofMillis(1000L);
        private double multiplier = 1.0;
        private Duration maxInterval = Duration.ofMillis(10000L);

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxAttempts() {
            return this.maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public Duration getInitialInterval() {
            return this.initialInterval;
        }

        public void setInitialInterval(Duration initialInterval) {
            this.initialInterval = initialInterval;
        }

        public double getMultiplier() {
            return this.multiplier;
        }

        public void setMultiplier(double multiplier) {
            this.multiplier = multiplier;
        }

        public Duration getMaxInterval() {
            return this.maxInterval;
        }

        public void setMaxInterval(Duration maxInterval) {
            this.maxInterval = maxInterval;
        }
    }

    public static class Template {
        private final Retry retry = new Retry();
        private Boolean mandatory;
        private Duration receiveTimeout;
        private Duration replyTimeout;
        private String exchange = "";
        private String routingKey = "";
        private String defaultReceiveQueue;

        public Retry getRetry() {
            return this.retry;
        }

        public Boolean getMandatory() {
            return this.mandatory;
        }

        public void setMandatory(Boolean mandatory) {
            this.mandatory = mandatory;
        }

        public Duration getReceiveTimeout() {
            return this.receiveTimeout;
        }

        public void setReceiveTimeout(Duration receiveTimeout) {
            this.receiveTimeout = receiveTimeout;
        }

        public Duration getReplyTimeout() {
            return this.replyTimeout;
        }

        public void setReplyTimeout(Duration replyTimeout) {
            this.replyTimeout = replyTimeout;
        }

        public String getExchange() {
            return this.exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getDefaultReceiveQueue() {
            return this.defaultReceiveQueue;
        }

        public void setDefaultReceiveQueue(String defaultReceiveQueue) {
            this.defaultReceiveQueue = defaultReceiveQueue;
        }
    }

    public static class StreamContainer
    extends BaseContainer {
        private boolean nativeListener;

        public boolean isNativeListener() {
            return this.nativeListener;
        }

        public void setNativeListener(boolean nativeListener) {
            this.nativeListener = nativeListener;
        }
    }

    public static class DirectContainer
    extends AmqpContainer {
        private Integer consumersPerQueue;
        private boolean missingQueuesFatal = false;

        public Integer getConsumersPerQueue() {
            return this.consumersPerQueue;
        }

        public void setConsumersPerQueue(Integer consumersPerQueue) {
            this.consumersPerQueue = consumersPerQueue;
        }

        @Override
        public boolean isMissingQueuesFatal() {
            return this.missingQueuesFatal;
        }

        public void setMissingQueuesFatal(boolean missingQueuesFatal) {
            this.missingQueuesFatal = missingQueuesFatal;
        }
    }

    public static class SimpleContainer
    extends AmqpContainer {
        private Integer concurrency;
        private Integer maxConcurrency;
        private Integer batchSize;
        private boolean missingQueuesFatal = true;
        private boolean consumerBatchEnabled;

        public Integer getConcurrency() {
            return this.concurrency;
        }

        public void setConcurrency(Integer concurrency) {
            this.concurrency = concurrency;
        }

        public Integer getMaxConcurrency() {
            return this.maxConcurrency;
        }

        public void setMaxConcurrency(Integer maxConcurrency) {
            this.maxConcurrency = maxConcurrency;
        }

        public Integer getBatchSize() {
            return this.batchSize;
        }

        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        @Override
        public boolean isMissingQueuesFatal() {
            return this.missingQueuesFatal;
        }

        public void setMissingQueuesFatal(boolean missingQueuesFatal) {
            this.missingQueuesFatal = missingQueuesFatal;
        }

        public boolean isConsumerBatchEnabled() {
            return this.consumerBatchEnabled;
        }

        public void setConsumerBatchEnabled(boolean consumerBatchEnabled) {
            this.consumerBatchEnabled = consumerBatchEnabled;
        }
    }

    public static abstract class AmqpContainer
    extends BaseContainer {
        private AcknowledgeMode acknowledgeMode;
        private Integer prefetch;
        private Boolean defaultRequeueRejected;
        private Duration idleEventInterval;
        private boolean deBatchingEnabled = true;
        private final ListenerRetry retry = new ListenerRetry();

        public AcknowledgeMode getAcknowledgeMode() {
            return this.acknowledgeMode;
        }

        public void setAcknowledgeMode(AcknowledgeMode acknowledgeMode) {
            this.acknowledgeMode = acknowledgeMode;
        }

        public Integer getPrefetch() {
            return this.prefetch;
        }

        public void setPrefetch(Integer prefetch) {
            this.prefetch = prefetch;
        }

        public Boolean getDefaultRequeueRejected() {
            return this.defaultRequeueRejected;
        }

        public void setDefaultRequeueRejected(Boolean defaultRequeueRejected) {
            this.defaultRequeueRejected = defaultRequeueRejected;
        }

        public Duration getIdleEventInterval() {
            return this.idleEventInterval;
        }

        public void setIdleEventInterval(Duration idleEventInterval) {
            this.idleEventInterval = idleEventInterval;
        }

        public abstract boolean isMissingQueuesFatal();

        public boolean isDeBatchingEnabled() {
            return this.deBatchingEnabled;
        }

        public void setDeBatchingEnabled(boolean deBatchingEnabled) {
            this.deBatchingEnabled = deBatchingEnabled;
        }

        public ListenerRetry getRetry() {
            return this.retry;
        }
    }

    public static abstract class BaseContainer {
        private boolean autoStartup = true;

        public boolean isAutoStartup() {
            return this.autoStartup;
        }

        public void setAutoStartup(boolean autoStartup) {
            this.autoStartup = autoStartup;
        }
    }

    public static class Listener {
        private ContainerType type = ContainerType.SIMPLE;
        private final SimpleContainer simple = new SimpleContainer();
        private final DirectContainer direct = new DirectContainer();
        private final StreamContainer stream = new StreamContainer();

        public ContainerType getType() {
            return this.type;
        }

        public void setType(ContainerType containerType) {
            this.type = containerType;
        }

        public SimpleContainer getSimple() {
            return this.simple;
        }

        public DirectContainer getDirect() {
            return this.direct;
        }

        public StreamContainer getStream() {
            return this.stream;
        }
    }

    public static enum ContainerType {
        SIMPLE,
        DIRECT,
        STREAM;

    }

    public static class Cache {
        private final Channel channel = new Channel();
        private final Connection connection = new Connection();

        public Channel getChannel() {
            return this.channel;
        }

        public Connection getConnection() {
            return this.connection;
        }

        public static class Connection {
            private CachingConnectionFactory.CacheMode mode = CachingConnectionFactory.CacheMode.CHANNEL;
            private Integer size;

            public CachingConnectionFactory.CacheMode getMode() {
                return this.mode;
            }

            public void setMode(CachingConnectionFactory.CacheMode mode) {
                this.mode = mode;
            }

            public Integer getSize() {
                return this.size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }
        }

        public static class Channel {
            private Integer size;
            private Duration checkoutTimeout;

            public Integer getSize() {
                return this.size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public Duration getCheckoutTimeout() {
                return this.checkoutTimeout;
            }

            public void setCheckoutTimeout(Duration checkoutTimeout) {
                this.checkoutTimeout = checkoutTimeout;
            }
        }
    }

    public class Ssl {
        private static final String SUN_X509 = "SunX509";
        private Boolean enabled;
        private String keyStore;
        private String keyStoreType = "PKCS12";
        private String keyStorePassword;
        private String keyStoreAlgorithm = "SunX509";
        private String trustStore;
        private String trustStoreType = "JKS";
        private String trustStorePassword;
        private String trustStoreAlgorithm = "SunX509";
        private String algorithm;
        private boolean validateServerCertificate = true;
        private boolean verifyHostname = true;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public boolean determineEnabled() {
            boolean defaultEnabled = Optional.ofNullable(this.getEnabled()).orElse(false);
            if (CollectionUtils.isEmpty((Collection)RabbitProperties.this.parsedAddresses)) {
                return defaultEnabled;
            }
            Address address = (Address)RabbitProperties.this.parsedAddresses.get(0);
            return address.determineSslEnabled(defaultEnabled);
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getKeyStore() {
            return this.keyStore;
        }

        public void setKeyStore(String keyStore) {
            this.keyStore = keyStore;
        }

        public String getKeyStoreType() {
            return this.keyStoreType;
        }

        public void setKeyStoreType(String keyStoreType) {
            this.keyStoreType = keyStoreType;
        }

        public String getKeyStorePassword() {
            return this.keyStorePassword;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public String getKeyStoreAlgorithm() {
            return this.keyStoreAlgorithm;
        }

        public void setKeyStoreAlgorithm(String keyStoreAlgorithm) {
            this.keyStoreAlgorithm = keyStoreAlgorithm;
        }

        public String getTrustStore() {
            return this.trustStore;
        }

        public void setTrustStore(String trustStore) {
            this.trustStore = trustStore;
        }

        public String getTrustStoreType() {
            return this.trustStoreType;
        }

        public void setTrustStoreType(String trustStoreType) {
            this.trustStoreType = trustStoreType;
        }

        public String getTrustStorePassword() {
            return this.trustStorePassword;
        }

        public void setTrustStorePassword(String trustStorePassword) {
            this.trustStorePassword = trustStorePassword;
        }

        public String getTrustStoreAlgorithm() {
            return this.trustStoreAlgorithm;
        }

        public void setTrustStoreAlgorithm(String trustStoreAlgorithm) {
            this.trustStoreAlgorithm = trustStoreAlgorithm;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public void setAlgorithm(String sslAlgorithm) {
            this.algorithm = sslAlgorithm;
        }

        public boolean isValidateServerCertificate() {
            return this.validateServerCertificate;
        }

        public void setValidateServerCertificate(boolean validateServerCertificate) {
            this.validateServerCertificate = validateServerCertificate;
        }

        public boolean getVerifyHostname() {
            return this.verifyHostname;
        }

        public void setVerifyHostname(boolean verifyHostname) {
            this.verifyHostname = verifyHostname;
        }
    }
}

