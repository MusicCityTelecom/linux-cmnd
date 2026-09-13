/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.log.LogMessage
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.messaging.core.DestinationResolutionException
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.support.management.MappingMessageRouterManagement;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractMappingMessageRouter
extends AbstractMessageRouter
implements MappingMessageRouterManagement {
    private static final int DEFAULT_DYNAMIC_CHANNEL_LIMIT = 100;
    private int dynamicChannelLimit = 100;
    private final Map<String, MessageChannel> dynamicChannels = Collections.synchronizedMap(new LinkedHashMap<String, MessageChannel>(100, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, MessageChannel> eldest) {
            return this.size() > AbstractMappingMessageRouter.this.dynamicChannelLimit;
        }
    });
    private String prefix;
    private String suffix;
    private boolean resolutionRequired = true;
    private boolean channelKeyFallback = true;
    private volatile Map<String, String> channelMappings = new LinkedHashMap<String, String>();

    @Override
    @ManagedAttribute
    public void setChannelMappings(Map<String, String> channelMappings) {
        Assert.notNull(channelMappings, (String)"'channelMappings' must not be null");
        LinkedHashMap<String, String> newChannelMappings = new LinkedHashMap<String, String>(channelMappings);
        this.doSetChannelMappings(newChannelMappings);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setResolutionRequired(boolean resolutionRequired) {
        this.resolutionRequired = resolutionRequired;
    }

    public void setChannelKeyFallback(boolean channelKeyFallback) {
        this.channelKeyFallback = channelKeyFallback;
    }

    public void setDynamicChannelLimit(int dynamicChannelLimit) {
        this.dynamicChannelLimit = dynamicChannelLimit;
    }

    @Override
    @ManagedAttribute
    public Map<String, String> getChannelMappings() {
        return new LinkedHashMap<String, String>(this.channelMappings);
    }

    @Override
    @ManagedOperation
    public void setChannelMapping(String key, String channelName) {
        LinkedHashMap<String, String> newChannelMappings = new LinkedHashMap<String, String>(this.channelMappings);
        newChannelMappings.put(key, channelName);
        this.channelMappings = newChannelMappings;
    }

    @Override
    @ManagedOperation
    public void removeChannelMapping(String key) {
        LinkedHashMap<String, String> newChannelMappings = new LinkedHashMap<String, String>(this.channelMappings);
        newChannelMappings.remove(key);
        this.channelMappings = newChannelMappings;
    }

    @Override
    @ManagedAttribute
    public Collection<String> getDynamicChannelNames() {
        return Collections.unmodifiableSet(this.dynamicChannels.keySet());
    }

    protected abstract List<Object> getChannelKeys(Message<?> var1);

    @Override
    protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        ArrayList<MessageChannel> channels = new ArrayList<MessageChannel>();
        List<Object> channelKeys = this.getChannelKeys(message);
        this.addToCollection(channels, channelKeys, message);
        return channels;
    }

    @Override
    @ManagedOperation
    public void replaceChannelMappings(Properties channelMappings) {
        Assert.notNull((Object)channelMappings, (String)"'channelMappings' must not be null");
        LinkedHashMap<String, String> newChannelMappings = new LinkedHashMap<String, String>();
        Set<String> keys = channelMappings.stringPropertyNames();
        for (String key : keys) {
            newChannelMappings.put(key.trim(), channelMappings.getProperty(key).trim());
        }
        this.doSetChannelMappings(newChannelMappings);
    }

    private void doSetChannelMappings(Map<String, String> newChannelMappings) {
        Map<String, String> oldChannelMappings = this.channelMappings;
        this.channelMappings = newChannelMappings;
        this.logger.debug((CharSequence)LogMessage.format((String)"Channel mappings: %s replaced with: %s", oldChannelMappings, newChannelMappings));
    }

    private MessageChannel resolveChannelForName(String channelName, Message<?> message) {
        MessageChannel channel;
        block2: {
            channel = null;
            try {
                channel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
            }
            catch (DestinationResolutionException e) {
                if (!this.resolutionRequired) break block2;
                throw new MessagingException(message, "failed to resolve channel name '" + channelName + "'", (Throwable)e);
            }
        }
        return channel;
    }

    private void addChannelFromString(Collection<MessageChannel> channels, String channelKey, Message<?> message) {
        if (channelKey.indexOf(44) != -1) {
            for (String name : StringUtils.tokenizeToStringArray((String)channelKey, (String)",")) {
                this.addChannelFromString(channels, name, message);
            }
            return;
        }
        String channelName = this.channelKeyFallback ? channelKey : null;
        boolean mapped = false;
        if (this.channelMappings.containsKey(channelKey)) {
            channelName = this.channelMappings.get(channelKey);
            mapped = true;
        }
        if (channelName != null) {
            this.addChannel(channels, message, channelName, mapped);
        }
    }

    private void addChannel(Collection<MessageChannel> channels, Message<?> message, String channelNameArg, boolean mapped) {
        MessageChannel channel;
        String channelName = channelNameArg;
        if (this.prefix != null) {
            channelName = this.prefix + channelName;
        }
        if (this.suffix != null) {
            channelName = channelName + this.suffix;
        }
        if ((channel = this.resolveChannelForName(channelName, message)) != null) {
            channels.add(channel);
            if (!mapped && this.dynamicChannels.get(channelName) == null) {
                this.dynamicChannels.put(channelName, channel);
            }
        }
    }

    private void addToCollection(Collection<MessageChannel> channels, Collection<?> channelKeys, Message<?> message) {
        if (channelKeys == null) {
            return;
        }
        for (Object channelKey : channelKeys) {
            if (channelKey == null) continue;
            this.addChannelKeyToCollection(channels, message, channelKey);
        }
    }

    private void addChannelKeyToCollection(Collection<MessageChannel> channels, Message<?> message, Object channelKey) {
        ConversionService conversionService = this.getRequiredConversionService();
        if (channelKey instanceof MessageChannel) {
            channels.add((MessageChannel)channelKey);
        } else if (channelKey instanceof MessageChannel[]) {
            channels.addAll(Arrays.asList((MessageChannel[])channelKey));
        } else if (channelKey instanceof String) {
            this.addChannelFromString(channels, (String)channelKey, message);
        } else if (channelKey instanceof Class) {
            this.addChannelFromString(channels, ((Class)channelKey).getName(), message);
        } else if (channelKey instanceof String[]) {
            for (String indicatorName : (String[])channelKey) {
                this.addChannelFromString(channels, indicatorName, message);
            }
        } else if (channelKey instanceof Collection) {
            this.addToCollection(channels, (Collection)channelKey, message);
        } else if (conversionService.canConvert(channelKey.getClass(), String.class)) {
            String converted = (String)conversionService.convert(channelKey, String.class);
            if (converted != null) {
                this.addChannelFromString(channels, converted, message);
            }
        } else {
            throw new MessagingException("unsupported return type for router [" + channelKey.getClass() + "]");
        }
    }
}

