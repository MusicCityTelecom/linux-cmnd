/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.h2.server.web.WebServlet
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.web.servlet.ServletRegistrationBean
 *  org.springframework.context.annotation.Bean
 */
package org.springframework.boot.autoconfigure.h2;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after={DataSourceAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={WebServlet.class})
@ConditionalOnProperty(prefix="spring.h2.console", name={"enabled"}, havingValue="true")
@EnableConfigurationProperties(value={H2ConsoleProperties.class})
public class H2ConsoleAutoConfiguration {
    private static final Log logger = LogFactory.getLog(H2ConsoleAutoConfiguration.class);

    @Bean
    public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties, ObjectProvider<DataSource> dataSource) {
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean registration = new ServletRegistrationBean((Servlet)new WebServlet(), new String[]{urlMapping});
        this.configureH2ConsoleSettings((ServletRegistrationBean<WebServlet>)registration, properties.getSettings());
        if (logger.isInfoEnabled()) {
            this.logDataSources(dataSource, path);
        }
        return registration;
    }

    private void logDataSources(ObjectProvider<DataSource> dataSource, String path) {
        List urls = dataSource.orderedStream().map(available -> {
            try (Connection connection = available.getConnection();){
                String string = "'" + connection.getMetaData().getURL() + "'";
                return string;
            }
            catch (Exception ex) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (!urls.isEmpty()) {
            StringBuilder sb = new StringBuilder("H2 console available at '").append(path).append("'. ");
            String tmp = urls.size() > 1 ? "Databases" : "Database";
            sb.append(tmp).append(" available at ");
            sb.append(String.join((CharSequence)", ", urls));
            logger.info((Object)sb.toString());
        }
    }

    private void configureH2ConsoleSettings(ServletRegistrationBean<WebServlet> registration, H2ConsoleProperties.Settings settings) {
        if (settings.isTrace()) {
            registration.addInitParameter("trace", "");
        }
        if (settings.isWebAllowOthers()) {
            registration.addInitParameter("webAllowOthers", "");
        }
        if (settings.getWebAdminPassword() != null) {
            registration.addInitParameter("webAdminPassword", settings.getWebAdminPassword());
        }
    }
}

