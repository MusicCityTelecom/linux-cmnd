/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.tools.build.bundletool.model.utils.AutoValue_CsvFormatter;
import com.google.auto.value.AutoValue;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.escape.CharEscaperBuilder;
import com.google.common.escape.Escaper;
import com.google.errorprone.annotations.Immutable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class CsvFormatter {
    private static final Joiner COMMA_JOINER = Joiner.on(",");
    public static final String CRLF = "\r\n";
    private static final Escaper DOUBLE_QUOTER = new CharEscaperBuilder().addEscape('\"', "\"\"").toEscaper();
    private static final CharMatcher QUOTE_NEEDED = CharMatcher.anyOf("\",\n\r");

    public abstract Optional<ImmutableList<String>> getHeader();

    public abstract ImmutableList<ImmutableList<String>> getRows();

    public static Builder builder() {
        return new AutoValue_CsvFormatter.Builder();
    }

    public String format() {
        StringBuilder stringBuilder = new StringBuilder();
        this.getHeader().ifPresent(row -> stringBuilder.append(CsvFormatter.getRowInCsv(row)));
        this.getRows().forEach((Consumer<ImmutableList<String>>)((Consumer<ImmutableList>)row -> stringBuilder.append(CsvFormatter.getRowInCsv(row))));
        return stringBuilder.toString();
    }

    private static String getRowInCsv(ImmutableList<String> text) {
        return COMMA_JOINER.join(text.stream().map(CsvFormatter::encloseInQuotes).collect(ImmutableList.toImmutableList())) + CRLF;
    }

    private static String encloseInQuotes(String value) {
        if (QUOTE_NEEDED.matchesAnyOf(value)) {
            return '\"' + DOUBLE_QUOTER.escape(value) + '\"';
        }
        return value;
    }

    CsvFormatter() {
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setHeader(ImmutableList<String> var1);

        abstract ImmutableList.Builder<ImmutableList<String>> rowsBuilder();

        public Builder addRow(ImmutableList<String> row) {
            this.rowsBuilder().add((Object)row);
            return this;
        }

        abstract CsvFormatter autoBuild();

        public CsvFormatter build() {
            CsvFormatter result = this.autoBuild();
            ImmutableList.Builder allRows = ImmutableList.builder();
            result.getHeader().ifPresent(allRows::add);
            allRows.addAll(result.getRows());
            Preconditions.checkState(allRows.build().stream().mapToInt(Collection::size).distinct().count() <= 1L, "All rows must have the same size.");
            return result;
        }
    }
}

