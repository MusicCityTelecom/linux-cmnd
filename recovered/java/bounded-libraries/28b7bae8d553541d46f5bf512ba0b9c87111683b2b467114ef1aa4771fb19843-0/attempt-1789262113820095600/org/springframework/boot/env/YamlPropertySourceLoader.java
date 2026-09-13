/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.env;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.OriginTrackedYamlLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

public class YamlPropertySourceLoader
implements PropertySourceLoader {
    @Override
    public String[] getFileExtensions() {
        return new String[]{"yml", "yaml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        if (!ClassUtils.isPresent((String)"org.yaml.snakeyaml.Yaml", (ClassLoader)this.getClass().getClassLoader())) {
            throw new IllegalStateException("Attempted to load " + name + " but snakeyaml was not found on the classpath");
        }
        List<Map<String, Object>> loaded = new OriginTrackedYamlLoader(resource).load();
        if (loaded.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList propertySources = new ArrayList(loaded.size());
        for (int i = 0; i < loaded.size(); ++i) {
            String documentNumber = loaded.size() != 1 ? " (document #" + i + ")" : "";
            propertySources.add((PropertySource<?>)new OriginTrackedMapPropertySource(name + documentNumber, Collections.unmodifiableMap(loaded.get(i)), true));
        }
        return propertySources;
    }
}

