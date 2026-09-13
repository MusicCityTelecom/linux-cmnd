/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class LogCatFilter {
    private static final String PID_KEYWORD = "pid:";
    private static final String APP_KEYWORD = "app:";
    private static final String TAG_KEYWORD = "tag:";
    private static final String TEXT_KEYWORD = "text:";
    private final String mName;
    private final String mTag;
    private final String mText;
    private final String mPid;
    private final String mAppName;
    private final Log.LogLevel mLogLevel;
    private boolean mCheckPid;
    private boolean mCheckAppName;
    private boolean mCheckTag;
    private boolean mCheckText;
    private Pattern mAppNamePattern;
    private Pattern mTagPattern;
    private Pattern mTextPattern;

    public LogCatFilter(String name, String tag, String text, String pid, String appName, Log.LogLevel logLevel) {
        this.mName = name.trim();
        this.mTag = tag.trim();
        this.mText = text.trim();
        this.mPid = pid.trim();
        this.mAppName = appName.trim();
        this.mLogLevel = logLevel;
        boolean bl = this.mCheckPid = !this.mPid.isEmpty();
        if (!this.mAppName.isEmpty()) {
            try {
                this.mAppNamePattern = Pattern.compile(this.mAppName, this.getPatternCompileFlags(this.mAppName));
                this.mCheckAppName = true;
            }
            catch (PatternSyntaxException e2) {
                this.mCheckAppName = false;
            }
        }
        if (!this.mTag.isEmpty()) {
            try {
                this.mTagPattern = Pattern.compile(this.mTag, this.getPatternCompileFlags(this.mTag));
                this.mCheckTag = true;
            }
            catch (PatternSyntaxException e3) {
                this.mCheckTag = false;
            }
        }
        if (!this.mText.isEmpty()) {
            try {
                this.mTextPattern = Pattern.compile(this.mText, this.getPatternCompileFlags(this.mText));
                this.mCheckText = true;
            }
            catch (PatternSyntaxException e4) {
                this.mCheckText = false;
            }
        }
    }

    private int getPatternCompileFlags(String regex) {
        for (char c2 : regex.toCharArray()) {
            if (!Character.isUpperCase(c2)) continue;
            return 0;
        }
        return 2;
    }

    public static List<LogCatFilter> fromString(String query, Log.LogLevel minLevel) {
        ArrayList<LogCatFilter> filterSettings = new ArrayList<LogCatFilter>();
        for (String s3 : query.trim().split(" ")) {
            String tag = "";
            String text = "";
            String pid = "";
            String app = "";
            if (s3.startsWith(PID_KEYWORD)) {
                pid = s3.substring(PID_KEYWORD.length());
            } else if (s3.startsWith(APP_KEYWORD)) {
                app = s3.substring(APP_KEYWORD.length());
            } else if (s3.startsWith(TAG_KEYWORD)) {
                tag = s3.substring(TAG_KEYWORD.length());
            } else {
                text = s3.startsWith(TEXT_KEYWORD) ? s3.substring(TEXT_KEYWORD.length()) : s3;
            }
            filterSettings.add(new LogCatFilter("livefilter-" + s3, tag, text, pid, app, minLevel));
        }
        return filterSettings;
    }

    public String getName() {
        return this.mName;
    }

    public String getTag() {
        return this.mTag;
    }

    public String getText() {
        return this.mText;
    }

    public String getPid() {
        return this.mPid;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public Log.LogLevel getLogLevel() {
        return this.mLogLevel;
    }

    public boolean matches(LogCatMessage m3) {
        Matcher matcher;
        if (m3.getLogLevel().getPriority() < this.mLogLevel.getPriority()) {
            return false;
        }
        if (this.mCheckPid && !Integer.toString(m3.getPid()).equals(this.mPid)) {
            return false;
        }
        if (this.mCheckAppName && !(matcher = this.mAppNamePattern.matcher(m3.getAppName())).find()) {
            return false;
        }
        if (this.mCheckTag && !(matcher = this.mTagPattern.matcher(m3.getTag())).find()) {
            return false;
        }
        return !this.mCheckText || (matcher = this.mTextPattern.matcher(m3.getMessage())).find();
    }
}

