/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.LruTracker;
import com.android.tools.build.apkzlib.bytestorage.SwitchableDelegateCloseableByteSource;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;

class LruTrackedCloseableByteSource
extends SwitchableDelegateCloseableByteSource {
    private final LruTracker<LruTrackedCloseableByteSource> tracker;
    private boolean tracking;
    private boolean closed;

    LruTrackedCloseableByteSource(CloseableByteSource delegate, LruTracker<LruTrackedCloseableByteSource> tracker) throws IOException {
        super(delegate);
        this.tracker = tracker;
        tracker.track(this);
        this.tracking = true;
        this.closed = false;
    }

    @Override
    public synchronized InputStream openStream() throws IOException {
        Preconditions.checkState(!this.closed);
        if (this.tracking) {
            this.tracker.access(this);
        }
        return super.openStream();
    }

    @Override
    protected synchronized void innerClose() throws IOException {
        this.closed = true;
        this.untrack();
        super.innerClose();
    }

    private synchronized void untrack() {
        if (this.tracking) {
            this.tracking = false;
            this.tracker.untrack(this);
        }
    }

    synchronized void move(ByteStorage diskStorage) throws IOException {
        if (this.closed) {
            return;
        }
        CloseableByteSource diskSource = diskStorage.fromSource(this);
        this.untrack();
        this.switchSource(diskSource);
    }
}

