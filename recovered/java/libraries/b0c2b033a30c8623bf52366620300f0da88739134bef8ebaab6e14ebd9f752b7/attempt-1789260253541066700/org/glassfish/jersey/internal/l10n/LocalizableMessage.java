/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.l10n;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;

public final class LocalizableMessage
implements Localizable {
    private final String _bundlename;
    private final LocalizableMessageFactory.ResourceBundleSupplier _rbSupplier;
    private final String _key;
    private final Object[] _args;

    @Deprecated
    public LocalizableMessage(String bundlename, String key, Object ... args) {
        this(bundlename, null, key, args);
    }

    public LocalizableMessage(String bundlename, LocalizableMessageFactory.ResourceBundleSupplier rbSupplier, String key, Object ... args) {
        this._bundlename = bundlename;
        this._rbSupplier = rbSupplier;
        this._key = key;
        if (args == null) {
            args = new Object[]{};
        }
        this._args = args;
    }

    @Override
    public String getKey() {
        return this._key;
    }

    @Override
    public Object[] getArguments() {
        return Arrays.copyOf(this._args, this._args.length);
    }

    @Override
    public String getResourceBundleName() {
        return this._bundlename;
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        if (this._rbSupplier == null) {
            return null;
        }
        return this._rbSupplier.getResourceBundle(locale);
    }
}

