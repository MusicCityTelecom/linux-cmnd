/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.l10n;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessage;

public class LocalizableMessageFactory {
    private final String _bundlename;
    private final ResourceBundleSupplier _rbSupplier;

    @Deprecated
    public LocalizableMessageFactory(String bundlename) {
        this._bundlename = bundlename;
        this._rbSupplier = null;
    }

    public LocalizableMessageFactory(String bundlename, ResourceBundleSupplier rbSupplier) {
        this._bundlename = bundlename;
        this._rbSupplier = rbSupplier;
    }

    public Localizable getMessage(String key, Object ... args) {
        return new LocalizableMessage(this._bundlename, this._rbSupplier, key, args);
    }

    public static interface ResourceBundleSupplier {
        public ResourceBundle getResourceBundle(Locale var1);
    }
}

