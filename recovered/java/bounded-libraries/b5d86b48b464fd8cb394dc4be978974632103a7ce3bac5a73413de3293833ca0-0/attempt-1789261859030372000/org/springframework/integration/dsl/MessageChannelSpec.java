/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.dsl.WireTapSpec;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.Assert;

public abstract class MessageChannelSpec<S extends MessageChannelSpec<S, C>, C extends AbstractMessageChannel>
extends IntegrationComponentSpec<S, C>
implements ComponentsRegistration {
    private final Map<Object, String> componentsToRegister = new LinkedHashMap<Object, String>();
    private final List<Class<?>> datatypes = new ArrayList();
    private final List<ChannelInterceptor> interceptors = new LinkedList<ChannelInterceptor>();
    protected C channel;
    private MessageConverter messageConverter;

    protected MessageChannelSpec() {
    }

    public S datatype(Class<?> ... types) {
        Assert.notNull(types, (String)"'datatypes' must not be null");
        Assert.noNullElements((Object[])types, (String)"'datatypes' must not contain null elements");
        this.datatypes.addAll(Arrays.asList(types));
        return (S)((MessageChannelSpec)this._this());
    }

    public S interceptor(ChannelInterceptor ... interceptorArray) {
        Assert.notNull((Object)interceptorArray, (String)"'interceptorArray' must not be null");
        Assert.noNullElements((Object[])interceptorArray, (String)"'interceptorArray' must not contain null elements");
        this.interceptors.addAll(Arrays.asList(interceptorArray));
        return (S)((MessageChannelSpec)this._this());
    }

    public S wireTap(String wireTapChannel) {
        return this.wireTap(new WireTapSpec(wireTapChannel));
    }

    public S wireTap(MessageChannel wireTapChannel) {
        return this.wireTap(new WireTapSpec(wireTapChannel));
    }

    public S wireTap(WireTapSpec wireTapSpec) {
        WireTap interceptor = (WireTap)wireTapSpec.get();
        this.componentsToRegister.put(interceptor, null);
        return this.interceptor(interceptor);
    }

    public S messageConverter(MessageConverter converter) {
        this.messageConverter = converter;
        return (S)((MessageChannelSpec)this._this());
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return this.componentsToRegister;
    }

    @Override
    protected C doGet() {
        ((AbstractMessageChannel)this.channel).setDatatypes(this.datatypes.toArray(new Class[0]));
        ((IntegrationObjectSupport)this.channel).setBeanName(this.getId());
        ((AbstractMessageChannel)this.channel).setInterceptors(this.interceptors);
        ((AbstractMessageChannel)this.channel).setMessageConverter(this.messageConverter);
        return this.channel;
    }
}

