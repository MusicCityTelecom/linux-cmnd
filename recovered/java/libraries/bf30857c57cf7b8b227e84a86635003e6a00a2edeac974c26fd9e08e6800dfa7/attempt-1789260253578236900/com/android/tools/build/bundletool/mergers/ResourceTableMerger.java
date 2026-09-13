/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.aapt.Resources;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import com.google.common.primitives.Ints;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.CheckReturnValue;

public class ResourceTableMerger {
    public Resources.ResourceTable merge(Resources.ResourceTable table1, Resources.ResourceTable table2) {
        ImmutableList<Resources.Overlayable> mergedOverlayables;
        if (!table1.getSourcePool().equals(table2.getSourcePool())) {
            Resources.ResourceTable.Builder table2Builder = table2.toBuilder();
            ResourceTableMerger.stripSourceReferences(table2Builder);
            table2 = table2Builder.build();
        }
        if (!(mergedOverlayables = this.mergeRepeatedValues(table1.getOverlayableList(), table2.getOverlayableList(), Resources.Overlayable::getName, this::mergeOverlayables)).isEmpty()) {
            ImmutableMap<Integer, String> idxToOverlayableName = ResourceTableMerger.toIndexMap(mergedOverlayables, Resources.Overlayable::getName);
            BiMap idxByOverlayableName = ImmutableBiMap.copyOf(idxToOverlayableName).inverse();
            table1 = ResourceTableMerger.reIndexOverlayables(table1, (ImmutableMap<String, Integer>)((Object)idxByOverlayableName));
            table2 = ResourceTableMerger.reIndexOverlayables(table2, (ImmutableMap<String, Integer>)((Object)idxByOverlayableName));
        }
        return table1.toBuilder().clearOverlayable().addAllOverlayable(mergedOverlayables).clearPackage().addAllPackage(this.mergeRepeatedValues(table1.getPackageList(), table2.getPackageList(), pkg -> pkg.getPackageId().getId(), this::mergePackages)).build();
    }

    @CheckReturnValue
    private static Resources.ResourceTable reIndexOverlayables(Resources.ResourceTable table, ImmutableMap<String, Integer> newOverlayableIdxMap) {
        ImmutableMap<Integer, Integer> oldToNewIndex = ResourceTableMerger.toIndexMap(ImmutableList.copyOf(table.getOverlayableList()), overlayable -> (Integer)newOverlayableIdxMap.get(overlayable.getName()));
        Resources.ResourceTable.Builder newTable = table.toBuilder();
        for (Resources.Package.Builder pkg : newTable.getPackageBuilderList()) {
            for (Resources.Type.Builder type : pkg.getTypeBuilderList()) {
                for (Resources.Entry.Builder entry : type.getEntryBuilderList()) {
                    if (!entry.hasOverlayableItem()) continue;
                    int newIdx = oldToNewIndex.get(entry.getOverlayableItem().getOverlayableIdx());
                    entry.getOverlayableItemBuilder().setOverlayableIdx(newIdx);
                }
            }
        }
        return newTable.build();
    }

    private static <T, R> ImmutableMap<Integer, R> toIndexMap(ImmutableList<T> list, Function<T, R> valueFn) {
        Map map = Streams.mapWithIndex(list.stream(), (value, i2) -> new AbstractMap.SimpleEntry<Integer, Object>(Ints.checkedCast(i2), value)).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
        return ImmutableMap.copyOf(Maps.transformValues(map, valueFn::apply));
    }

    private Resources.Overlayable mergeOverlayables(Resources.Overlayable overlayable1, Resources.Overlayable overlayable2) {
        ResourceTableMerger.assertEqualFields(overlayable1, overlayable2, Resources.Overlayable::getName, "name");
        ResourceTableMerger.assertEqualFields(overlayable1, overlayable2, Resources.Overlayable::getActor, "actor");
        return overlayable1;
    }

    private Resources.Package mergePackages(Resources.Package pkg1, Resources.Package pkg2) {
        ResourceTableMerger.assertEqualFields(pkg1, pkg2, Resources.Package::getPackageId, "package_id");
        ResourceTableMerger.assertEqualFields(pkg1, pkg2, Resources.Package::getPackageName, "package_name");
        return pkg1.toBuilder().clearType().addAllType(this.mergeRepeatedValues(pkg1.getTypeList(), pkg2.getTypeList(), type -> type.getTypeId().getId(), this::mergeTypes)).build();
    }

