/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.SettableFuture;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PropertyFetcher {
    private static final String GETPROP_COMMAND = "getprop";
    private static final Pattern GETPROP_PATTERN = Pattern.compile("^\\[([^]]+)\\]\\:\\s*\\[(.*)\\]$");
    private static final int GETPROP_TIMEOUT_SEC = 2;
    private static final int EXPECTED_PROP_COUNT = 150;
    private final Map<String, String> mProperties = Maps.newHashMapWithExpectedSize(150);
    private final IDevice mDevice;
    private CacheState mCacheState = CacheState.UNPOPULATED;
    private final Map<String, SettableFuture<String>> mPendingRequests = Maps.newHashMapWithExpectedSize(4);
    private static boolean sEnableCachingMutableProps = true;

    public PropertyFetcher(IDevice device) {
        this.mDevice = device;
    }

    public synchronized Map<String, String> getProperties() {
        return this.mProperties;
    }

    public static void enableCachingMutableProps(boolean enabled) {
        sEnableCachingMutableProps = enabled;
    }

    public synchronized Future<String> getProperty(String name) {
        SettableFuture<String> result;
        if (this.mCacheState.equals((Object)CacheState.FETCHING)) {
            result = this.addPendingRequest(name);
        } else if (this.mDevice.isOnline() && this.mCacheState.equals((Object)CacheState.UNPOPULATED) || !PropertyFetcher.isImmutableProperty(name)) {
            result = this.addPendingRequest(name);
            this.mCacheState = CacheState.FETCHING;
            this.initiatePropertiesQuery();
        } else {
            result = SettableFuture.create();
            result.set(this.mProperties.get(name));
        }
        return result;
    }

    private SettableFuture<String> addPendingRequest(String name) {
        SettableFuture<String> future = this.mPendingRequests.get(name);
        if (future == null) {
            future = SettableFuture.create();
            this.mPendingRequests.put(name, future);
        }
        return future;
    }

    private void initiatePropertiesQuery() {
        String threadName = String.format("query-prop-%s", this.mDevice.getSerialNumber());
        Thread propThread = new Thread(threadName){

            @Override
            public void run() {
                try {
                    GetPropReceiver propReceiver = new GetPropReceiver();
                    PropertyFetcher.this.mDevice.executeShellCommand(PropertyFetcher.GETPROP_COMMAND, propReceiver, 2L, TimeUnit.SECONDS);
                    PropertyFetcher.this.populateCache(propReceiver.getCollectedProperties());
                }
                catch (Throwable e2) {
                    PropertyFetcher.this.handleException(e2);
                }
            }
        };
        propThread.setDaemon(true);
        propThread.start();
    }

    private synchronized void populateCache(Map<String, String> props) {
        CacheState cacheState = this.mCacheState = props.isEmpty() ? CacheState.UNPOPULATED : CacheState.POPULATED;
        if (!props.isEmpty()) {
            if (sEnableCachingMutableProps) {
                this.mProperties.putAll(props);
            } else {
                for (Map.Entry<String, Object> entry : props.entrySet()) {
                    if (!PropertyFetcher.isImmutableProperty(entry.getKey())) continue;
                    this.mProperties.put(entry.getKey(), (String)entry.getValue());
                }
            }
        }
        for (Map.Entry<String, Object> entry : this.mPendingRequests.entrySet()) {
            if (sEnableCachingMutableProps || PropertyFetcher.isImmutableProperty(entry.getKey())) {
                ((SettableFuture)entry.getValue()).set(this.mProperties.get(entry.getKey()));
                continue;
            }
            ((SettableFuture)entry.getValue()).set(props.get(entry.getKey()));
        }
        this.mPendingRequests.clear();
    }

    private synchronized void handleException(Throwable e2) {
        this.mCacheState = CacheState.UNPOPULATED;
        Log.w("PropertyFetcher", String.format("%s getting properties for device %s: %s", e2.getClass().getSimpleName(), this.mDevice.getSerialNumber(), e2.getMessage()));
        for (Map.Entry<String, SettableFuture<String>> entry : this.mPendingRequests.entrySet()) {
            entry.getValue().setException(e2);
        }
        this.mPendingRequests.clear();
    }

    @Deprecated
    public synchronized boolean arePropertiesSet() {
        return CacheState.POPULATED.equals((Object)this.mCacheState);
    }

    private static boolean isImmutableProperty(String propName) {
        return propName.startsWith("ro.") || propName.equals("qemu.sf.lcd_density");
    }

    @VisibleForTesting
    static class GetPropReceiver
    extends MultiLineReceiver {
        private final Map<String, String> mCollectedProperties = Maps.newHashMapWithExpectedSize(150);

        GetPropReceiver() {
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                Matcher m3;
                if (line.isEmpty() || line.startsWith("#") || !(m3 = GETPROP_PATTERN.matcher(line)).matches()) continue;
                String label = m3.group(1);
                String value = m3.group(2);
                if (label.isEmpty()) continue;
                this.mCollectedProperties.put(label, value);
            }
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        Map<String, String> getCollectedProperties() {
            return this.mCollectedProperties;
        }
    }

    private static enum CacheState {
        UNPOPULATED,
        FETCHING,
        POPULATED;

    }
}

