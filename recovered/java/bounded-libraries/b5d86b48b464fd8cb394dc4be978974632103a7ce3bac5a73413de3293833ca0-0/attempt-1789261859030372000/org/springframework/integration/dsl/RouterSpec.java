/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.dsl;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.dsl.AbstractRouterSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.MappingMessageRouterManagement;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class RouterSpec<K, R extends AbstractMappingMessageRouter>
extends AbstractRouterSpec<RouterSpec<K, R>, R> {
    private final RouterMappingProvider mappingProvider;
    private String prefix;
    private String suffix;
    private boolean mappingProviderRegistered;

    protected RouterSpec(R router) {
        super(router);
        this.mappingProvider = new RouterMappingProvider((MappingMessageRouterManagement)this.handler);
    }

    public RouterSpec<K, R> resolutionRequired(boolean resolutionRequired) {
        ((AbstractMappingMessageRouter)this.handler).setResolutionRequired(resolutionRequired);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> dynamicChannelLimit(int dynamicChannelLimit) {
        ((AbstractMappingMessageRouter)this.handler).setDynamicChannelLimit(dynamicChannelLimit);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> prefix(String prefix) {
        Assert.state((boolean)this.componentsToRegister.isEmpty(), (String)"The 'prefix'('suffix') and 'subFlowMapping' are mutually exclusive");
        this.prefix = prefix;
        ((AbstractMappingMessageRouter)this.handler).setPrefix(prefix);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> suffix(String suffix) {
        Assert.state((boolean)this.componentsToRegister.isEmpty(), (String)"The 'prefix'('suffix') and 'subFlowMapping' are mutually exclusive");
        this.suffix = suffix;
        ((AbstractMappingMessageRouter)this.handler).setSuffix(suffix);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> noChannelKeyFallback() {
        ((AbstractMappingMessageRouter)this.handler).setChannelKeyFallback(false);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> channelMapping(K key, final String channelName) {
        Assert.notNull(key, (String)"'key' must not be null");
        Assert.hasText((String)channelName, (String)"'channelName' must not be empty");
        if (key instanceof String) {
            ((AbstractMappingMessageRouter)this.handler).setChannelMapping((String)key, channelName);
        } else {
            this.mappingProvider.addMapping(key, new NamedComponent(){

                @Override
                public String getComponentName() {
                    return channelName;
                }

                @Override
                public String getComponentType() {
                    return "channel";
                }
            });
        }
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> channelMapping(K key, MessageChannel channel) {
        Assert.notNull(key, (String)"'key' must not be null");
        Assert.notNull((Object)channel, (String)"'channel' must not be null");
        Assert.isInstanceOf(NamedComponent.class, (Object)channel, () -> "The routing channel '" + channel + " must be instance of 'NamedComponent'.");
        this.mappingProvider.addMapping(key, (NamedComponent)channel);
        return (RouterSpec)this._this();
    }

    public RouterSpec<K, R> subFlowMapping(K key, IntegrationFlow subFlow) {
        Assert.notNull(key, (String)"'key' must not be null");
        Assert.state((!StringUtils.hasText((String)this.prefix) && !StringUtils.hasText((String)this.suffix) ? 1 : 0) != 0, (String)"The 'prefix'('suffix') and 'subFlowMapping' are mutually exclusive");
        MessageChannel channel = this.obtainInputChannelFromFlow(subFlow, false);
        Assert.isInstanceOf(NamedComponent.class, (Object)channel, () -> "The routing channel '" + channel + "' from the flow '" + subFlow + "' must be instance of 'NamedComponent'.");
        this.mappingProvider.addMapping(key, (NamedComponent)channel);
        return (RouterSpec)this._this();
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        if (!this.mappingProviderRegistered) {
            if (!this.mappingProvider.mapping.isEmpty()) {
                this.componentsToRegister.put(this.mappingProvider, null);
            }
            this.mappingProviderRegistered = true;
        }
        return super.getComponentsToRegister();
    }

    private static class RouterMappingProvider
    extends IntegrationObjectSupport {
        private final MappingMessageRouterManagement router;
        private final Map<Object, NamedComponent> mapping = new LinkedHashMap<Object, NamedComponent>();

        RouterMappingProvider(MappingMessageRouterManagement router) {
            this.router = router;
        }

        void addMapping(Object key, NamedComponent channel) {
            this.mapping.put(key, channel);
        }

        @Override
        protected void onInit() {
            super.onInit();
            ConversionService conversionService = this.getConversionService();
            if (conversionService == null) {
                conversionService = DefaultConversionService.getSharedInstance();
            }
            for (Map.Entry<Object, NamedComponent> entry : this.mapping.entrySet()) {
                String channelKey;
                Object key = entry.getKey();
                if (key instanceof String) {
                    channelKey = (String)key;
                } else if (key instanceof Class) {
                    channelKey = ((Class)key).getName();
                } else if (conversionService.canConvert(key.getClass(), String.class)) {
                    channelKey = (String)conversionService.convert(key, String.class);
                } else {
                    throw new MessagingException("Unsupported channel mapping type for router [" + key.getClass() + "]");
                }
                this.router.setChannelMapping(channelKey, entry.getValue().getComponentName());
            }
        }
    }
}

