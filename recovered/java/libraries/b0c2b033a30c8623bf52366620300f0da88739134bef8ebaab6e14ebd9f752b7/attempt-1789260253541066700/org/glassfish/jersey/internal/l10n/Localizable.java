/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

public interface Localizable {
    public static final String NOT_LOCALIZABLE = "\u0000";

    public String getKey();

    public Object[] getArguments();

    public String getResourceBundleName();

    public ResourceBundle getResourceBundle(Locale var1);
}

