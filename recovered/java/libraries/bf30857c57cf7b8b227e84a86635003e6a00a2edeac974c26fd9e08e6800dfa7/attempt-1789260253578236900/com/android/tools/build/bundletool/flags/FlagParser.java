/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.flags;

import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlagParser {
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on("=").limit(2);

    public ParsedFlags parse(String ... args) {
        ArrayList commands = new ArrayList();
        ArrayList<String> argsToProcess = new ArrayList<String>(Arrays.asList(args));
        while (argsToProcess.size() > 0 && !((String)argsToProcess.get(0)).startsWith("-")) {
            commands.add(argsToProcess.get(0));
            argsToProcess.remove(0);
        }
        return new ParsedFlags(ImmutableList.copyOf(commands), this.parseFlags(argsToProcess));
    }

    private ImmutableListMultimap<String, String> parseFlags(List<String> args) {
        ImmutableListMultimap.Builder flagMap = ImmutableListMultimap.builder();
        String lastFlag = null;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (lastFlag != null) {
                    flagMap.put(lastFlag, "");
                    lastFlag = null;
                }
                if (arg.contains(KEY_VALUE_SEPARATOR)) {
                    List<String> segments = KEY_VALUE_SPLITTER.splitToList(arg);
                    flagMap.put(segments.get(0).substring(2), segments.get(1));
                    continue;
                }
                lastFlag = arg.substring(2);
                continue;
            }
            if (lastFlag == null) {
                throw new FlagParseException(String.format("Syntax error: flags should start with -- (%s)", arg));
            }
            flagMap.put(lastFlag, arg);
            lastFlag = null;
        }
        if (lastFlag != null) {
            flagMap.put(lastFlag, "");
        }
        return flagMap.build();
    }

    public static class FlagParseException
    extends IllegalStateException {
        public FlagParseException(String message) {
            super(message);
        }
    }
}

