/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Logger;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.spi.ExternalConfigurationModel;

class SystemPropertiesConfigurationModel
implements ExternalConfigurationModel<Void> {
    private static final Logger log = Logger.getLogger(SystemPropertiesConfigurationModel.class.getName());
    static final List<String> PROPERTY_CLASSES = Arrays.asList("org.glassfish.jersey.server.ServerProperties", "org.glassfish.jersey.client.ClientProperties", "org.glassfish.jersey.servlet.ServletProperties", "org.glassfish.jersey.message.MessageProperties", "org.glassfish.jersey.apache.connector.ApacheClientProperties", "org.glassfish.jersey.jdk.connector.JdkConnectorProperties", "org.glassfish.jersey.jetty.connector.JettyClientProperties", "org.glassfish.jersey.media.multipart.MultiPartProperties", "org.glassfish.jersey.server.oauth1.OAuth1ServerProperties");
    private static final Map<Class, Function> converters = new HashMap<Class, Function>();

    SystemPropertiesConfigurationModel() {
    }

    private String getSystemProperty(String name) {
        return AccessController.doPrivileged(PropertiesHelper.getSystemProperty(name));
    }

    @Override
    public <T> T as(String name, Class<T> clazz) {
        if (converters.get(clazz) == null) {
            throw new IllegalArgumentException("Unsupported class type");
        }
        return name != null && clazz != null && this.isProperty(name) ? (T)clazz.cast(converters.get(clazz).apply(this.getSystemProperty(name))) : null;
    }

    @Override
    public <T> Optional<T> getOptionalProperty(String name, Class<T> clazz) {
        return Optional.of(this.as(name, clazz));
    }

    @Override
    public ExternalConfigurationModel mergeProperties(Map<String, Object> inputProperties) {
        return this;
    }

    @Override
    public Void getConfig() {
        return null;
    }

    @Override
    public boolean isProperty(String name) {
        return Optional.ofNullable(AccessController.doPrivileged(PropertiesHelper.getSystemProperty(name))).isPresent();
    }

    @Override
    public RuntimeType getRuntimeType() {
        return null;
    }

    @Override
    public Map<String, Object> getProperties() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Boolean allowSystemPropertiesProvider = this.as("jersey.config.allowSystemPropertiesProvider", Boolean.class);
        if (!Boolean.TRUE.equals(allowSystemPropertiesProvider)) {
            log.finer(LocalizationMessages.WARNING_PROPERTIES());
            return result;
        }
        try {
            AccessController.doPrivileged(PropertiesHelper.getSystemProperties()).forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(k, v) -> result.put(String.valueOf(k), v)));
        }
        catch (SecurityException se) {
            log.warning(LocalizationMessages.SYSTEM_PROPERTIES_WARNING());
            return this.getExpectedSystemProperties();
        }
        return result;
    }

    private Map<String, Object> getExpectedSystemProperties() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        this.mapFieldsToProperties(result, CommonProperties.class);
        for (String propertyClass : PROPERTY_CLASSES) {
            this.mapFieldsToProperties(result, AccessController.doPrivileged(ReflectionHelper.classForNamePA(propertyClass)));
        }
        return result;
    }

    private <T> void mapFieldsToProperties(Map<String, Object> properties, Class<T> clazz) {
        Field[] fields;
        if (clazz == null) {
            return;
        }
        for (Field field : fields = AccessController.doPrivileged(ReflectionHelper.getDeclaredFieldsPA(clazz))) {
            String value;
            String propertyValue;
            if (!Modifier.isStatic(field.getModifiers()) || !field.getType().isAssignableFrom(String.class) || (propertyValue = this.getPropertyNameByField(field)) == null || (value = this.getSystemProperty(propertyValue)) == null) continue;
            properties.put(propertyValue, value);
        }
    }

    private String getPropertyNameByField(Field field) {
        return AccessController.doPrivileged(() -> {
            try {
                return (String)field.get(null);
            }
            catch (IllegalAccessException e) {
                log.warning(e.getLocalizedMessage());
                return null;
            }
        });
    }

    @Override
    public Object getProperty(String name) {
        return this.getSystemProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return PropertiesHelper.getSystemProperties().run().stringPropertyNames();
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return false;
    }

    @Override
    public boolean isEnabled(Class<? extends Feature> featureClass) {
        return false;
    }

    @Override
    public boolean isRegistered(Object component) {
        return false;
    }

    @Override
    public boolean isRegistered(Class<?> componentClass) {
        return false;
    }

    @Override
    public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
        return null;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return null;
    }

    @Override
    public Set<Object> getInstances() {
        return null;
    }

    static {
        converters.put(String.class, s -> s);
        converters.put(Integer.class, s -> Integer.valueOf(s));
        converters.put(Boolean.class, s -> s.equalsIgnoreCase("1") ? true : Boolean.parseBoolean(s));
    }
}

