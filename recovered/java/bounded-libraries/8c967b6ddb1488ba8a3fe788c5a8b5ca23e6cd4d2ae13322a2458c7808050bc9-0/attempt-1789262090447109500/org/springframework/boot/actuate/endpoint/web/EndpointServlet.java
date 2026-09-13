/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.springframework.beans.BeanUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Servlet;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class EndpointServlet {
    private final Servlet servlet;
    private final Map<String, String> initParameters;
    private final int loadOnStartup;

    public EndpointServlet(Class<? extends Servlet> servlet) {
        this(EndpointServlet.instantiateClass(servlet));
    }

    private static Servlet instantiateClass(Class<? extends Servlet> servlet) {
        Assert.notNull(servlet, (String)"Servlet must not be null");
        return (Servlet)BeanUtils.instantiateClass(servlet);
    }

    public EndpointServlet(Servlet servlet) {
        this(servlet, Collections.emptyMap(), -1);
    }

    private EndpointServlet(Servlet servlet, Map<String, String> initParameters, int loadOnStartup) {
        Assert.notNull((Object)servlet, (String)"Servlet must not be null");
        this.servlet = servlet;
        this.initParameters = Collections.unmodifiableMap(initParameters);
        this.loadOnStartup = loadOnStartup;
    }

    public EndpointServlet withInitParameter(String name, String value) {
        Assert.hasText((String)name, (String)"Name must not be empty");
        return this.withInitParameters(Collections.singletonMap(name, value));
    }

    public EndpointServlet withInitParameters(Map<String, String> initParameters) {
        Assert.notNull(initParameters, (String)"InitParameters must not be null");
        boolean hasEmptyName = initParameters.keySet().stream().anyMatch(name -> !StringUtils.hasText((String)name));
        Assert.isTrue((!hasEmptyName ? 1 : 0) != 0, (String)"InitParameters must not contain empty names");
        LinkedHashMap<String, String> mergedInitParameters = new LinkedHashMap<String, String>(this.initParameters);
        mergedInitParameters.putAll(initParameters);
        return new EndpointServlet(this.servlet, mergedInitParameters, this.loadOnStartup);
    }

    public EndpointServlet withLoadOnStartup(int loadOnStartup) {
        return new EndpointServlet(this.servlet, this.initParameters, loadOnStartup);
    }

    Servlet getServlet() {
        return this.servlet;
    }

    Map<String, String> getInitParameters() {
        return this.initParameters;
    }

    int getLoadOnStartup() {
        return this.loadOnStartup;
    }
}

