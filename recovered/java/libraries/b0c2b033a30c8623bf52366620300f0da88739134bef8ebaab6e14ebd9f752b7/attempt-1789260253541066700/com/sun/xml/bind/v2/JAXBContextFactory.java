/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2;

import com.sun.xml.bind.v2.ContextFactory;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBContextFactory
implements javax.xml.bind.JAXBContextFactory {
    @Override
    public JAXBContext createContext(Class<?>[] classesToBeBound, Map<String, ?> properties) throws JAXBException {
        return ContextFactory.createContext(classesToBeBound, properties);
    }

    @Override
    public JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, ?> properties) throws JAXBException {
        return ContextFactory.createContext(contextPath, classLoader, properties);
    }
}

