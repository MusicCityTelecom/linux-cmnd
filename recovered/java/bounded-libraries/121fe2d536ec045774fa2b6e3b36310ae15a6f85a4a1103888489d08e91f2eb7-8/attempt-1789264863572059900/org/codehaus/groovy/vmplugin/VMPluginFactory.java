/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin;

import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.vmplugin.VMPlugin;

public class VMPluginFactory {
    private static final Logger LOGGER = Logger.getLogger(VMPluginFactory.class.getName());
    private static final Map<BigDecimal, String> PLUGIN_MAP = Maps.of(new BigDecimal("16"), "org.codehaus.groovy.vmplugin.v16.Java16", new BigDecimal("10"), "org.codehaus.groovy.vmplugin.v10.Java10", new BigDecimal("9"), "org.codehaus.groovy.vmplugin.v9.Java9", new BigDecimal("1.8"), "org.codehaus.groovy.vmplugin.v8.Java8");
    private static final VMPlugin PLUGIN = VMPluginFactory.createPlugin();

    public static VMPlugin getPlugin() {
        return PLUGIN;
    }

    private static <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    private static VMPlugin createPlugin() {
        return VMPluginFactory.doPrivileged(() -> {
            BigDecimal specVer = new BigDecimal(VMPlugin.getJavaVersion());
            ClassLoader loader = VMPluginFactory.class.getClassLoader();
            for (Map.Entry<BigDecimal, String> entry : PLUGIN_MAP.entrySet()) {
                if (!DefaultGroovyMethods.isAtLeast(specVer, entry.getKey()).booleanValue()) continue;
                String pluginName = entry.getValue();
                try {
                    return (VMPlugin)loader.loadClass(pluginName).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Throwable t) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.fine("Trying to create VM plugin `" + pluginName + "`, but failed:\n" + DefaultGroovyMethods.asString(t));
                    }
                    return null;
                }
            }
            return null;
        });
    }
}

