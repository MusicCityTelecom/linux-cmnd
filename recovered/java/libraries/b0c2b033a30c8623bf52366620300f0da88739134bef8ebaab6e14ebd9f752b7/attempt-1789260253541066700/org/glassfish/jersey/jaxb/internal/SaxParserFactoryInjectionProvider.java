/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.util.LinkedHashMap;
import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.jaxb.internal.AbstractXmlFactory;
import org.glassfish.jersey.jaxb.internal.JaxbFeatureUtil;
import org.glassfish.jersey.jaxb.internal.SecureSaxParserFactory;

public class SaxParserFactoryInjectionProvider
extends AbstractXmlFactory<SAXParserFactory> {
    @Inject
    private InjectionManager injectionManager;

    @Inject
    public SaxParserFactoryInjectionProvider(Configuration config) {
        super(config);
    }

    @Override
    public SAXParserFactory get() {
        SecureSaxParserFactory factory = new SecureSaxParserFactory(SAXParserFactory.newInstance(), !this.isXmlSecurityDisabled());
        factory.setNamespaceAware(true);
        LinkedHashMap<String, Object> saxParserProperties = new LinkedHashMap<String, Object>();
        JaxbFeatureUtil.setProperties(this.injectionManager, SAXParser.class, saxParserProperties::put);
        factory.setSaxParserProperties(saxParserProperties);
        JaxbFeatureUtil.setFeatures(this.injectionManager, SAXParserFactory.class, factory::setFeature);
        return factory;
    }
}

