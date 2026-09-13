/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_UserCountriesCondition;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class UserCountriesCondition {
    public abstract ImmutableList<String> getCountries();

    public abstract boolean getExclude();

    public static UserCountriesCondition create(ImmutableList<String> countryCodeList, boolean exclude) {
        return new AutoValue_UserCountriesCondition(countryCodeList, exclude);
    }
}

