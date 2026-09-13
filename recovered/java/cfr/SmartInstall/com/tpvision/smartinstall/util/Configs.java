/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.NetworkUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configs {
    private static final Logger LOG = LoggerFactory.getLogger(Configs.class);
    private static Map<String, String> props = null;
    private static final Map<String, String> DEFAULT_PROPS = new HashMap<String, String>();

    private Configs() {
    }

    private static void initDefaultProps() {
        DEFAULT_PROPS.put("mysql.ip", "localhost");
        DEFAULT_PROPS.put("mysql.port", "3306");
    }

    public static String getProperty(String key, String defaultValue) {
        String value = Configs.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public static String getProperty(String key) {
        String value;
        if (null == props) {
            Configs.loadProperties();
        }
        if (null == (value = props.get(key))) {
            value = Configs.getDefaultValue(key);
        }
        return value;
    }

    private static String getDefaultValue(String key) {
        String defaultval = DEFAULT_PROPS.get(key);
        if (null != defaultval) {
            props.put(key, defaultval);
            LOG.info("si_properties::check_default key={}, unknown, set default to:{}", (Object)key, (Object)defaultval);
        }
        return defaultval;
    }

    private static void loadProperties() {
        LOG.info("si_properties::loadproperties called ");
        props = new HashMap<String, String>();
        Properties properties = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("../config.properties");){
            if (stream != null) {
                properties.load(stream);
            }
            properties.forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(key, value) -> {
                String newValue = Configs.handlePlaceholder(properties, (String)value);
                props.put((String)key, newValue);
            }));
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        Configs.logProps();
    }

    private static String handlePlaceholder(Properties properties, String value) {
        Matcher m = Pattern.compile("\\$\\{([a-zA-Z.]*)\\}").matcher(value);
        LinkedHashMap<String, String> vars = new LinkedHashMap<String, String>();
        while (m.find()) {
            String key = m.group(1);
            vars.put(key, properties.getProperty(key));
        }
        String newValue = value;
        for (Map.Entry entry : vars.entrySet()) {
            newValue = newValue.replace("${" + (String)entry.getKey() + "}", (CharSequence)entry.getValue());
        }
        return newValue;
    }

    private static void logProps() {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            LOG.info("configs: {} = {}", (Object)entry.getKey(), (Object)entry.getValue());
        }
    }

    public static String getServerUrl(String clientIp) {
        String cmndIp = clientIp != null ? NetworkUtils.getServerIpFromSameRoute(clientIp) : Configs.getProperty("server.name", "localhost");
        return String.format(Locale.ENGLISH, "http://%s:%s/%s/", cmndIp, Configs.getProperty("server.port", "8080"), Configs.getProperty("server.context", "SmartInstall"));
    }

    public static int getProperty(String key, int defaultValue) {
        String value = Configs.getProperty(key, String.valueOf(defaultValue));
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    static {
        Configs.initDefaultProps();
    }
}

