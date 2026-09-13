/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.flags;

import com.android.tools.build.bundletool.flags.FlagParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class ParsedFlags {
    private final Set<String> accessedFlags = new HashSet<String>();
    private final ImmutableList<String> commands;
    private final ImmutableListMultimap<String, String> flags;

    ParsedFlags(ImmutableList<String> commands, ImmutableListMultimap<String, String> flags) {
        this.commands = commands;
        this.flags = flags;
    }

    public ImmutableList<String> getCommands() {
        return this.commands;
    }

    public Optional<String> getMainCommand() {
        return this.getSubCommand(0);
    }

    public Optional<String> getSubCommand() {
        return this.getSubCommand(1);
    }

    private Optional<String> getSubCommand(int index) {
        return index < this.commands.size() ? Optional.of(this.commands.get(index)) : Optional.empty();
    }

    Optional<String> getFlagValue(String name) {
        ImmutableList<String> values2 = this.getFlagValues(name);
        switch (values2.size()) {
            case 0: {
                return Optional.empty();
            }
            case 1: {
                return Optional.of(values2.get(0));
            }
        }
        throw new FlagParser.FlagParseException(String.format("Flag --%s has been set more than once.", name));
    }

    ImmutableList<String> getFlagValues(String name) {
        this.accessedFlags.add(name);
        return this.flags.get((Object)name);
    }

    public void checkNoUnknownFlags() {
        Sets.SetView<String> unknownFlags = Sets.difference(this.flags.keySet(), this.accessedFlags);
        if (!unknownFlags.isEmpty()) {
            throw new UnknownFlagsException(unknownFlags);
        }
    }

    public static class UnknownFlagsException
    extends FlagParser.FlagParseException {
        public UnknownFlagsException(Collection<String> flags) {
            super("Unrecognized flags: " + flags.stream().map(f2 -> "--" + f2).collect(Collectors.joining(", ")));
        }
    }
}

