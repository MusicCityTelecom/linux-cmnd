/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure;

import java.util.Set;

public interface AutoConfigurationMetadata {
    public boolean wasProcessed(String var1);

    public Integer getInteger(String var1, String var2);

    public Integer getInteger(String var1, String var2, Integer var3);

    public Set<String> getSet(String var1, String var2);

    public Set<String> getSet(String var1, String var2, Set<String> var3);

    public String get(String var1, String var2);

    public String get(String var1, String var2, String var3);
}

