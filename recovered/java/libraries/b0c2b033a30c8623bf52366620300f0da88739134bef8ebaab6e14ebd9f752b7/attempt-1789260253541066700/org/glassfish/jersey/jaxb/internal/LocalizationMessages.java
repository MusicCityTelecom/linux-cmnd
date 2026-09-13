/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jaxb.internal;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.jaxb.internal.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.jaxb.internal.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableERROR_UNMARSHALLING_JAXB(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.unmarshalling.jaxb", arg0);
    }

    public static String ERROR_UNMARSHALLING_JAXB(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_UNMARSHALLING_JAXB(arg0));
    }

    public static Localizable localizableCANNOT_SET_PROPERTY(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("cannot.set.property", arg0, arg1, arg2, arg3);
    }

    public static String CANNOT_SET_PROPERTY(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableCANNOT_SET_PROPERTY(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.parameter.entity.processing.feature", arg0);
    }

    public static String SAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_CANNOT_DISABLE_PARAMETER_ENTITY_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableERROR_READING_ENTITY_MISSING() {
        return MESSAGE_FACTORY.getMessage("error.reading.entity.missing", new Object[0]);
    }

    public static String ERROR_READING_ENTITY_MISSING() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_READING_ENTITY_MISSING());
    }

    public static Localizable localizableSAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.enable.disallow.doctype.declaration.feature", arg0);
    }

    public static String SAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_CANNOT_ENABLE_DISALLOW_DOCTYPE_DECLARATION_FEATURE(arg0));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.general.entity.processing.feature.on.reader", arg0);
    }

    public static String SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE_ON_READER(arg0));
    }

    public static Localizable localizableNO_PARAM_CONSTRUCTOR_MISSING(Object arg0) {
        return MESSAGE_FACTORY.getMessage("no.param.constructor.missing", arg0);
    }

    public static String NO_PARAM_CONSTRUCTOR_MISSING(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableNO_PARAM_CONSTRUCTOR_MISSING(arg0));
    }

    public static Localizable localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.disable.general.entity.processing.feature", arg0);
    }

    public static String SAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_CANNOT_DISABLE_GENERAL_ENTITY_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableUNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING() {
        return MESSAGE_FACTORY.getMessage("unable.to.secure.xml.transformer.processing", new Object[0]);
    }

    public static String UNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING() {
        return LOCALIZER.localize(LocalizationMessages.localizableUNABLE_TO_SECURE_XML_TRANSFORMER_PROCESSING());
    }

    public static Localizable localizableSAX_XDK_NO_SECURITY_FEATURES() {
        return MESSAGE_FACTORY.getMessage("sax.xdk.no.security.features", new Object[0]);
    }

    public static String SAX_XDK_NO_SECURITY_FEATURES() {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_XDK_NO_SECURITY_FEATURES());
    }

    public static Localizable localizableSAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("sax.cannot.enable.secure.processing.feature", arg0);
    }

    public static String SAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSAX_CANNOT_ENABLE_SECURE_PROCESSING_FEATURE(arg0));
    }

    public static Localizable localizableUNABLE_TO_ACCESS_METHODS_OF_CLASS(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unable.to.access.methods.of.class", arg0);
    }

    public static String UNABLE_TO_ACCESS_METHODS_OF_CLASS(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNABLE_TO_ACCESS_METHODS_OF_CLASS(arg0));
    }

    public static Localizable localizableUNABLE_TO_INSTANTIATE_CLASS(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unable.to.instantiate.class", arg0);
    }

    public static String UNABLE_TO_INSTANTIATE_CLASS(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNABLE_TO_INSTANTIATE_CLASS(arg0));
    }

    public static Localizable localizableCANNOT_SET_FEATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("cannot.set.feature", arg0, arg1, arg2, arg3);
    }

    public static String CANNOT_SET_FEATURE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableCANNOT_SET_FEATURE(arg0, arg1, arg2, arg3));
    }

    private static class BundleSupplier
    implements LocalizableMessageFactory.ResourceBundleSupplier {
        private BundleSupplier() {
        }

        @Override
        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(LocalizationMessages.BUNDLE_NAME, locale);
        }
    }
}

