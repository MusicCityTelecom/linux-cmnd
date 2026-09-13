/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.l10n;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;
import org.glassfish.jersey.internal.OsgiRegistry;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public class Localizer {
    private final Locale _locale;
    private final HashMap<String, ResourceBundle> _resourceBundles;

    public Localizer() {
        this(Locale.getDefault());
    }

    public Localizer(Locale l) {
        this._locale = l;
        this._resourceBundles = new HashMap();
    }

    public Locale getLocale() {
        return this._locale;
    }

    public String localize(Localizable l) {
        String key = l.getKey();
        if ("\u0000".equals(key)) {
            return (String)l.getArguments()[0];
        }
        String bundlename = l.getResourceBundleName();
        try {
            String msg;
            ResourceBundle bundle = this._resourceBundles.get(bundlename);
            if (bundle == null) {
                block18: {
                    try {
                        bundle = ResourceBundle.getBundle(bundlename, this._locale);
                    }
                    catch (MissingResourceException e) {
                        int i = bundlename.lastIndexOf(46);
                        if (i == -1) break block18;
                        String alternateBundleName = bundlename.substring(i + 1);
                        try {
                            bundle = ResourceBundle.getBundle(alternateBundleName, this._locale);
                        }
                        catch (MissingResourceException e2) {
                            try {
                                bundle = ResourceBundle.getBundle(bundlename, this._locale, Thread.currentThread().getContextClassLoader());
                            }
                            catch (MissingResourceException e3) {
                                OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
                                if (osgiRegistry != null) {
                                    bundle = osgiRegistry.getResourceBundle(bundlename);
                                    break block18;
                                }
                                String path = bundlename.replace('.', '/') + ".properties";
                                URL bundleUrl = ResourceFinder.findEntry(path);
                                if (bundleUrl == null) break block18;
                                try {
                                    bundle = new PropertyResourceBundle(bundleUrl.openStream());
                                }
                                catch (IOException iOException) {
                                    // empty catch block
                                }
                            }
                        }
                    }
                }
                if (bundle == null) {
                    return this.getDefaultMessage(l);
                }
                this._resourceBundles.put(bundlename, bundle);
            }
            if (key == null) {
                key = "undefined";
            }
            try {
                msg = bundle.getString(key);
            }
            catch (MissingResourceException e) {
                msg = bundle.getString("undefined");
            }
            Object[] args = l.getArguments();
            for (int i = 0; i < args.length; ++i) {
                if (!(args[i] instanceof Localizable)) continue;
                args[i] = this.localize((Localizable)args[i]);
            }
            String message = MessageFormat.format(msg, args);
            return message;
        }
        catch (MissingResourceException e) {
            return this.getDefaultMessage(l);
        }
    }

    private String getDefaultMessage(Localizable l) {
        String key = l.getKey();
        Object[] args = l.getArguments();
        StringBuilder sb = new StringBuilder();
        sb.append("[failed to localize] ");
        sb.append(key);
        if (args != null) {
            sb.append('(');
            for (int i = 0; i < args.length; ++i) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(String.valueOf(args[i]));
            }
            sb.append(')');
        }
        return sb.toString();
    }
}

