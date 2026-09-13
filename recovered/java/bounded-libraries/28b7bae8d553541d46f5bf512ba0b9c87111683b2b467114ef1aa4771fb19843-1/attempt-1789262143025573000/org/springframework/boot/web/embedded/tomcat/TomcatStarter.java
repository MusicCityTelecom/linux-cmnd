/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContainerInitializer
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.boot.web.embedded.tomcat;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;

class TomcatStarter
implements ServletContainerInitializer {
    private static final Log logger = LogFactory.getLog(TomcatStarter.class);
    private final ServletContextInitializer[] initializers;
    private volatile Exception startUpException;

    TomcatStarter(ServletContextInitializer[] initializers) {
        this.initializers = initializers;
    }

    public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {
        block3: {
            try {
                for (ServletContextInitializer initializer : this.initializers) {
                    initializer.onStartup(servletContext);
                }
            }
            catch (Exception ex) {
                this.startUpException = ex;
                if (!logger.isErrorEnabled()) break block3;
                logger.error((Object)("Error starting Tomcat context. Exception: " + ex.getClass().getName() + ". Message: " + ex.getMessage()));
            }
        }
    }

    Exception getStartUpException() {
        return this.startUpException;
    }
}

