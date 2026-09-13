/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.inject.hk2.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.inject.hk2.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableHK_2_UNKNOWN_ERROR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.unknown.error", arg0);
    }

    public static String HK_2_UNKNOWN_ERROR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_UNKNOWN_ERROR(arg0));
    }

    public static Localizable localizableHK_2_UNKNOWN_PARENT_INJECTION_MANAGER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.unknown.parent.injection.manager", arg0);
    }

    public static String HK_2_UNKNOWN_PARENT_INJECTION_MANAGER(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_UNKNOWN_PARENT_INJECTION_MANAGER(arg0));
    }

    public static Localizable localizableHK_2_FAILURE_OUTSIDE_ERROR_SCOPE() {
        return MESSAGE_FACTORY.getMessage("hk2.failure.outside.error.scope", new Object[0]);
    }

    public static String HK_2_FAILURE_OUTSIDE_ERROR_SCOPE() {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_FAILURE_OUTSIDE_ERROR_SCOPE());
    }

    public static Localizable localizableHK_2_PROVIDER_NOT_REGISTRABLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hk2.provider.not.registrable", arg0);
    }

    public static String HK_2_PROVIDER_NOT_REGISTRABLE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_PROVIDER_NOT_REGISTRABLE(arg0));
    }

    public static Localizable localizableHK_2_CLEARING_CACHE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("hk2.clearing.cache", arg0, arg1);
    }

    public static String HK_2_CLEARING_CACHE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_CLEARING_CACHE(arg0, arg1));
    }

    public static Localizable localizableHK_2_REIFICATION_ERROR(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("hk2.reification.error", arg0, arg1);
    }

    public static String HK_2_REIFICATION_ERROR(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableHK_2_REIFICATION_ERROR(arg0, arg1));
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

