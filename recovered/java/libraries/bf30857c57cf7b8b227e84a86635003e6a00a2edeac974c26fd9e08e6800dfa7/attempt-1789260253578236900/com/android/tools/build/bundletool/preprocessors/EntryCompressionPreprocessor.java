/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.preprocessors;

import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.preprocessors.AppBundlePreprocessor;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import javax.annotation.CheckReturnValue;

public class EntryCompressionPreprocessor
implements AppBundlePreprocessor {
    @Override
    public AppBundle preprocess(AppBundle bundle) {
        return bundle.toBuilder().setRawModules(EntryCompressionPreprocessor.setEntryCompression((ImmutableCollection<BundleModule>)bundle.getModules().values())).build();
    }

    @CheckReturnValue
    private static ImmutableList<BundleModule> setEntryCompression(ImmutableCollection<BundleModule> modules) {
        return modules.stream().map(EntryCompressionPreprocessor::setEntryCompression).collect(ImmutableList.toImmutableList());
    }

    private static BundleModule setEntryCompression(BundleModule module) {
        if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) {
            return module.toBuilder().setRawEntries(module.getEntries().stream().map(entry -> entry.toBuilder().setShouldCompress(!entry.getPath().startsWith(BundleModule.ASSETS_DIRECTORY)).build()).collect(ImmutableList.toImmutableList())).build();
        }
        return module;
    }
}

