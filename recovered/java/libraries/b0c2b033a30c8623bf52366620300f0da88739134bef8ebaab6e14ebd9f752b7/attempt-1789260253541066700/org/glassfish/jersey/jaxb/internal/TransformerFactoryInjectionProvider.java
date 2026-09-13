/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.jaxb.internal.AbstractXmlFactory;
import org.glassfish.jersey.jaxb.internal.JaxbFeatureUtil;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;

public class TransformerFactoryInjectionProvider
extends AbstractXmlFactory<TransformerFactory> {
    private static final Logger LOGGER = Logger.getLogger(TransformerFactoryInjectionProvider.class.getName());
    @Inject
    private InjectionManager injectionManager;

    @Inject
    public TransformerFactoryInjectionProvider(Configuration config) {
        super(config);
    }

    @Override
    public TransformerFactory get() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        if (!this.isXmlSecurityDisabled()) {
            try {
                transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            }
            catch (TransformerConfigurationException e) {
                LOGGER.log(Level.CONFIG, LocalizationMessages.UNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING(), e);
            }
        }
        JaxbFeatureUtil.setFeatures(this.injectionManager, TransformerFactory.class, transformerFactory::setFeature);
        JaxbFeatureUtil.setProperties(this.injectionManager, TransformerFactory.class, transformerFactory::setAttribute);
        return transformerFactory;
    }
}

