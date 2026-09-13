/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.FileUrlResource
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.StandardConfigDataReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class StandardConfigDataResource
extends ConfigDataResource {
    private final StandardConfigDataReference reference;
    private final Resource resource;
    private final boolean emptyDirectory;

    StandardConfigDataResource(StandardConfigDataReference reference, Resource resource) {
        this(reference, resource, false);
    }

    StandardConfigDataResource(StandardConfigDataReference reference, Resource resource, boolean emptyDirectory) {
        Assert.notNull((Object)reference, (String)"Reference must not be null");
        Assert.notNull((Object)resource, (String)"Resource must not be null");
        this.reference = reference;
        this.resource = resource;
        this.emptyDirectory = emptyDirectory;
    }

    StandardConfigDataReference getReference() {
        return this.reference;
    }

    public Resource getResource() {
        return this.resource;
    }

    public String getProfile() {
        return this.reference.getProfile();
    }

    boolean isEmptyDirectory() {
        return this.emptyDirectory;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        StandardConfigDataResource other = (StandardConfigDataResource)obj;
        return this.resource.equals(other.resource) && this.emptyDirectory == other.emptyDirectory;
    }

    public int hashCode() {
        return this.resource.hashCode();
    }

    public String toString() {
        if (this.resource instanceof FileSystemResource || this.resource instanceof FileUrlResource) {
            try {
                return "file [" + this.resource.getFile().toString() + "]";
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.resource.toString();
    }
}

