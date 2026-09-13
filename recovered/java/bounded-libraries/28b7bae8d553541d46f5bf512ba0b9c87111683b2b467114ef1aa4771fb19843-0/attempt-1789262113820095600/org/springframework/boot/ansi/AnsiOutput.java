/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.ansi;

import java.util.Locale;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.util.Assert;

public abstract class AnsiOutput {
    private static final String ENCODE_JOIN = ";";
    private static Enabled enabled = Enabled.DETECT;
    private static Boolean consoleAvailable;
    private static Boolean ansiCapable;
    private static final String OPERATING_SYSTEM_NAME;
    private static final String ENCODE_START = "\u001b[";
    private static final String ENCODE_END = "m";
    private static final String RESET;

    public static void setEnabled(Enabled enabled) {
        Assert.notNull((Object)((Object)enabled), (String)"Enabled must not be null");
        AnsiOutput.enabled = enabled;
    }

    public static Enabled getEnabled() {
        return enabled;
    }

    public static void setConsoleAvailable(Boolean consoleAvailable) {
        AnsiOutput.consoleAvailable = consoleAvailable;
    }

    public static String encode(AnsiElement element) {
        if (AnsiOutput.isEnabled()) {
            return ENCODE_START + element + ENCODE_END;
        }
        return "";
    }

    public static String toString(Object ... elements) {
        StringBuilder sb = new StringBuilder();
        if (AnsiOutput.isEnabled()) {
            AnsiOutput.buildEnabled(sb, elements);
        } else {
            AnsiOutput.buildDisabled(sb, elements);
        }
        return sb.toString();
    }

    private static void buildEnabled(StringBuilder sb, Object[] elements) {
        boolean writingAnsi = false;
        boolean containsEncoding = false;
        for (Object element : elements) {
            if (element instanceof AnsiElement) {
                containsEncoding = true;
                if (!writingAnsi) {
                    sb.append(ENCODE_START);
                    writingAnsi = true;
                } else {
                    sb.append(ENCODE_JOIN);
                }
            } else if (writingAnsi) {
                sb.append(ENCODE_END);
                writingAnsi = false;
            }
            sb.append(element);
        }
        if (containsEncoding) {
            sb.append(writingAnsi ? ENCODE_JOIN : ENCODE_START);
            sb.append(RESET);
            sb.append(ENCODE_END);
        }
    }

    private static void buildDisabled(StringBuilder sb, Object[] elements) {
        for (Object element : elements) {
            if (element instanceof AnsiElement || element == null) continue;
            sb.append(element);
        }
    }

    private static boolean isEnabled() {
        if (enabled == Enabled.DETECT) {
            if (ansiCapable == null) {
                ansiCapable = AnsiOutput.detectIfAnsiCapable();
            }
            return ansiCapable;
        }
        return enabled == Enabled.ALWAYS;
    }

    private static boolean detectIfAnsiCapable() {
        try {
            if (Boolean.FALSE.equals(consoleAvailable)) {
                return false;
            }
            if (consoleAvailable == null && System.console() == null) {
                return false;
            }
            return !OPERATING_SYSTEM_NAME.contains("win");
        }
        catch (Throwable ex) {
            return false;
        }
    }

    static {
        OPERATING_SYSTEM_NAME = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        RESET = "0;" + AnsiColor.DEFAULT;
    }

    public static enum Enabled {
        DETECT,
        ALWAYS,
        NEVER;

    }
}

