/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.util.concurrent.TimeUnit;

public class ScreenRecorderOptions {
    public final int width;
    public final int height;
    public final int bitrateMbps;
    public final long timeLimit;
    public final TimeUnit timeLimitUnits;
    public final boolean showTouches;

    private ScreenRecorderOptions(Builder builder) {
        this.width = builder.mWidth;
        this.height = builder.mHeight;
        this.bitrateMbps = builder.mBitRate;
        this.timeLimit = builder.mTime;
        this.timeLimitUnits = builder.mTimeUnits;
        this.showTouches = builder.mShowTouches;
    }

    public static class Builder {
        private int mWidth;
        private int mHeight;
        private int mBitRate;
        private boolean mShowTouches;
        private long mTime;
        private TimeUnit mTimeUnits;

        public Builder setSize(int w3, int h2) {
            this.mWidth = w3;
            this.mHeight = h2;
            return this;
        }

        public Builder setBitRate(int bitRateMbps) {
            this.mBitRate = bitRateMbps;
            return this;
        }

        public Builder setTimeLimit(long time, TimeUnit units) {
            this.mTime = time;
            this.mTimeUnits = units;
            return this;
        }

        public Builder setShowTouches(boolean showTouches) {
            this.mShowTouches = showTouches;
            return this;
        }

        public ScreenRecorderOptions build() {
            return new ScreenRecorderOptions(this);
        }
    }
}

