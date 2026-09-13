/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Properties;
import javax.el.ELException;

class FactoryFinder {
    FactoryFinder() {
    }

    private static Object newInstance(String className, ClassLoader classLoader, Properties properties) {
        try {
            Class<?> spiClass = classLoader == null ? Class.forName(className) : classLoader.loadClass(className);
            if (properties != null) {
                Constructor<?> constr = null;
                try {
                    constr = spiClass.getConstructor(Properties.class);
                }
                catch (Exception ex) {
                    // empty catch block
                }
                if (constr != null) {
                    return constr.newInstance(properties);
                }
            }
            return spiClass.newInstance();
        }
        catch (ClassNotFoundException x) {
            throw new ELException("Provider " + className + " not found", x);
        }
        catch (Exception x) {
            throw new ELException("Provider " + className + " could not be instantiated: " + x, x);
        }
    }

    static Object find(String factoryId, String fallbackClassName, Properties properties) {
        ClassLoader classLoader;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        catch (Exception x) {
            throw new ELException(x.toString(), x);
        }
        String serviceId = "META-INF/services/" + factoryId;
        try {
            InputStream is = null;
            is = classLoader == null ? ClassLoader.getSystemResourceAsStream(serviceId) : classLoader.getResourceAsStream(serviceId);
            if (is != null) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String factoryClassName = rd.readLine();
                rd.close();
                if (factoryClassName != null && !"".equals(factoryClassName)) {
                    return FactoryFinder.newInstance(factoryClassName, classLoader, properties);
                }
            }
        }
        catch (Exception ex) {
            // empty catch block
        }
        try {
            String javah = System.getProperty("java.home");
            String configFile = javah + File.separator + "lib" + File.separator + "el.properties";
            File f = new File(configFile);
            if (f.exists()) {
                Properties props = new Properties();
                props.load(new FileInputStream(f));
                String factoryClassName = props.getProperty(factoryId);
                return FactoryFinder.newInstance(factoryClassName, classLoader, properties);
            }
        }
        catch (Exception ex) {
            // empty catch block
        }
        try {
            String systemProp = System.getProperty(factoryId);
            if (systemProp != null) {
                return FactoryFinder.newInstance(systemProp, classLoader, properties);
            }
        }
        catch (SecurityException se) {
            // empty catch block
        }
        if (fallbackClassName == null) {
            throw new ELException("Provider for " + factoryId + " cannot be found", null);
        }
        return FactoryFinder.newInstance(fallbackClassName, classLoader, properties);
    }
}

