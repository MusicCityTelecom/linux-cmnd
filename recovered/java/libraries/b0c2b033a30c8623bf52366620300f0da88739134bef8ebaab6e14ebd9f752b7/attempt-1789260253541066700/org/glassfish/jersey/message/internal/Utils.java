/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.File;
import java.io.IOException;

public final class Utils {
    static void throwIllegalArgumentExceptionIfNull(Object toCheck, String errorMessage) {
        if (toCheck == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static File createTempFile() throws IOException {
        File file = File.createTempFile("rep", "tmp");
        file.deleteOnExit();
        return file;
    }

    private Utils() {
        throw new AssertionError((Object)"No instances allowed.");
    }
}

