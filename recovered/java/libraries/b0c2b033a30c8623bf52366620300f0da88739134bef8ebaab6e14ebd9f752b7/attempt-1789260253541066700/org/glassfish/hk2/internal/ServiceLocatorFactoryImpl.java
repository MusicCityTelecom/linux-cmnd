/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.api.ServiceLocatorListener;
import org.glassfish.hk2.extension.ServiceLocatorGenerator;
import org.glassfish.hk2.utilities.reflection.Logger;

public class ServiceLocatorFactoryImpl
extends ServiceLocatorFactory {
    private static final String DEBUG_SERVICE_LOCATOR_PROPERTY = "org.jvnet.hk2.properties.debug.service.locator.lifecycle";
    private static final boolean DEBUG_SERVICE_LOCATOR_LIFECYCLE = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return Boolean.parseBoolean(System.getProperty(ServiceLocatorFactoryImpl.DEBUG_SERVICE_LOCATOR_PROPERTY, "false"));
        }
    });
    private static final Object sLock = new Object();
    private static int name_count = 0;
    private static final String GENERATED_NAME_PREFIX = "__HK2_Generated_";
    private final Object lock = new Object();
    private final HashMap<String, ServiceLocator> serviceLocators = new HashMap();
    private final HashSet<ServiceLocatorListener> listeners = new HashSet();

    private static ServiceLocatorGenerator getGeneratorSecure() {
        return AccessController.doPrivileged(new PrivilegedAction<ServiceLocatorGenerator>(){

            @Override
            public ServiceLocatorGenerator run() {
                try {
                    return ServiceLocatorFactoryImpl.getGenerator();
                }
                catch (Throwable th) {
                    Logger.getLogger().warning("Error finding implementation of hk2:", th);
                    return null;
                }
            }
        });
    }

    private static Iterable<? extends ServiceLocatorGenerator> getOSGiSafeGenerators() {
        try {
            return org.glassfish.hk2.osgiresourcelocator.ServiceLoader.lookupProviderInstances(ServiceLocatorGenerator.class);
        }
        catch (Throwable th) {
            return null;
        }
    }

    private static ServiceLocatorGenerator getGenerator() {
        Iterable<? extends ServiceLocatorGenerator> generators = ServiceLocatorFactoryImpl.getOSGiSafeGenerators();
        if (generators != null) {
            Iterator<? extends ServiceLocatorGenerator> iterator = generators.iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }
        ClassLoader classLoader = ServiceLocatorFactoryImpl.class.getClassLoader();
        Iterator<ServiceLocatorGenerator> providers = ServiceLoader.load(ServiceLocatorGenerator.class, classLoader).iterator();
        while (providers.hasNext()) {
            try {
                return providers.next();
            }
            catch (ServiceConfigurationError sce) {
                Logger.getLogger().debug("ServiceLocatorFactoryImpl", "getGenerator", sce);
            }
        }
        Logger.getLogger().warning("Cannot find a default implementation of the HK2 ServiceLocatorGenerator");
        return null;
    }

    @Override
    public ServiceLocator create(String name) {
        return this.create(name, null, null, ServiceLocatorFactory.CreatePolicy.RETURN);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServiceLocator find(String name) {
        Object object = this.lock;
        synchronized (object) {
            return this.serviceLocators.get(name);
        }
    }

    @Override
    public void destroy(String name) {
        this.destroy(name, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void destroy(String name, ServiceLocator locator) {
        ServiceLocator killMe = null;
        Object object = this.lock;
        synchronized (object) {
            if (name != null) {
                killMe = this.serviceLocators.remove(name);
            }
            if (DEBUG_SERVICE_LOCATOR_LIFECYCLE) {
                Logger.getLogger().debug("ServiceFactoryImpl destroying locator with name " + name + " and locator " + locator + " with found locator " + killMe, new Throwable());
            }
            if (killMe == null) {
                killMe = locator;
            }
            if (killMe != null) {
                for (ServiceLocatorListener listener : this.listeners) {
                    try {
                        listener.locatorDestroyed(killMe);
                    }
                    catch (Throwable th) {
                        Logger.getLogger().debug(this.getClass().getName(), "destroy " + listener, th);
                    }
                }
            }
        }
        if (killMe != null) {
            killMe.shutdown();
        }
    }

    @Override
    public void destroy(ServiceLocator locator) {
        if (locator == null) {
            return;
        }
        this.destroy(locator.getName(), locator);
    }

    @Override
    public ServiceLocator create(String name, ServiceLocator parent) {
        return this.create(name, parent, null, ServiceLocatorFactory.CreatePolicy.RETURN);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String getGeneratedName() {
        Object object = sLock;
        synchronized (object) {
            return GENERATED_NAME_PREFIX + name_count++;
        }
    }

    @Override
    public ServiceLocator create(String name, ServiceLocator parent, ServiceLocatorGenerator generator) {
        return this.create(name, parent, generator, ServiceLocatorFactory.CreatePolicy.RETURN);
    }

    private void callListenerAdded(ServiceLocator added) {
        for (ServiceLocatorListener listener : this.listeners) {
            try {
                listener.locatorAdded(added);
            }
            catch (Throwable th) {
                Logger.getLogger().debug(this.getClass().getName(), "create " + listener, th);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ServiceLocator create(String name, ServiceLocator parent, ServiceLocatorGenerator generator, ServiceLocatorFactory.CreatePolicy policy) {
        if (DEBUG_SERVICE_LOCATOR_LIFECYCLE) {
            Logger.getLogger().debug("ServiceFactoryImpl given create of " + name + " with parent " + parent + " with generator " + generator + " and policy " + (Object)((Object)policy), new Throwable());
        }
        Object object = this.lock;
        synchronized (object) {
            if (name == null) {
                name = ServiceLocatorFactoryImpl.getGeneratedName();
                ServiceLocator added = this.internalCreate(name, parent, generator);
                this.callListenerAdded(added);
                if (DEBUG_SERVICE_LOCATOR_LIFECYCLE) {
                    Logger.getLogger().debug("ServiceFactoryImpl added untracked listener " + added);
                }
                return added;
            }
            ServiceLocator retVal = this.serviceLocators.get(name);
            if (retVal != null) {
                if (policy == null || ServiceLocatorFactory.CreatePolicy.RETURN.equals((Object)policy)) {
                    if (DEBUG_SERVICE_LOCATOR_LIFECYCLE) {
                        Logger.getLogger().debug("ServiceFactoryImpl added found listener under RETURN policy of " + retVal);
                    }
                    return retVal;
                }
                if (policy.equals((Object)ServiceLocatorFactory.CreatePolicy.DESTROY)) {
                    this.destroy(retVal);
                } else {
                    throw new IllegalStateException("A ServiceLocator named " + name + " already exists");
                }
            }
            retVal = this.internalCreate(name, parent, generator);
            this.serviceLocators.put(name, retVal);
            this.callListenerAdded(retVal);
            if (DEBUG_SERVICE_LOCATOR_LIFECYCLE) {
                Logger.getLogger().debug("ServiceFactoryImpl created locator " + retVal);
            }
            return retVal;
        }
    }

    private ServiceLocator internalCreate(String name, ServiceLocator parent, ServiceLocatorGenerator generator) {
        if (generator == null) {
            if (DefaultGeneratorInitializer.defaultGenerator == null) {
                throw new IllegalStateException("No generator was provided and there is no default generator registered");
            }
            generator = DefaultGeneratorInitializer.defaultGenerator;
        }
        return generator.create(name, parent);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addListener(ServiceLocatorListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException();
        }
        Object object = this.lock;
        synchronized (object) {
            if (this.listeners.contains(listener)) {
                return;
            }
            try {
                HashSet<ServiceLocator> currentLocators = new HashSet<ServiceLocator>(this.serviceLocators.values());
                listener.initialize(Collections.unmodifiableSet(currentLocators));
            }
            catch (Throwable th) {
                Logger.getLogger().debug(this.getClass().getName(), "addListener " + listener, th);
                return;
            }
            this.listeners.add(listener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeListener(ServiceLocatorListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException();
        }
        Object object = this.lock;
        synchronized (object) {
            this.listeners.remove(listener);
        }
    }

    static /* synthetic */ ServiceLocatorGenerator access$000() {
        return ServiceLocatorFactoryImpl.getGeneratorSecure();
    }

    private static final class DefaultGeneratorInitializer {
        private static final ServiceLocatorGenerator defaultGenerator = ServiceLocatorFactoryImpl.access$000();

        private DefaultGeneratorInitializer() {
        }
    }
}

