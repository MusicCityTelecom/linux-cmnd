/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import java.util.Optional;

public class DefaultSystemEnvironmentProvider
implements SystemEnvironmentProvider {
    @Override
    public Optional<String> getVariable(String name) {
        return Optional.ofNullable(System.getenv(name));
    }

    @Override
    public Optional<String> getProperty(String name) {
        return Optional.ofNullable(System.getProperty(name));
    }
}

