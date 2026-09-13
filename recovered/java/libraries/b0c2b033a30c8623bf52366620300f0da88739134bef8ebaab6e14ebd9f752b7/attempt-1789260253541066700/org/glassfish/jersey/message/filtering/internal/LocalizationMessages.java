/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.internal;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.message.filtering.internal.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.message.filtering.internal.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableMERGING_FILTERING_SCOPES() {
        return MESSAGE_FACTORY.getMessage("merging.filtering.scopes", new Object[0]);
    }

    public static String MERGING_FILTERING_SCOPES() {
        return LOCALIZER.localize(LocalizationMessages.localizableMERGING_FILTERING_SCOPES());
    }

    public static Localizable localizableENTITY_FILTERING_SCOPE_NOT_ANNOTATIONS(Object arg0) {
        return MESSAGE_FACTORY.getMessage("entity.filtering.scope.not.annotations", arg0);
    }

    public static String ENTITY_FILTERING_SCOPE_NOT_ANNOTATIONS(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableENTITY_FILTERING_SCOPE_NOT_ANNOTATIONS(arg0));
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

