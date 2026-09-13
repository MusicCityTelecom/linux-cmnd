/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.android.tools.build.bundletool.model.AbiName;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;

public class AbiStringParser {
    public static ImmutableList<String> parseAbiLine(String abiLine) {
        Preconditions.checkArgument(abiLine.startsWith("abi:"), "Expected ABI output to start with 'abi:'.");
        String abiString = abiLine.substring("abi: ".length());
        return Arrays.stream(abiString.split(",")).map(String::trim).filter(abiName -> AbiName.fromPlatformName(abiName).isPresent()).collect(ImmutableList.toImmutableList());
    }
}

