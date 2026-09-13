/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class FilterHolder
extends Holder<Filter> {
    private static final Logger LOG = Log.getLogger(FilterHolder.class);
    private transient Filter _filter;
    private transient Config _config;
    private transient FilterRegistration.Dynamic _registration;

    public FilterHolder() {
        this(Source.EMBEDDED);
    }

    public FilterHolder(Source source) {
        super(source);
    }

    public FilterHolder(Class<? extends Filter> filter) {
        this(Source.EMBEDDED);
        this.setHeldClass(filter);
    }

    public FilterHolder(Filter filter) {
        this(Source.EMBEDDED);
        this.setFilter(filter);
    }

    @Override
    public void doStart() throws Exception {
        super.doStart();
        if (!Filter.class.isAssignableFrom(this.getHeldClass())) {
            String msg = this.getHeldClass() + " is not a javax.servlet.Filter";
            this.doStop();
            throw new IllegalStateException(msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initialize() throws Exception {
        FilterHolder filterHolder = this;
        synchronized (filterHolder) {
            if (this._filter != null) {
                return;
            }
            super.initialize();
            this._filter = (Filter)this.getInstance();
            if (this._filter == null) {
                try {
                    ServletContext context = this.getServletHandler().getServletContext();
                    this._filter = context != null ? context.createFilter(this.getHeldClass()) : (Filter)this.getHeldClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (ServletException ex) {
                    Throwable cause = ex.getRootCause();
                    if (cause instanceof InstantiationException) {
                        throw (InstantiationException)cause;
                    }
                    if (cause instanceof IllegalAccessException) {
                        throw (IllegalAccessException)cause;
                    }
                    throw ex;
                }
            }
            this._filter = this.wrap(this._filter, WrapFunction.class, WrapFunction::wrapFilter);
            this._config = new Config();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Filter.init {}", this._filter);
            }
            this._filter.init(this._config);
        }
    }

    @Override
    public void doStop() throws Exception {
        super.doStop();
        this._config = null;
        if (this._filter != null) {
            try {
                this.destroyInstance(this._filter);
            }
            finally {
                this._filter = null;
            }
        }
    }

    @Override
    public void destroyInstance(Object o) {
        if (o == null) {
            return;
        }
        Filter filter = (Filter)o;
        this.getServletHandler().destroyFilter(this.unwrap(filter));
        filter.destroy();
    }

    public synchronized void setFilter(Filter filter) {
        this.setInstance(filter);
    }

    public Filter getFilter() {
        return this._filter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (this.isAsyncSupported() || !request.isAsyncSupported()) {
            this.getFilter().doFilter(request, response, chain);
        } else {
            Request baseRequest = Request.getBaseRequest(request);
            Objects.requireNonNull(baseRequest);
            try {
                baseRequest.setAsyncSupported(false, this);
                this.getFilter().doFilter(request, response, chain);
            }
            finally {
                baseRequest.setAsyncSupported(true, null);
            }
        }
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        if (this.getInitParameters().isEmpty()) {
            Dumpable.dumpObjects(out, indent, this, this._filter == null ? this.getHeldClass() : this._filter);
        } else {
            Dumpable.dumpObjects(out, indent, this, this._filter == null ? this.getHeldClass() : this._filter, new DumpableCollection("initParams", this.getInitParameters().entrySet()));
        }
    }

    @Override
    public String toString() {
        return String.format("%s==%s@%x{inst=%b,async=%b,src=%s}", this.getName(), this.getClassName(), this.hashCode(), this._filter != null, this.isAsyncSupported(), this.getSource());
    }

    public FilterRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    public static class Wrapper
    implements Filter,
    BaseHolder.Wrapped<Filter> {
        private final Filter _filter;

        public Wrapper(Filter filter) {
            this._filter = Objects.requireNonNull(filter, "Filter cannot be null");
        }

        @Override
        public Filter getWrapped() {
            return this._filter;
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            this._filter.init(filterConfig);
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            this._filter.doFilter(request, response, chain);
        }

        @Override
        public void destroy() {
            this._filter.destroy();
        }

        public String toString() {
            return String.format("%s:%s", this.getClass().getSimpleName(), this._filter.toString());
        }
    }

    public static interface WrapFunction {
        public Filter wrapFilter(Filter var1);
    }

    class Config
    extends Holder.HolderConfig
    implements FilterConfig {
        Config() {
            super(FilterHolder.this);
        }

        @Override
        public String getFilterName() {
            return FilterHolder.this.getName();
        }
    }

    protected class Registration
    extends Holder.HolderRegistration
    implements FilterRegistration.Dynamic {
        protected Registration() {
            super(FilterHolder.this);
        }

        @Override
        public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String ... servletNames) {
            FilterHolder.this.illegalStateIfContextStarted();
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterHolder(FilterHolder.this);
            mapping.setServletNames(servletNames);
            mapping.setDispatcherTypes(dispatcherTypes);
            if (isMatchAfter) {
                FilterHolder.this.getServletHandler().addFilterMapping(mapping);
            } else {
                FilterHolder.this.getServletHandler().prependFilterMapping(mapping);
            }
        }

        @Override
        public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String ... urlPatterns) {
            FilterHolder.this.illegalStateIfContextStarted();
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterHolder(FilterHolder.this);
            mapping.setPathSpecs(urlPatterns);
            mapping.setDispatcherTypes(dispatcherTypes);
            if (isMatchAfter) {
                FilterHolder.this.getServletHandler().addFilterMapping(mapping);
            } else {
                FilterHolder.this.getServletHandler().prependFilterMapping(mapping);
            }
        }

        @Override
        public Collection<String> getServletNameMappings() {
            FilterMapping[] mappings = FilterHolder.this.getServletHandler().getFilterMappings();
            ArrayList<String> names = new ArrayList<String>();
            for (FilterMapping mapping : mappings) {
                String[] servlets;
                if (mapping.getFilterHolder() != FilterHolder.this || (servlets = mapping.getServletNames()) == null || servlets.length <= 0) continue;
                names.addAll(Arrays.asList(servlets));
            }
            return names;
        }

        @Override
        public Collection<String> getUrlPatternMappings() {
            FilterMapping[] mappings = FilterHolder.this.getServletHandler().getFilterMappings();
            ArrayList<String> patterns = new ArrayList<String>();
            for (FilterMapping mapping : mappings) {
                if (mapping.getFilterHolder() != FilterHolder.this) continue;
                String[] specs = mapping.getPathSpecs();
                patterns.addAll(TypeUtil.asList(specs));
            }
            return patterns;
        }
    }
}

