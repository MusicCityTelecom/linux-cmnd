/*
 * Decompiled with CFR 0.152.
 */
package org.aspectj.weaver.tools;

import org.aspectj.weaver.tools.DefaultTraceFactory;
import org.aspectj.weaver.tools.Trace;

public abstract class TraceFactory {
    public static final String DEBUG_PROPERTY = "org.aspectj.tracing.debug";
    public static final String FACTORY_PROPERTY = "org.aspectj.tracing.factory";
    public static final String DEFAULT_FACTORY_NAME = "default";
    protected static boolean debug;
    private static TraceFactory instance;

    public Trace getTrace(Class clazz) {
        return instance.getTrace(clazz);
    }

    public static TraceFactory getTraceFactory() {
        return instance;
    }

    protected static boolean getBoolean(String name, boolean def) {
        String defaultValue = String.valueOf(def);
        String value = System.getProperty(name, defaultValue);
        return Boolean.parseBoolean(value);
    }

    static {
        block11: {
            Class<?> factoryClass;
            block10: {
                debug = TraceFactory.getBoolean(DEBUG_PROPERTY, false);
                String factoryName = System.getProperty(FACTORY_PROPERTY);
                if (factoryName != null) {
                    try {
                        if (factoryName.equals(DEFAULT_FACTORY_NAME)) {
                            instance = new DefaultTraceFactory();
                        } else {
                            factoryClass = Class.forName(factoryName);
                            instance = (TraceFactory)factoryClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                        }
                    }
                    catch (Throwable th) {
                        if (!debug) break block10;
                        th.printStackTrace();
                    }
                }
            }
            if (instance == null) {
                try {
                    factoryClass = Class.forName("org.aspectj.weaver.tools.Jdk14TraceFactory");
                    instance = (TraceFactory)factoryClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Throwable th) {
                    if (!debug) break block11;
                    th.printStackTrace();
                }
            }
        }
        if (instance == null) {
            instance = new DefaultTraceFactory();
        }
        if (debug) {
            System.err.println("TraceFactory.instance=" + instance);
        }
    }
}

