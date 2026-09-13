/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.UndertowMessages
 *  io.undertow.server.handlers.resource.Resource
 *  io.undertow.server.handlers.resource.ResourceChangeListener
 *  io.undertow.server.handlers.resource.ResourceManager
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.UndertowMessages;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceChangeListener;
import io.undertow.server.handlers.resource.ResourceManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class CompositeResourceManager
implements ResourceManager {
    private final List<ResourceManager> resourceManagers;

    CompositeResourceManager(ResourceManager ... resourceManagers) {
        this.resourceManagers = Arrays.asList(resourceManagers);
    }

    public void close() throws IOException {
        for (ResourceManager resourceManager : this.resourceManagers) {
            resourceManager.close();
        }
    }

    public Resource getResource(String path) throws IOException {
        for (ResourceManager resourceManager : this.resourceManagers) {
            Resource resource = resourceManager.getResource(path);
            if (resource == null) continue;
            return resource;
        }
        return null;
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
}

