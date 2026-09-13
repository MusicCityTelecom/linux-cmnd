/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ResouceTableException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.HashSet;

public class ResourceTableValidator
extends SubValidator {
    @Override
    public void validateModule(BundleModule module) {
        Resources.ResourceTable resourceTable = module.getResourceTable().orElse(Resources.ResourceTable.getDefaultInstance());
        String moduleName = module.getName().getName();
        ImmutableSet resFiles = module.findEntriesUnderPath(BundleModule.RESOURCES_DIRECTORY).map(ModuleEntry::getPath).collect(ImmutableSet.toImmutableSet());
        if (!resFiles.isEmpty() && !module.getResourceTable().isPresent()) {
            throw new ResouceTableException.ResourceTableMissingException(moduleName);
        }
        ImmutableSet<ZipPath> referencedFiles = ResourcesUtils.getAllFileReferences(resourceTable);
        for (ZipPath referencedFile : referencedFiles) {
            if (referencedFile.startsWith(BundleModule.RESOURCES_DIRECTORY)) continue;
            throw new ResouceTableException.ReferencesFileOutsideOfResException(moduleName, referencedFile, BundleModule.RESOURCES_DIRECTORY);
        }
        ImmutableSet<ZipPath> nonReferencedFiles = ImmutableSet.copyOf(Sets.difference(resFiles, referencedFiles));
        if (!nonReferencedFiles.isEmpty()) {
            throw new ResouceTableException.UnreferencedResourcesException(moduleName, nonReferencedFiles);
        }
        ImmutableSet<ZipPath> nonExistingFiles = ImmutableSet.copyOf(Sets.difference(referencedFiles, resFiles));
        if (!nonExistingFiles.isEmpty()) {
            throw new ResouceTableException.ReferencesMissingFilesException(moduleName, nonExistingFiles);
        }
    }

    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        this.checkResourceIdsAreUnique(modules);
    }

    @VisibleForTesting
    void checkResourceIdsAreUnique(ImmutableList<BundleModule> modules) {
        HashSet usedResourceIds = Sets.newHashSet();
        for (BundleModule module : modules) {
            Resources.ResourceTable resourceTable = module.getResourceTable().orElse(Resources.ResourceTable.getDefaultInstance());
            ResourcesUtils.entries(resourceTable).forEach(resourceTableEntry -> {
                boolean foundDuplicate;
                boolean bl = foundDuplicate = !usedResourceIds.add(resourceTableEntry.getResourceId());
                if (foundDuplicate) {
                    throw ValidationException.builder().withMessage("Duplicate resource id (%s).", resourceTableEntry.getResourceId().toString()).build();
                }
            });
        }
    }
}

