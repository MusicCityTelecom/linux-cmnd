/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.BundleModule;
import java.util.stream.Stream;

public interface BundleModuleVariantGenerator {
    public Stream<Targeting.VariantTargeting> generate(BundleModule var1);
}

