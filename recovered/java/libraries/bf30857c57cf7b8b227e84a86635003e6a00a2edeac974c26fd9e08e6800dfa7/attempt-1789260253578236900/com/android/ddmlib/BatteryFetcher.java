/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.google.common.util.concurrent.SettableFuture;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BatteryFetcher {
    private static final String LOG_TAG = "BatteryFetcher";
    private static final long BATTERY_TIMEOUT_MS = 2000L;
    private Integer mBatteryLevel;
    private final IDevice mDevice;
    private long mLastSuccessTime;
    private SettableFuture<Integer> mPendingRequest;

    public BatteryFetcher(IDevice device) {
        this.mDevice = device;
    }

    public synchronized Future<Integer> getBattery(long freshness, TimeUnit timeUnit) {
        SettableFuture<Integer> result;
        if (this.mBatteryLevel == null || this.isFetchRequired(freshness, timeUnit)) {
            if (this.mPendingRequest == null) {
                this.mPendingRequest = SettableFuture.create();
                this.initiateBatteryQuery();
            }
            result = this.mPendingRequest;
        } else {
            result = SettableFuture.create();
            result.set(this.mBatteryLevel);
        }
        return result;
    }

    private boolean isFetchRequired(long freshness, TimeUnit timeUnit) {
        long freshnessMs = timeUnit.toMillis(freshness);
        return System.currentTimeMillis() - this.mLastSuccessTime > freshnessMs;
    }

    private void initiateBatteryQuery() {
        String threadName = String.format("query-battery-%s", this.mDevice.getSerialNumber());
        Thread fetchThread = new Thread(threadName){

            @Override
            public void run() {
                Throwable exception;
                try {
                    SysFsBatteryLevelReceiver sysBattReceiver = new SysFsBatteryLevelReceiver();
                    BatteryFetcher.this.mDevice.executeShellCommand("cat /sys/class/power_supply/*/capacity", sysBattReceiver, 2000L, TimeUnit.MILLISECONDS);
                    if (!BatteryFetcher.this.setBatteryLevel(sysBattReceiver.getBatteryLevel())) {
                        BatteryReceiver receiver = new BatteryReceiver();
                        BatteryFetcher.this.mDevice.executeShellCommand("dumpsys battery", receiver, 2000L, TimeUnit.MILLISECONDS);
                        if (BatteryFetcher.this.setBatteryLevel(receiver.getBatteryLevel())) {
                            return;
                        }
                    } else {
                        return;
                    }
                    exception = new IOException("Unrecognized response to battery level queries");
                }
                catch (Throwable e2) {
                    exception = e2;
                }
                BatteryFetcher.this.handleBatteryLevelFailure(exception);
            }
        };
        fetchThread.setDaemon(true);
        fetchThread.start();
    }

    private synchronized boolean setBatteryLevel(Integer batteryLevel) {
        if (batteryLevel == null) {
            return false;
        }
        this.mLastSuccessTime = System.currentTimeMillis();
        this.mBatteryLevel = batteryLevel;
        if (this.mPendingRequest != null) {
            this.mPendingRequest.set(this.mBatteryLevel);
        }
        this.mPendingRequest = null;
        return true;
    }

    private synchronized void handleBatteryLevelFailure(Throwable e2) {
        Log.w(LOG_TAG, String.format("%s getting battery level for device %s: %s", e2.getClass().getSimpleName(), this.mDevice.getSerialNumber(), e2.getMessage()));
        if (this.mPendingRequest != null && !this.mPendingRequest.setException(e2)) {
            Log.e(LOG_TAG, "Future.setException failed");
            this.mPendingRequest.set(null);
        }
        this.mPendingRequest = null;
    }

    private static final class BatteryReceiver
    extends MultiLineReceiver {
        private static final Pattern BATTERY_LEVEL = Pattern.compile("\\s*level: (\\d+)");
        private static final Pattern SCALE = Pattern.compile("\\s*scale: (\\d+)");
        private Integer mBatteryLevel;
        private Integer mBatteryScale;

        private BatteryReceiver() {
        }

        public Integer getBatteryLevel() {
            if (this.mBatteryLevel != null && this.mBatteryScale != null) {
                return this.mBatteryLevel * 100 / this.mBatteryScale;
            }
            return null;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                Matcher scaleMatch;
                Matcher batteryMatch = BATTERY_LEVEL.matcher(line);
                if (batteryMatch.matches()) {
                    try {
                        this.mBatteryLevel = Integer.parseInt(batteryMatch.group(1));
                    }
                    catch (NumberFormatException e2) {
                        Log.w(BatteryFetcher.LOG_TAG, String.format("Failed to parse %s as an integer", batteryMatch.group(1)));
                    }
                }
                if (!(scaleMatch = SCALE.matcher(line)).matches()) continue;
                try {
                    this.mBatteryScale = Integer.parseInt(scaleMatch.group(1));
                }
                catch (NumberFormatException e3) {
                    Log.w(BatteryFetcher.LOG_TAG, String.format("Failed to parse %s as an integer", batteryMatch.group(1)));
                }
            }
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }

    static final class SysFsBatteryLevelReceiver
    extends MultiLineReceiver {
        private static final Pattern BATTERY_LEVEL = Pattern.compile("^(\\d+)[.\\s]*");
        private Integer mBatteryLevel;

        SysFsBatteryLevelReceiver() {
        }

        public Integer getBatteryLevel() {
            return this.mBatteryLevel;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                Matcher batteryMatch = BATTERY_LEVEL.matcher(line);
                if (!batteryMatch.matches()) continue;
                if (this.mBatteryLevel == null) {
                    this.mBatteryLevel = Integer.parseInt(batteryMatch.group(1));
                    continue;
                }
                Integer tmpLevel = Integer.parseInt(batteryMatch.group(1));
                if (this.mBatteryLevel.equals(tmpLevel)) continue;
                Log.w(BatteryFetcher.LOG_TAG, String.format("Multiple lines matched with different value; Original: %s, Current: %s (keeping original)", this.mBatteryLevel.toString(), tmpLevel.toString()));
            }
        }
    }
}

