/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import javax.xml.parsers.DocumentBuilderFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.jaxb.internal.AbstractXmlFactory;
import org.glassfish.jersey.jaxb.internal.JaxbFeatureUtil;

public class DocumentBuilderFactoryInjectionProvider
extends AbstractXmlFactory<DocumentBuilderFactory> {
    @Inject
    private InjectionManager injectionManager;

    @Inject
    public DocumentBuilderFactoryInjectionProvider(Configuration config) {
        super(config);
    }

    @Override
    public DocumentBuilderFactory get() {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        if (!this.isXmlSecurityDisabled()) {
            f.setExpandEntityReferences(false);
        }
        JaxbFeatureUtil.setProperties(this.injectionManager, DocumentBuilderFactory.class, f::setAttribute);
        return f;
    }
}

