/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.WritableResource
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.origin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class OriginTrackedResource
implements Resource,
OriginProvider {
    private final Resource resource;
    private final Origin origin;

    OriginTrackedResource(Resource resource, Origin origin) {
        Assert.notNull((Object)resource, (String)"Resource must not be null");
        this.resource = resource;
        this.origin = origin;
    }

    public InputStream getInputStream() throws IOException {
        return this.getResource().getInputStream();
    }

    public boolean exists() {
        return this.getResource().exists();
    }

    public boolean isReadable() {
        return this.getResource().isReadable();
    }

    public boolean isOpen() {
        return this.getResource().isOpen();
    }

    public boolean isFile() {
        return this.getResource().isFile();
    }

    public URL getURL() throws IOException {
        return this.getResource().getURL();
    }

    public URI getURI() throws IOException {
        return this.getResource().getURI();
    }

    public File getFile() throws IOException {
        return this.getResource().getFile();
    }

    public ReadableByteChannel readableChannel() throws IOException {
        return this.getResource().readableChannel();
    }

    public long contentLength() throws IOException {
        return this.getResource().contentLength();
    }

    public long lastModified() throws IOException {
        return this.getResource().lastModified();
    }

    public Resource createRelative(String relativePath) throws IOException {
        return this.getResource().createRelative(relativePath);
    }

    public String getFilename() {
        return this.getResource().getFilename();
    }

    public String getDescription() {
        return this.getResource().getDescription();
    }

    public Resource getResource() {
        return this.resource;
    }

    @Override
    public Origin getOrigin() {
        return this.origin;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        OriginTrackedResource other = (OriginTrackedResource)obj;
        return this.resource.equals(other) && ObjectUtils.nullSafeEquals((Object)this.origin, (Object)other.origin);
    }

    public int hashCode() {
        int prime = 31;
        int result = this.resource.hashCode();
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.origin);
        return result;
    }

    public String toString() {
        return this.resource.toString();
    }

    public static OriginTrackedWritableResource of(WritableResource resource, Origin origin) {
        return (OriginTrackedWritableResource)OriginTrackedResource.of((Resource)resource, origin);
    }

    public static OriginTrackedResource of(Resource resource, Origin origin) {
        if (resource instanceof WritableResource) {
            return new OriginTrackedWritableResource((WritableResource)resource, origin);
        }
        return new OriginTrackedResource(resource, origin);
    }

    public static class OriginTrackedWritableResource
    extends OriginTrackedResource
    implements WritableResource {
        OriginTrackedWritableResource(WritableResource resource, Origin origin) {
            super((Resource)resource, origin);
        }

        public WritableResource getResource() {
            return (WritableResource)super.getResource();
        }

        public OutputStream getOutputStream() throws IOException {
            return this.getResource().getOutputStream();
        }
    }
}

