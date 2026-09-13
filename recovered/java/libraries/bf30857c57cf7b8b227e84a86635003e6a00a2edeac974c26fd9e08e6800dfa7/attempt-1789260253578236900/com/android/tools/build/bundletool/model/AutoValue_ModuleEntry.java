/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ModuleEntry;
import com.android.tools.build.bundletool.model.ZipPath;

final class AutoValue_ModuleEntry
extends ModuleEntry {
    private final ZipPath path;
    private final boolean shouldCompress;
    private final InputStreamSupplier contentSupplier;

    private AutoValue_ModuleEntry(ZipPath path, boolean shouldCompress, InputStreamSupplier contentSupplier) {
        this.path = path;
        this.shouldCompress = shouldCompress;
        this.contentSupplier = contentSupplier;
    }

    @Override
    public ZipPath getPath() {
        return this.path;
    }

    @Override
    public boolean getShouldCompress() {
        return this.shouldCompress;
    }

    @Override
    public InputStreamSupplier getContentSupplier() {
        return this.contentSupplier;
    }

    public String toString() {
        return "ModuleEntry{path=" + this.path + ", shouldCompress=" + this.shouldCompress + ", contentSupplier=" + this.contentSupplier + "}";
    }

    @Override
    public ModuleEntry.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder
    extends ModuleEntry.Builder {
        private ZipPath path;
        private Boolean shouldCompress;
        private InputStreamSupplier contentSupplier;

        Builder() {
        }

        private Builder(ModuleEntry source) {
            this.path = source.getPath();
            this.shouldCompress = source.getShouldCompress();
            this.contentSupplier = source.getContentSupplier();
        }

        @Override
        public ModuleEntry.Builder setPath(ZipPath path) {
            if (path == null) {
                throw new NullPointerException("Null path");
            }
            this.path = path;
            return this;
        }

        @Override
        public ModuleEntry.Builder setShouldCompress(boolean shouldCompress) {
            this.shouldCompress = shouldCompress;
            return this;
        }

        @Override
        public ModuleEntry.Builder setContentSupplier(InputStreamSupplier contentSupplier) {
            if (contentSupplier == null) {
                throw new NullPointerException("Null contentSupplier");
            }
            this.contentSupplier = contentSupplier;
            return this;
        }

        @Override
        public ModuleEntry build() {
            String missing = "";
            if (this.path == null) {
                missing = missing + " path";
            }
            if (this.shouldCompress == null) {
                missing = missing + " shouldCompress";
            }
            if (this.contentSupplier == null) {
                missing = missing + " contentSupplier";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ModuleEntry(this.path, this.shouldCompress, this.contentSupplier);
        }
    }
}

