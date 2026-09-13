/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.UserCountriesCondition;
import com.google.common.collect.ImmutableList;

final class AutoValue_UserCountriesCondition
extends UserCountriesCondition {
    private final ImmutableList<String> countries;
    private final boolean exclude;

    AutoValue_UserCountriesCondition(ImmutableList<String> countries, boolean exclude) {
        if (countries == null) {
            throw new NullPointerException("Null countries");
        }
        this.countries = countries;
        this.exclude = exclude;
    }

    @Override
    public ImmutableList<String> getCountries() {
        return this.countries;
    }

    @Override
    public boolean getExclude() {
        return this.exclude;
    }

    public String toString() {
        return "UserCountriesCondition{countries=" + this.countries + ", exclude=" + this.exclude + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof UserCountriesCondition) {
            UserCountriesCondition that = (UserCountriesCondition)o3;
            return this.countries.equals(that.getCountries()) && this.exclude == that.getExclude();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.countries.hashCode();
        h$ *= 1000003;
        return h$ ^= this.exclude ? 1231 : 1237;
    }
}

