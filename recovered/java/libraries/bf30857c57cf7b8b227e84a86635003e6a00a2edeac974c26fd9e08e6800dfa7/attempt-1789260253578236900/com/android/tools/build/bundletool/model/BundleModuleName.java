/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.regex.Pattern;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class BundleModuleName
implements Comparable<BundleModuleName> {
    public static final BundleModuleName BASE_MODULE_NAME = new AutoValue_BundleModuleName("base");
    private static final Pattern MODULE_NAME_FORMAT = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");

    static boolean isValid(String name) {
        return MODULE_NAME_FORMAT.matcher(name).matches();
    }

    public static BundleModuleName create(String name) throws InvalidBundleModuleNameException {
        if (!BundleModuleName.isValid(name)) {
            throw new InvalidBundleModuleNameException(name);
        }
        return new AutoValue_BundleModuleName(name);
    }

    public abstract String getName();

    public String getNameForSplitId() {
        if (this.equals(BASE_MODULE_NAME)) {
            return "";
        }
        return this.getName();
    }

    public final String toString() {
        return this.getName();
    }

    @Override
    public final int compareTo(BundleModuleName o3) {
        return Comparator.comparing(BundleModuleName::getName).compare(this, o3);
    }

    static class InvalidBundleModuleNameException
    extends ValidationException {
        InvalidBundleModuleNameException(String name) {
            super("Module names with special characters not supported: " + name);
        }
    }
}

