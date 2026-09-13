/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBContext
 *  javax.xml.bind.JAXBContextFactory
 *  javax.xml.bind.JAXBException
 */
package com.sun.xml.bind.v2;

import com.sun.xml.bind.v2.ContextFactory;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBContextFactory
implements javax.xml.bind.JAXBContextFactory {
    public JAXBContext createContext(Class<?>[] classesToBeBound, Map<String, ?> properties) throws JAXBException {
        return ContextFactory.createContext(classesToBeBound, properties);
    }

    public JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, ?> properties) throws JAXBException {
        return ContextFactory.createContext(contextPath, classLoader, properties);
    }
}