    private Resources.Type mergeTypes(Resources.Type type1, Resources.Type type2) {
        ResourceTableMerger.assertEqualFields(type1, type2, Resources.Type::getTypeId, "type_id");
        ResourceTableMerger.assertEqualFields(type1, type2, Resources.Type::getName, "name");
        return type1.toBuilder().clearEntry().addAllEntry(this.mergeRepeatedValues(type1.getEntryList(), type2.getEntryList(), entry -> entry.getEntryId().getId(), this::mergeEntries)).build();
    }

    private Resources.Entry mergeEntries(Resources.Entry entry1, Resources.Entry entry2) {
        ResourceTableMerger.assertEqualFields(entry1, entry2, Resources.Entry::getEntryId, "entry_id");
        ResourceTableMerger.assertEqualFields(entry1, entry2, Resources.Entry::getName, "name");
        ResourceTableMerger.assertEqualFields(entry1, entry2, Resources.Entry::getVisibility, "visibility");
        ResourceTableMerger.assertEqualFields(entry1, entry2, Resources.Entry::getAllowNew, "allow_new");
        ResourceTableMerger.assertEqualFields(entry1, entry2, Resources.Entry::getOverlayableItem, "overlayable_item");
        return entry1.toBuilder().clearConfigValue().addAllConfigValue(this.mergeConfigValueLists(entry1.getConfigValueList(), entry2.getConfigValueList())).build();
    }

    private List<Resources.ConfigValue> mergeConfigValueLists(List<Resources.ConfigValue> configValues1, List<Resources.ConfigValue> configValues2) {
        HashSet<Resources.ConfigValue> configValues1Set = Sets.newHashSet(configValues1);
        return ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(configValues1)).addAll((Iterable)configValues2.stream().filter(Predicates.not(configValues1Set::contains)).collect(ImmutableList.toImmutableList()))).build();
    }

    private <V, I extends Comparable<?>> ImmutableList<V> mergeRepeatedValues(List<V> values1, List<V> values2, Function<V, I> getIdFn, BiFunction<V, V, V> mergeValuesFn) {
        ImmutableList.Builder result = ImmutableList.builder();
        ImmutableMap idToValue1 = Maps.uniqueIndex(values1, getIdFn::apply);
        ImmutableMap idToValue2 = Maps.uniqueIndex(values2, getIdFn::apply);
        ImmutableList allIds = Sets.union(idToValue1.keySet(), idToValue2.keySet()).stream().sorted().collect(ImmutableList.toImmutableList());
        for (Comparable id : allIds) {
            Object value1 = idToValue1.get(id);
            Object value2 = idToValue2.get(id);
            if (value1 != null && value2 != null) {
                result.add(mergeValuesFn.apply(value1, value2));
                continue;
            }
            if (value1 != null) {
                result.add(value1);
                continue;
            }
            result.add(value2);
        }
        return result.build();
    }

    private static <V, F> void assertEqualFields(V protoMsg1, V protoMsg2, Function<V, F> getFieldFn, String fieldName) {
        F field1 = getFieldFn.apply(protoMsg1);
        F field2 = getFieldFn.apply(protoMsg2);
        Preconditions.checkState(field1.equals(field2), "Expected same values of field '%s', found [%s] and [%s].", (Object)fieldName, field1, field2);
    }

    @VisibleForTesting
    static void stripSourceReferences(Message.Builder msg) {
        for (Descriptors.FieldDescriptor fieldDesc : msg.getAllFields().keySet()) {
            if (!fieldDesc.getJavaType().equals((Object)Descriptors.FieldDescriptor.JavaType.MESSAGE)) continue;
            if (fieldDesc.getMessageType().getFullName().equals(Resources.Source.getDescriptor().getFullName())) {
                msg.clearField(fieldDesc);
                continue;
            }
            if (fieldDesc.isRepeated()) {
                int repeatCount = msg.getRepeatedFieldCount(fieldDesc);
                for (int i2 = 0; i2 < repeatCount; ++i2) {
                    ResourceTableMerger.stripSourceReferences(msg.getRepeatedFieldBuilder(fieldDesc, i2));
                }
                continue;
            }
            ResourceTableMerger.stripSourceReferences(msg.getFieldBuilder(fieldDesc));
        }
    }
}

