/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.crossstore;

import java.util.Map;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;

public interface ChangeSet {
    @Nullable
    public <T> T get(String var1, Class<T> var2, ConversionService var3);

    public void set(String var1, Object var2);

    public Map<String, Object> getValues();

    @Nullable
    public Object removeProperty(String var1);
}

