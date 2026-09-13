/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.GenericServlet;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.RunAsToken;
import org.eclipse.jetty.server.MultiPartCleanerListener;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@ManagedObject(value="Servlet Holder")
public class ServletHolder
extends Holder<Servlet>
implements UserIdentity.Scope,
Comparable<ServletHolder> {
    private static final Logger LOG = Log.getLogger(ServletHolder.class);
    private int _initOrder = -1;
    private boolean _initOnStartup = false;
    private Map<String, String> _roleMap;
    private String _forcedPath;
    private String _runAsRole;
    private ServletRegistration.Dynamic _registration;
    private JspContainer _jspContainer;
    private volatile Servlet _servlet;
    private Config _config;
    private boolean _enabled = true;
    public static final String APACHE_SENTINEL_CLASS = "org.apache.tomcat.InstanceManager";
    public static final String JSP_GENERATED_PACKAGE_NAME = "org.eclipse.jetty.servlet.jspPackagePrefix";

    public ServletHolder() {
        this(Source.EMBEDDED);
    }

    public ServletHolder(Source creator) {
        super(creator);
    }

    public ServletHolder(Servlet servlet) {
        this(Source.EMBEDDED);
        this.setServlet(servlet);
    }

    public ServletHolder(String name, Class<? extends Servlet> servlet) {
        this(Source.EMBEDDED);
        this.setName(name);
        this.setHeldClass(servlet);
    }

    public ServletHolder(String name, Servlet servlet) {
        this(Source.EMBEDDED);
        this.setName(name);
        this.setServlet(servlet);
    }

    public ServletHolder(Class<? extends Servlet> servlet) {
        this(Source.EMBEDDED);
        this.setHeldClass(servlet);
    }

    public UnavailableException getUnavailableException() {
        Servlet servlet = this._servlet;
        if (servlet instanceof UnavailableServlet) {
            return ((UnavailableServlet)servlet).getUnavailableException();
        }
        return null;
    }

    public synchronized void setServlet(Servlet servlet) {
        if (servlet == null || servlet instanceof SingleThreadModel) {
            throw new IllegalArgumentException();
        }
        this.setInstance(servlet);
    }

    @ManagedAttribute(value="initialization order", readonly=true)
    public int getInitOrder() {
        return this._initOrder;
    }

    public void setInitOrder(int order) {
        this._initOnStartup = order >= 0;
        this._initOrder = order;
    }

    @Override
    public int compareTo(ServletHolder sh) {
        if (sh == this) {
            return 0;
        }
        if (sh._initOrder < this._initOrder) {
            return 1;
        }
        if (sh._initOrder > this._initOrder) {
            return -1;
        }
        int c = this.getClassName() == null && sh.getClassName() == null ? 0 : (this.getClassName() == null ? -1 : (sh.getClassName() == null ? 1 : this.getClassName().compareTo(sh.getClassName())));
        if (c == 0) {
            c = this.getName().compareTo(sh.getName());
        }
        return c;
    }

    public boolean equals(Object o) {
        return o instanceof ServletHolder && this.compareTo((ServletHolder)o) == 0;
    }

    public int hashCode() {
        return this.getName() == null ? System.identityHashCode(this) : this.getName().hashCode();
    }

    public synchronized void setUserRoleLink(String name, String link) {
        if (this._roleMap == null) {
            this._roleMap = new HashMap<String, String>();
        }
        this._roleMap.put(name, link);
    }

    public String getUserRoleLink(String name) {
        if (this._roleMap == null) {
            return name;
        }
        String link = this._roleMap.get(name);
        return link == null ? name : link;
    }

    @ManagedAttribute(value="forced servlet path", readonly=true)
    public String getForcedPath() {
        return this._forcedPath;
    }

    public void setForcedPath(String forcedPath) {
        this._forcedPath = forcedPath;
    }

    public boolean isEnabled() {
        return this._enabled;
    }

    public void setEnabled(boolean enabled) {
        this._enabled = enabled;
    }

    @Override
    public void doStart() throws Exception {
        if (!this._enabled) {
            return;
        }
        if (this._forcedPath != null) {
            String precompiled = this.getClassNameForJsp(this._forcedPath);
            if (!StringUtil.isBlank(precompiled)) {
                ServletHolder jsp;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Checking for precompiled servlet {} for jsp {}", precompiled, this._forcedPath);
                }
                if ((jsp = this.getServletHandler().getServlet(precompiled)) != null && jsp.getClassName() != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("JSP file {} for {} mapped to Servlet {}", this._forcedPath, this.getName(), jsp.getClassName());
                    }
                    this.setClassName(jsp.getClassName());
                } else {
                    jsp = this.getServletHandler().getServlet("jsp");
                    if (jsp != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("JSP file {} for {} mapped to JspServlet class {}", this._forcedPath, this.getName(), jsp.getClassName());
                        }
                        this.setClassName(jsp.getClassName());
                        for (Map.Entry<String, String> entry : jsp.getInitParameters().entrySet()) {
                            if (this.getInitParameters().containsKey(entry.getKey())) continue;
                            this.setInitParameter(entry.getKey(), entry.getValue());
                        }
                        this.setInitParameter("jspFile", this._forcedPath);
                    }
                }
            } else {
                LOG.warn("Bad jsp-file {} conversion to classname in holder {}", this._forcedPath, this.getName());
            }
        }
        try {
            super.doStart();
        }
        catch (UnavailableException ex) {
            this.makeUnavailable(ex);
            if (this.getServletHandler().isStartWithUnavailable()) {
                LOG.ignore(ex);
                return;
            }
            throw ex;
        }
        try {
            this.checkServletType();
        }
        catch (UnavailableException ex) {
            this.makeUnavailable(ex);
            if (this.getServletHandler().isStartWithUnavailable()) {
                LOG.ignore(ex);
                return;
            }
            throw ex;
        }
        this.checkInitOnStartup();
        this._config = new Config();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initialize() throws Exception {
        ServletHolder servletHolder = this;
        synchronized (servletHolder) {
            if (this._servlet == null && (this._initOnStartup || this.isInstance())) {
                super.initialize();
                this.initServlet();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doStop() throws Exception {
        ServletHolder servletHolder = this;
        synchronized (servletHolder) {
            Servlet servlet = this._servlet;
            if (servlet != null) {
                this._servlet = null;
                try {
                    this.destroyInstance(servlet);
                }
                catch (Exception e) {
                    LOG.warn(e);
                }
            }
            this._config = null;
        }
    }

    @Override
    public void destroyInstance(Object o) {
        if (o == null) {
            return;
        }
        Servlet servlet = (Servlet)o;
        this.predestroyServlet(servlet);
        servlet.destroy();
    }

    private void predestroyServlet(Servlet servlet) {
        this.getServletHandler().destroyServlet(this.unwrap(servlet));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Servlet getServlet() throws ServletException {
        Servlet servlet = this._servlet;
        if (servlet == null) {
            ServletHolder servletHolder = this;
            synchronized (servletHolder) {
                if (this._servlet == null && this.isRunning() && this.getHeldClass() != null) {
                    this.initServlet();
                }
                servlet = this._servlet;
            }
        }
        return servlet;
    }

    public Servlet getServletInstance() {
        return this._servlet;
    }

    public void checkServletType() throws UnavailableException {
        if (this.getHeldClass() == null || !Servlet.class.isAssignableFrom(this.getHeldClass())) {
            throw new UnavailableException("Servlet " + this.getHeldClass() + " is not a javax.servlet.Servlet");
        }
    }

    public boolean isAvailable() {
        return this.isStarted() && !(this._servlet instanceof UnavailableServlet);
    }

    private void checkInitOnStartup() {
        if (this.getHeldClass() == null) {
            return;
        }
        if (this.getHeldClass().getAnnotation(ServletSecurity.class) != null && !this._initOnStartup) {
            this.setInitOrder(Integer.MAX_VALUE);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Servlet makeUnavailable(UnavailableException e) {
        ServletHolder servletHolder = this;
        synchronized (servletHolder) {
            if (this._servlet instanceof UnavailableServlet) {
                UnavailableException cause = ((UnavailableServlet)this._servlet).getUnavailableException();
                if (cause != e) {
                    cause.addSuppressed(e);
                }
            } else {
                this._servlet = new UnavailableServlet(e, this._servlet);
            }
            return this._servlet;
        }
    }

    private void makeUnavailable(final Throwable e) {
        if (e instanceof UnavailableException) {
            this.makeUnavailable((UnavailableException)e);
        } else {
            ServletContext ctx = this.getServletHandler().getServletContext();
            if (ctx == null) {
                LOG.info("unavailable", e);
            } else {
                ctx.log("unavailable", e);
            }
            UnavailableException unavailable = new UnavailableException(String.valueOf(e), -1){
                {
                    super(arg0, arg1);
                    this.initCause(e);
                }
            };
            this.makeUnavailable(unavailable);
        }
    }

    private void initServlet() throws ServletException {
        if (this._servlet != null) {
            throw new IllegalStateException("Servlet already initialised: " + this._servlet);
        }
        Servlet servlet = null;
        try {
            IdentityService identityService;
            servlet = (Servlet)this.getInstance();
            if (servlet == null) {
                servlet = this.newInstance();
            }
            if (servlet instanceof SingleThreadModel) {
                this.predestroyServlet(servlet);
                servlet = new SingleThreadedWrapper();
            }
            if (this._config == null) {
                this._config = new Config();
            }
            if (this._runAsRole != null && (identityService = this.getServletHandler().getIdentityService()) != null) {
                RunAsToken runAsToken = identityService.newRunAsToken(this._runAsRole);
                servlet = new RunAs(servlet, identityService, runAsToken);
            }
            if (!this.isAsyncSupported()) {
                servlet = new NotAsync(servlet);
            }
            if (this.isJspServlet()) {
                this.initJspServlet();
                this.detectJspContainer();
            } else if (this._forcedPath != null) {
                this.detectJspContainer();
            }
            this.initMultiPart();
            servlet = this.wrap(servlet, WrapFunction.class, WrapFunction::wrapServlet);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Servlet.init {} for {}", this._servlet, this.getName());
            }
            try {
                servlet.init(this._config);
                this._servlet = servlet;
            }
            catch (UnavailableException e) {
                this._servlet = new UnavailableServlet(e, servlet);
            }
        }
        catch (ServletException e) {
            this.makeUnavailable(e.getCause() == null ? e : e.getCause());
            this.predestroyServlet(servlet);
            throw e;
        }
        catch (Exception e) {
            this.makeUnavailable(e);
            this.predestroyServlet(servlet);
            throw new ServletException(this.toString(), e);
        }
    }

    protected void initJspServlet() throws Exception {
        File scratch;
        ContextHandler ch = ContextHandler.getContextHandler(this.getServletHandler().getServletContext());
        ch.setAttribute("org.apache.catalina.jsp_classpath", ch.getClassPath());
        if ("?".equals(this.getInitParameter("classpath"))) {
            String classpath = ch.getClassPath();
            if (LOG.isDebugEnabled()) {
                LOG.debug("classpath=" + classpath, new Object[0]);
            }
            if (classpath != null) {
                this.setInitParameter("classpath", classpath);
            }
        }
        if (this.getInitParameter("scratchdir") == null) {
            File tmp = (File)this.getServletHandler().getServletContext().getAttribute("javax.servlet.context.tempdir");
            scratch = new File(tmp, "jsp");
            this.setInitParameter("scratchdir", scratch.getAbsolutePath());
        }
        if (!(scratch = new File(this.getInitParameter("scratchdir"))).exists() && !scratch.mkdir()) {
            throw new IllegalStateException("Could not create JSP scratch directory");
        }
    }

    protected void initMultiPart() throws Exception {
        if (((Registration)this.getRegistration()).getMultipartConfig() != null) {
            ContextHandler ch;
            if (LOG.isDebugEnabled()) {
                LOG.debug("multipart cleanup listener added for {}", this);
            }
            if (!Arrays.asList((ch = ContextHandler.getContextHandler(this.getServletHandler().getServletContext())).getEventListeners()).contains(MultiPartCleanerListener.INSTANCE)) {
                ch.addEventListener(MultiPartCleanerListener.INSTANCE);
            }
        }
    }

    @Override
    public ContextHandler getContextHandler() {
        return ContextHandler.getContextHandler(this._config.getServletContext());
    }

    @Override
    public String getContextPath() {
        return this._config.getServletContext().getContextPath();
    }

    @Override
    public Map<String, String> getRoleRefMap() {
        return this._roleMap;
    }

    @ManagedAttribute(value="role to run servlet as", readonly=true)
    public String getRunAsRole() {
        return this._runAsRole;
    }

    public void setRunAsRole(String role) {
        this._runAsRole = role;
    }

    protected void prepare(Request baseRequest, ServletRequest request, ServletResponse response) throws ServletException, UnavailableException {
        MultipartConfigElement mpce;
        this.getServlet();
        if (this._registration != null && (mpce = ((Registration)this._registration).getMultipartConfig()) != null) {
            baseRequest.setAttribute("org.eclipse.jetty.multipartConfig", mpce);
        }
    }

    @Deprecated
    public Servlet ensureInstance() throws ServletException {
        return this.getServlet();
    }

    public void handle(Request baseRequest, ServletRequest request, ServletResponse response) throws ServletException, UnavailableException, IOException {
        try {
            Servlet servlet = this.getServletInstance();
            if (servlet == null) {
                throw new UnavailableException("Servlet Not Initialized");
            }
            servlet.service(request, response);
        }
        catch (UnavailableException e) {
            this.makeUnavailable(e).service(request, response);
        }
    }

    protected boolean isJspServlet() {
        Class<Object> c;
        Servlet servlet = this.getServletInstance();
        Class<Object> clazz = c = servlet == null ? this.getHeldClass() : servlet.getClass();
        while (c != null) {
            if (this.isJspServlet(c.getName())) {
                return true;
            }
            c = c.getSuperclass();
        }
        return false;
    }

    protected boolean isJspServlet(String classname) {
        if (classname == null) {
            return false;
        }
        return "org.apache.jasper.servlet.JspServlet".equals(classname);
    }

    private void detectJspContainer() {
        if (this._jspContainer == null) {
            try {
                Loader.loadClass(APACHE_SENTINEL_CLASS);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Apache jasper detected", new Object[0]);
                }
                this._jspContainer = JspContainer.APACHE;
            }
            catch (ClassNotFoundException x) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Other jasper detected", new Object[0]);
                }
                this._jspContainer = JspContainer.OTHER;
            }
        }
    }

    public String getNameOfJspClass(String jsp) {
        if (StringUtil.isBlank(jsp)) {
            return "";
        }
        if ("/".equals(jsp = jsp.trim())) {
            return "";
        }
        int i = jsp.lastIndexOf(47);
        if (i == jsp.length() - 1) {
            return "";
        }
        jsp = jsp.substring(i + 1);
        try {
            Class jspUtil = Loader.loadClass("org.apache.jasper.compiler.JspUtil");
            Method makeJavaIdentifier = jspUtil.getMethod("makeJavaIdentifier", String.class);
            return (String)makeJavaIdentifier.invoke(null, jsp);
        }
        catch (Exception e) {
            String tmp = StringUtil.replace(jsp, '.', '_');
            if (LOG.isDebugEnabled()) {
                LOG.warn("JspUtil.makeJavaIdentifier failed for jsp " + jsp + " using " + tmp + " instead", new Object[0]);
                LOG.warn(e);
            }
            return tmp;
        }
    }

    public String getPackageOfJspClass(String jsp) {
        if (jsp == null) {
            return "";
        }
        int i = jsp.lastIndexOf(47);
        if (i <= 0) {
            return "";
        }
        try {
            Class jspUtil = Loader.loadClass("org.apache.jasper.compiler.JspUtil");
            Method makeJavaPackage = jspUtil.getMethod("makeJavaPackage", String.class);
            return (String)makeJavaPackage.invoke(null, jsp.substring(0, i));
        }
        catch (Exception e) {
            String tmp = jsp;
            int s = 0;
            if ('/' == tmp.charAt(0)) {
                s = 1;
            }
            tmp = tmp.substring(s, i).trim();
            String string = tmp = ".".equals(tmp = StringUtil.replace(tmp, '/', '.')) ? "" : tmp;
            if (LOG.isDebugEnabled()) {
                LOG.warn("JspUtil.makeJavaPackage failed for " + jsp + " using " + tmp + " instead", new Object[0]);
                LOG.warn(e);
            }
            return tmp;
        }
    }

    public String getJspPackagePrefix() {
        String jspPackageName = null;
        if (this.getServletHandler() != null && this.getServletHandler().getServletContext() != null) {
            jspPackageName = this.getServletHandler().getServletContext().getInitParameter(JSP_GENERATED_PACKAGE_NAME);
        }
        if (jspPackageName == null) {
            jspPackageName = "org.apache.jsp";
        }
        return jspPackageName;
    }

    public String getClassNameForJsp(String jsp) {
        if (jsp == null) {
            return null;
        }
        String name = this.getNameOfJspClass(jsp);
        if (StringUtil.isBlank(name)) {
            return null;
        }
        StringBuffer fullName = new StringBuffer();
        this.appendPath(fullName, this.getJspPackagePrefix());
        this.appendPath(fullName, this.getPackageOfJspClass(jsp));
        this.appendPath(fullName, name);
        return fullName.toString();
    }

    protected void appendPath(StringBuffer path, String element) {
        if (StringUtil.isBlank(element)) {
            return;
        }
        if (path.length() > 0) {
            path.append(".");
        }
        path.append(element);
    }

    public ServletRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    protected Servlet newInstance() throws ServletException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try {
            ServletContext ctx = this.getServletHandler().getServletContext();
            if (ctx != null) {
                return ctx.createServlet(this.getHeldClass());
            }
            return (Servlet)this.getHeldClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (ServletException ex) {
            Throwable cause = ex.getRootCause();
            if (cause instanceof InstantiationException) {
                throw (InstantiationException)cause;
            }
            if (cause instanceof IllegalAccessException) {
                throw (IllegalAccessException)cause;
            }
            if (cause instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)cause;
            }
            if (cause instanceof InvocationTargetException) {
                throw (InvocationTargetException)cause;
            }
            throw ex;
        }
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        if (this.getInitParameters().isEmpty()) {
            Dumpable.dumpObjects(out, indent, this, this._servlet == null ? this.getHeldClass() : this._servlet);
        } else {
            Dumpable.dumpObjects(out, indent, this, this._servlet == null ? this.getHeldClass() : this._servlet, new DumpableCollection("initParams", this.getInitParameters().entrySet()));
        }
    }

    @Override
    public String toString() {
        return String.format("%s==%s@%x{jsp=%s,order=%d,inst=%b,async=%b,src=%s}", this.getName(), this.getClassName(), this.hashCode(), this._forcedPath, this._initOrder, this._servlet != null, this.isAsyncSupported(), this.getSource());
    }

    private static class NotAsync
    extends Wrapper {
        public NotAsync(Servlet servlet) {
            super(servlet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            if (req.isAsyncSupported()) {
                Request baseRequest = Request.getBaseRequest(req);
                try {
                    baseRequest.setAsyncSupported(false, this.toString());
                    this.getWrapped().service(req, res);
                }
                finally {
                    baseRequest.setAsyncSupported(true, null);
                }
            } else {
                this.getWrapped().service(req, res);
            }
        }
    }

    private static class RunAs
    extends Wrapper {
        final IdentityService _identityService;
        final RunAsToken _runAsToken;

        public RunAs(Servlet servlet, IdentityService identityService, RunAsToken runAsToken) {
            super(servlet);
            this._identityService = identityService;
            this._runAsToken = runAsToken;
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().init(config);
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().service(req, res);
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }

        @Override
        public void destroy() {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().destroy();
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }
    }

    public static class Wrapper
    implements Servlet,
    BaseHolder.Wrapped<Servlet> {
        private final Servlet _wrappedServlet;

        public Wrapper(Servlet servlet) {
            this._wrappedServlet = Objects.requireNonNull(servlet, "Servlet cannot be null");
        }

        @Override
        public Servlet getWrapped() {
            return this._wrappedServlet;
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            this._wrappedServlet.init(config);
        }

        @Override
        public ServletConfig getServletConfig() {
            return this._wrappedServlet.getServletConfig();
        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            this._wrappedServlet.service(req, res);
        }

        @Override
        public String getServletInfo() {
            return this._wrappedServlet.getServletInfo();
        }

        @Override
        public void destroy() {
            this._wrappedServlet.destroy();
        }

        public String toString() {
            return String.format("%s:%s", this.getClass().getSimpleName(), this._wrappedServlet.toString());
        }
    }

    public static interface WrapFunction {
        public Servlet wrapServlet(Servlet var1);
    }

    private class UnavailableServlet
    extends Wrapper {
        final UnavailableException _unavailableException;
        final AtomicLong _unavailableStart;

        public UnavailableServlet(UnavailableException unavailableException, Servlet servlet) {
            super(servlet != null ? servlet : new GenericServlet(){

                @Override
                public void service(ServletRequest req, ServletResponse res) throws IOException {
                    ((HttpServletResponse)res).sendError(404);
                }
            });
            this._unavailableException = unavailableException;
            if (unavailableException.isPermanent()) {
                this._unavailableStart = null;
            } else {
                long start = System.nanoTime();
                while (start == 0L) {
                    start = System.nanoTime();
                }
                this._unavailableStart = new AtomicLong(start);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unavailable {}", req, this._unavailableException);
            }
            if (this._unavailableStart == null) {
                ((HttpServletResponse)res).sendError(404);
            } else {
                long start = this._unavailableStart.get();
                if (start == 0L || System.nanoTime() - start < TimeUnit.SECONDS.toNanos(this._unavailableException.getUnavailableSeconds())) {
                    ((HttpServletResponse)res).sendError(503);
                } else if (this._unavailableStart.compareAndSet(start, 0L)) {
                    UnavailableServlet unavailableServlet = this;
                    synchronized (unavailableServlet) {
                        ServletHolder.this._servlet = this.getWrapped();
                    }
                    Request baseRequest = Request.getBaseRequest(req);
                    ServletHolder.this.prepare(baseRequest, req, res);
                    ServletHolder.this.handle(baseRequest, req, res);
                } else {
                    ((HttpServletResponse)res).sendError(503);
                }
            }
        }

        public UnavailableException getUnavailableException() {
            return this._unavailableException;
        }
    }

    private class SingleThreadedWrapper
    implements Servlet {
        Stack<Servlet> _stack = new Stack();

        private SingleThreadedWrapper() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void destroy() {
            SingleThreadedWrapper singleThreadedWrapper = this;
            synchronized (singleThreadedWrapper) {
                while (this._stack.size() > 0) {
                    try {
                        this._stack.pop().destroy();
                    }
                    catch (Exception e) {
                        LOG.warn(e);
                    }
                }
            }
        }

        @Override
        public ServletConfig getServletConfig() {
            return ServletHolder.this._config;
        }

        @Override
        public String getServletInfo() {
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void init(ServletConfig config) throws ServletException {
            SingleThreadedWrapper singleThreadedWrapper = this;
            synchronized (singleThreadedWrapper) {
                if (this._stack.size() == 0) {
                    try {
                        Servlet s = ServletHolder.this.newInstance();
                        s.init(config);
                        this._stack.push(s);
                    }
                    catch (ServletException e) {
                        throw e;
                    }
                    catch (Exception e) {
                        throw new ServletException(e);
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            Servlet s;
            SingleThreadedWrapper singleThreadedWrapper = this;
            synchronized (singleThreadedWrapper) {
                if (this._stack.size() > 0) {
                    s = this._stack.pop();
                } else {
                    try {
                        s = ServletHolder.this.newInstance();
                        s.init(ServletHolder.this._config);
                    }
                    catch (ServletException e) {
                        throw e;
                    }
                    catch (Exception e) {
                        throw new ServletException(e);
                    }
                }
            }
            try {
                s.service(req, res);
            }
            finally {
                singleThreadedWrapper = this;
                synchronized (singleThreadedWrapper) {
                    this._stack.push(s);
                }
            }
        }
    }

    public class Registration
    extends Holder.HolderRegistration
    implements ServletRegistration.Dynamic {
        protected MultipartConfigElement _multipartConfig;

        @Override
        public Set<String> addMapping(String ... urlPatterns) {
            ServletHolder.this.illegalStateIfContextStarted();
            HashSet<String> clash = null;
            for (String pattern : urlPatterns) {
                ServletMapping mapping = ServletHolder.this.getServletHandler().getServletMapping(pattern);
                if (mapping == null || mapping.isDefault()) continue;
                if (clash == null) {
                    clash = new HashSet<String>();
                }
                clash.add(pattern);
            }
            if (clash != null) {
                return clash;
            }
            ServletMapping mapping = new ServletMapping(Source.JAVAX_API);
            mapping.setServletName(ServletHolder.this.getName());
            mapping.setPathSpecs(urlPatterns);
            ServletHolder.this.getServletHandler().addServletMapping(mapping);
            return Collections.emptySet();
        }

        @Override
        public Collection<String> getMappings() {
            ServletMapping[] mappings = ServletHolder.this.getServletHandler().getServletMappings();
            ArrayList<String> patterns = new ArrayList<String>();
            if (mappings != null) {
                for (ServletMapping mapping : mappings) {
                    String[] specs;
                    if (!mapping.getServletName().equals(this.getName()) || (specs = mapping.getPathSpecs()) == null || specs.length <= 0) continue;
                    patterns.addAll(Arrays.asList(specs));
                }
            }
            return patterns;
        }

        @Override
        public String getRunAsRole() {
            return ServletHolder.this._runAsRole;
        }

        @Override
        public void setLoadOnStartup(int loadOnStartup) {
            ServletHolder.this.illegalStateIfContextStarted();
            ServletHolder.this.setInitOrder(loadOnStartup);
        }

        public int getInitOrder() {
            return ServletHolder.this.getInitOrder();
        }

        @Override
        public void setMultipartConfig(MultipartConfigElement element) {
            this._multipartConfig = element;
        }

        public MultipartConfigElement getMultipartConfig() {
            return this._multipartConfig;
        }

        @Override
        public void setRunAsRole(String role) {
            ServletHolder.this._runAsRole = role;
        }

        @Override
        public Set<String> setServletSecurity(ServletSecurityElement securityElement) {
            return ServletHolder.this.getServletHandler().setServletSecurity(this, securityElement);
        }
    }

    protected class Config
    extends Holder.HolderConfig
    implements ServletConfig {
        protected Config() {
        }

        @Override
        public String getServletName() {
            return ServletHolder.this.getName();
        }
    }

    public static enum JspContainer {
        APACHE,
        OTHER;

    }
}

