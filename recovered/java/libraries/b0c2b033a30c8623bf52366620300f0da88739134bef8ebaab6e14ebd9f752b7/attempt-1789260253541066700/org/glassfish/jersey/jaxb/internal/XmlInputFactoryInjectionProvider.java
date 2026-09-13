/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import javax.xml.stream.XMLInputFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.jaxb.internal.AbstractXmlFactory;
import org.glassfish.jersey.jaxb.internal.JaxbFeatureUtil;

public class XmlInputFactoryInjectionProvider
extends AbstractXmlFactory<XMLInputFactory> {
    @Inject
    private InjectionManager injectionManager;

    @Inject
    public XmlInputFactoryInjectionProvider(Configuration config) {
        super(config);
    }

    @Override
    public XMLInputFactory get() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        if (!this.isXmlSecurityDisabled()) {
            factory.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.FALSE);
        }
        JaxbFeatureUtil.setProperties(this.injectionManager, XMLInputFactory.class, factory::setProperty);
        return factory;
    }
}

