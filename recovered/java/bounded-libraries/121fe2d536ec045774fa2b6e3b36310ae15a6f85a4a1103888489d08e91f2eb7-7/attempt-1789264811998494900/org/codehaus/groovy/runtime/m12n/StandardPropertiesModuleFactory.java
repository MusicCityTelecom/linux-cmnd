/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.m12n;

import groovy.lang.GroovyRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.codehaus.groovy.runtime.m12n.ExtensionModule;
import org.codehaus.groovy.runtime.m12n.MetaInfExtensionModule;
import org.codehaus.groovy.runtime.m12n.PropertiesModuleFactory;

public class StandardPropertiesModuleFactory
extends PropertiesModuleFactory {
    public static final String MODULE_FACTORY_KEY = "moduleFactory";

    @Override
    public ExtensionModule newModule(Properties properties, ClassLoader classLoader) {
        String factoryName = properties.getProperty(MODULE_FACTORY_KEY);
        if (factoryName != null) {
            try {
                Class<?> factoryClass = classLoader.loadClass(factoryName);
                PropertiesModuleFactory delegate = (PropertiesModuleFactory)factoryClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                return delegate.newModule(properties, classLoader);
            }
            catch (ClassNotFoundException | NoSuchMethodException e) {
                throw new GroovyRuntimeException("Unable to load module factory [" + factoryName + "]", e);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new GroovyRuntimeException("Unable to instantiate module factory [" + factoryName + "]", e);
            }
        }
        return MetaInfExtensionModule.newModule(properties, classLoader);
    }
}

