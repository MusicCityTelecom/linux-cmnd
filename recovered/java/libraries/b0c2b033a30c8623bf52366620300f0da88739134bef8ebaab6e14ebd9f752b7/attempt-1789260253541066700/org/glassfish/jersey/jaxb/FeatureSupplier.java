/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb;

import java.util.Collections;
import java.util.Map;
import javax.xml.parsers.SAXParserFactory;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface FeatureSupplier {
    public boolean isFor(Class<?> var1);

    public Map<String, Boolean> getFeatures();

    public static FeatureSupplier allowDoctypeDeclFeature() {
        return new FeatureSupplier(){

            @Override
            public boolean isFor(Class<?> factoryClass) {
                return SAXParserFactory.class == factoryClass;
            }

            @Override
            public Map<String, Boolean> getFeatures() {
                return Collections.singletonMap("http://apache.org/xml/features/disallow-doctype-decl", false);
            }
        };
    }
}

