/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.splitters;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.ResourceId;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.stream.Stream;

public class ResourceAnalyzer {
    private final AppBundle appBundle;
    private final ResourceIndex resourceIndex;

    public ResourceAnalyzer(AppBundle appBundle) {
        this.appBundle = appBundle;
        this.resourceIndex = new ResourceIndex(appBundle);
    }

    public ImmutableSet<ResourceId> findAllAppResourcesReachableFromBaseManifest() throws IOException {
        ImmutableSet<ResourceId> resourceIdsInBaseManifest = this.findAllReferencedAppResources(this.appBundle.getBaseModule().getAndroidManifest().getManifestRoot().getProto(), this.appBundle.getBaseModule());
        return this.transitiveClosure(resourceIdsInBaseManifest);
    }

    private ImmutableSet<ResourceId> transitiveClosure(ImmutableSet<ResourceId> anchorResources) throws IOException {
        HashSet<ResourceId> referencedResources = new HashSet<ResourceId>();
        ArrayDeque<ResourceId> resourcesToInspect = new ArrayDeque<ResourceId>();
        resourcesToInspect.addAll(anchorResources);
        while (!resourcesToInspect.isEmpty()) {
            ResourceId resourceId = (ResourceId)resourcesToInspect.remove();
            if (referencedResources.contains(resourceId) || !this.resourceIndex.isResourceFromApp(resourceId)) continue;
            referencedResources.add(resourceId);
            ResourceTableEntry resourceEntry = this.resourceIndex.getEntryForResourceId(resourceId);
            BundleModule module = this.resourceIndex.getModuleForResourceId(resourceId);
            for (Resources.ConfigValue configValue : resourceEntry.getEntry().getConfigValueList()) {
                switch (configValue.getValue().getValueCase()) {
                    case ITEM: {
                        Resources.Item item = configValue.getValue().getItem();
                        resourcesToInspect.addAll(this.findAllReferencedAppResources(item, module));
                        break;
                    }
                    case COMPOUND_VALUE: {
                        Resources.CompoundValue compoundValue = configValue.getValue().getCompoundValue();
                        resourcesToInspect.addAll(this.findAllReferencedAppResources(compoundValue, module));
                        break;
                    }
                }
            }
        }
        return ImmutableSet.copyOf(referencedResources);
    }

    private ImmutableSet<ResourceId> findAllReferencedAppResources(Resources.XmlNode xmlRoot, BundleModule module) {
        return ResourceAnalyzer.getAllAttributesRecursively(xmlRoot.getElement()).filter(Resources.XmlAttribute::hasCompiledItem).map(Resources.XmlAttribute::getCompiledItem).flatMap(item -> this.findAllReferencedAppResources((Resources.Item)item, module).stream()).collect(ImmutableSet.toImmutableSet());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ImmutableSet<ResourceId> findAllReferencedAppResources(Resources.Item item, BundleModule module) {
        switch (item.getValueCase()) {
            case REF: {
                if (item.getRef().getId() == 0) return ImmutableSet.of();
                return ImmutableSet.of(ResourceId.create(item.getRef().getId()));
            }
            case FILE: {
                Resources.FileReference fileRef = item.getFile();
                if (!fileRef.getType().equals(Resources.FileReference.Type.PROTO_XML)) {
                    return ImmutableSet.of();
                }
                ZipPath xmlResourcePath = ZipPath.create(fileRef.getPath());
                try (InputStream is = module.getEntry(xmlResourcePath).get().getContent();){
                    Resources.XmlNode xmlRoot = Resources.XmlNode.parseFrom(is);
                    ImmutableSet<ResourceId> immutableSet = this.findAllReferencedAppResources(xmlRoot, module);
                    return immutableSet;
                }
                catch (InvalidProtocolBufferException e2) {
                    throw ValidationException.builder().withMessage("Error parsing XML file '%s'.", xmlResourcePath).withCause(e2).build();
                }
                catch (IOException e3) {
                    throw new UncheckedIOException(String.format("Failed to parse file '%s' in module '%s'.", xmlResourcePath, module.getName().getName()), e3);
                }
            }
        }
        return ImmutableSet.of();
    }

    private ImmutableSet<ResourceId> findAllReferencedAppResources(Resources.CompoundValue compoundValue, BundleModule module) throws IOException {
        switch (compoundValue.getValueCase()) {
            case ATTR: {
                return compoundValue.getAttr().getSymbolList().stream().map(symbol -> symbol.getName().getId()).filter(id -> id != 0).map(ResourceId::create).collect(ImmutableSet.toImmutableSet());
            }
            case STYLE: {
                ImmutableSet.Builder referencedResources = ImmutableSet.builder();
                if (compoundValue.getStyle().getParent().getId() != 0) {
                    referencedResources.add(ResourceId.create(compoundValue.getStyle().getParent().getId()));
                }
                for (Resources.Style.Entry entry : compoundValue.getStyle().getEntryList()) {
                    referencedResources.addAll(this.findAllReferencedAppResources(entry.getItem(), module));
                    if (entry.getKey().getId() == 0) continue;
                    referencedResources.add(ResourceId.create(entry.getKey().getId()));
                }
                return referencedResources.build();
            }
        }
        return ImmutableSet.of();
    }

    private static Stream<Resources.XmlAttribute> getAllAttributesRecursively(Resources.XmlElement element) {
        return Stream.concat(element.getAttributeList().stream(), element.getChildList().stream().filter(node -> node.hasElement()).flatMap(node -> ResourceAnalyzer.getAllAttributesRecursively(node.getElement())));
    }

    private static class ResourceIndex {
        private final ImmutableMap<ResourceId, BundleModule> resourceIdToModule;
        private final ImmutableMap<ResourceId, ResourceTableEntry> resourceIdToEntry;

        private ResourceIndex(AppBundle appBundle) {
            ImmutableMap.Builder resourceIdToModule = ImmutableMap.builder();
            ImmutableMap.Builder resourceIdToEntry = ImmutableMap.builder();
            for (BundleModule module : appBundle.getFeatureModules().values()) {
                if (!module.getResourceTable().isPresent()) continue;
                Resources.ResourceTable resourceTable = module.getResourceTable().get();
                ResourcesUtils.entries(resourceTable).forEach(entry -> {
                    resourceIdToModule.put(entry.getResourceId(), module);
                    resourceIdToEntry.put(entry.getResourceId(), entry);
                });
            }
            this.resourceIdToModule = resourceIdToModule.build();
            this.resourceIdToEntry = resourceIdToEntry.build();
        }

        BundleModule getModuleForResourceId(ResourceId resourceId) {
            return Preconditions.checkNotNull(this.resourceIdToModule.get(resourceId), "Resource ID %s not found", (Object)resourceId);
        }

        ResourceTableEntry getEntryForResourceId(ResourceId resourceId) {
            return Preconditions.checkNotNull(this.resourceIdToEntry.get(resourceId), "Resource ID %s not found", (Object)resourceId);
        }

        ImmutableSet<ResourceId> getAllResourceIds() {
            return this.resourceIdToModule.keySet();
        }

        boolean isResourceFromApp(ResourceId resourceId) {
            return this.getAllResourceIds().contains(resourceId);
        }
    }
}

