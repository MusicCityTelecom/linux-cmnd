/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.CommandHelp;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

final class AutoValue_CommandHelp
extends CommandHelp {
    private final String commandName;
    private final ImmutableList<String> subCommandNames;
    private final CommandHelp.CommandDescription commandDescription;
    private final ImmutableSortedSet<CommandHelp.FlagDescription> flags;

    private AutoValue_CommandHelp(String commandName, ImmutableList<String> subCommandNames, CommandHelp.CommandDescription commandDescription, ImmutableSortedSet<CommandHelp.FlagDescription> flags) {
        this.commandName = commandName;
        this.subCommandNames = subCommandNames;
        this.commandDescription = commandDescription;
        this.flags = flags;
    }

    @Override
    String getCommandName() {
        return this.commandName;
    }

    @Override
    ImmutableList<String> getSubCommandNames() {
        return this.subCommandNames;
    }

    @Override
    CommandHelp.CommandDescription getCommandDescription() {
        return this.commandDescription;
    }

    @Override
    ImmutableSortedSet<CommandHelp.FlagDescription> getFlags() {
        return this.flags;
    }

    public String toString() {
        return "CommandHelp{commandName=" + this.commandName + ", subCommandNames=" + this.subCommandNames + ", commandDescription=" + this.commandDescription + ", flags=" + this.flags + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof CommandHelp) {
            CommandHelp that = (CommandHelp)o3;
            return this.commandName.equals(that.getCommandName()) && this.subCommandNames.equals(that.getSubCommandNames()) && this.commandDescription.equals(that.getCommandDescription()) && this.flags.equals(that.getFlags());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.commandName.hashCode();
        h$ *= 1000003;
        h$ ^= this.subCommandNames.hashCode();
        h$ *= 1000003;
        h$ ^= this.commandDescription.hashCode();
        h$ *= 1000003;
        return h$ ^= this.flags.hashCode();
    }

    static final class Builder
    extends CommandHelp.Builder {
        private String commandName;
        private ImmutableList<String> subCommandNames;
        private CommandHelp.CommandDescription commandDescription;
        private ImmutableSortedSet<CommandHelp.FlagDescription> flags;

        Builder() {
        }

        @Override
        public CommandHelp.Builder setCommandName(String commandName) {
            if (commandName == null) {
                throw new NullPointerException("Null commandName");
            }
            this.commandName = commandName;
            return this;
        }

        @Override
        public CommandHelp.Builder setSubCommandNames(ImmutableList<String> subCommandNames) {
            if (subCommandNames == null) {
                throw new NullPointerException("Null subCommandNames");
            }
            this.subCommandNames = subCommandNames;
            return this;
        }

        @Override
        public CommandHelp.Builder setCommandDescription(CommandHelp.CommandDescription commandDescription) {
            if (commandDescription == null) {
                throw new NullPointerException("Null commandDescription");
            }
            this.commandDescription = commandDescription;
            return this;
        }

        @Override
        CommandHelp.Builder setFlags(ImmutableSortedSet<CommandHelp.FlagDescription> flags) {
            if (flags == null) {
                throw new NullPointerException("Null flags");
            }
            this.flags = flags;
            return this;
        }

        @Override
        CommandHelp autoBuild() {
            String missing = "";
            if (this.commandName == null) {
                missing = missing + " commandName";
            }
            if (this.subCommandNames == null) {
                missing = missing + " subCommandNames";
            }
            if (this.commandDescription == null) {
                missing = missing + " commandDescription";
            }
            if (this.flags == null) {
                missing = missing + " flags";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_CommandHelp(this.commandName, this.subCommandNames, this.commandDescription, this.flags);
        }
    }
}

