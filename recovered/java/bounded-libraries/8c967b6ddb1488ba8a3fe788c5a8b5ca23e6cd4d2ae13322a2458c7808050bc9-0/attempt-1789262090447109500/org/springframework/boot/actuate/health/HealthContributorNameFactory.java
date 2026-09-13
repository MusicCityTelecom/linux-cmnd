/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.util.Locale;
import java.util.function.Function;

public class HealthContributorNameFactory
implements Function<String, String> {
    private static final String[] SUFFIXES = new String[]{"healthindicator", "healthcontributor"};
    public static final HealthContributorNameFactory INSTANCE = new HealthContributorNameFactory();

    @Override
    public String apply(String name) {
        for (String suffix : SUFFIXES) {
            if (name == null || !name.toLowerCase(Locale.ENGLISH).endsWith(suffix)) continue;
            return name.substring(0, name.length() - suffix.length());
        }
        return name;
    }
}

