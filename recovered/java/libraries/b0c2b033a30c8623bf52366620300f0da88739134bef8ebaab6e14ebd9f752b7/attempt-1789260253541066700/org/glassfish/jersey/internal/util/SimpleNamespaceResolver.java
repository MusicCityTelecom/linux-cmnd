/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class SimpleNamespaceResolver
implements NamespaceContext {
    private final String prefix;
    private final String nsURI;

    public SimpleNamespaceResolver(String prefix, String nsURI) {
        this.prefix = prefix;
        this.nsURI = nsURI;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix.equals(this.prefix)) {
            return this.nsURI;
        }
        return "";
    }

    @Override
    public String getPrefix(String namespaceURI) {
        if (namespaceURI.equals(this.nsURI)) {
            return this.prefix;
        }
        return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }
}

