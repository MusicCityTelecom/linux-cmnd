/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Registration$Dynamic
 *  javax.servlet.ServletContext
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.Conventions
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.servlet;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Registration;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.core.Conventions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class DynamicRegistrationBean<D extends Registration.Dynamic>
extends RegistrationBean {
    private static final Log logger = LogFactory.getLog(RegistrationBean.class);
    private String name;
    private boolean asyncSupported = true;
    private Map<String, String> initParameters = new LinkedHashMap<String, String>();

    public void setName(String name) {
        Assert.hasLength((String)name, (String)"Name must not be empty");
        this.name = name;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    public boolean isAsyncSupported() {
        return this.asyncSupported;
    }

    public void setInitParameters(Map<String, String> initParameters) {
        Assert.notNull(initParameters, (String)"InitParameters must not be null");
        this.initParameters = new LinkedHashMap<String, String>(initParameters);
    }

    public Map<String, String> getInitParameters() {
        return this.initParameters;
    }

    public void addInitParameter(String name, String value) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        this.initParameters.put(name, value);
    }

    @Override
    protected final void register(String description, ServletContext servletContext) {
        D registration = this.addRegistration(description, servletContext);
        if (registration == null) {
            logger.info((Object)(StringUtils.capitalize((String)description) + " was not registered (possibly already registered?)"));
            return;
        }
        this.configure(registration);
    }

    protected abstract D addRegistration(String var1, ServletContext var2);

    protected void configure(D registration) {
        registration.setAsyncSupported(this.asyncSupported);
        if (!this.initParameters.isEmpty()) {
            registration.setInitParameters(this.initParameters);
        }
    }

    protected final String getOrDeduceName(Object value) {
        return this.name != null ? this.name : Conventions.getVariableName((Object)value);
    }
}

