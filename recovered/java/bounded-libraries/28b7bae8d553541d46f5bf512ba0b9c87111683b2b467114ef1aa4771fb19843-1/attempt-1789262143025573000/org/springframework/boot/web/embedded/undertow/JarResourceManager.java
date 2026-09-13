/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.UndertowMessages
 *  io.undertow.server.handlers.resource.Resource
 *  io.undertow.server.handlers.resource.ResourceChangeListener
 *  io.undertow.server.handlers.resource.ResourceManager
 *  io.undertow.server.handlers.resource.URLResource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.UndertowMessages;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceChangeListener;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.server.handlers.resource.URLResource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.util.StringUtils;

class JarResourceManager
implements ResourceManager {
    private final String jarPath;

    JarResourceManager(File jarFile) {
        try {
            this.jarPath = jarFile.getAbsoluteFile().toURI().toURL().toString();
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public Resource getResource(String path) throws IOException {
        URL url = new URL("jar:" + this.jarPath + "!" + (path.startsWith("/") ? path : "/" + path));
        URLResource resource = new URLResource(url, path);
        if (StringUtils.hasText((String)path) && !"/".equals(path) && resource.getContentLength() < 0L) {
            return null;
        }
        return resource;
    }

    public boolean isResourceChangeListenerSupported() {
        return false;
    }

    public void registerResourceChangeListener(ResourceChangeListener listener) {
        throw UndertowMessages.MESSAGES.resourceChangeListenerNotSupported();
    }

    public void removeResourceChangeListener(ResourceChangeListener listener) {
        throw UndertowMessages.MESSAGES.resourceChangeListenerNotSupported();
    }

    public void close() throws IOException {
    }
}

