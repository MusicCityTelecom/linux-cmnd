/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.m12n;

import groovy.lang.MetaMethod;
import java.util.List;

public abstract class ExtensionModule {
    private final String name;
    private final String version;

    public ExtensionModule(String moduleName, String moduleVersion) {
        this.name = moduleName;
        this.version = moduleVersion;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public abstract List<MetaMethod> getMetaMethods();

    public String toString() {
        String sb = "ExtensionModule{name='" + this.name + '\'' + ", version='" + this.version + '\'' + '}';
        return sb;
    }
}

