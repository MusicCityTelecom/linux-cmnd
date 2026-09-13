/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.util.BiConsumer
 *  org.apache.logging.log4j.util.PropertySource
 *  org.apache.logging.log4j.util.PropertySource$Util
 */
package org.springframework.boot.logging.log4j2;

import java.util.Collections;
import java.util.Map;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.PropertySource;

public class SpringBootPropertySource
implements PropertySource {
    private static final String PREFIX = "log4j.";
    private final Map<String, String> properties = Collections.singletonMap("log4j.shutdownHookEnabled", "false");

    public void forEach(BiConsumer<String, String> action) {
        this.properties.forEach((arg_0, arg_1) -> action.accept(arg_0, arg_1));
    }

    public CharSequence getNormalForm(Iterable<? extends CharSequence> tokens) {
        return PREFIX + PropertySource.Util.joinAsCamelCase(tokens);
    }

    public int getPriority() {
        return -200;
    }

    public String getProperty(String key) {
        return this.properties.get(key);
    }

    public boolean containsProperty(String key) {
        return this.properties.containsKey(key);
    }
}

