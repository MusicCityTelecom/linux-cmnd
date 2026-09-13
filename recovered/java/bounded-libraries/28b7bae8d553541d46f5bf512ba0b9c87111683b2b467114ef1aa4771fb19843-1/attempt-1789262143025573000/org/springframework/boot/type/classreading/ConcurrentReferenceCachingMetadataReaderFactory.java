/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.core.type.classreading.SimpleMetadataReaderFactory
 *  org.springframework.util.ConcurrentReferenceHashMap
 */
package org.springframework.boot.type.classreading;

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

public class ConcurrentReferenceCachingMetadataReaderFactory
extends SimpleMetadataReaderFactory {
    private final Map<Resource, MetadataReader> cache = new ConcurrentReferenceHashMap();

    public ConcurrentReferenceCachingMetadataReaderFactory() {
    }

    public ConcurrentReferenceCachingMetadataReaderFactory(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    public ConcurrentReferenceCachingMetadataReaderFactory(ClassLoader classLoader) {
        super(classLoader);
    }

    public MetadataReader getMetadataReader(Resource resource) throws IOException {
        MetadataReader metadataReader = this.cache.get(resource);
        if (metadataReader == null) {
            metadataReader = this.createMetadataReader(resource);
            this.cache.put(resource, metadataReader);
        }
        return metadataReader;
    }

    protected MetadataReader createMetadataReader(Resource resource) throws IOException {
        return super.getMetadataReader(resource);
    }

    public void clearCache() {
        this.cache.clear();
    }
}

