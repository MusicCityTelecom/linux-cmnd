/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.ReflectPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.OsgiRegistry;
import org.glassfish.jersey.internal.ServiceConfigurationError;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public final class ServiceFinder<T>
implements Iterable<T> {
    private static final Logger LOGGER = Logger.getLogger(ServiceFinder.class.getName());
    private static final String PREFIX = "META-INF/services/";
    private final Class<T> serviceClass;
    private final String serviceName;
    private final ClassLoader classLoader;
    private final boolean ignoreOnClassNotFound;

    private static Enumeration<URL> getResources(ClassLoader loader, String name) throws IOException {
        if (loader == null) {
            return ServiceFinder.getResources(name);
        }
        Enumeration<URL> resources = loader.getResources(name);
        if (resources != null && resources.hasMoreElements()) {
            return resources;
        }
        return ServiceFinder.getResources(name);
    }

    private static Enumeration<URL> getResources(String name) throws IOException {
        if (ServiceFinder.class.getClassLoader() != null) {
            return ServiceFinder.class.getClassLoader().getResources(name);
        }
        return ClassLoader.getSystemResources(name);
    }

    private static ClassLoader _getContextClassLoader() {
        return AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
    }

    public static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader) throws ServiceConfigurationError {
        return ServiceFinder.find(service, loader, false);
    }

    public static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader, boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
        return new ServiceFinder<T>(service, loader, ignoreOnClassNotFound);
    }

    public static <T> ServiceFinder<T> find(Class<T> service) throws ServiceConfigurationError {
        return ServiceFinder.find(service, ServiceFinder._getContextClassLoader(), false);
    }

    public static <T> ServiceFinder<T> find(Class<T> service, boolean ignoreOnClassNotFound) throws ServiceConfigurationError {
        return ServiceFinder.find(service, ServiceFinder._getContextClassLoader(), ignoreOnClassNotFound);
    }

    public static ServiceFinder<?> find(String serviceName) throws ServiceConfigurationError {
        return new ServiceFinder<Object>(Object.class, serviceName, ServiceFinder._getContextClassLoader(), false);
    }

    public static void setIteratorProvider(ServiceIteratorProvider sip) throws SecurityException {
        ServiceIteratorProvider.setInstance(sip);
    }

    private ServiceFinder(Class<T> service, ClassLoader loader, boolean ignoreOnClassNotFound) {
        this(service, service.getName(), loader, ignoreOnClassNotFound);
    }

    private ServiceFinder(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
        this.serviceClass = service;
        this.serviceName = serviceName;
        this.classLoader = loader;
        this.ignoreOnClassNotFound = ignoreOnClassNotFound;
    }

    @Override
    public Iterator<T> iterator() {
        return ServiceIteratorProvider.getInstance().createIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
    }

    public T[] toArray() throws ServiceConfigurationError {
        ArrayList<T> result = new ArrayList<T>();
        for (T t : this) {
            result.add(t);
        }
        return result.toArray((Object[])Array.newInstance(this.serviceClass, result.size()));
    }

    public Class<T>[] toClassArray() throws ServiceConfigurationError {
        ArrayList<Class<T>> result = new ArrayList<Class<T>>();
        ServiceIteratorProvider iteratorProvider = ServiceIteratorProvider.getInstance();
        Iterator<Class<T>> i = iteratorProvider.createClassIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
        while (i.hasNext()) {
            result.add(i.next());
        }
        return result.toArray((Class[])Array.newInstance(Class.class, result.size()));
    }

    private static void fail(String serviceName, String msg, Throwable cause) throws ServiceConfigurationError {
        ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + msg);
        sce.initCause(cause);
        throw sce;
    }

    private static void fail(String serviceName, String msg) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(serviceName + ": " + msg);
    }

    private static void fail(String serviceName, URL u, int line, String msg) throws ServiceConfigurationError {
        ServiceFinder.fail(serviceName, u + ":" + line + ": " + msg);
    }

    private static int parseLine(String serviceName, URL u, BufferedReader r, int lc, List<String> names, Set<String> returned) throws IOException, ServiceConfigurationError {
        int n;
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf(35);
        if (ci >= 0) {
            ln = ln.substring(0, ci);
        }
        if ((n = (ln = ln.trim()).length()) != 0) {
            int cp;
            if (ln.indexOf(32) >= 0 || ln.indexOf(9) >= 0) {
                ServiceFinder.fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_CONFIG_SYNTAX());
            }
            if (!Character.isJavaIdentifierStart(cp = ln.codePointAt(0))) {
                ServiceFinder.fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
            }
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (Character.isJavaIdentifierPart(cp) || cp == 46) continue;
                ServiceFinder.fail(serviceName, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln));
            }
            if (!returned.contains(ln)) {
                names.add(ln);
                returned.add(ln);
            }
        }
        return lc + 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Iterator<String> parse(String serviceName, URL u, Set<String> returned) throws ServiceConfigurationError {
        InputStream in = null;
        BufferedReader r = null;
        ArrayList<String> names = new ArrayList<String>();
        try {
            URLConnection uConn = u.openConnection();
            uConn.setUseCaches(false);
            in = uConn.getInputStream();
            r = new BufferedReader(new InputStreamReader(in, "utf-8"));
            int lc = 1;
            while ((lc = ServiceFinder.parseLine(serviceName, u, r, lc, names, returned)) >= 0) {
            }
        }
        catch (IOException x) {
            ServiceFinder.fail(serviceName, ": " + x);
        }
        finally {
            try {
                if (r != null) {
                    r.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException y) {
                ServiceFinder.fail(serviceName, ": " + y);
            }
        }
        return names.iterator();
    }

    static {
        OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
        if (osgiRegistry != null) {
            LOGGER.log(Level.CONFIG, "Running in an OSGi environment");
            osgiRegistry.hookUp();
        } else {
            LOGGER.log(Level.CONFIG, "Running in a non-OSGi environment");
        }
    }

    public static final class DefaultServiceIteratorProvider
    extends ServiceIteratorProvider {
        @Override
        public <T> Iterator<T> createIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            return new LazyObjectIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }

        @Override
        public <T> Iterator<Class<T>> createClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            return new LazyClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }
    }

    public static abstract class ServiceIteratorProvider {
        private static volatile ServiceIteratorProvider sip;
        private static final Object sipLock;

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static ServiceIteratorProvider getInstance() {
            ServiceIteratorProvider result = sip;
            if (result == null) {
                Object object = sipLock;
                synchronized (object) {
                    result = sip;
                    if (result == null) {
                        sip = result = new DefaultServiceIteratorProvider();
                    }
                }
            }
            return result;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static void setInstance(ServiceIteratorProvider sip) throws SecurityException {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                ReflectPermission rp = new ReflectPermission("suppressAccessChecks");
                security.checkPermission(rp);
            }
            Object object = sipLock;
            synchronized (object) {
                ServiceIteratorProvider.sip = sip;
            }
        }

        public abstract <T> Iterator<T> createIterator(Class<T> var1, String var2, ClassLoader var3, boolean var4);

        public abstract <T> Iterator<Class<T>> createClassIterator(Class<T> var1, String var2, ClassLoader var3, boolean var4);

        static {
            sipLock = new Object();
        }
    }

    private static final class LazyObjectIterator<T>
    extends AbstractLazyIterator<T>
    implements Iterator<T> {
        private T t;

        private LazyObjectIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            super(service, serviceName, loader, ignoreOnClassNotFound);
        }

        @Override
        public boolean hasNext() throws ServiceConfigurationError {
            if (this.nextName != null) {
                return true;
            }
            this.setConfigs();
            while (this.nextName == null) {
                while (this.pending == null || !this.pending.hasNext()) {
                    if (!this.configs.hasMoreElements()) {
                        return false;
                    }
                    this.pending = ServiceFinder.parse(this.serviceName, (URL)this.configs.nextElement(), this.returned);
                }
                this.nextName = (String)this.pending.next();
                try {
                    this.t = this.service.cast(AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(this.nextName, this.loader)).newInstance());
                }
                catch (InstantiationException ex) {
                    if (this.ignoreOnClassNotFound) {
                        if (LOGGER.isLoggable(Level.CONFIG)) {
                            LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, ex.getLocalizedMessage()));
                        }
                        this.nextName = null;
                        continue;
                    }
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, ex.getLocalizedMessage()), ex);
                }
                catch (IllegalAccessException ex) {
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, ex.getLocalizedMessage()), ex);
                }
                catch (ClassNotFoundException ex) {
                    this.handleClassNotFoundException();
                }
                catch (NoClassDefFoundError ex) {
                    if (this.ignoreOnClassNotFound) {
                        if (LOGGER.isLoggable(Level.CONFIG)) {
                            LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                        continue;
                    }
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(ex.getLocalizedMessage(), this.nextName, this.service), ex);
                }
                catch (PrivilegedActionException pae) {
                    Throwable cause = pae.getCause();
                    if (cause instanceof ClassNotFoundException) {
                        this.handleClassNotFoundException();
                        continue;
                    }
                    if (cause instanceof ClassFormatError) {
                        if (this.ignoreOnClassNotFound) {
                            if (LOGGER.isLoggable(Level.CONFIG)) {
                                LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(cause.getLocalizedMessage(), this.nextName, this.service));
                            }
                            this.nextName = null;
                            continue;
                        }
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(cause.getLocalizedMessage(), this.nextName, this.service), cause);
                        continue;
                    }
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(this.nextName, this.service, cause.getLocalizedMessage()), cause);
                }
            }
            return true;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.nextName = null;
            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.log(Level.FINEST, "Loading next object: " + this.t.getClass().getName());
            }
            return this.t;
        }

        private void handleClassNotFoundException() throws ServiceConfigurationError {
            if (this.ignoreOnClassNotFound) {
                if (LOGGER.isLoggable(Level.CONFIG)) {
                    LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
                }
                this.nextName = null;
            } else {
                ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
            }
        }
    }

    private static final class LazyClassIterator<T>
    extends AbstractLazyIterator<T>
    implements Iterator<Class<T>> {
        private LazyClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            super(service, serviceName, loader, ignoreOnClassNotFound);
        }

        @Override
        public Class<T> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            String cn = this.nextName;
            this.nextName = null;
            try {
                Class tClass = AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(cn, this.loader));
                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "Loading next class: " + tClass.getName());
                }
                return tClass;
            }
            catch (ClassNotFoundException ex) {
                ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
            }
            catch (PrivilegedActionException pae) {
                Throwable thrown = pae.getCause();
                if (thrown instanceof ClassNotFoundException) {
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
                }
                if (thrown instanceof NoClassDefFoundError) {
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(thrown.getLocalizedMessage(), cn, this.service));
                }
                if (thrown instanceof ClassFormatError) {
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(thrown.getLocalizedMessage(), cn, this.service));
                }
                ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_CLASS_COULD_NOT_BE_LOADED(cn, this.service, thrown.getLocalizedMessage()), thrown);
            }
            return null;
        }
    }

    private static class AbstractLazyIterator<T> {
        final Class<T> service;
        final String serviceName;
        final ClassLoader loader;
        final boolean ignoreOnClassNotFound;
        Enumeration<URL> configs = null;
        Iterator<String> pending = null;
        Set<String> returned = new TreeSet<String>();
        String nextName = null;

        private AbstractLazyIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            this.service = service;
            this.serviceName = serviceName;
            this.loader = loader;
            this.ignoreOnClassNotFound = ignoreOnClassNotFound;
        }

        protected final void setConfigs() {
            if (this.configs == null) {
                try {
                    String fullName = ServiceFinder.PREFIX + this.serviceName;
                    this.configs = ServiceFinder.getResources(this.loader, fullName);
                }
                catch (IOException x) {
                    ServiceFinder.fail(this.serviceName, ": " + x);
                }
            }
        }

        public boolean hasNext() throws ServiceConfigurationError {
            if (this.nextName != null) {
                return true;
            }
            this.setConfigs();
            while (this.nextName == null) {
                while (this.pending == null || !this.pending.hasNext()) {
                    if (!this.configs.hasMoreElements()) {
                        return false;
                    }
                    this.pending = ServiceFinder.parse(this.serviceName, this.configs.nextElement(), this.returned);
                }
                this.nextName = this.pending.next();
                if (!this.ignoreOnClassNotFound) continue;
                try {
                    AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(this.nextName, this.loader));
                }
                catch (ClassNotFoundException ex) {
                    this.handleClassNotFoundException();
                }
                catch (PrivilegedActionException pae) {
                    Exception thrown = pae.getException();
                    if (thrown instanceof ClassNotFoundException) {
                        this.handleClassNotFoundException();
                        continue;
                    }
                    if (thrown instanceof NoClassDefFoundError) {
                        if (LOGGER.isLoggable(Level.CONFIG)) {
                            LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(thrown.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                        continue;
                    }
                    if (thrown instanceof ClassFormatError) {
                        if (LOGGER.isLoggable(Level.CONFIG)) {
                            LOGGER.log(Level.CONFIG, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(thrown.getLocalizedMessage(), this.nextName, this.service));
                        }
                        this.nextName = null;
                        continue;
                    }
                    if (thrown instanceof RuntimeException) {
                        throw (RuntimeException)thrown;
                    }
                    throw new IllegalStateException(thrown);
                }
            }
            return true;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void handleClassNotFoundException() {
            if (LOGGER.isLoggable(Level.CONFIG)) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
            }
            this.nextName = null;
        }
    }
}

