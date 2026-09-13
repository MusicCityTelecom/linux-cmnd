/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.jaxb.FeatureSupplier;
import org.glassfish.jersey.jaxb.PropertySupplier;
import org.glassfish.jersey.jaxb.internal.LocalizationMessages;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

final class JaxbFeatureUtil {
    private static final Logger LOGGER = Logger.getLogger(JaxbFeatureUtil.class.getName());
    private static final RankedComparator<PropertySupplier> PROPERTY_COMPARATOR = new RankedComparator(RankedComparator.Order.DESCENDING);
    private static final RankedComparator<FeatureSupplier> FEATURE_COMPARATOR = new RankedComparator(RankedComparator.Order.DESCENDING);

    private JaxbFeatureUtil() {
    }

    static void setFeatures(InjectionManager injectionManager, Class<?> clazz, Settable<Boolean> consumer) {
        if (injectionManager != null) {
            Iterable<FeatureSupplier> featureSuppliers = Providers.getAllProviders(injectionManager, FeatureSupplier.class, FEATURE_COMPARATOR);
            for (FeatureSupplier featureSupplier : featureSuppliers) {
                if (!featureSupplier.isFor(clazz)) continue;
                for (Map.Entry<String, Boolean> entry : featureSupplier.getFeatures().entrySet()) {
                    JaxbFeatureUtil.setFeature(clazz, entry, consumer);
                }
            }
        }
    }

    static void setProperties(InjectionManager injectionManager, Class<?> clazz, Settable<Object> consumer) {
        if (injectionManager != null) {
            Iterable<PropertySupplier> propertySuppliers = Providers.getAllProviders(injectionManager, PropertySupplier.class, PROPERTY_COMPARATOR);
            for (PropertySupplier propertySupplier : propertySuppliers) {
                if (!propertySupplier.isFor(clazz)) continue;
                for (Map.Entry<String, Object> entry : propertySupplier.getProperties().entrySet()) {
                    JaxbFeatureUtil.setProperty(clazz, entry, consumer);
                }
            }
        }
    }

    static <T> void setProperty(Class<?> clazz, Map.Entry<String, T> settable, Settable<T> consumer) {
        Optional<Exception> exception = consumer.accept(settable.getKey(), settable.getValue());
        exception.ifPresent(ex -> LOGGER.warning(LocalizationMessages.CANNOT_SET_PROPERTY(settable.getKey(), settable.getValue(), clazz.getName(), ex)));
    }

    private static <T> void setFeature(Class<?> clazz, Map.Entry<String, T> settable, Settable<T> consumer) {
        Optional<Exception> exception = consumer.accept(settable.getKey(), settable.getValue());
        exception.ifPresent(ex -> LOGGER.warning(LocalizationMessages.CANNOT_SET_FEATURE(settable.getKey(), settable.getValue(), clazz.getName(), ex)));
    }

    @FunctionalInterface
    static interface Settable<T> {
        public void set(String var1, T var2) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException, TransformerConfigurationException;

        default public Optional<Exception> accept(String key, T t) {
            try {
                this.set(key, t);
                return Optional.empty();
            }
            catch (Exception e) {
                return Optional.of(e);
            }
        }
    }
}

