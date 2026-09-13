/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 */
package org.springframework.boot.env;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.OriginTrackedPropertiesLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesPropertySourceLoader
implements PropertySourceLoader {
    private static final String XML_FILE_EXTENSION = ".xml";

    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties", "xml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        List<Map<String, ?>> properties = this.loadProperties(resource);
        if (properties.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList propertySources = new ArrayList(properties.size());
        for (int i = 0; i < properties.size(); ++i) {
            String documentNumber = properties.size() != 1 ? " (document #" + i + ")" : "";
            propertySources.add((PropertySource<?>)new OriginTrackedMapPropertySource(name + documentNumber, Collections.unmodifiableMap(properties.get(i)), true));
        }
        return propertySources;
    }

    private List<Map<String, ?>> loadProperties(Resource resource) throws IOException {
        String filename = resource.getFilename();
        ArrayList result = new ArrayList();
        if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
            result.add(PropertiesLoaderUtils.loadProperties((Resource)resource));
        } else {
            List<OriginTrackedPropertiesLoader.Document> documents = new OriginTrackedPropertiesLoader(resource).load();
            documents.forEach(document -> result.add(document.asMap()));
        }
        return result;
    }
}

