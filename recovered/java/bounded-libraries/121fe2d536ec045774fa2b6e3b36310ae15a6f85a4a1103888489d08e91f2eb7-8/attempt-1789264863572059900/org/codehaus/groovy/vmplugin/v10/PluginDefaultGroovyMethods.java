/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v10;

import java.lang.management.ManagementFactory;

public class PluginDefaultGroovyMethods {
    public static String getPid(Runtime self) {
        return String.valueOf(ManagementFactory.getRuntimeMXBean().getPid());
    }
}

