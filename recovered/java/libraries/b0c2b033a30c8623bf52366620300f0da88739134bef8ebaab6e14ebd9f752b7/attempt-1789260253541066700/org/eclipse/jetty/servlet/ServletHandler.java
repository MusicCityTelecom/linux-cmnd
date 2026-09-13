/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.pathmap.MappedResource;
import org.eclipse.jetty.http.pathmap.PathMappings;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.ServletRequestHttpWrapper;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.ArrayUtil;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@ManagedObject(value="Servlet Handler")
public class ServletHandler
extends ScopedHandler {
    private static final Logger LOG = Log.getLogger(ServletHandler.class);
    public static final String __DEFAULT_SERVLET = "default";
    private ServletContextHandler _contextHandler;
    private ServletContext _servletContext;
    private FilterHolder[] _filters = new FilterHolder[0];
    private FilterMapping[] _filterMappings;
    private int _matchBeforeIndex = -1;
    private int _matchAfterIndex = -1;
    private boolean _filterChainsCached = true;
    private int _maxFilterChainsCacheSize = 1024;
    private boolean _startWithUnavailable = false;
    private boolean _ensureDefaultServlet = true;
    private IdentityService _identityService;
    private boolean _allowDuplicateMappings = false;
    private ServletHolder[] _servlets = new ServletHolder[0];
    private ServletMapping[] _servletMappings;
    private final Map<String, FilterHolder> _filterNameMap = new HashMap<String, FilterHolder>();
    private List<FilterMapping> _filterPathMappings;
    private MultiMap<FilterMapping> _filterNameMappings;
    private List<FilterMapping> _wildFilterNameMappings;
    private final Map<String, ServletHolder> _servletNameMap = new HashMap<String, ServletHolder>();
    private PathMappings<ServletHolder> _servletPathMap;
    private ListenerHolder[] _listeners = new ListenerHolder[0];
    private boolean _initialized = false;
    protected final ConcurrentMap<String, FilterChain>[] _chainCache = new ConcurrentMap[31];

    @Override
    public boolean isDumpable(Object o) {
        return !(o instanceof BaseHolder) && !(o instanceof FilterMapping) && !(o instanceof ServletMapping);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, DumpableCollection.fromArray("listeners " + this, this._listeners), DumpableCollection.fromArray("filters " + this, this._filters), DumpableCollection.fromArray("filterMappings " + this, this._filterMappings), DumpableCollection.fromArray("servlets " + this, this._servlets), DumpableCollection.fromArray("servletMappings " + this, this._servletMappings));
    }

    @Override
    protected synchronized void doStart() throws Exception {
        SecurityHandler securityHandler;
        ContextHandler.Context context = ContextHandler.getCurrentContext();
        this._servletContext = context == null ? new ContextHandler.StaticContext() : context;
        this._contextHandler = (ServletContextHandler)(context == null ? null : context.getContextHandler());
        if (this._contextHandler != null && (securityHandler = this._contextHandler.getChildHandlerByClass(SecurityHandler.class)) != null) {
            this._identityService = securityHandler.getIdentityService();
        }
        this.updateNameMappings();
        this.updateMappings();
        if (this.getServletMapping("/") == null && this.isEnsureDefaultServlet()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding Default404Servlet to {}", this);
            }
            this.addServletWithMapping(Default404Servlet.class, "/");
            this.updateMappings();
            this.getServletMapping("/").setDefault(true);
        }
        if (this.isFilterChainsCached()) {
            this._chainCache[1] = new ConcurrentHashMap<String, FilterChain>();
            this._chainCache[2] = new ConcurrentHashMap<String, FilterChain>();
            this._chainCache[4] = new ConcurrentHashMap<String, FilterChain>();
            this._chainCache[8] = new ConcurrentHashMap<String, FilterChain>();
            this._chainCache[16] = new ConcurrentHashMap<String, FilterChain>();
        }
        if (this._contextHandler == null) {
            this.initialize();
        }
        super.doStart();
    }

    public boolean isEnsureDefaultServlet() {
        return this._ensureDefaultServlet;
    }

    public void setEnsureDefaultServlet(boolean ensureDefaultServlet) {
        this._ensureDefaultServlet = ensureDefaultServlet;
    }

    @Override
    protected void start(LifeCycle l) throws Exception {
        if (!(l instanceof Holder)) {
            super.start(l);
        }
    }

    @Override
    protected void stop(LifeCycle l) throws Exception {
        if (!(l instanceof Holder)) {
            super.stop(l);
        }
    }

    @Override
    protected synchronized void doStop() throws Exception {
        super.doStop();
        ArrayList<FilterHolder> filterHolders = new ArrayList<FilterHolder>();
        List<FilterMapping> filterMappings = ArrayUtil.asMutableList(this._filterMappings);
        if (this._filters != null) {
            int i = this._filters.length;
            while (i-- > 0) {
                try {
                    this._filters[i].stop();
                }
                catch (Exception e) {
                    LOG.warn("EXCEPTION ", e);
                }
                if (this._filters[i].getSource() != Source.EMBEDDED) {
                    this._filterNameMap.remove(this._filters[i].getName());
                    ListIterator<FilterMapping> fmitor = filterMappings.listIterator();
                    while (fmitor.hasNext()) {
                        FilterMapping fm = fmitor.next();
                        if (!fm.getFilterName().equals(this._filters[i].getName())) continue;
                        fmitor.remove();
                    }
                    continue;
                }
                filterHolders.add(this._filters[i]);
            }
        }
        Object[] fhs = filterHolders.toArray(new FilterHolder[0]);
        this.updateBeans(this._filters, fhs);
        this._filters = fhs;
        Object[] fms = filterMappings.toArray(new FilterMapping[0]);
        this.updateBeans(this._filterMappings, fms);
        this._filterMappings = fms;
        this._matchAfterIndex = this._filterMappings.length == 0 ? -1 : this._filterMappings.length - 1;
        this._matchBeforeIndex = -1;
        ArrayList<ServletHolder> servletHolders = new ArrayList<ServletHolder>();
        List<ServletMapping> servletMappings = ArrayUtil.asMutableList(this._servletMappings);
        if (this._servlets != null) {
            int i = this._servlets.length;
            while (i-- > 0) {
                try {
                    this._servlets[i].stop();
                }
                catch (Exception e) {
                    LOG.warn("EXCEPTION ", e);
                }
                if (this._servlets[i].getSource() != Source.EMBEDDED) {
                    this._servletNameMap.remove(this._servlets[i].getName());
                    ListIterator<ServletMapping> smitor = servletMappings.listIterator();
                    while (smitor.hasNext()) {
                        ServletMapping sm = smitor.next();
                        if (!sm.getServletName().equals(this._servlets[i].getName())) continue;
                        smitor.remove();
                    }
                    continue;
                }
                servletHolders.add(this._servlets[i]);
            }
        }
        Object[] shs = servletHolders.toArray(new ServletHolder[0]);
        this.updateBeans(this._servlets, shs);
        this._servlets = shs;
        Object[] sms = servletMappings.toArray(new ServletMapping[0]);
        this.updateBeans(this._servletMappings, sms);
        this._servletMappings = sms;
        if (this._contextHandler != null) {
            this._contextHandler.contextDestroyed();
        }
        ArrayList<ListenerHolder> listenerHolders = new ArrayList<ListenerHolder>();
        if (this._listeners != null) {
            int i = this._listeners.length;
            while (i-- > 0) {
                try {
                    this._listeners[i].stop();
                }
                catch (Exception e) {
                    LOG.warn("EXCEPTION ", e);
                }
                if (this._listeners[i].getSource() != Source.EMBEDDED) continue;
                listenerHolders.add(this._listeners[i]);
            }
        }
        Object[] listeners = listenerHolders.toArray(new ListenerHolder[0]);
        this.updateBeans(this._listeners, listeners);
        this._listeners = listeners;
        this._filterPathMappings = null;
        this._filterNameMappings = null;
        this._servletPathMap = null;
        this._initialized = false;
    }

    protected IdentityService getIdentityService() {
        return this._identityService;
    }

    @ManagedAttribute(value="filters", readonly=true)
    public FilterMapping[] getFilterMappings() {
        return this._filterMappings;
    }

    @ManagedAttribute(value="filters", readonly=true)
    public FilterHolder[] getFilters() {
        return this._filters;
    }

    @Deprecated
    public MappedResource<ServletHolder> getHolderEntry(String target) {
        if (target.startsWith("/")) {
            return this.getMappedServlet(target);
        }
        return null;
    }

    public ServletContext getServletContext() {
        return this._servletContext;
    }

    public ServletContextHandler getServletContextHandler() {
        return this._contextHandler;
    }

    @ManagedAttribute(value="mappings of servlets", readonly=true)
    public ServletMapping[] getServletMappings() {
        return this._servletMappings;
    }

    public ServletMapping getServletMapping(String pathSpec) {
        if (pathSpec == null || this._servletMappings == null) {
            return null;
        }
        ServletMapping mapping = null;
        block0: for (int i = 0; i < this._servletMappings.length && mapping == null; ++i) {
            ServletMapping m = this._servletMappings[i];
            if (m.getPathSpecs() == null) continue;
            for (String p : m.getPathSpecs()) {
                if (!pathSpec.equals(p)) continue;
                mapping = m;
                continue block0;
            }
        }
        return mapping;
    }

    @ManagedAttribute(value="servlets", readonly=true)
    public ServletHolder[] getServlets() {
        return this._servlets;
    }

    public List<ServletHolder> getServlets(Class<?> clazz) {
        ArrayList<ServletHolder> holders = null;
        for (ServletHolder holder : this._servlets) {
            Class held = holder.getHeldClass();
            if ((held != null || holder.getClassName() == null || !holder.getClassName().equals(clazz.getName())) && (held == null || !clazz.isAssignableFrom(holder.getHeldClass()))) continue;
            if (holders == null) {
                holders = new ArrayList<ServletHolder>();
            }
            holders.add(holder);
        }
        return holders == null ? Collections.emptyList() : holders;
    }

    public ServletHolder getServlet(String name) {
        return this._servletNameMap.get(name);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String old_servlet_path = baseRequest.getServletPath();
        String old_path_info = baseRequest.getPathInfo();
        DispatcherType type = baseRequest.getDispatcherType();
        ServletHolder servletHolder = null;
        UserIdentity.Scope oldScope = null;
        MappedResource<ServletHolder> mapping = this.getMappedServlet(target);
        if (mapping != null) {
            servletHolder = mapping.getResource();
            if (mapping.getPathSpec() != null) {
                PathSpec pathSpec = mapping.getPathSpec();
                String servletPath = pathSpec.getPathMatch(target);
                String pathInfo = pathSpec.getPathInfo(target);
                if (DispatcherType.INCLUDE.equals((Object)type)) {
                    baseRequest.setAttribute("javax.servlet.include.servlet_path", servletPath);
                    baseRequest.setAttribute("javax.servlet.include.path_info", pathInfo);
                } else {
                    baseRequest.setServletPath(servletPath);
                    baseRequest.setPathInfo(pathInfo);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("servlet {}|{}|{} -> {}", baseRequest.getContextPath(), baseRequest.getServletPath(), baseRequest.getPathInfo(), servletHolder);
        }
        try {
            oldScope = baseRequest.getUserIdentityScope();
            baseRequest.setUserIdentityScope(servletHolder);
            this.nextScope(target, baseRequest, request, response);
        }
        finally {
            if (oldScope != null) {
                baseRequest.setUserIdentityScope(oldScope);
            }
            if (!DispatcherType.INCLUDE.equals((Object)type)) {
                baseRequest.setServletPath(old_servlet_path);
                baseRequest.setPathInfo(old_path_info);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletHolder servletHolder = (ServletHolder)baseRequest.getUserIdentityScope();
        FilterChain chain = null;
        if (servletHolder != null && this._filterMappings != null && this._filterMappings.length > 0) {
            chain = this.getFilterChain(baseRequest, target.startsWith("/") ? target : null, servletHolder);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("chain={}", chain);
        }
        try {
            if (servletHolder == null) {
                this.notFound(baseRequest, request, response);
            } else {
                ServletResponse res;
                ServletRequest req = request;
                if (req instanceof ServletRequestHttpWrapper) {
                    req = ((ServletRequestHttpWrapper)req).getRequest();
                }
                if ((res = response) instanceof ServletResponseHttpWrapper) {
                    res = ((ServletResponseHttpWrapper)res).getResponse();
                }
                servletHolder.prepare(baseRequest, req, res);
                if (chain != null) {
                    chain.doFilter(req, res);
                } else {
                    servletHolder.handle(baseRequest, req, res);
                }
            }
        }
        finally {
            if (servletHolder != null) {
                baseRequest.setHandled(true);
            }
        }
    }

    public MappedResource<ServletHolder> getMappedServlet(String target) {
        if (target.startsWith("/")) {
            if (this._servletPathMap == null) {
                return null;
            }
            return this._servletPathMap.getMatch(target);
        }
        ServletHolder holder = this._servletNameMap.get(target);
        if (holder == null) {
            return null;
        }
        return new MappedResource<ServletHolder>(null, holder);
    }

    protected FilterChain getFilterChain(Request baseRequest, String pathInContext, ServletHolder servletHolder) {
        FilterChain chain;
        Objects.requireNonNull(servletHolder);
        String key = pathInContext == null ? servletHolder.getName() : pathInContext;
        int dispatch = FilterMapping.dispatch(baseRequest.getDispatcherType());
        if (this._filterChainsCached && (chain = (FilterChain)this._chainCache[dispatch].get(key)) != null) {
            return chain;
        }
        chain = null;
        if (this._filterNameMappings != null && !this._filterNameMappings.isEmpty()) {
            Object nameMappings;
            if (this._wildFilterNameMappings != null) {
                for (FilterMapping filterMapping : this._wildFilterNameMappings) {
                    chain = this.newFilterChain(filterMapping.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
                }
            }
            if ((nameMappings = (List)this._filterNameMappings.get(servletHolder.getName())) != null) {
                Iterator iterator = nameMappings.iterator();
                while (iterator.hasNext()) {
                    FilterMapping mapping2 = (FilterMapping)iterator.next();
                    if (!mapping2.appliesTo(dispatch)) continue;
                    chain = this.newFilterChain(mapping2.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
                }
            }
        }
        if (pathInContext != null && this._filterPathMappings != null) {
            for (FilterMapping filterMapping : this._filterPathMappings) {
                if (!filterMapping.appliesTo(pathInContext, dispatch)) continue;
                chain = this.newFilterChain(filterMapping.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
            }
        }
        if (this._filterChainsCached) {
            ConcurrentMap<String, FilterChain> cache = this._chainCache[dispatch];
            if (this._maxFilterChainsCacheSize > 0 && cache.size() >= this._maxFilterChainsCacheSize) {
                LOG.debug("{} flushed filter chain cache for {}", new Object[]{this, baseRequest.getDispatcherType()});
                cache.clear();
            }
            chain = chain == null ? new ChainEnd(servletHolder) : chain;
            LOG.debug("{} cached filter chain for {}: {}", new Object[]{this, baseRequest.getDispatcherType(), chain});
            cache.put(key, chain);
        }
        return chain;
    }

    protected FilterChain newFilterChain(FilterHolder filterHolder, FilterChain chain) {
        return new Chain(filterHolder, chain);
    }

    protected void invalidateChainsCache() {
        if (this._chainCache[1] != null) {
            this._chainCache[1].clear();
            this._chainCache[2].clear();
            this._chainCache[4].clear();
            this._chainCache[8].clear();
            this._chainCache[16].clear();
        }
    }

    public boolean isAvailable() {
        ServletHolder[] holders;
        if (!this.isStarted()) {
            return false;
        }
        for (ServletHolder holder : holders = this.getServlets()) {
            if (holder == null || holder.isAvailable()) continue;
            return false;
        }
        return true;
    }

    public void setStartWithUnavailable(boolean start) {
        this._startWithUnavailable = start;
    }

    public boolean isAllowDuplicateMappings() {
        return this._allowDuplicateMappings;
    }

    public void setAllowDuplicateMappings(boolean allowDuplicateMappings) {
        this._allowDuplicateMappings = allowDuplicateMappings;
    }

    public boolean isStartWithUnavailable() {
        return this._startWithUnavailable;
    }

    public void initialize() throws Exception {
        MultiException mx = new MultiException();
        Consumer<BaseHolder> c = h -> {
            try {
                if (!h.isStarted()) {
                    h.start();
                    h.initialize();
                }
            }
            catch (Throwable e) {
                LOG.debug("EXCEPTION ", e);
                mx.add(e);
            }
        };
        Arrays.stream(this._listeners).forEach(c);
        if (this._contextHandler != null) {
            this._contextHandler.contextInitialized();
        }
        this._initialized = true;
        Stream.concat(Arrays.stream(this._filters), Arrays.stream(this._servlets).sorted()).forEach(c);
        mx.ifExceptionThrow();
    }

    public boolean isInitialized() {
        return this._initialized;
    }

    protected void initializeHolders(BaseHolder<?>[] holders) {
        for (BaseHolder<?> holder : holders) {
            holder.setServletHandler(this);
            if (!this.isInitialized()) continue;
            try {
                if (holder.isStarted()) continue;
                holder.start();
                holder.initialize();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isFilterChainsCached() {
        return this._filterChainsCached;
    }

    public void addListener(ListenerHolder listener) {
        if (listener != null) {
            this.setListeners(ArrayUtil.addToArray(this.getListeners(), listener, ListenerHolder.class));
        }
    }

    public ListenerHolder[] getListeners() {
        return this._listeners;
    }

    public void setListeners(ListenerHolder[] listeners) {
        if (listeners != null) {
            this.initializeHolders(listeners);
        }
        this.updateBeans(this._listeners, listeners);
        this._listeners = listeners;
    }

    public ListenerHolder newListenerHolder(Source source) {
        return new ListenerHolder(source);
    }

    public ServletHolder newServletHolder(Source source) {
        return new ServletHolder(source);
    }

    public ServletHolder addServletWithMapping(String className, String pathSpec) {
        ServletHolder holder = this.newServletHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    public ServletHolder addServletWithMapping(Class<? extends Servlet> servlet, String pathSpec) {
        ServletHolder holder = this.newServletHolder(Source.EMBEDDED);
        holder.setHeldClass(servlet);
        this.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addServletWithMapping(ServletHolder servlet, String pathSpec) {
        Objects.requireNonNull(servlet);
        ServletHolder[] holders = this.getServlets();
        try {
            ServletHandler servletHandler = this;
            synchronized (servletHandler) {
                if (!this.containsServletHolder(servlet)) {
                    this.setServlets(ArrayUtil.addToArray(holders, servlet, ServletHolder.class));
                }
            }
            ServletMapping mapping = new ServletMapping();
            mapping.setServletName(servlet.getName());
            mapping.setPathSpec(pathSpec);
            this.setServletMappings(ArrayUtil.addToArray(this.getServletMappings(), mapping, ServletMapping.class));
        }
        catch (RuntimeException e) {
            this.setServlets(holders);
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addServlet(ServletHolder holder) {
        if (holder == null) {
            return;
        }
        ServletHandler servletHandler = this;
        synchronized (servletHandler) {
            if (!this.containsServletHolder(holder)) {
                this.setServlets(ArrayUtil.addToArray(this.getServlets(), holder, ServletHolder.class));
            }
        }
    }

    public void addServletMapping(ServletMapping mapping) {
        this.setServletMappings(ArrayUtil.addToArray(this.getServletMappings(), mapping, ServletMapping.class));
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic registration, ServletSecurityElement servletSecurityElement) {
        if (this._contextHandler != null) {
            return this._contextHandler.setServletSecurity(registration, servletSecurityElement);
        }
        return Collections.emptySet();
    }

    public FilterHolder newFilterHolder(Source source) {
        return new FilterHolder(source);
    }

    public FilterHolder getFilter(String name) {
        return this._filterNameMap.get(name);
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> filter, String pathSpec, EnumSet<DispatcherType> dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setHeldClass(filter);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public FilterHolder addFilterWithMapping(String className, String pathSpec, EnumSet<DispatcherType> dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFilterWithMapping(FilterHolder holder, String pathSpec, EnumSet<DispatcherType> dispatches) {
        Objects.requireNonNull(holder);
        FilterHolder[] holders = this.getFilters();
        try {
            ServletHandler servletHandler = this;
            synchronized (servletHandler) {
                if (!this.containsFilterHolder(holder)) {
                    this.setFilters(ArrayUtil.addToArray(holders, holder, FilterHolder.class));
                }
            }
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterName(holder.getName());
            mapping.setPathSpec(pathSpec);
            mapping.setDispatcherTypes(dispatches);
            this.addFilterMapping(mapping);
        }
        catch (Throwable e) {
            this.setFilters(holders);
            throw e;
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> filter, String pathSpec, int dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setHeldClass(filter);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public FilterHolder addFilterWithMapping(String className, String pathSpec, int dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFilterWithMapping(FilterHolder holder, String pathSpec, int dispatches) {
        Objects.requireNonNull(holder);
        FilterHolder[] holders = this.getFilters();
        if (holders != null) {
            holders = (FilterHolder[])holders.clone();
        }
        try {
            ServletHandler servletHandler = this;
            synchronized (servletHandler) {
                if (!this.containsFilterHolder(holder)) {
                    this.setFilters(ArrayUtil.addToArray(holders, holder, FilterHolder.class));
                }
            }
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterName(holder.getName());
            mapping.setPathSpec(pathSpec);
            mapping.setDispatches(dispatches);
            this.addFilterMapping(mapping);
        }
        catch (Throwable e) {
            this.setFilters(holders);
            throw e;
        }
    }

    @Deprecated
    public FilterHolder addFilter(String className, String pathSpec, EnumSet<DispatcherType> dispatches) {
        return this.addFilterWithMapping(className, pathSpec, dispatches);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFilter(FilterHolder filter, FilterMapping filterMapping) {
        if (filter != null) {
            ServletHandler servletHandler = this;
            synchronized (servletHandler) {
                if (!this.containsFilterHolder(filter)) {
                    this.setFilters(ArrayUtil.addToArray(this.getFilters(), filter, FilterHolder.class));
                }
            }
        }
        if (filterMapping != null) {
            this.addFilterMapping(filterMapping);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFilter(FilterHolder filter) {
        if (filter == null) {
            return;
        }
        ServletHandler servletHandler = this;
        synchronized (servletHandler) {
            if (!this.containsFilterHolder(filter)) {
                this.setFilters(ArrayUtil.addToArray(this.getFilters(), filter, FilterHolder.class));
            }
        }
    }

    public void addFilterMapping(FilterMapping mapping) {
        if (mapping != null) {
            Source source = mapping.getFilterHolder() == null ? null : mapping.getFilterHolder().getSource();
            FilterMapping[] mappings = this.getFilterMappings();
            if (mappings == null || mappings.length == 0) {
                this.setFilterMappings(this.insertFilterMapping(mapping, 0, false));
                if (source == Source.JAVAX_API) {
                    this._matchAfterIndex = 0;
                }
            } else if (source == Source.JAVAX_API) {
                this.setFilterMappings(this.insertFilterMapping(mapping, mappings.length - 1, false));
                if (this._matchAfterIndex < 0) {
                    this._matchAfterIndex = this.getFilterMappings().length - 1;
                }
            } else if (this._matchAfterIndex < 0) {
                this.setFilterMappings(this.insertFilterMapping(mapping, mappings.length - 1, false));
            } else {
                FilterMapping[] newMappings = this.insertFilterMapping(mapping, this._matchAfterIndex, true);
                ++this._matchAfterIndex;
                this.setFilterMappings(newMappings);
            }
        }
    }

    public void prependFilterMapping(FilterMapping mapping) {
        if (mapping != null) {
            Source source = mapping.getFilterHolder() == null ? null : mapping.getFilterHolder().getSource();
            FilterMapping[] mappings = this.getFilterMappings();
            if (mappings == null || mappings.length == 0) {
                this.setFilterMappings(this.insertFilterMapping(mapping, 0, false));
                if (source == Source.JAVAX_API) {
                    this._matchBeforeIndex = 0;
                }
            } else {
                if (source == Source.JAVAX_API) {
                    if (this._matchBeforeIndex < 0) {
                        this._matchBeforeIndex = 0;
                        FilterMapping[] newMappings = this.insertFilterMapping(mapping, 0, true);
                        this.setFilterMappings(newMappings);
                    } else {
                        FilterMapping[] newMappings = this.insertFilterMapping(mapping, this._matchBeforeIndex, false);
                        ++this._matchBeforeIndex;
                        this.setFilterMappings(newMappings);
                    }
                } else {
                    FilterMapping[] newMappings = this.insertFilterMapping(mapping, 0, true);
                    this.setFilterMappings(newMappings);
                }
                if (this._matchAfterIndex >= 0) {
                    ++this._matchAfterIndex;
                }
            }
        }
    }

    protected FilterMapping[] insertFilterMapping(FilterMapping mapping, int pos, boolean before) {
        if (pos < 0) {
            throw new IllegalArgumentException("FilterMapping insertion pos < 0");
        }
        FilterMapping[] mappings = this.getFilterMappings();
        if (mappings == null || mappings.length == 0) {
            return new FilterMapping[]{mapping};
        }
        FilterMapping[] newMappings = new FilterMapping[mappings.length + 1];
        if (before) {
            System.arraycopy(mappings, 0, newMappings, 0, pos);
            newMappings[pos] = mapping;
            System.arraycopy(mappings, pos, newMappings, pos + 1, mappings.length - pos);
        } else {
            System.arraycopy(mappings, 0, newMappings, 0, pos + 1);
            newMappings[pos + 1] = mapping;
            if (mappings.length > pos + 1) {
                System.arraycopy(mappings, pos + 1, newMappings, pos + 2, mappings.length - (pos + 1));
            }
        }
        return newMappings;
    }

    protected synchronized void updateNameMappings() {
        this._filterNameMap.clear();
        if (this._filters != null) {
            for (Holder holder : this._filters) {
                this._filterNameMap.put(holder.getName(), (FilterHolder)holder);
                holder.setServletHandler(this);
            }
        }
        this._servletNameMap.clear();
        if (this._servlets != null) {
            for (Holder holder : this._servlets) {
                this._servletNameMap.put(holder.getName(), (ServletHolder)holder);
                holder.setServletHandler(this);
            }
        }
    }

    protected synchronized void updateMappings() {
        if (this._filterMappings == null) {
            this._filterPathMappings = null;
            this._filterNameMappings = null;
            this._wildFilterNameMappings = Collections.emptyList();
        } else {
            this._filterPathMappings = new ArrayList<FilterMapping>();
            this._filterNameMappings = new MultiMap();
            for (FilterMapping filtermapping : this._filterMappings) {
                FilterHolder filterHolder = this._filterNameMap.get(filtermapping.getFilterName());
                if (filterHolder == null) {
                    throw new IllegalStateException("No filter named " + filtermapping.getFilterName());
                }
                filtermapping.setFilterHolder(filterHolder);
                if (filtermapping.getPathSpecs() != null) {
                    this._filterPathMappings.add(filtermapping);
                }
                if (filtermapping.getServletNames() == null) continue;
                String[] names = filtermapping.getServletNames();
                for (String name : names) {
                    if (name == null) continue;
                    this._filterNameMappings.add(name, filtermapping);
                }
            }
            for (Map.Entry entry : this._filterNameMappings.entrySet()) {
                Collections.reverse((List)entry.getValue());
            }
            Collections.reverse(this._filterPathMappings);
            this._wildFilterNameMappings = (List)this._filterNameMappings.get("*");
            if (this._wildFilterNameMappings != null) {
                Collections.reverse(this._wildFilterNameMappings);
            }
        }
        if (this._servletMappings == null) {
            this._servletPathMap = null;
        } else {
            PathMappings<ServletHolder> pm = new PathMappings<ServletHolder>();
            HashMap<String, List> hashMap = new HashMap<String, List>();
            for (ServletMapping servletMapping : this._servletMappings) {
                String[] pathSpecs = servletMapping.getPathSpecs();
                if (pathSpecs == null) continue;
                for (String pathSpec : pathSpecs) {
                    List mappings = hashMap.computeIfAbsent(pathSpec, k -> new ArrayList());
                    mappings.add(servletMapping);
                }
            }
            for (String pathSpec : hashMap.keySet()) {
                List mappings = (List)hashMap.get(pathSpec);
                ServletMapping finalMapping = null;
                for (ServletMapping mapping : mappings) {
                    ServletHolder servletHolder = this._servletNameMap.get(mapping.getServletName());
                    if (servletHolder == null) {
                        throw new IllegalStateException("No such servlet: " + mapping.getServletName());
                    }
                    if (!servletHolder.isEnabled()) continue;
                    if (finalMapping == null) {
                        finalMapping = mapping;
                        continue;
                    }
                    if (finalMapping.isDefault()) {
                        finalMapping = mapping;
                        continue;
                    }
                    if (this.isAllowDuplicateMappings()) {
                        LOG.warn("Multiple servlets map to path {}: {} and {}, choosing {}", pathSpec, finalMapping.getServletName(), mapping.getServletName(), mapping);
                        finalMapping = mapping;
                        continue;
                    }
                    if (mapping.isDefault()) continue;
                    ServletHolder finalMappedServlet = this._servletNameMap.get(finalMapping.getServletName());
                    throw new IllegalStateException("Multiple servlets map to path " + pathSpec + ": " + finalMappedServlet.getName() + "[mapped:" + finalMapping.getSource() + "]," + mapping.getServletName() + "[mapped:" + mapping.getSource() + "]");
                }
                if (finalMapping == null) {
                    throw new IllegalStateException("No acceptable servlet mappings for " + pathSpec);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Path={}[{}] mapped to servlet={}[{}]", pathSpec, finalMapping.getSource(), finalMapping.getServletName(), this._servletNameMap.get(finalMapping.getServletName()).getSource());
                }
                pm.put(new ServletPathSpec(pathSpec), this._servletNameMap.get(finalMapping.getServletName()));
            }
            this._servletPathMap = pm;
        }
        int i = this._chainCache.length;
        while (i-- > 0) {
            if (this._chainCache[i] == null) continue;
            this._chainCache[i].clear();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("filterNameMap=" + this._filterNameMap, new Object[0]);
            LOG.debug("pathFilters=" + this._filterPathMappings, new Object[0]);
            LOG.debug("servletFilterMap=" + this._filterNameMappings, new Object[0]);
            LOG.debug("servletPathMap=" + this._servletPathMap, new Object[0]);
            LOG.debug("servletNameMap=" + this._servletNameMap, new Object[0]);
        }
    }

    protected void notFound(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Not Found {}", request.getRequestURI());
        }
        if (this.getHandler() != null) {
            this.nextHandle(URIUtil.addPaths(request.getServletPath(), request.getPathInfo()), baseRequest, request, response);
        }
    }

    protected synchronized boolean containsFilterHolder(FilterHolder holder) {
        if (this._filters == null) {
            return false;
        }
        for (FilterHolder f : this._filters) {
            if (f != holder) continue;
            return true;
        }
        return false;
    }

    protected synchronized boolean containsServletHolder(ServletHolder holder) {
        if (this._servlets == null) {
            return false;
        }
        for (ServletHolder s : this._servlets) {
            boolean foundServletHolder;
            boolean bl = foundServletHolder = s == holder;
            if (!foundServletHolder) continue;
            return true;
        }
        return false;
    }

    public void setFilterChainsCached(boolean filterChainsCached) {
        this._filterChainsCached = filterChainsCached;
    }

    public void setFilterMappings(FilterMapping[] filterMappings) {
        this.updateBeans(this._filterMappings, filterMappings);
        this._filterMappings = filterMappings;
        if (this.isRunning()) {
            this.updateMappings();
        }
        this.invalidateChainsCache();
    }

    public synchronized void setFilters(FilterHolder[] holders) {
        if (holders != null) {
            this.initializeHolders(holders);
        }
        this.updateBeans(this._filters, holders);
        this._filters = holders;
        this.updateNameMappings();
        this.invalidateChainsCache();
    }

    public void setServletMappings(ServletMapping[] servletMappings) {
        this.updateBeans(this._servletMappings, servletMappings);
        this._servletMappings = servletMappings;
        if (this.isRunning()) {
            this.updateMappings();
        }
        this.invalidateChainsCache();
    }

    public synchronized void setServlets(ServletHolder[] holders) {
        if (holders != null) {
            this.initializeHolders(holders);
        }
        this.updateBeans(this._servlets, holders);
        this._servlets = holders;
        this.updateNameMappings();
        this.invalidateChainsCache();
    }

    public int getMaxFilterChainsCacheSize() {
        return this._maxFilterChainsCacheSize;
    }

    public void setMaxFilterChainsCacheSize(int maxFilterChainsCacheSize) {
        this._maxFilterChainsCacheSize = maxFilterChainsCacheSize;
    }

    void destroyServlet(Servlet servlet) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyServlet(servlet);
        }
    }

    void destroyFilter(Filter filter) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyFilter(filter);
        }
    }

    void destroyListener(EventListener listener) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyListener(listener);
        }
    }

    static class ChainEnd
    implements FilterChain {
        private final ServletHolder _servletHolder;

        ChainEnd(ServletHolder holder) {
            Objects.requireNonNull(holder);
            this._servletHolder = holder;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            Request baseRequest = Request.getBaseRequest(request);
            Objects.requireNonNull(baseRequest);
            this._servletHolder.handle(baseRequest, request, response);
        }

        public String toString() {
            return String.format("ChainEnd@%x(%s)", this.hashCode(), this._servletHolder);
        }
    }

    static class Chain
    implements FilterChain {
        private final FilterHolder _filterHolder;
        private final FilterChain _filterChain;

        Chain(FilterHolder filter, FilterChain chain) {
            this._filterHolder = filter;
            this._filterChain = chain;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            this._filterHolder.doFilter(request, response, this._filterChain);
        }

        public String toString() {
            return String.format("Chain@%x(%s)->%s", this.hashCode(), this._filterHolder, this._filterChain);
        }
    }

    public static class Default404Servlet
    extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.sendError(404);
        }
    }
}

