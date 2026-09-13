/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.boot.convert;

import java.io.File;
import java.io.IOException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

class StringToFileConverter
implements Converter<String, File> {
    private static final ResourceLoader resourceLoader = new DefaultResourceLoader(null);

    StringToFileConverter() {
    }

    public File convert(String source) {
        if (ResourceUtils.isUrl((String)source)) {
            return this.getFile(resourceLoader.getResource(source));
        }
        File file = new File(source);
        if (file.exists()) {
            return file;
        }
        Resource resource = resourceLoader.getResource(source);
        if (resource.exists()) {
            return this.getFile(resource);
        }
        return file;
    }

    private File getFile(Resource resource) {
        try {
            return resource.getFile();
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not retrieve file for " + resource + ": " + ex.getMessage());
        }
    }
}

