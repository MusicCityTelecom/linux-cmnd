/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRegistration$Dynamic
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.web.servlet.ServletContextInitializer
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.web.EndpointServlet;
import org.springframework.boot.actuate.endpoint.web.ExposableServletEndpoint;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class ServletEndpointRegistrar
implements ServletContextInitializer {
    private static final Log logger = LogFactory.getLog(ServletEndpointRegistrar.class);
    private final String basePath;
    private final Collection<ExposableServletEndpoint> servletEndpoints;

    public ServletEndpointRegistrar(String basePath, Collection<ExposableServletEndpoint> servletEndpoints) {
        Assert.notNull(servletEndpoints, (String)"ServletEndpoints must not be null");
        this.basePath = ServletEndpointRegistrar.cleanBasePath(basePath);
        this.servletEndpoints = servletEndpoints;
    }

    private static String cleanBasePath(String basePath) {
        if (StringUtils.hasText((String)basePath) && basePath.endsWith("/")) {
            return basePath.substring(0, basePath.length() - 1);
        }
        return basePath != null ? basePath : "";
    }

    public void onStartup(ServletContext servletContext) throws ServletException {
        this.servletEndpoints.forEach(servletEndpoint -> this.register(servletContext, (ExposableServletEndpoint)servletEndpoint));
    }

    private void register(ServletContext servletContext, ExposableServletEndpoint endpoint) {
        String name = endpoint.getEndpointId().toLowerCaseString() + "-actuator-endpoint";
        String path = this.basePath + "/" + endpoint.getRootPath();
        String urlMapping = path.endsWith("/") ? path + "*" : path + "/*";
        EndpointServlet endpointServlet = endpoint.getEndpointServlet();
        ServletRegistration.Dynamic registration = servletContext.addServlet(name, endpointServlet.getServlet());
        registration.addMapping(new String[]{urlMapping});
        registration.setInitParameters(endpointServlet.getInitParameters());
        registration.setLoadOnStartup(endpointServlet.getLoadOnStartup());
        logger.info((Object)("Registered '" + path + "' to " + name));
    }
}

