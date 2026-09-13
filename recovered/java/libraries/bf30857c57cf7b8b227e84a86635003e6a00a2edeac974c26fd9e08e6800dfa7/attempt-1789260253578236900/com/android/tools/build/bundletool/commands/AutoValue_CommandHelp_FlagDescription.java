/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.CommandHelp;
import java.util.Optional;

final class AutoValue_CommandHelp_FlagDescription
extends CommandHelp.FlagDescription {
    private final String flagName;
    private final boolean optional;
    private final String description;
    private final Optional<String> exampleValue;

    private AutoValue_CommandHelp_FlagDescription(String flagName, boolean optional, String description, Optional<String> exampleValue) {
        this.flagName = flagName;
        this.optional = optional;
        this.description = description;
        this.exampleValue = exampleValue;
    }

    @Override
    String getFlagName() {
        return this.flagName;
    }

    @Override
    boolean isOptional() {
        return this.optional;
    }

    @Override
    String getDescription() {
        return this.description;
    }

    @Override
    Optional<String> getExampleValue() {
        return this.exampleValue;
    }

    public String toString() {
        return "FlagDescription{flagName=" + this.flagName + ", optional=" + this.optional + ", description=" + this.description + ", exampleValue=" + this.exampleValue + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof CommandHelp.FlagDescription) {
            CommandHelp.FlagDescription that = (CommandHelp.FlagDescription)o3;
            return this.flagName.equals(that.getFlagName()) && this.optional == that.isOptional() && this.description.equals(that.getDescription()) && this.exampleValue.equals(that.getExampleValue());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.flagName.hashCode();
        h$ *= 1000003;
        h$ ^= this.optional ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.description.hashCode();
        h$ *= 1000003;
        return h$ ^= this.exampleValue.hashCode();
    }

    static final class Builder
    extends CommandHelp.FlagDescription.Builder {
        private String flagName;
        private Boolean optional;
        private String description;
        private Optional<String> exampleValue = Optional.empty();

        Builder() {
        }

        @Override
        CommandHelp.FlagDescription.Builder setFlagName(String flagName) {
            if (flagName == null) {
                throw new NullPointerException("Null flagName");
            }
            this.flagName = flagName;
            return this;
        }

        @Override
        CommandHelp.FlagDescription.Builder setOptional(boolean optional) {
            this.optional = optional;
            return this;
        }

        @Override
        CommandHelp.FlagDescription.Builder setDescription(String description) {
            if (description == null) {
                throw new NullPointerException("Null description");
            }
            this.description = description;
            return this;
        }

        @Override
        CommandHelp.FlagDescription.Builder setExampleValue(String exampleValue) {
            this.exampleValue = Optional.of(exampleValue);
            return this;
        }

        @Override
        CommandHelp.FlagDescription build() {
            String missing = "";
            if (this.flagName == null) {
                missing = missing + " flagName";
            }
            if (this.optional == null) {
                missing = missing + " optional";
            }
            if (this.description == null) {
                missing = missing + " description";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_CommandHelp_FlagDescription(this.flagName, this.optional, this.description, this.exampleValue);
        }
    }
}

