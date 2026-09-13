/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.context;

import java.util.Arrays;
import java.util.Properties;
import org.springframework.integration.JavaUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class IntegrationProperties {
    public static final String INTEGRATION_PROPERTIES_PREFIX = "spring.integration.";
    public static final String CHANNELS_AUTOCREATE = "spring.integration.channels.autoCreate";
    public static final String CHANNELS_MAX_UNICAST_SUBSCRIBERS = "spring.integration.channels.maxUnicastSubscribers";
    public static final String CHANNELS_MAX_BROADCAST_SUBSCRIBERS = "spring.integration.channels.maxBroadcastSubscribers";
    public static final String ERROR_CHANNEL_REQUIRE_SUBSCRIBERS = "spring.integration.channels.error.requireSubscribers";
    public static final String ERROR_CHANNEL_IGNORE_FAILURES = "spring.integration.channels.error.ignoreFailures";
    public static final String TASK_SCHEDULER_POOL_SIZE = "spring.integration.taskScheduler.poolSize";
    public static final String THROW_EXCEPTION_ON_LATE_REPLY = "spring.integration.messagingTemplate.throwExceptionOnLateReply";
    public static final String READ_ONLY_HEADERS = "spring.integration.readOnly.headers";
    public static final String ENDPOINTS_NO_AUTO_STARTUP = "spring.integration.endpoints.noAutoStartup";
    private static final Properties DEFAULTS = new IntegrationProperties().toProperties();
    private boolean channelsAutoCreate = true;
    private int channelsMaxUnicastSubscribers = Integer.MAX_VALUE;
    private int channelsMaxBroadcastSubscribers = Integer.MAX_VALUE;
    private boolean errorChannelRequireSubscribers = true;
    private boolean errorChannelIgnoreFailures = true;
    private int taskSchedulerPoolSize = 10;
    private boolean messagingTemplateThrowExceptionOnLateReply = false;
    private String[] readOnlyHeaders = new String[0];
    private String[] noAutoStartupEndpoints = new String[0];

    public void setChannelsAutoCreate(boolean channelsAutoCreate) {
        this.channelsAutoCreate = channelsAutoCreate;
    }

    public boolean isChannelsAutoCreate() {
        return this.channelsAutoCreate;
    }

    public void setChannelsMaxUnicastSubscribers(int channelsMaxUnicastSubscribers) {
        this.channelsMaxUnicastSubscribers = channelsMaxUnicastSubscribers;
    }

    public int getChannelsMaxUnicastSubscribers() {
        return this.channelsMaxUnicastSubscribers;
    }

    public void setChannelsMaxBroadcastSubscribers(int channelsMaxBroadcastSubscribers) {
        this.channelsMaxBroadcastSubscribers = channelsMaxBroadcastSubscribers;
    }

    public int getChannelsMaxBroadcastSubscribers() {
        return this.channelsMaxBroadcastSubscribers;
    }

    public void setErrorChannelRequireSubscribers(boolean errorChannelRequireSubscribers) {
        this.errorChannelRequireSubscribers = errorChannelRequireSubscribers;
    }

    public boolean isErrorChannelRequireSubscribers() {
        return this.errorChannelRequireSubscribers;
    }

    public void setErrorChannelIgnoreFailures(boolean errorChannelIgnoreFailures) {
        this.errorChannelIgnoreFailures = errorChannelIgnoreFailures;
    }

    public boolean isErrorChannelIgnoreFailures() {
        return this.errorChannelIgnoreFailures;
    }

    public void setTaskSchedulerPoolSize(int taskSchedulerPoolSize) {
        this.taskSchedulerPoolSize = taskSchedulerPoolSize;
    }

    public int getTaskSchedulerPoolSize() {
        return this.taskSchedulerPoolSize;
    }

    public void setMessagingTemplateThrowExceptionOnLateReply(boolean messagingTemplateThrowExceptionOnLateReply) {
        this.messagingTemplateThrowExceptionOnLateReply = messagingTemplateThrowExceptionOnLateReply;
    }

    public boolean isMessagingTemplateThrowExceptionOnLateReply() {
        return this.messagingTemplateThrowExceptionOnLateReply;
    }

    public void setReadOnlyHeaders(String ... readOnlyHeaders) {
        Assert.notNull((Object)readOnlyHeaders, (String)"'readOnlyHeaders' must not be null.");
        this.readOnlyHeaders = Arrays.copyOf(readOnlyHeaders, readOnlyHeaders.length);
    }

    public String[] getReadOnlyHeaders() {
        return Arrays.copyOf(this.readOnlyHeaders, this.readOnlyHeaders.length);
    }

    public void setNoAutoStartupEndpoints(String ... noAutoStartupEndpoints) {
        Assert.notNull((Object)noAutoStartupEndpoints, (String)"'noAutoStartupEndpoints' must not be null.");
        this.noAutoStartupEndpoints = Arrays.copyOf(noAutoStartupEndpoints, noAutoStartupEndpoints.length);
    }

    public String[] getNoAutoStartupEndpoints() {
        return Arrays.copyOf(this.noAutoStartupEndpoints, this.noAutoStartupEndpoints.length);
    }

    public Properties toProperties() {
        Properties properties = new Properties();
        properties.setProperty(CHANNELS_AUTOCREATE, "" + this.channelsAutoCreate);
        properties.setProperty(CHANNELS_MAX_UNICAST_SUBSCRIBERS, "" + this.channelsMaxUnicastSubscribers);
        properties.setProperty(CHANNELS_MAX_BROADCAST_SUBSCRIBERS, "" + this.channelsMaxBroadcastSubscribers);
        properties.setProperty(ERROR_CHANNEL_REQUIRE_SUBSCRIBERS, "" + this.errorChannelRequireSubscribers);
        properties.setProperty(ERROR_CHANNEL_IGNORE_FAILURES, "" + this.errorChannelIgnoreFailures);
        properties.setProperty(TASK_SCHEDULER_POOL_SIZE, "" + this.taskSchedulerPoolSize);
        properties.setProperty(THROW_EXCEPTION_ON_LATE_REPLY, "" + this.messagingTemplateThrowExceptionOnLateReply);
        properties.setProperty(READ_ONLY_HEADERS, StringUtils.arrayToCommaDelimitedString((Object[])this.readOnlyHeaders));
        properties.setProperty(ENDPOINTS_NO_AUTO_STARTUP, StringUtils.arrayToCommaDelimitedString((Object[])this.noAutoStartupEndpoints));
        return properties;
    }

    public static IntegrationProperties parse(Properties properties) {
        IntegrationProperties integrationProperties = new IntegrationProperties();
        JavaUtils.INSTANCE.acceptIfHasText(properties.getProperty(CHANNELS_AUTOCREATE), value -> integrationProperties.setChannelsAutoCreate(Boolean.parseBoolean(value))).acceptIfHasText(properties.getProperty(CHANNELS_MAX_UNICAST_SUBSCRIBERS), value -> integrationProperties.setChannelsMaxUnicastSubscribers(Integer.parseInt(value))).acceptIfHasText(properties.getProperty(CHANNELS_MAX_BROADCAST_SUBSCRIBERS), value -> integrationProperties.setChannelsMaxBroadcastSubscribers(Integer.parseInt(value))).acceptIfHasText(properties.getProperty(ERROR_CHANNEL_REQUIRE_SUBSCRIBERS), value -> integrationProperties.setErrorChannelRequireSubscribers(Boolean.parseBoolean(value))).acceptIfHasText(properties.getProperty(ERROR_CHANNEL_IGNORE_FAILURES), value -> integrationProperties.setErrorChannelIgnoreFailures(Boolean.parseBoolean(value))).acceptIfHasText(properties.getProperty(TASK_SCHEDULER_POOL_SIZE), value -> integrationProperties.setTaskSchedulerPoolSize(Integer.parseInt(value))).acceptIfHasText(properties.getProperty(THROW_EXCEPTION_ON_LATE_REPLY), value -> integrationProperties.setMessagingTemplateThrowExceptionOnLateReply(Boolean.parseBoolean(value))).acceptIfHasText(properties.getProperty(READ_ONLY_HEADERS), value -> integrationProperties.setReadOnlyHeaders(StringUtils.commaDelimitedListToStringArray((String)value))).acceptIfHasText(properties.getProperty(ENDPOINTS_NO_AUTO_STARTUP), value -> integrationProperties.setNoAutoStartupEndpoints(StringUtils.commaDelimitedListToStringArray((String)value)));
        return integrationProperties;
    }

    public static Properties defaults() {
        return DEFAULTS;
    }

    public static String getExpressionFor(String key) {
        if (DEFAULTS.containsKey(key)) {
            return "#{T(org.springframework.integration.context.IntegrationContextUtils).getIntegrationProperties(beanFactory).getProperty('" + key + "')}";
        }
        throw new IllegalArgumentException("The provided key [" + key + "] isn't the one of Integration properties: " + DEFAULTS.keySet());
    }
}

