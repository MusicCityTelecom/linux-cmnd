/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.utils.CsvFormatter;
import com.google.common.collect.ImmutableList;
import java.util.Optional;

final class AutoValue_CsvFormatter
extends CsvFormatter {
    private final Optional<ImmutableList<String>> header;
    private final ImmutableList<ImmutableList<String>> rows;

    private AutoValue_CsvFormatter(Optional<ImmutableList<String>> header, ImmutableList<ImmutableList<String>> rows) {
        this.header = header;
        this.rows = rows;
    }

    @Override
    public Optional<ImmutableList<String>> getHeader() {
        return this.header;
    }

    @Override
    public ImmutableList<ImmutableList<String>> getRows() {
        return this.rows;
    }

    public String toString() {
        return "CsvFormatter{header=" + this.header + ", rows=" + this.rows + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof CsvFormatter) {
            CsvFormatter that = (CsvFormatter)o3;
            return this.header.equals(that.getHeader()) && this.rows.equals(that.getRows());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.header.hashCode();
        h$ *= 1000003;
        return h$ ^= this.rows.hashCode();
    }

    static final class Builder
    extends CsvFormatter.Builder {
        private Optional<ImmutableList<String>> header = Optional.empty();
        private ImmutableList.Builder<ImmutableList<String>> rowsBuilder$;
        private ImmutableList<ImmutableList<String>> rows;

        Builder() {
        }

        @Override
        public CsvFormatter.Builder setHeader(ImmutableList<String> header) {
            this.header = Optional.of(header);
            return this;
        }

        @Override
        ImmutableList.Builder<ImmutableList<String>> rowsBuilder() {
            if (this.rowsBuilder$ == null) {
                this.rowsBuilder$ = ImmutableList.builder();
            }
            return this.rowsBuilder$;
        }

        @Override
        CsvFormatter autoBuild() {
            if (this.rowsBuilder$ != null) {
                this.rows = this.rowsBuilder$.build();
            } else if (this.rows == null) {
                this.rows = ImmutableList.of();
            }
            return new AutoValue_CsvFormatter(this.header, this.rows);
        }
    }
}

