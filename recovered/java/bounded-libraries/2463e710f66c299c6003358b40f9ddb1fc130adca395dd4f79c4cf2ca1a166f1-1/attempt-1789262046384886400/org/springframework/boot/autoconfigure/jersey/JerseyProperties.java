/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.jersey;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.jersey")
public class JerseyProperties {
    private Type type = Type.SERVLET;
    private Map<String, String> init = new HashMap<String, String>();
    private final Filter filter = new Filter();
    private final Servlet servlet = new Servlet();
    private String applicationPath;

    public Filter getFilter() {
        return this.filter;
    }

    public Servlet getServlet() {
        return this.servlet;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, String> getInit() {
        return this.init;
    }

    public void setInit(Map<String, String> init) {
        this.init = init;
    }

    public String getApplicationPath() {
        return this.applicationPath;
    }

    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public static class Servlet {
        private int loadOnStartup = -1;

        public int getLoadOnStartup() {
            return this.loadOnStartup;
        }

        public void setLoadOnStartup(int loadOnStartup) {
            this.loadOnStartup = loadOnStartup;
        }
    }

    public static class Filter {
        private int order;

        public int getOrder() {
            return this.order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    public static enum Type {
        SERVLET,
        FILTER;

    }
}

