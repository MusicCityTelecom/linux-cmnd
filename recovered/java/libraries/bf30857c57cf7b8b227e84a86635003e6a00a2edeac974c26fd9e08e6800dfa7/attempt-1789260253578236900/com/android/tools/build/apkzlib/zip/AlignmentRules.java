/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.google.common.base.Preconditions;

public final class AlignmentRules {
    private AlignmentRules() {
    }

    public static AlignmentRule constant(int alignment) {
        Preconditions.checkArgument(alignment > 0, "alignment <= 0");
        return path -> alignment;
    }

    public static AlignmentRule constantForSuffix(String suffix, int alignment) {
        Preconditions.checkArgument(!suffix.isEmpty(), "suffix.isEmpty()");
        Preconditions.checkArgument(alignment > 0, "alignment <= 0");
        return path -> path.endsWith(suffix) ? alignment : 1;
    }

    public static AlignmentRule compose(AlignmentRule ... rules) {
        return path -> {
            for (AlignmentRule r3 : rules) {
                int align = r3.alignment(path);
                if (align == 1) continue;
                return align;
            }
            return 1;
        };
    }
}

