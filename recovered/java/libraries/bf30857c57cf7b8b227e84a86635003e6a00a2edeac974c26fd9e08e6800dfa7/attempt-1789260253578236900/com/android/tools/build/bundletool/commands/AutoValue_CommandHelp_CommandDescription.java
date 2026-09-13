/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.CommandHelp;
import com.google.common.collect.ImmutableList;

final class AutoValue_CommandHelp_CommandDescription
extends CommandHelp.CommandDescription {
    private final String shortDescription;
    private final ImmutableList<String> additionalParagraphs;

    private AutoValue_CommandHelp_CommandDescription(String shortDescription, ImmutableList<String> additionalParagraphs) {
        this.shortDescription = shortDescription;
        this.additionalParagraphs = additionalParagraphs;
    }

    @Override
    String getShortDescription() {
        return this.shortDescription;
    }

    @Override
    ImmutableList<String> getAdditionalParagraphs() {
        return this.additionalParagraphs;
    }

    public String toString() {
        return "CommandDescription{shortDescription=" + this.shortDescription + ", additionalParagraphs=" + this.additionalParagraphs + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof CommandHelp.CommandDescription) {
            CommandHelp.CommandDescription that = (CommandHelp.CommandDescription)o3;
            return this.shortDescription.equals(that.getShortDescription()) && this.additionalParagraphs.equals(that.getAdditionalParagraphs());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.shortDescription.hashCode();
        h$ *= 1000003;
        return h$ ^= this.additionalParagraphs.hashCode();
    }

    static final class Builder
    extends CommandHelp.CommandDescription.Builder {
        private String shortDescription;
        private ImmutableList.Builder<String> additionalParagraphsBuilder$;
        private ImmutableList<String> additionalParagraphs;

        Builder() {
        }

        @Override
        CommandHelp.CommandDescription.Builder setShortDescription(String shortDescription) {
            if (shortDescription == null) {
                throw new NullPointerException("Null shortDescription");
            }
            this.shortDescription = shortDescription;
            return this;
        }

        @Override
        ImmutableList.Builder<String> additionalParagraphsBuilder() {
            if (this.additionalParagraphsBuilder$ == null) {
                this.additionalParagraphsBuilder$ = ImmutableList.builder();
            }
            return this.additionalParagraphsBuilder$;
        }

        @Override
        CommandHelp.CommandDescription build() {
            if (this.additionalParagraphsBuilder$ != null) {
                this.additionalParagraphs = this.additionalParagraphsBuilder$.build();
            } else if (this.additionalParagraphs == null) {
                this.additionalParagraphs = ImmutableList.of();
            }
            String missing = "";
            if (this.shortDescription == null) {
                missing = missing + " shortDescription";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_CommandHelp_CommandDescription(this.shortDescription, this.additionalParagraphs);
        }
    }
}

