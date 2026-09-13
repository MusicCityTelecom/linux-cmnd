/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.Ordered
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

public abstract class RegistrationBean
implements ServletContextInitializer,
Ordered {
    private static final Log logger = LogFactory.getLog(RegistrationBean.class);
    private int order = Integer.MAX_VALUE;
    private boolean enabled = true;

    @Override
    public final void onStartup(ServletContext servletContext) throws ServletException {
        String description = this.getDescription();
        if (!this.isEnabled()) {
            logger.info((Object)(StringUtils.capitalize((String)description) + " was not registered (disabled)"));
            return;
        }
        this.register(description, servletContext);
    }

    protected abstract String getDescription();

    protected abstract void register(String var1, ServletContext var2);

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }
}

