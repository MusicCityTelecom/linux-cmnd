/*
 * Decompiled with CFR 0.152.
 */
package javax.servlet;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public abstract class GenericFilter
implements Filter,
FilterConfig,
Serializable {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.LocalStrings");
    private transient FilterConfig config;

    @Override
    public String getInitParameter(String name) {
        FilterConfig fc = this.getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }
        return fc.getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        FilterConfig fc = this.getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }
        return fc.getInitParameterNames();
    }

    public FilterConfig getFilterConfig() {
        return this.config;
    }

    @Override
    public ServletContext getServletContext() {
        FilterConfig sc = this.getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }
        return sc.getServletContext();
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
    }

    @Override
    public String getFilterName() {
        FilterConfig sc = this.getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getFilterName();
    }
}

