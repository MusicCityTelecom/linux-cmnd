/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.AbiName;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.InputStreamSuppliers;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;

public class AbiPlaceholderInjector {
    private final ImmutableSet<Targeting.Abi> abiPlaceholders;

    public AbiPlaceholderInjector(ImmutableSet<Targeting.Abi> abiPlaceholders) {
        this.abiPlaceholders = abiPlaceholders;
    }

    public ModuleSplit addPlaceholderNativeEntries(ModuleSplit moduleSplit) {
        return moduleSplit.toBuilder().setEntries((List<ModuleEntry>)((Object)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(moduleSplit.getEntries())).addAll((Iterable)this.abiPlaceholders.stream().map(AbiPlaceholderInjector::createEntryForAbi).collect(ImmutableList.toImmutableList()))).build())).build();
    }

    private static ModuleEntry createEntryForAbi(Targeting.Abi abi) {
        return ModuleEntry.builder().setPath(BundleModule.LIB_DIRECTORY.resolve(AbiName.fromProto(abi.getAlias()).getPlatformName()).resolve("libplaceholder.so")).setContentSupplier(InputStreamSuppliers.fromBytes(new byte[0])).build();
    }
}

