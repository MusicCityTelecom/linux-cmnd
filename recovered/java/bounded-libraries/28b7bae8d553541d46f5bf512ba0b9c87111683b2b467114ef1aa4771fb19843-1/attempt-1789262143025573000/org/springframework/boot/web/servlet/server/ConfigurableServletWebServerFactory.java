/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.WebListenerRegistry;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Session;

public interface ConfigurableServletWebServerFactory
extends ConfigurableWebServerFactory,
ServletWebServerFactory,
WebListenerRegistry {
    public void setContextPath(String var1);

    public void setDisplayName(String var1);

    public void setSession(Session var1);

    public void setRegisterDefaultServlet(boolean var1);

    public void setMimeMappings(MimeMappings var1);

    public void setDocumentRoot(File var1);

    public void setInitializers(List<? extends ServletContextInitializer> var1);

    public void addInitializers(ServletContextInitializer ... var1);

    public void setJsp(Jsp var1);

    public void setLocaleCharsetMappings(Map<Locale, Charset> var1);

    public void setInitParameters(Map<String, String> var1);

    public void setCookieSameSiteSuppliers(List<? extends CookieSameSiteSupplier> var1);

    public void addCookieSameSiteSuppliers(CookieSameSiteSupplier ... var1);
}

