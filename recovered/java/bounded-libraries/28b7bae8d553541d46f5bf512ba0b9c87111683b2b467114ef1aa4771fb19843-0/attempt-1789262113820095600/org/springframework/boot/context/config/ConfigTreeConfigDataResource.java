/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.util.Assert;

public class ConfigTreeConfigDataResource
extends ConfigDataResource {
    private final Path path;

    ConfigTreeConfigDataResource(String path) {
        Assert.notNull((Object)path, (String)"Path must not be null");
        this.path = Paths.get(path, new String[0]).toAbsolutePath();
    }

    ConfigTreeConfigDataResource(Path path) {
        Assert.notNull((Object)path, (String)"Path must not be null");
        this.path = path.toAbsolutePath();
    }

    Path getPath() {
        return this.path;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ConfigTreeConfigDataResource other = (ConfigTreeConfigDataResource)obj;
        return Objects.equals(this.path, other.path);
    }

    public int hashCode() {
        return this.path.hashCode();
    }

    public String toString() {
        return "config tree [" + this.path + "]";
    }
}

