/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_ZipPath;
import com.google.auto.value.AutoValue;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ZipPath
implements Comparable<ZipPath> {
    private static final String SEPARATOR = "/";
    private static final Splitter SPLITTER = Splitter.on("/").omitEmptyStrings();
    private static final Joiner JOINER = Joiner.on("/");
    private static final ImmutableSet<String> FORBIDDEN_NAMES = ImmutableSet.of("", ".", "..");
    public static final ZipPath ROOT = ZipPath.create("");

    ZipPath() {
    }

    public abstract ImmutableList<String> getNames();

    public static ZipPath create(String path) {
        Preconditions.checkNotNull(path, "Path cannot be null.");
        return ZipPath.create(ImmutableList.copyOf(SPLITTER.splitToList(path)));
    }

    public static ZipPath create(ImmutableList<String> names) {
        names.forEach(name -> {
            Preconditions.checkArgument(!name.contains(SEPARATOR), "Name '%s' contains a forward slash and cannot be used in a path.", name);
            Preconditions.checkArgument(!FORBIDDEN_NAMES.contains(name), "Name '%s' is not supported inside path.", name);
        });
        return new AutoValue_ZipPath(names);
    }

    @CheckReturnValue
    public ZipPath resolve(ZipPath p3) {
        Preconditions.checkNotNull(p3, "Path cannot be null.");
        return ZipPath.create((ImmutableList<String>)((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().addAll(this.getNames())).addAll(p3.getNames())).build());
    }

    @CheckReturnValue
    public ZipPath resolve(String path) {
        return this.resolve(ZipPath.create(path));
    }

    @CheckReturnValue
    public ZipPath resolveSibling(ZipPath path) {
        Preconditions.checkNotNull(path, "Path cannot be null.");
        Preconditions.checkState(!this.getNames().isEmpty(), "Root has not sibling.");
        return this.getParent().resolve(path);
    }

    @CheckReturnValue
    public ZipPath resolveSibling(String path) {
        return this.resolveSibling(ZipPath.create(path));
    }

    @CheckReturnValue
    public ZipPath subpath(int from, int to) {
        Preconditions.checkArgument(from >= 0 && from < this.getNames().size());
        Preconditions.checkArgument(to >= 0 && to <= this.getNames().size());
        Preconditions.checkArgument(from < to);
        return ZipPath.create((ImmutableList<String>)this.getNames().subList(from, to));
    }

    @Nullable
    @CheckReturnValue
    public ZipPath getParent() {
        if (this.getNames().isEmpty()) {
            return null;
        }
        return ZipPath.create((ImmutableList<String>)this.getNames().subList(0, this.getNames().size() - 1));
    }

    public int getNameCount() {
        return this.getNames().size();
    }

    public ZipPath getRoot() {
        return ROOT;
    }

    public ZipPath getName(int index) {
        Preconditions.checkArgument(index >= 0 && index < this.getNames().size());
        return ZipPath.create((String)this.getNames().get(index));
    }

    public boolean startsWith(ZipPath p3) {
        if (p3.getNameCount() > this.getNameCount()) {
            return false;
        }
        ImmutableList<String> names = this.getNames();
        ImmutableList<String> otherNames = p3.getNames();
        for (int i2 = 0; i2 < p3.getNameCount(); ++i2) {
            if (((String)otherNames.get(i2)).equals(names.get(i2))) continue;
            return false;
        }
        return true;
    }

    public boolean startsWith(String p3) {
        return this.startsWith(ZipPath.create(p3));
    }

    public boolean endsWith(ZipPath p3) {
        if (p3.getNameCount() > this.getNameCount()) {
            return false;
        }
        ImmutableList<String> names = this.getNames();
        ImmutableList<String> otherNames = p3.getNames();
        for (int i2 = 0; i2 < p3.getNameCount(); ++i2) {
            if (((String)otherNames.get(otherNames.size() - i2 - 1)).equals(names.get(names.size() - i2 - 1))) continue;
            return false;
        }
        return true;
    }

    public boolean endsWith(String p3) {
        return this.endsWith(ZipPath.create(p3));
    }

    @Override
    public final int compareTo(ZipPath other) {
        return Comparators.lexicographical(Comparator.naturalOrder()).compare(this.getNames(), other.getNames());
    }

    public final String toString() {
        return JOINER.join(this.getNames());
    }

    public ZipPath getFileName() {
        Preconditions.checkArgument(this.getNameCount() > 0, "Root does not have a file name.");
        return this.getName(this.getNameCount() - 1);
    }
}

