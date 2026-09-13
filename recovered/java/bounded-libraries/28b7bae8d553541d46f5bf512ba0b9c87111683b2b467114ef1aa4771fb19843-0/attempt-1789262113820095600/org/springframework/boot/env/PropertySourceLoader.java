/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.env;

import java.io.IOException;
import java.util.List;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public interface PropertySourceLoader {
    public String[] getFileExtensions();

    public List<PropertySource<?>> load(String var1, Resource var2) throws IOException;
}

