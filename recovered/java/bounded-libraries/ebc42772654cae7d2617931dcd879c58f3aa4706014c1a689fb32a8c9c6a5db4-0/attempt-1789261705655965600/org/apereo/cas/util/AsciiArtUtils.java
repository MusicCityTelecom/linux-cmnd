/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.Marker
 *  org.slf4j.MarkerFactory
 */
package org.apereo.cas.util;

import java.io.PrintStream;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public final class AsciiArtUtils {
    private static final Marker ASCII_ART_LOGGER_MARKER = MarkerFactory.getMarker((String)"AsciiArt");
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_CYAN = "\u001b[36m";

    public static void printAsciiArt(PrintStream out, String asciiArt, String additional) {
        out.println(ANSI_CYAN);
        if (StringUtils.isNotBlank((CharSequence)additional)) {
            out.println(asciiArt);
            out.println(additional);
        } else {
            out.print(asciiArt);
        }
        out.println(ANSI_RESET);
    }

    public static void printAsciiArtWarning(Logger out, String additional) {
        String ascii = "\n  ____ _____ ___  ____  _ \n / ___|_   _/ _ \\|  _ \\| |\n \\___ \\ | || | | | |_) | |\n  ___) || || |_| |  __/|_|\n |____/ |_| \\___/|_|   (_)\n                          \n";
        out.warn(ASCII_ART_LOGGER_MARKER, ANSI_CYAN);
        out.warn(ASCII_ART_LOGGER_MARKER, "\n\n".concat("\n  ____ _____ ___  ____  _ \n / ___|_   _/ _ \\|  _ \\| |\n \\___ \\ | || | | | |_) | |\n  ___) || || |_| |  __/|_|\n |____/ |_| \\___/|_|   (_)\n                          \n").concat(additional));
        out.warn(ASCII_ART_LOGGER_MARKER, ANSI_RESET);
    }

    public static void printAsciiArtReady(Logger out, String additional) {
        String ascii = "\n  ____  _____    _    ______   __\n |  _ \\| ____|  / \\  |  _ \\ \\ / /\n | |_) |  _|   / _ \\ | | | \\ V / \n |  _ <| |___ / ___ \\| |_| || |  \n |_| \\_\\_____/_/   \\_\\____/ |_|  \n                                 \n";
        out.info(ASCII_ART_LOGGER_MARKER, ANSI_CYAN);
        out.info(ASCII_ART_LOGGER_MARKER, "\n\n".concat("\n  ____  _____    _    ______   __\n |  _ \\| ____|  / \\  |  _ \\ \\ / /\n | |_) |  _|   / _ \\ | | | \\ V / \n |  _ <| |___ / ___ \\| |_| || |  \n |_| \\_\\_____/_/   \\_\\____/ |_|  \n                                 \n").concat(additional));
        out.info(ASCII_ART_LOGGER_MARKER, ANSI_RESET);
    }

    @Generated
    private AsciiArtUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

