/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.BundleReference
 *  org.osgi.framework.FrameworkUtil
 *  org.osgi.framework.SynchronousBundleListener
 */
package org.glassfish.jersey.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.ServiceConfigurationError;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.SynchronousBundleListener;

public final class OsgiRegistry
implements SynchronousBundleListener {
    private static final String WEB_INF_CLASSES = "WEB-INF/classes/";
    private static final String CoreBundleSymbolicNAME = "org.glassfish.jersey.core.jersey-common";
    private static final Logger LOGGER = Logger.getLogger(OsgiRegistry.class.getName());
    private final BundleContext bundleContext;
    private final Map<Long, Map<String, Callable<List<Class<?>>>>> factories = new HashMap();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static OsgiRegistry instance;
    private final Map<String, Bundle> classToBundleMapping = new HashMap<String, Bundle>();

    public static synchronized OsgiRegistry getInstance() {
        BundleContext context;
        ClassLoader classLoader;
        if (instance == null && (classLoader = AccessController.doPrivileged(ReflectionHelper.getClassLoaderPA(ReflectionHelper.class))) instanceof BundleReference && (context = FrameworkUtil.getBundle(OsgiRegistry.class).getBundleContext()) != null) {
            instance = new OsgiRegistry(context);
        }
        return instance;
    }

    public void bundleChanged(BundleEvent event) {
        if (event.getType() == 32) {
            this.register(event.getBundle());
        } else if (event.getType() == 64 || event.getType() == 16) {
            Bundle unregisteredBundle = event.getBundle();
            this.lock.writeLock().lock();
            try {
                this.factories.remove(unregisteredBundle.getBundleId());
                if (unregisteredBundle.getSymbolicName().equals(CoreBundleSymbolicNAME)) {
                    this.bundleContext.removeBundleListener((BundleListener)this);
                    this.factories.clear();
                }
            }
            finally {
                this.lock.writeLock().unlock();
            }
        }
    }

    public static String bundleEntryPathToClassName(String packagePath, String bundleEntryPath) {
        int packageIndex;
        packagePath = OsgiRegistry.normalizedPackagePath(packagePath);
        if (bundleEntryPath.contains(WEB_INF_CLASSES)) {
            bundleEntryPath = bundleEntryPath.substring(bundleEntryPath.indexOf(WEB_INF_CLASSES) + WEB_INF_CLASSES.length());
        }
        String normalizedClassNamePath = (packageIndex = bundleEntryPath.indexOf(packagePath)) > -1 ? bundleEntryPath.substring(packageIndex) : packagePath + bundleEntryPath.substring(bundleEntryPath.lastIndexOf(47) + 1);
        return (normalizedClassNamePath.startsWith("/") ? normalizedClassNamePath.substring(1) : normalizedClassNamePath).replace(".class", "").replace('/', '.');
    }

    public static boolean isPackageLevelEntry(String packagePath, String entryPath) {
        String entryWithoutPackagePath = entryPath.contains(packagePath = OsgiRegistry.normalizedPackagePath(packagePath)) ? entryPath.substring(entryPath.indexOf(packagePath) + packagePath.length()) : entryPath;
        return !(entryWithoutPackagePath.startsWith("/") ? entryWithoutPackagePath.substring(1) : entryWithoutPackagePath).contains("/");
    }

    public static String normalizedPackagePath(String packagePath) {
        packagePath = packagePath.startsWith("/") ? packagePath.substring(1) : packagePath;
        packagePath = packagePath.endsWith("/") ? packagePath : packagePath + "/";
        packagePath = "/".equals(packagePath) ? "" : packagePath;
        return packagePath;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Enumeration<URL> getPackageResources(String packagePath, ClassLoader classLoader, boolean recursive) {
        LinkedList<URL> result = new LinkedList<URL>();
        for (Bundle bundle : this.bundleContext.getBundles()) {
            for (String bundlePackagePath : new String[]{packagePath, WEB_INF_CLASSES + packagePath}) {
                Enumeration<URL> enumeration = OsgiRegistry.findEntries(bundle, bundlePackagePath, "*.class", recursive);
                if (enumeration == null) continue;
                while (enumeration.hasMoreElements()) {
                    URL url = enumeration.nextElement();
                    String path = url.getPath();
                    this.classToBundleMapping.put(OsgiRegistry.bundleEntryPathToClassName(packagePath, path), bundle);
                    result.add(url);
                }
            }
            Enumeration<URL> jars = OsgiRegistry.findEntries(bundle, "/", "*.jar", true);
            if (jars == null) continue;
            while (jars.hasMoreElements()) {
                JarInputStream jarInputStream;
                URL jar = jars.nextElement();
                InputStream inputStream = classLoader.getResourceAsStream(jar.getPath());
                if (inputStream == null) {
                    LOGGER.config(LocalizationMessages.OSGI_REGISTRY_ERROR_OPENING_RESOURCE_STREAM(jar));
                    continue;
                }
                try {
                    jarInputStream = new JarInputStream(inputStream);
                }
                catch (IOException ex) {
                    LOGGER.log(Level.CONFIG, LocalizationMessages.OSGI_REGISTRY_ERROR_PROCESSING_RESOURCE_STREAM(jar), ex);
                    try {
                        inputStream.close();
                    }
                    catch (IOException url) {}
                    continue;
                }
                try {
                    JarEntry jarEntry;
                    while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                        String jarEntryNameLeadingSlash;
                        String jarEntryName = jarEntry.getName();
                        String string = jarEntryNameLeadingSlash = jarEntryName.startsWith("/") ? jarEntryName : "/" + jarEntryName;
                        if (!jarEntryName.endsWith(".class") || !jarEntryNameLeadingSlash.contains("/" + OsgiRegistry.normalizedPackagePath(packagePath)) || !recursive && !OsgiRegistry.isPackageLevelEntry(packagePath, jarEntryName)) continue;
                        this.classToBundleMapping.put(jarEntryName.replace(".class", "").replace('/', '.'), bundle);
                        result.add(bundle.getResource(jarEntryName));
                    }
                }
                catch (Exception ex) {
                    LOGGER.log(Level.CONFIG, LocalizationMessages.OSGI_REGISTRY_ERROR_PROCESSING_RESOURCE_STREAM(jar), ex);
                }
                finally {
                    try {
                        jarInputStream.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        return Collections.enumeration(result);
    }

    public Class<?> classForNameWithException(String className) throws ClassNotFoundException {
        Bundle bundle = this.classToBundleMapping.get(className);
        if (bundle == null) {
            throw new ClassNotFoundException(className);
        }
        return OsgiRegistry.loadClass(bundle, className);
    }

    public ResourceBundle getResourceBundle(String bundleName) {
        int lastDotIndex = bundleName.lastIndexOf(46);
        String path = bundleName.substring(0, lastDotIndex).replace('.', '/');
        String propertiesName = bundleName.substring(lastDotIndex + 1, bundleName.length()) + ".properties";
        for (Bundle bundle : this.bundleContext.getBundles()) {
            Enumeration<URL> entries = OsgiRegistry.findEntries(bundle, path, propertiesName, false);
            if (entries == null || !entries.hasMoreElements()) continue;
            URL entryUrl = entries.nextElement();
            try {
                return new PropertyResourceBundle(entryUrl.openStream());
            }
            catch (IOException ex) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine("Exception caught when tried to load resource bundle in OSGi");
                }
                return null;
            }
        }
        return null;
    }

    private OsgiRegistry(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    void hookUp() {
        this.setOSGiServiceFinderIteratorProvider();
        this.bundleContext.addBundleListener((BundleListener)this);
        this.registerExistingBundles();
    }

    private void registerExistingBundles() {
        for (Bundle bundle : this.bundleContext.getBundles()) {
            if (bundle.getState() != 4 && bundle.getState() != 8 && bundle.getState() != 32 && bundle.getState() != 16) continue;
            this.register(bundle);
        }
    }

    private void setOSGiServiceFinderIteratorProvider() {
        ServiceFinder.setIteratorProvider(new OsgiServiceFinder());
    }

    private void register(Bundle bundle) {
        Map<String, Callable<List<Class<?>>>> map;
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "checking bundle {0}", bundle.getBundleId());
        }
        this.lock.writeLock().lock();
        try {
            map = this.factories.get(bundle.getBundleId());
            if (map == null) {
                map = new ConcurrentHashMap();
                this.factories.put(bundle.getBundleId(), map);
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
        Enumeration<URL> e = OsgiRegistry.findEntries(bundle, "META-INF/services/", "*", false);
        if (e != null) {
            while (e.hasMoreElements()) {
                URL u = e.nextElement();
                String url = u.toString();
                if (url.endsWith("/")) continue;
                String factoryId = url.substring(url.lastIndexOf("/") + 1);
                map.put(factoryId, new BundleSpiProvidersLoader(factoryId, u, bundle));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Class<?>> locateAllProviders(String serviceName) {
        this.lock.readLock().lock();
        try {
            LinkedList result = new LinkedList();
            for (Map<String, Callable<List<Class<?>>>> value : this.factories.values()) {
                if (!value.containsKey(serviceName)) continue;
                try {
                    result.addAll(value.get(serviceName).call());
                }
                catch (Exception exception) {}
            }
            LinkedList linkedList = result;
            return linkedList;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    private static Class<?> loadClass(final Bundle bundle, final String className) throws ClassNotFoundException {
        try {
            return (Class)AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>(){

                @Override
                public Class<?> run() throws ClassNotFoundException {
                    return bundle.loadClass(className);
                }
            });
        }
        catch (PrivilegedActionException ex) {
            Exception originalException = ex.getException();
            if (originalException instanceof ClassNotFoundException) {
                throw (ClassNotFoundException)originalException;
            }
            if (originalException instanceof RuntimeException) {
                throw (RuntimeException)originalException;
            }
            throw new ProcessingException(originalException);
        }
    }

    private static Enumeration<URL> findEntries(final Bundle bundle, final String path, final String fileNamePattern, final boolean recursive) {
        return AccessController.doPrivileged(new PrivilegedAction<Enumeration<URL>>(){

            @Override
            public Enumeration<URL> run() {
                return bundle.findEntries(path, fileNamePattern, recursive);
            }
        });
    }

    private static class BundleSpiProvidersLoader
    implements Callable<List<Class<?>>> {
        private final String spi;
        private final URL spiRegistryUrl;
        private final String spiRegistryUrlString;
        private final Bundle bundle;

        BundleSpiProvidersLoader(String spi, URL spiRegistryUrl, Bundle bundle) {
            this.spi = spi;
            this.spiRegistryUrl = spiRegistryUrl;
            this.spiRegistryUrlString = spiRegistryUrl.toExternalForm();
            this.bundle = bundle;
        }

        @Override
        public List<Class<?>> call() throws Exception {
            BufferedReader reader = null;
            try {
                String providerClassName;
                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "Loading providers for SPI: {0}", this.spi);
                }
                reader = new BufferedReader(new InputStreamReader(this.spiRegistryUrl.openStream(), "UTF-8"));
                ArrayList providerClasses = new ArrayList();
                while ((providerClassName = reader.readLine()) != null) {
                    if (providerClassName.trim().length() == 0 || providerClassName.startsWith("#")) continue;
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.log(Level.FINEST, "SPI provider: {0}", providerClassName);
                    }
                    providerClasses.add(OsgiRegistry.loadClass(this.bundle, providerClassName));
                }
                ArrayList arrayList = providerClasses;
                return arrayList;
            }
            catch (Exception e) {
                LOGGER.log(Level.WARNING, LocalizationMessages.EXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), e);
                throw e;
            }
            catch (Error e) {
                LOGGER.log(Level.WARNING, LocalizationMessages.ERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), e);
                throw e;
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (IOException ioe) {
                        LOGGER.log(Level.FINE, "Error closing SPI registry stream:" + this.spiRegistryUrl, ioe);
                    }
                }
            }
        }

        public String toString() {
            return this.spiRegistryUrlString;
        }

        public int hashCode() {
            return this.spiRegistryUrlString.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof BundleSpiProvidersLoader) {
                return this.spiRegistryUrlString.equals(((BundleSpiProvidersLoader)obj).spiRegistryUrlString);
            }
            return false;
        }
    }

    private final class OsgiServiceFinder
    extends ServiceFinder.ServiceIteratorProvider {
        final ServiceFinder.ServiceIteratorProvider defaultIterator = new ServiceFinder.DefaultServiceIteratorProvider();

        private OsgiServiceFinder() {
        }

        @Override
        public <T> Iterator<T> createIterator(final Class<T> serviceClass, final String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            final List providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
            if (!providerClasses.isEmpty()) {
                return new Iterator<T>(){
                    Iterator<Class<?>> it;
                    {
                        this.it = providerClasses.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.it.hasNext();
                    }

                    @Override
                    public T next() {
                        Class<?> nextClass = this.it.next();
                        try {
                            return nextClass.newInstance();
                        }
                        catch (Exception ex) {
                            ServiceConfigurationError sce = new ServiceConfigurationError(serviceName + ": " + LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(nextClass.getName(), serviceClass, ex.getLocalizedMessage()));
                            sce.initCause(ex);
                            throw sce;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            return this.defaultIterator.createIterator(serviceClass, serviceName, loader, ignoreOnClassNotFound);
        }

        @Override
        public <T> Iterator<Class<T>> createClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            final List providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
            if (!providerClasses.isEmpty()) {
                return new Iterator<Class<T>>(){
                    Iterator<Class<?>> it;
                    {
                        this.it = providerClasses.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.it.hasNext();
                    }

                    @Override
                    public Class<T> next() {
                        return this.it.next();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            return this.defaultIterator.createClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }
    }
}

