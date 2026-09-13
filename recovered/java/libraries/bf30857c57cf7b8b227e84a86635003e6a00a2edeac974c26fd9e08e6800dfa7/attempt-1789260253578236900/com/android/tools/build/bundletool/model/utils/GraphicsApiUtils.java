/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Targeting;

public final class GraphicsApiUtils {
    public static Targeting.GraphicsApiTargeting openGlVersionFrom(int major, int minor) {
        return Targeting.GraphicsApiTargeting.newBuilder().addValue(Targeting.GraphicsApi.newBuilder().setMinOpenGlVersion(Targeting.OpenGlVersion.newBuilder().setMajor(major).setMinor(minor))).build();
    }

    public static Targeting.GraphicsApiTargeting vulkanVersionFrom(int major, int minor) {
        return Targeting.GraphicsApiTargeting.newBuilder().addValue(Targeting.GraphicsApi.newBuilder().setMinVulkanVersion(Targeting.VulkanVersion.newBuilder().setMajor(major).setMinor(minor))).build();
    }
}

