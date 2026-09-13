/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.DdmPreferences;
import com.google.common.collect.Sets;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public final class Log {
    private static LogLevel sLevel = DdmPreferences.getLogLevel();
    private static ILogOutput sLogOutput;
    private static final Set<ILogOutput> sOutputLoggers;
    private static final char[] mSpaceLine;
    private static final char[] mHexDigit;

    private Log() {
    }

    public static void v(String tag, String message) {
        Log.println(LogLevel.VERBOSE, tag, message);
    }

    public static void d(String tag, String message) {
        Log.println(LogLevel.DEBUG, tag, message);
    }

    public static void i(String tag, String message) {
        Log.println(LogLevel.INFO, tag, message);
    }

    public static void w(String tag, String message) {
        Log.println(LogLevel.WARN, tag, message);
    }

    public static void e(String tag, String message) {
        Log.println(LogLevel.ERROR, tag, message);
    }

    public static void logAndDisplay(LogLevel logLevel, String tag, String message) {
        if (!sOutputLoggers.isEmpty()) {
            for (ILogOutput logger : sOutputLoggers) {
                logger.printAndPromptLog(logLevel, tag, message);
            }
        }
        if (sLogOutput != null) {
            sLogOutput.printAndPromptLog(logLevel, tag, message);
        } else if (sOutputLoggers.isEmpty()) {
            Log.println(logLevel, tag, message);
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            Log.println(LogLevel.ERROR, tag, throwable.getMessage() + '\n' + sw.toString());
        }
    }

    static void setLevel(LogLevel logLevel) {
        sLevel = logLevel;
    }

    static boolean isAtLeast(LogLevel logLevel) {
        return logLevel.getPriority() >= sLevel.getPriority();
    }

    @Deprecated
    public static void setLogOutput(ILogOutput logOutput) {
        sLogOutput = logOutput;
    }

    public static void addLogger(ILogOutput logOutput) {
        sOutputLoggers.add(logOutput);
    }

    public static void removeLogger(ILogOutput logOutput) {
        sOutputLoggers.remove(logOutput);
    }

    static void hexDump(String tag, LogLevel level, byte[] data, int offset, int length) {
        int kHexOffset = 6;
        int kAscOffset = 55;
        char[] line = new char[mSpaceLine.length];
        boolean needErase = true;
        int baseAddr = 0;
        while (length != 0) {
            int count;
            if (length > 16) {
                count = 16;
            } else {
                count = length;
                needErase = true;
            }
            if (needErase) {
                System.arraycopy(mSpaceLine, 0, line, 0, mSpaceLine.length);
                needErase = false;
            }
            int addr = baseAddr;
            addr &= 0xFFFF;
            int ch = 3;
            while (addr != 0) {
                line[ch] = mHexDigit[addr & 0xF];
                --ch;
                addr >>>= 4;
            }
            ch = kHexOffset;
            for (int i2 = 0; i2 < count; ++i2) {
                byte val = data[offset + i2];
                line[ch++] = mHexDigit[val >>> 4 & 0xF];
                line[ch++] = mHexDigit[val & 0xF];
                ++ch;
                line[kAscOffset + i2] = val >= 32 && val < 127 ? (int)val : 46;
            }
            Log.println(level, tag, new String(line));
            length -= count;
            offset += count;
            baseAddr += count;
        }
    }

    static void hexDump(byte[] data) {
        Log.hexDump("ddms", LogLevel.DEBUG, data, 0, data.length);
    }

    private static void println(LogLevel logLevel, String tag, String message) {
        if (!Log.isAtLeast(logLevel)) {
            return;
        }
        if (!sOutputLoggers.isEmpty()) {
            for (ILogOutput logger : sOutputLoggers) {
                logger.printLog(logLevel, tag, message);
            }
        }
        if (sLogOutput != null) {
            sLogOutput.printLog(logLevel, tag, message);
        } else if (sOutputLoggers.isEmpty()) {
            Log.printLog(logLevel, tag, message);
        }
    }

    public static void printLog(LogLevel logLevel, String tag, String message) {
        System.out.print(Log.getLogFormatString(logLevel, tag, message));
    }

    public static String getLogFormatString(LogLevel logLevel, String tag, String message) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        return String.format("%s %c/%s: %s\n", formatter.format(new Date()), Character.valueOf(logLevel.getPriorityLetter()), tag, message);
    }

    static {
        sOutputLoggers = Sets.newCopyOnWriteArraySet();
        mSpaceLine = new char[72];
        mHexDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int i2 = mSpaceLine.length - 1;
        while (i2 >= 0) {
            Log.mSpaceLine[i2--] = 32;
        }
        Log.mSpaceLine[3] = 48;
        Log.mSpaceLine[2] = 48;
        Log.mSpaceLine[1] = 48;
        Log.mSpaceLine[0] = 48;
        Log.mSpaceLine[4] = 45;
    }

    static final class Config {
        static final boolean LOGV = true;
        static final boolean LOGD = true;

        Config() {
        }
    }

    public static interface ILogOutput {
        public void printLog(LogLevel var1, String var2, String var3);

        public void printAndPromptLog(LogLevel var1, String var2, String var3);
    }

    public static enum LogLevel {
        VERBOSE(2, "verbose", 'V'),
        DEBUG(3, "debug", 'D'),
        INFO(4, "info", 'I'),
        WARN(5, "warn", 'W'),
        ERROR(6, "error", 'E'),
        ASSERT(7, "assert", 'A');

        private int mPriorityLevel;
        private String mStringValue;
        private char mPriorityLetter;

        private LogLevel(int intPriority, String stringValue, char priorityChar) {
            this.mPriorityLevel = intPriority;
            this.mStringValue = stringValue;
            this.mPriorityLetter = priorityChar;
        }

        public static LogLevel getByString(String value) {
            for (LogLevel mode : LogLevel.values()) {
                if (!mode.mStringValue.equals(value)) continue;
                return mode;
            }
            return null;
        }

        public static LogLevel getByLetter(char letter) {
            for (LogLevel mode : LogLevel.values()) {
                if (mode.mPriorityLetter != letter) continue;
                return mode;
            }
            return null;
        }

        public static LogLevel getByLetterString(String letter) {
            if (!letter.isEmpty()) {
                return LogLevel.getByLetter(letter.charAt(0));
            }
            return null;
        }

        public char getPriorityLetter() {
            return this.mPriorityLetter;
        }

        public int getPriority() {
            return this.mPriorityLevel;
        }

        public String getStringValue() {
            return this.mStringValue;
        }
    }
}

