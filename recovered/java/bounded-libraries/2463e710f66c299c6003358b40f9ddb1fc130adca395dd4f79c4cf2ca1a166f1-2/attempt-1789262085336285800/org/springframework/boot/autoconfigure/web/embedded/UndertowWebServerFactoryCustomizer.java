/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.UndertowOptions
 *  org.springframework.boot.cloud.CloudPlatform
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.embedded.undertow.ConfigurableUndertowWebServerFactory
 *  org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.unit.DataSize
 *  org.xnio.Option
 *  org.xnio.Options
 */
package org.springframework.boot.autoconfigure.web.embedded;

import io.undertow.UndertowOptions;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.undertow.ConfigurableUndertowWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.unit.DataSize;
import org.xnio.Option;
import org.xnio.Options;

public class UndertowWebServerFactoryCustomizer
implements WebServerFactoryCustomizer<ConfigurableUndertowWebServerFactory>,
Ordered {
    private final Environment environment;
    private final ServerProperties serverProperties;

    public UndertowWebServerFactoryCustomizer(Environment environment, ServerProperties serverProperties) {
        this.environment = environment;
        this.serverProperties = serverProperties;
    }

    public int getOrder() {
        return 0;
    }

    public void customize(ConfigurableUndertowWebServerFactory factory) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        ServerOptions options = new ServerOptions(factory);
        ServerProperties properties = this.serverProperties;
        map.from(properties::getMaxHttpHeaderSize).asInt(DataSize::toBytes).when(this::isPositive).to(options.option(UndertowOptions.MAX_HEADER_SIZE));
        this.mapUndertowProperties(factory, options);
        this.mapAccessLogProperties(factory);
        map.from(this::getOrDeduceUseForwardHeaders).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setUseForwardHeaders(arg_0));
    }

    private void mapUndertowProperties(ConfigurableUndertowWebServerFactory factory, ServerOptions serverOptions) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        ServerProperties.Undertow properties = this.serverProperties.getUndertow();
        map.from(properties::getBufferSize).whenNonNull().asInt(DataSize::toBytes).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setBufferSize(arg_0));
        ServerProperties.Undertow.Threads threadProperties = properties.getThreads();
        map.from(threadProperties::getIo).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setIoThreads(arg_0));
        map.from(threadProperties::getWorker).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setWorkerThreads(arg_0));
        map.from(properties::getDirectBuffers).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setUseDirectBuffers(arg_0));
        map.from(properties::getMaxHttpPostSize).as(DataSize::toBytes).when(this::isPositive).to(serverOptions.option(UndertowOptions.MAX_ENTITY_SIZE));
        map.from(properties::getMaxParameters).to(serverOptions.option(UndertowOptions.MAX_PARAMETERS));
        map.from(properties::getMaxHeaders).to(serverOptions.option(UndertowOptions.MAX_HEADERS));
        map.from(properties::getMaxCookies).to(serverOptions.option(UndertowOptions.MAX_COOKIES));
        map.from(properties::isAllowEncodedSlash).to(serverOptions.option(UndertowOptions.ALLOW_ENCODED_SLASH));
        map.from(properties::isDecodeUrl).to(serverOptions.option(UndertowOptions.DECODE_URL));
        map.from(properties::getUrlCharset).as(Charset::name).to(serverOptions.option(UndertowOptions.URL_CHARSET));
        map.from(properties::isAlwaysSetKeepAlive).to(serverOptions.option(UndertowOptions.ALWAYS_SET_KEEP_ALIVE));
        map.from(properties::getNoRequestTimeout).asInt(Duration::toMillis).to(serverOptions.option(UndertowOptions.NO_REQUEST_TIMEOUT));
        map.from(properties.getOptions()::getServer).to(serverOptions.forEach(serverOptions::option));
        SocketOptions socketOptions = new SocketOptions(factory);
        map.from(properties.getOptions()::getSocket).to(socketOptions.forEach(socketOptions::option));
    }

    private boolean isPositive(Number value) {
        return value.longValue() > 0L;
    }

    private void mapAccessLogProperties(ConfigurableUndertowWebServerFactory factory) {
        ServerProperties.Undertow.Accesslog properties = this.serverProperties.getUndertow().getAccesslog();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::isEnabled).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogEnabled(arg_0));
        map.from(properties::getDir).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogDirectory(arg_0));
        map.from(properties::getPattern).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogPattern(arg_0));
        map.from(properties::getPrefix).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogPrefix(arg_0));
        map.from(properties::getSuffix).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogSuffix(arg_0));
        map.from(properties::isRotate).to(arg_0 -> ((ConfigurableUndertowWebServerFactory)factory).setAccessLogRotate(arg_0));
    }

    private boolean getOrDeduceUseForwardHeaders() {
        if (this.serverProperties.getForwardHeadersStrategy() == null) {
            CloudPlatform platform = CloudPlatform.getActive((Environment)this.environment);
            return platform != null && platform.isUsingForwardHeaders();
        }
        return this.serverProperties.getForwardHeadersStrategy().equals((Object)ServerProperties.ForwardHeadersStrategy.NATIVE);
    }

    private static class SocketOptions
    extends AbstractOptions {
        SocketOptions(ConfigurableUndertowWebServerFactory factory) {
            super(Options.class, factory);
        }

        <T> Consumer<T> option(Option<T> option) {
            return value -> this.getFactory().addBuilderCustomizers(new UndertowBuilderCustomizer[]{builder -> builder.setSocketOption(option, value)});
        }
    }

    private static class ServerOptions
    extends AbstractOptions {
        ServerOptions(ConfigurableUndertowWebServerFactory factory) {
            super(UndertowOptions.class, factory);
        }

        <T> Consumer<T> option(Option<T> option) {
            return value -> this.getFactory().addBuilderCustomizers(new UndertowBuilderCustomizer[]{builder -> builder.setServerOption(option, value)});
        }
    }

    private static abstract class AbstractOptions {
        private final Class<?> source;
        private final Map<String, Option<?>> nameLookup;
        private final ConfigurableUndertowWebServerFactory factory;

        AbstractOptions(Class<?> source, ConfigurableUndertowWebServerFactory factory) {
            HashMap lookup = new HashMap();
            ReflectionUtils.doWithLocalFields(source, field -> {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Option.class.isAssignableFrom(field.getType())) {
                    try {
                        Option option = (Option)field.get(null);
                        lookup.put(AbstractOptions.getCanonicalName(field.getName()), option);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                }
            });
            this.source = source;
            this.nameLookup = Collections.unmodifiableMap(lookup);
            this.factory = factory;
        }

        protected ConfigurableUndertowWebServerFactory getFactory() {
            return this.factory;
        }

        <T> Consumer<Map<String, String>> forEach(Function<Option<T>, Consumer<T>> function) {
            return map -> map.forEach((? super K key, ? super V value) -> {
                Option<?> option = this.nameLookup.get(AbstractOptions.getCanonicalName(key));
                Assert.state((option != null ? 1 : 0) != 0, () -> "Unable to find '" + key + "' in " + ClassUtils.getShortName(this.source));
                Object parsed = option.parseValue(value, this.getClass().getClassLoader());
                ((Consumer)function.apply(option)).accept(parsed);
            });
        }

        private static String getCanonicalName(String name) {
            StringBuilder canonicalName = new StringBuilder(name.length());
            name.chars().filter(Character::isLetterOrDigit).map(Character::toLowerCase).forEach((int c) -> canonicalName.append((char)c));
            return canonicalName.toString();
        }
    }
}

