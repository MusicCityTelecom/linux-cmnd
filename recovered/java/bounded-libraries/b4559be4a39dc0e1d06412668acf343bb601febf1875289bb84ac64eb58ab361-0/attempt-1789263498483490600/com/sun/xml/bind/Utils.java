/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind;

import com.sun.xml.bind.StackHelper;
import java.util.logging.Logger;

public final class Utils {
    private Utils() {
    }

    public static Logger getClassLogger() {
        try {
            return Logger.getLogger(StackHelper.getCallerClassName());
        }
        catch (SecurityException e) {
            return Logger.getLogger("com.sun.xml.bind");
        }
    }

    public static String getSystemProperty(String name) {
        try {
            return System.getProperty(name);
        }
        catch (SecurityException e) {
            return null;
        }
    }
}

