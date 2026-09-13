/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.CheckReturnValue;

public class ClassesDexNameSanitizer {
    private static final Pattern CLASSES_DEX_REGEX_PATTERN = Pattern.compile("dex/classes(\\d*)\\.dex");
    private static final Predicate<ModuleEntry> IS_DEX_FILE = entry -> entry.getPath().toString().matches(CLASSES_DEX_REGEX_PATTERN.pattern());

    @CheckReturnValue
    public BundleModule sanitize(BundleModule module) {
        if (!module.getEntry(ZipPath.create("dex/classes1.dex")).isPresent()) {
            return module;
        }
        Map partitionedEntries = module.getEntries().stream().collect(Collectors.partitioningBy(IS_DEX_FILE, ImmutableList.toImmutableList()));
        ImmutableList dexEntries = partitionedEntries.get(true);
        ImmutableList nonDexEntries = partitionedEntries.get(false);
        ImmutableCollection newEntries = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll((Iterable)nonDexEntries)).addAll((Iterable)dexEntries.stream().map(ClassesDexNameSanitizer::sanitizeDexEntry).collect(ImmutableList.toImmutableList()))).build();
        return module.toBuilder().setEntryMap(newEntries.stream().collect(ImmutableMap.toImmutableMap(ModuleEntry::getPath, Function.identity()))).build();
    }

    private static ModuleEntry sanitizeDexEntry(ModuleEntry dexEntry) {
        ZipPath sanitizedEntryPath = ClassesDexNameSanitizer.incrementClassesDexNumber(dexEntry.getPath());
        return dexEntry.toBuilder().setPath(sanitizedEntryPath).build();
    }

    private static ZipPath incrementClassesDexNumber(ZipPath entryPath) {
        String fileName = entryPath.toString();
        Matcher matcher = CLASSES_DEX_REGEX_PATTERN.matcher(fileName);
        Preconditions.checkState(matcher.matches());
        String num = matcher.group(1);
        if (num.isEmpty()) {
            return entryPath;
        }
        return ZipPath.create("dex/classes" + (Integer.parseInt(num) + 1) + ".dex");
    }
}

