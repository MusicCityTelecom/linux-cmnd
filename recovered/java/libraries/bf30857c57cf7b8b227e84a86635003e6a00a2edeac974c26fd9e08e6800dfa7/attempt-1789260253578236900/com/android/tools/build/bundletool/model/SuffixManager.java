/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class SuffixManager {
    @GuardedBy(value="this")
    private final Multimap<Targeting.VariantTargeting, String> usedSuffixes = HashMultimap.create();

    public synchronized String createSuffix(ModuleSplit moduleSplit) {
        String currentProposal = moduleSplit.getSuffix();
        int serialNumber = 1;
        while (this.usedSuffixes.containsEntry(moduleSplit.getVariantTargeting(), currentProposal)) {
            currentProposal = String.format("%s_%d", moduleSplit.getSuffix(), ++serialNumber);
        }
        this.usedSuffixes.put(moduleSplit.getVariantTargeting(), currentProposal);
        return currentProposal;
    }
}

