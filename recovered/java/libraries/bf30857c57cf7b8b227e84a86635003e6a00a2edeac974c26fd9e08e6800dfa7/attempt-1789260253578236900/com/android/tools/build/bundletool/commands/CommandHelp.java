/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.AutoValue_CommandHelp;
import com.android.tools.build.bundletool.commands.AutoValue_CommandHelp_CommandDescription;
import com.android.tools.build.bundletool.commands.AutoValue_CommandHelp_FlagDescription;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import com.google.errorprone.annotations.Immutable;
import java.io.PrintStream;
import java.text.BreakIterator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import javax.annotation.CheckReturnValue;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class CommandHelp {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int MAX_WIDTH = 80;
    private static final int INDENT_SIZE = 4;
    private static final Comparator<FlagDescription> FLAG_ORDER = Comparator.comparing(FlagDescription::isOptional).thenComparing(FlagDescription::getFlagName);

    abstract String getCommandName();

    abstract ImmutableList<String> getSubCommandNames();

    private String getSubCommandNamesAsString() {
        switch (this.getSubCommandNames().size()) {
            case 0: {
                return "";
            }
            case 1: {
                return Iterables.getOnlyElement(this.getSubCommandNames());
            }
        }
        StringJoiner joiner = new StringJoiner("|", "<", ">");
        this.getSubCommandNames().forEach(joiner::add);
        return joiner.toString();
    }

    abstract CommandDescription getCommandDescription();

    abstract ImmutableSortedSet<FlagDescription> getFlags();

    static Builder builder() {
        return new AutoValue_CommandHelp.Builder().setSubCommandNames(ImmutableList.of());
    }

    public void printSummary(PrintStream output) {
        output.printf("%s command:%n", this.getCommandName());
        output.println(CommandHelp.wrap(this.getCommandDescription().getShortDescription(), 80, 4, 4));
        output.println();
    }

    public void printDetails(PrintStream output) {
        output.println("Description:");
        output.println(CommandHelp.wrap(this.getCommandDescription().getShortDescription(), 80, 4, 4));
        output.println();
        for (String additionalParagraph : this.getCommandDescription().getAdditionalParagraphs()) {
            output.println(CommandHelp.wrap(additionalParagraph, 80, 4, 4));
            output.println();
        }
        output.println("Synopsis:");
        output.println(CommandHelp.wrap(String.format("bundletool %s %s", this.getCommandName(), this.getSubCommandNamesAsString()), 80, 4, 4));
        for (FlagDescription flag : this.getFlags()) {
            output.println(CommandHelp.wrap(flag.getPrintForSyntax(), 80, 8, 12));
        }
        output.println();
        output.println("Flags:");
        for (FlagDescription flag : this.getFlags()) {
            output.println(CommandHelp.wrap(flag.getPrintForDescription(), 80, 4, 8));
            output.println();
        }
    }

    @CheckReturnValue
    @VisibleForTesting
    static String wrap(String text, int maxWidth, int firstLineIndent, int otherLinesIndent) {
        int newLineIdx = text.indexOf(LINE_SEPARATOR);
        if (newLineIdx != -1) {
            return CommandHelp.wrap(text.substring(0, newLineIdx), maxWidth, firstLineIndent, otherLinesIndent) + LINE_SEPARATOR + CommandHelp.wrap(text.substring(newLineIdx + LINE_SEPARATOR.length()), maxWidth, firstLineIndent, otherLinesIndent);
        }
        BreakIterator boundary = BreakIterator.getLineInstance(Locale.ENGLISH);
        boundary.setText(text);
        int start = boundary.first();
        int end = boundary.next();
        StringBuilder wrappedText = new StringBuilder();
        StringBuilder line = new StringBuilder(Strings.repeat(" ", firstLineIndent));
        while (end != -1) {
            String word = text.substring(start, end);
            if (line.length() + word.trim().length() > maxWidth) {
                wrappedText.append(CharMatcher.whitespace().trimTrailingFrom(line.toString())).append(LINE_SEPARATOR);
                line = new StringBuilder(Strings.repeat(" ", otherLinesIndent));
            }
            line.append(word);
            start = end;
            end = boundary.next();
        }
        wrappedText.append((CharSequence)line);
        return wrappedText.toString();
    }

    static /* synthetic */ Comparator access$000() {
        return FLAG_ORDER;
    }

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    static abstract class FlagDescription
    implements Comparable<FlagDescription> {
        FlagDescription() {
        }

        abstract String getFlagName();

        abstract boolean isOptional();

        abstract String getDescription();

        abstract Optional<String> getExampleValue();

        static Builder builder() {
            return new AutoValue_CommandHelp_FlagDescription.Builder().setOptional(false);
        }

        public String getPrintForSyntax() {
            StringBuilder builder = new StringBuilder();
            if (this.isOptional()) {
                builder.append("[");
            }
            builder.append("--").append(this.getFlagName());
            this.getExampleValue().ifPresent(value -> builder.append("=<").append((String)value).append(">"));
            if (this.isOptional()) {
                builder.append("]");
            }
            return builder.toString();
        }

        public String getPrintForDescription() {
            StringBuilder builder = new StringBuilder();
            builder.append("--").append(this.getFlagName()).append(": ");
            if (this.isOptional()) {
                builder.append("(Optional) ");
            }
            builder.append(this.getDescription());
            return builder.toString();
        }

        @Override
        public int compareTo(FlagDescription o3) {
            return ComparisonChain.start().compareFalseFirst(this.isOptional(), o3.isOptional()).compare((Comparable<?>)((Object)this.getFlagName()), (Comparable<?>)((Object)o3.getFlagName())).result();
        }

        @AutoValue.Builder
        static abstract class Builder {
            Builder() {
            }

            abstract Builder setFlagName(String var1);

            abstract Builder setOptional(boolean var1);

            abstract Builder setExampleValue(String var1);

            abstract Builder setDescription(String var1);

            @FormatMethod
            Builder setDescription(@FormatString String description, Object ... args) {
                return this.setDescription(String.format(description, args));
            }

            abstract FlagDescription build();
        }
    }

    @Immutable
    @AutoValue
    @AutoValue.CopyAnnotations
    static abstract class CommandDescription {
        CommandDescription() {
        }

        abstract String getShortDescription();

        abstract ImmutableList<String> getAdditionalParagraphs();

        static Builder builder() {
            return new AutoValue_CommandHelp_CommandDescription.Builder();
        }

        @AutoValue.Builder
        static abstract class Builder {
            Builder() {
            }

            abstract Builder setShortDescription(String var1);

            @FormatMethod
            Builder setShortDescription(String shortDescriptionFormat, Object ... args) {
                return this.setShortDescription(String.format(shortDescriptionFormat, args));
            }

            abstract ImmutableList.Builder<String> additionalParagraphsBuilder();

            Builder addAdditionalParagraph(String additionalParaghaph) {
                this.additionalParagraphsBuilder().add((Object)additionalParaghaph);
                return this;
            }

            abstract CommandDescription build();
        }
    }

    @AutoValue.Builder
    static abstract class Builder {
        private final ImmutableSortedSet.Builder<FlagDescription> flagsBuilder = ImmutableSortedSet.orderedBy(CommandHelp.access$000());

        Builder() {
        }

        public abstract Builder setCommandName(String var1);

        public abstract Builder setSubCommandNames(ImmutableList<String> var1);

        public abstract Builder setCommandDescription(CommandDescription var1);

        abstract Builder setFlags(ImmutableSortedSet<FlagDescription> var1);

        public Builder addFlag(FlagDescription flag) {
            this.flagsBuilder.add((Object)flag);
            return this;
        }

        abstract CommandHelp autoBuild();

        public CommandHelp build() {
            this.setFlags((ImmutableSortedSet<FlagDescription>)this.flagsBuilder.build());
            return this.autoBuild();
        }
    }
}

