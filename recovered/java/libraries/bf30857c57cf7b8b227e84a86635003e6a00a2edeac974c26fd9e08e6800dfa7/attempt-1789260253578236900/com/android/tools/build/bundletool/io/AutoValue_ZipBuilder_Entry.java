/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.android.tools.build.bundletool.io.ZipBuilder;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;

final class AutoValue_ZipBuilder_Entry
extends ZipBuilder.Entry {
    private final Optional<InputStreamSupplier> inputStreamSupplier;
    private final boolean isDirectory;
    private final ImmutableSet<ZipBuilder.EntryOption> options;

    private AutoValue_ZipBuilder_Entry(Optional<InputStreamSupplier> inputStreamSupplier, boolean isDirectory, ImmutableSet<ZipBuilder.EntryOption> options) {
        this.inputStreamSupplier = inputStreamSupplier;
        this.isDirectory = isDirectory;
        this.options = options;
    }

    @Override
    public Optional<InputStreamSupplier> getInputStreamSupplier() {
        return this.inputStreamSupplier;
    }

    @Override
    public boolean getIsDirectory() {
        return this.isDirectory;
    }

    @Override
    public ImmutableSet<ZipBuilder.EntryOption> getOptions() {
        return this.options;
    }

    public String toString() {
        return "Entry{inputStreamSupplier=" + this.inputStreamSupplier + ", isDirectory=" + this.isDirectory + ", options=" + this.options + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ZipBuilder.Entry) {
            ZipBuilder.Entry that = (ZipBuilder.Entry)o3;
            return this.inputStreamSupplier.equals(that.getInputStreamSupplier()) && this.isDirectory == that.getIsDirectory() && this.options.equals(that.getOptions());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.inputStreamSupplier.hashCode();
        h$ *= 1000003;
        h$ ^= this.isDirectory ? 1231 : 1237;
        h$ *= 1000003;
        return h$ ^= this.options.hashCode();
    }

    static final class Builder
    extends ZipBuilder.Entry.Builder {
        private Optional<InputStreamSupplier> inputStreamSupplier = Optional.empty();
        private Boolean isDirectory;
        private ImmutableSet<ZipBuilder.EntryOption> options;

        Builder() {
        }

        @Override
        public ZipBuilder.Entry.Builder setInputStreamSupplier(InputStreamSupplier inputStreamSupplier) {
            this.inputStreamSupplier = Optional.of(inputStreamSupplier);
            return this;
        }

        @Override
        public ZipBuilder.Entry.Builder setIsDirectory(boolean isDirectory) {
            this.isDirectory = isDirectory;
            return this;
        }

        @Override
        public ZipBuilder.Entry.Builder setOptions(ImmutableSet<ZipBuilder.EntryOption> options) {
            if (options == null) {
                throw new NullPointerException("Null options");
            }
            this.options = options;
            return this;
        }

        @Override
        public ZipBuilder.Entry autoBuild() {
            String missing = "";
            if (this.isDirectory == null) {
                missing = missing + " isDirectory";
            }
            if (this.options == null) {
                missing = missing + " options";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ZipBuilder_Entry(this.inputStreamSupplier, this.isDirectory, this.options);
        }
    }
}

