/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.eclipse.jetty.servlet.ServletHandler
 *  org.eclipse.jetty.webapp.WebAppContext
 */
package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

class JettyEmbeddedWebAppContext
extends WebAppContext {
    JettyEmbeddedWebAppContext() {
    }

    protected ServletHandler newServletHandler() {
        return new JettyEmbeddedServletHandler();
    }

    void deferredInitialize() throws Exception {
        ((JettyEmbeddedServletHandler)this.getServletHandler()).deferredInitialize();
    }

    private static class JettyEmbeddedServletHandler
    extends ServletHandler {
        private JettyEmbeddedServletHandler() {
        }

        public void initialize() throws Exception {
        }

        void deferredInitialize() throws Exception {
            super.initialize();
        }
    }
}

