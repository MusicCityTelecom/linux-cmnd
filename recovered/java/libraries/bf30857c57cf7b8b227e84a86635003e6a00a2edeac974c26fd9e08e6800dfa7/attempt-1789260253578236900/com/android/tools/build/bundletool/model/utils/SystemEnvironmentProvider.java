/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.utils.DefaultSystemEnvironmentProvider;
import java.util.Optional;

public interface SystemEnvironmentProvider {
    public static final SystemEnvironmentProvider DEFAULT_PROVIDER = new DefaultSystemEnvironmentProvider();
    public static final String ANDROID_HOME = "ANDROID_HOME";

    public Optional<String> getVariable(String var1);

    public Optional<String> getProperty(String var1);

    default public Optional<String> getAndroidHomePath() {
        return this.getVariable(ANDROID_HOME);
    }
}

