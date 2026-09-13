/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.ModuleSplit;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import java.util.List;

public class ResourceInjector {
    private static final int BASE_RESOURCE_TABLE_PACKAGE_ID = 127;
    private final Resources.ResourceTable.Builder resourceTable;
    private final String packageName;

    ResourceInjector(Resources.ResourceTable.Builder resourceTable, String packageName) {
        this.resourceTable = resourceTable;
        this.packageName = packageName;
    }

    public static ResourceInjector fromModuleSplit(ModuleSplit moduleSplit) {
        return new ResourceInjector(moduleSplit.getResourceTable().map(Resources.ResourceTable::toBuilder).orElseGet(Resources.ResourceTable::newBuilder), moduleSplit.getAndroidManifest().getPackageName());
    }

    public ResourceId addResource(String entryType, Resources.Entry entry) {
        ResourceId.Builder resourceIdBuilder = ResourceId.builder();
        Resources.Package.Builder packageBuilder = this.resourceTable.getPackageCount() == 0 ? this.resourceTable.addPackageBuilder().setPackageName(this.packageName).setPackageId(Resources.PackageId.newBuilder().setId(127)) : this.resourceTable.getPackageBuilder(0);
        resourceIdBuilder.setPackageId(packageBuilder.getPackageId().getId());
        ResourceInjector.addResourceToPackage(packageBuilder, resourceIdBuilder, entryType, entry);
        return resourceIdBuilder.build();
    }

    public Resources.ResourceTable build() {
        return this.resourceTable.build();
    }

    private static void addResourceToPackage(Resources.Package.Builder resourcePackage, ResourceId.Builder resourceId, String entryType, Resources.Entry entry) {
        Resources.Type.Builder type = resourcePackage.getTypeBuilderList().stream().filter(t3 -> t3.getName().equals(entryType)).findFirst().orElseGet(() -> resourcePackage.addTypeBuilder().setName(entryType).setTypeId(Resources.TypeId.newBuilder().setId(ResourceInjector.getNextTypeId(resourcePackage.getTypeList()))));
        resourceId.setTypeId(type.getTypeId().getId());
        ResourceInjector.addResourceToType(type, resourceId, entry);
    }

    private static void addResourceToType(Resources.Type.Builder type, ResourceId.Builder resourceId, Resources.Entry entry) {
        int nextEntryId = ResourceInjector.getNextEntryId(type.getEntryList());
        resourceId.setEntryId(nextEntryId);
        type.addEntry(entry.toBuilder().setEntryId(Resources.EntryId.newBuilder().setId(nextEntryId)));
    }

    private static int getNextEntryId(List<Resources.Entry> entryList) {
        int highestEntryId = entryList.stream().mapToInt(entry -> entry.getEntryId().getId()).max().orElse(-1);
        if (highestEntryId >= 65535) {
            throw new CommandExecutionException("No free entry id left in the resource table.");
        }
        return highestEntryId + 1;
    }

    private static int getNextTypeId(List<Resources.Type> typeList) {
        int highestTypeId = typeList.stream().mapToInt(type -> type.getTypeId().getId()).max().orElse(0);
        if (highestTypeId >= 255) {
            throw new CommandExecutionException("No free type id left in the resource table.");
        }
        return highestTypeId + 1;
    }
}

