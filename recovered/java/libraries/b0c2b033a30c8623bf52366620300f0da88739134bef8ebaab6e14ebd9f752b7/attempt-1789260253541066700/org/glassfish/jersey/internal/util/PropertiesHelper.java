/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public final class PropertiesHelper {
    private static final Logger LOGGER = Logger.getLogger(PropertiesHelper.class.getName());
    private static final boolean METAINF_SERVICES_LOOKUP_DISABLE_DEFAULT = false;

    public static PrivilegedAction<Properties> getSystemProperties() {
        return new PrivilegedAction<Properties>(){

            @Override
            public Properties run() {
                return System.getProperties();
            }
        };
    }

    public static PrivilegedAction<String> getSystemProperty(final String name) {
        return new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(name);
            }
        };
    }

    public static PrivilegedAction<String> getSystemProperty(final String name, final String def) {
        return new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(name, def);
            }
        };
    }

    public static <T> T getValue(Map<String, ?> properties, String key, T defaultValue, Map<String, String> legacyMap) {
        return PropertiesHelper.getValue(properties, null, key, defaultValue, legacyMap);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtimeType, String key, T defaultValue, Map<String, String> legacyMap) {
        return PropertiesHelper.getValue(properties, runtimeType, key, defaultValue, defaultValue.getClass(), legacyMap);
    }

    public static <T> T getValue(Map<String, ?> properties, String key, T defaultValue, Class<T> type, Map<String, String> legacyMap) {
        return PropertiesHelper.getValue(properties, null, key, defaultValue, type, legacyMap);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtimeType, String key, T defaultValue, Class<T> type, Map<String, String> legacyMap) {
        T value = PropertiesHelper.getValue(properties, runtimeType, key, type, legacyMap);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public static <T> T getValue(Map<String, ?> properties, String key, Class<T> type, Map<String, String> legacyMap) {
        return PropertiesHelper.getValue(properties, null, key, type, legacyMap);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtimeType, String key, Class<T> type, Map<String, String> legacyMap) {
        Object value = null;
        if (runtimeType != null) {
            String runtimeAwareKey = PropertiesHelper.getPropertyNameForRuntime(key, runtimeType);
            if (key.equals(runtimeAwareKey)) {
                runtimeAwareKey = key + "." + runtimeType.name().toLowerCase(Locale.ROOT);
            }
            value = properties.get(runtimeAwareKey);
        }
        if (value == null) {
            value = properties.get(key);
        }
        if (value == null) {
            value = PropertiesHelper.getLegacyFallbackValue(properties, legacyMap, key);
        }
        if (value == null) {
            return null;
        }
        return PropertiesHelper.convertValue(value, type);
    }

    public static String getPropertyNameForRuntime(String key, RuntimeType runtimeType) {
        if (runtimeType != null && key.startsWith("jersey.config")) {
            RuntimeType[] types;
            for (RuntimeType type : types = RuntimeType.values()) {
                if (!key.startsWith("jersey.config." + type.name().toLowerCase(Locale.ROOT))) continue;
                return key;
            }
            return key.replace("jersey.config", "jersey.config." + runtimeType.name().toLowerCase(Locale.ROOT));
        }
        return key;
    }

    private static Object getLegacyFallbackValue(Map<String, ?> properties, Map<String, String> legacyFallbackMap, String key) {
        if (legacyFallbackMap == null || !legacyFallbackMap.containsKey(key)) {
            return null;
        }
        String fallbackKey = legacyFallbackMap.get(key);
        Object value = properties.get(fallbackKey);
        if (value != null && LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.config(LocalizationMessages.PROPERTIES_HELPER_DEPRECATED_PROPERTY_NAME(fallbackKey, key));
        }
        return value;
    }

    public static <T> T convertValue(Object value, Class<T> type) {
        if (!type.isInstance(value)) {
            Method valueOf;
            Constructor constructor = AccessController.doPrivileged(ReflectionHelper.getStringConstructorPA(type));
            if (constructor != null) {
                try {
                    return type.cast(constructor.newInstance(value));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if ((valueOf = AccessController.doPrivileged(ReflectionHelper.getValueOfStringMethodPA(type))) != null) {
                try {
                    return type.cast(valueOf.invoke(null, value));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.warning(LocalizationMessages.PROPERTIES_HELPER_GET_VALUE_NO_TRANSFORM(String.valueOf(value), value.getClass().getName(), type.getName()));
            }
            return null;
        }
        return type.cast(value);
    }

    public static boolean isMetaInfServicesEnabled(Map<String, Object> properties, RuntimeType runtimeType) {
        boolean disableMetaInfServicesLookup = false;
        if (properties != null) {
            disableMetaInfServicesLookup = CommonProperties.getValue(properties, runtimeType, "jersey.config.disableMetainfServicesLookup", false, Boolean.class);
        }
        return !disableMetaInfServicesLookup;
    }

    public static boolean isProperty(Map<String, Object> properties, String name) {
        return properties.containsKey(name) && PropertiesHelper.isProperty(properties.get(name));
    }

    public static boolean isProperty(Object value) {
        if (value instanceof Boolean) {
            return (Boolean)Boolean.class.cast(value);
        }
        return value != null && Boolean.parseBoolean(value.toString());
    }

    private PropertiesHelper() {
    }
}

