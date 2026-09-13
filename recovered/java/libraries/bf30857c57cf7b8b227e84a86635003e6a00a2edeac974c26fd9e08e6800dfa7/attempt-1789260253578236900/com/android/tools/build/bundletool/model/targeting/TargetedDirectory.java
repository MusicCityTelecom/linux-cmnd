/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.AutoValue_TargetedDirectory;
import com.android.tools.build.bundletool.model.targeting.TargetedDirectorySegment;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MoreCollectors;
import com.google.errorprone.annotations.Immutable;
import java.util.HashSet;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class TargetedDirectory {
    public abstract ImmutableList<TargetedDirectorySegment> getPathSegments();

    public abstract ZipPath originalPath();

    public TargetedDirectorySegment getLastSegment() {
        return Iterables.getLast(this.getPathSegments());
    }

    public String getPathBaseName() {
        return this.getSubPathBaseName(this.getPathSegments().size() - 1);
    }

    public String getSubPathBaseName(int maxIndex) {
        return this.originalPath().subpath(0, maxIndex + 1).resolveSibling(((TargetedDirectorySegment)this.getPathSegments().get(maxIndex)).getName()).toString();
    }

    public Optional<Targeting.AssetsDirectoryTargeting> getTargeting(TargetingDimension dimension) {
        return this.getPathSegments().stream().filter(segment -> segment.getTargetingDimension().equals(Optional.of(dimension))).map(segment -> segment.getTargeting()).collect(MoreCollectors.toOptional());
    }

    public TargetedDirectory removeTargeting(TargetingDimension dimension) {
        ImmutableList<TargetedDirectorySegment> newSegments = this.getPathSegments().stream().map(segment -> segment.removeTargeting(dimension)).collect(ImmutableList.toImmutableList());
        if (this.getPathSegments().equals(newSegments)) {
            return this;
        }
        return TargetedDirectory.create(newSegments, this.originalPath());
    }

    public static TargetedDirectory parse(ZipPath directoryPath) {
        Preconditions.checkArgument(directoryPath.getNameCount() > 0, "Empty paths are not supported.");
        ImmutableList<TargetedDirectorySegment> segments = directoryPath.getNames().stream().map(TargetedDirectorySegment::parse).collect(ImmutableList.toImmutableList());
        TargetedDirectory.checkNoDuplicateDimensions(segments, directoryPath);
        return TargetedDirectory.create(segments, directoryPath);
    }

    public ZipPath toZipPath() {
        ImmutableList<String> pathSegments = this.getPathSegments().stream().map(TargetedDirectorySegment::toPathSegment).collect(ImmutableList.toImmutableList());
        return ZipPath.create(pathSegments);
    }

    private static void checkNoDuplicateDimensions(ImmutableList<TargetedDirectorySegment> directorySegments, ZipPath directoryPath) {
        HashSet<TargetingDimension> coveredDimensions = new HashSet<TargetingDimension>();
        for (TargetedDirectorySegment targetedDirectorySegment : directorySegments) {
            if (!targetedDirectorySegment.getTargetingDimension().isPresent()) continue;
            TargetingDimension lastSegmentDimension = targetedDirectorySegment.getTargetingDimension().get();
            if (coveredDimensions.contains((Object)lastSegmentDimension)) {
                throw ValidationException.builder().withMessage("Duplicate targeting dimension '%s' on path '%s'.", new Object[]{lastSegmentDimension, directoryPath}).build();
            }
            coveredDimensions.add(lastSegmentDimension);
        }
    }

    private static TargetedDirectory create(ImmutableList<TargetedDirectorySegment> segments, ZipPath originalPath) {
        return new AutoValue_TargetedDirectory(segments, originalPath);
    }
}

