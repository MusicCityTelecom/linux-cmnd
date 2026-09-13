/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.Properties;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

final class FactoryFinder {
    private static final Logger LOGGER = Logger.getLogger(FactoryFinder.class.getName());

    private FactoryFinder() {
    }

    static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(() -> {
            ClassLoader cl = null;
            try {
                cl = Thread.currentThread().getContextClassLoader();
            }
            catch (SecurityException ex) {
                LOGGER.log(Level.WARNING, "Unable to get context classloader instance.", ex);
            }
            return cl;
        });
    }

    private static Object newInstance(String className, ClassLoader classLoader) throws ClassNotFoundException {
        try {
            Class<?> spiClass;
            if (classLoader == null) {
                spiClass = Class.forName(className);
            } else {
                try {
                    spiClass = Class.forName(className, false, classLoader);
                }
                catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.FINE, "Unable to load provider class " + className + " using custom classloader " + classLoader.getClass().getName() + " trying again with current classloader.", ex);
                    spiClass = Class.forName(className);
                }
            }
            return spiClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (ClassNotFoundException x) {
            throw x;
        }
        catch (Exception x) {
            throw new ClassNotFoundException("Provider " + className + " could not be instantiated: " + x, x);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    static <T> Object find(String factoryId, String fallbackClassName, Class<T> service) throws ClassNotFoundException {
        block27: {
            block25: {
                block26: {
                    classLoader = FactoryFinder.getContextClassLoader();
                    try {
                        iterator = ServiceLoader.load(service, FactoryFinder.getContextClassLoader()).iterator();
                        if (iterator.hasNext()) {
                            return iterator.next();
                        }
                    }
                    catch (Exception | ServiceConfigurationError ex) {
                        FactoryFinder.LOGGER.log(Level.FINER, "Failed to load service " + factoryId + ".", ex);
                    }
                    try {
                        iterator = ServiceLoader.load(service, FactoryFinder.class.getClassLoader()).iterator();
                        if (iterator.hasNext()) {
                            return iterator.next();
                        }
                    }
                    catch (Exception | ServiceConfigurationError ex) {
                        FactoryFinder.LOGGER.log(Level.FINER, "Failed to load service " + factoryId + ".", ex);
                    }
                    inputStream = null;
                    configFile = null;
                    javah = System.getProperty("java.home");
                    configFile = javah + File.separator + "lib" + File.separator + "jaxrs.properties";
                    f = new File(configFile);
                    if (!f.exists()) break block25;
                    props = new Properties();
                    inputStream = new FileInputStream(f);
                    props.load(inputStream);
                    factoryClassName = props.getProperty(factoryId);
                    var10_16 = FactoryFinder.newInstance(factoryClassName, classLoader);
                    if (inputStream == null) break block26;
                    try {
                        inputStream.close();
                    }
                    catch (IOException ex) {
                        FactoryFinder.LOGGER.log(Level.FINER, String.format("Error closing %s file.", new Object[]{configFile}), ex);
                    }
                }
                return var10_16;
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException ex) {
                    FactoryFinder.LOGGER.log(Level.FINER, String.format("Error closing %s file.", new Object[]{configFile}), ex);
                }
            }
            break block27;
            catch (Exception ex) {
                try {
                    FactoryFinder.LOGGER.log(Level.FINER, "Failed to load service " + factoryId + " from $java.home/lib/jaxrs.properties", ex);
                    ** if (inputStream == null) goto lbl-1000
                }
                catch (Throwable var12_18) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (IOException ex) {
                            FactoryFinder.LOGGER.log(Level.FINER, String.format("Error closing %s file.", new Object[]{configFile}), ex);
                        }
                    }
                    throw var12_18;
                }
lbl-1000:
                // 1 sources

                {
                    try {
                        inputStream.close();
                    }
                    catch (IOException ex) {
                        FactoryFinder.LOGGER.log(Level.FINER, String.format("Error closing %s file.", new Object[]{configFile}), ex);
                    }
                }
lbl-1000:
                // 2 sources

                {
                }
            }
        }
        try {
            systemProp = System.getProperty(factoryId);
            if (systemProp != null) {
                return FactoryFinder.newInstance(systemProp, classLoader);
            }
        }
        catch (SecurityException se) {
            FactoryFinder.LOGGER.log(Level.FINER, "Failed to load service " + factoryId + " from a system property", se);
        }
        if (fallbackClassName == null) {
            throw new ClassNotFoundException("Provider for " + factoryId + " cannot be found", null);
        }
        return FactoryFinder.newInstance(fallbackClassName, classLoader);
    }
}

