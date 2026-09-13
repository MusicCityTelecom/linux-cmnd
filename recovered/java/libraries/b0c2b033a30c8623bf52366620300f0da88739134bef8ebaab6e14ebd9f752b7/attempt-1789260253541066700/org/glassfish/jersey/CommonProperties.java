/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.internal.util.PropertiesClass;
import org.glassfish.jersey.internal.util.PropertiesHelper;

@PropertiesClass
public final class CommonProperties {
    private static final Map<String, String> LEGACY_FALLBACK_MAP = new HashMap<String, String>();
    public static final String ALLOW_SYSTEM_PROPERTIES_PROVIDER = "jersey.config.allowSystemPropertiesProvider";
    public static final String FEATURE_AUTO_DISCOVERY_DISABLE = "jersey.config.disableAutoDiscovery";
    public static final String FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT = "jersey.config.client.disableAutoDiscovery";
    public static final String FEATURE_AUTO_DISCOVERY_DISABLE_SERVER = "jersey.config.server.disableAutoDiscovery";
    public static final String JSON_PROCESSING_FEATURE_DISABLE = "jersey.config.disableJsonProcessing";
    public static final String JSON_PROCESSING_FEATURE_DISABLE_CLIENT = "jersey.config.client.disableJsonProcessing";
    public static final String JSON_PROCESSING_FEATURE_DISABLE_SERVER = "jersey.config.server.disableJsonProcessing";
    public static final String METAINF_SERVICES_LOOKUP_DISABLE = "jersey.config.disableMetainfServicesLookup";
    public static final String METAINF_SERVICES_LOOKUP_DISABLE_CLIENT = "jersey.config.client.disableMetainfServicesLookup";
    public static final String METAINF_SERVICES_LOOKUP_DISABLE_SERVER = "jersey.config.server.disableMetainfServicesLookup";
    public static final String MOXY_JSON_FEATURE_DISABLE = "jersey.config.disableMoxyJson";
    public static final String MOXY_JSON_FEATURE_DISABLE_CLIENT = "jersey.config.client.disableMoxyJson";
    public static final String MOXY_JSON_FEATURE_DISABLE_SERVER = "jersey.config.server.disableMoxyJson";
    public static final String OUTBOUND_CONTENT_LENGTH_BUFFER = "jersey.config.contentLength.buffer";
    public static final String OUTBOUND_CONTENT_LENGTH_BUFFER_CLIENT = "jersey.config.client.contentLength.buffer";
    public static final String OUTBOUND_CONTENT_LENGTH_BUFFER_SERVER = "jersey.config.server.contentLength.buffer";
    public static final String PROVIDER_DEFAULT_DISABLE = "jersey.config.disableDefaultProvider";

    private CommonProperties() {
    }

    public static Object getValue(Map<String, ?> properties, String propertyName, Class<?> type) {
        return PropertiesHelper.getValue(properties, propertyName, type, LEGACY_FALLBACK_MAP);
    }

    public static <T> T getValue(Map<String, ?> properties, String propertyName, T defaultValue) {
        return PropertiesHelper.getValue(properties, propertyName, defaultValue, LEGACY_FALLBACK_MAP);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtime, String propertyName, T defaultValue) {
        return PropertiesHelper.getValue(properties, runtime, propertyName, defaultValue, LEGACY_FALLBACK_MAP);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtime, String propertyName, T defaultValue, Class<T> type) {
        return PropertiesHelper.getValue(properties, runtime, propertyName, defaultValue, type, LEGACY_FALLBACK_MAP);
    }

    public static <T> T getValue(Map<String, ?> properties, RuntimeType runtime, String propertyName, Class<T> type) {
        return PropertiesHelper.getValue(properties, runtime, propertyName, type, LEGACY_FALLBACK_MAP);
    }

    static {
        LEGACY_FALLBACK_MAP.put(OUTBOUND_CONTENT_LENGTH_BUFFER_CLIENT, "jersey.config.contentLength.buffer.client");
        LEGACY_FALLBACK_MAP.put(OUTBOUND_CONTENT_LENGTH_BUFFER_SERVER, "jersey.config.contentLength.buffer.server");
        LEGACY_FALLBACK_MAP.put(FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, "jersey.config.disableAutoDiscovery.client");
        LEGACY_FALLBACK_MAP.put(FEATURE_AUTO_DISCOVERY_DISABLE_SERVER, "jersey.config.disableAutoDiscovery.server");
        LEGACY_FALLBACK_MAP.put(JSON_PROCESSING_FEATURE_DISABLE_CLIENT, "jersey.config.disableJsonProcessing.client");
        LEGACY_FALLBACK_MAP.put(JSON_PROCESSING_FEATURE_DISABLE_SERVER, "jersey.config.disableJsonProcessing.server");
        LEGACY_FALLBACK_MAP.put(METAINF_SERVICES_LOOKUP_DISABLE_CLIENT, "jersey.config.disableMetainfServicesLookup.client");
        LEGACY_FALLBACK_MAP.put(METAINF_SERVICES_LOOKUP_DISABLE_SERVER, "jersey.config.disableMetainfServicesLookup.server");
        LEGACY_FALLBACK_MAP.put(MOXY_JSON_FEATURE_DISABLE_CLIENT, "jersey.config.disableMoxyJson.client");
        LEGACY_FALLBACK_MAP.put(MOXY_JSON_FEATURE_DISABLE_SERVER, "jersey.config.disableMoxyJson.server");
    }
}

