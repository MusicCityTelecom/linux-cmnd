/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.logging;

import org.springframework.boot.logging.LogLevel;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public final class LoggerConfiguration {
    private final String name;
    private final LogLevel configuredLevel;
    private final LogLevel effectiveLevel;

    public LoggerConfiguration(String name, LogLevel configuredLevel, LogLevel effectiveLevel) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        Assert.notNull((Object)((Object)effectiveLevel), (String)"EffectiveLevel must not be null");
        this.name = name;
        this.configuredLevel = configuredLevel;
        this.effectiveLevel = effectiveLevel;
    }

    public LogLevel getConfiguredLevel() {
        return this.configuredLevel;
    }

    public LogLevel getEffectiveLevel() {
        return this.effectiveLevel;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof LoggerConfiguration) {
            LoggerConfiguration other = (LoggerConfiguration)obj;
            boolean rtn = true;
            rtn = rtn && ObjectUtils.nullSafeEquals((Object)this.name, (Object)other.name);
            rtn = rtn && ObjectUtils.nullSafeEquals((Object)((Object)this.configuredLevel), (Object)((Object)other.configuredLevel));
            rtn = rtn && ObjectUtils.nullSafeEquals((Object)((Object)this.effectiveLevel), (Object)((Object)other.effectiveLevel));
            return rtn;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.name);
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)((Object)this.configuredLevel));
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)((Object)this.effectiveLevel));
        return result;
    }

    public String toString() {
        return "LoggerConfiguration [name=" + this.name + ", configuredLevel=" + (Object)((Object)this.configuredLevel) + ", effectiveLevel=" + (Object)((Object)this.effectiveLevel) + "]";
    }
}

