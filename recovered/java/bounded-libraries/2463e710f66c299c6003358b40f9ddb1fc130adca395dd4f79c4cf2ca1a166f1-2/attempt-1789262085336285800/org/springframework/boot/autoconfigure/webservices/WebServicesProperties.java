/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.webservices;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

@ConfigurationProperties(prefix="spring.webservices")
public class WebServicesProperties {
    private String path = "/services";
    private final Servlet servlet = new Servlet();

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        Assert.notNull((Object)path, (String)"Path must not be null");
        Assert.isTrue((path.length() > 1 ? 1 : 0) != 0, (String)"Path must have length greater than 1");
        Assert.isTrue((boolean)path.startsWith("/"), (String)"Path must start with '/'");
        this.path = path;
    }

    public Servlet getServlet() {
        return this.servlet;
    }

    public static class Servlet {
        private Map<String, String> init = new HashMap<String, String>();
        private int loadOnStartup = -1;

        public Map<String, String> getInit() {
            return this.init;
        }

        public void setInit(Map<String, String> init) {
            this.init = init;
        }

        public int getLoadOnStartup() {
            return this.loadOnStartup;
        }

        public void setLoadOnStartup(int loadOnStartup) {
            this.loadOnStartup = loadOnStartup;
        }
    }
}

