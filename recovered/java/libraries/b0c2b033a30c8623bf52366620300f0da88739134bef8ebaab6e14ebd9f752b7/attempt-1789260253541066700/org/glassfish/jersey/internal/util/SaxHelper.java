/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.security.AccessController;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public final class SaxHelper {
    private SaxHelper() {
    }

    public static boolean isXdkParserFactory(SAXParserFactory parserFactory) {
        return SaxHelper.isXdkFactory(parserFactory, "oracle.xml.jaxp.JXSAXParserFactory");
    }

    public static boolean isXdkDocumentBuilderFactory(DocumentBuilderFactory builderFactory) {
        return SaxHelper.isXdkFactory(builderFactory, "oracle.xml.jaxp.JXDocumentBuilderFactory");
    }

    private static boolean isXdkFactory(Object factory, String className) {
        Class xdkFactoryClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA(className, null));
        if (xdkFactoryClass == null) {
            return false;
        }
        return xdkFactoryClass.isAssignableFrom(factory.getClass());
    }
}

