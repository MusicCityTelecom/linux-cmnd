/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.model.exceptions.ParseException;
import com.google.common.collect.ImmutableList;

public class DeviceFeaturesParser {
    private static final String EXPECTED_LINE_PREFIX = "feature:";
    private static final String WARNING_LINE_PREFIX = "WARNING:";

    public ImmutableList<String> parse(ImmutableList<String> packageManagerListOutput) {
        ImmutableList.Builder features = ImmutableList.builder();
        for (String line : packageManagerListOutput) {
            if (line.isEmpty() || line.startsWith(WARNING_LINE_PREFIX)) continue;
            if (!line.startsWith(EXPECTED_LINE_PREFIX)) {
                throw ParseException.builder().withMessage("Unexpected output of 'pm list features' command: '%s'", line).build();
            }
            features.add(line.substring(EXPECTED_LINE_PREFIX.length()));
        }
        return features.build();
    }
}

