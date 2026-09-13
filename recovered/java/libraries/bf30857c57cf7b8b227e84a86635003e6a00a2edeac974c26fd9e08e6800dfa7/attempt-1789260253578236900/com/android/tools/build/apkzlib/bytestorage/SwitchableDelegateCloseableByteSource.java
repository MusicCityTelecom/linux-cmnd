/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.SwitchableDelegateInputStream;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.io.Closer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class SwitchableDelegateCloseableByteSource
extends CloseableByteSource {
    private CloseableByteSource delegate;
    private boolean closed;
    private final List<SwitchableDelegateInputStream> nonClosedStreams;

    SwitchableDelegateCloseableByteSource(CloseableByteSource source) {
        this.delegate = source;
        this.nonClosedStreams = new ArrayList<SwitchableDelegateInputStream>();
    }

    @Override
    protected synchronized void innerClose() throws IOException {
        this.closed = true;
        try (Closer closer = Closer.create();){
            for (SwitchableDelegateInputStream stream : this.nonClosedStreams) {
                closer.register(stream);
            }
            this.nonClosedStreams.clear();
        }
        this.delegate.close();
    }

    @Override
    public synchronized InputStream openStream() throws IOException {
        SwitchableDelegateInputStream stream = new SwitchableDelegateInputStream(this.delegate.openStream()){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void close() throws IOException {
                SwitchableDelegateCloseableByteSource switchableDelegateCloseableByteSource = SwitchableDelegateCloseableByteSource.this;
                synchronized (switchableDelegateCloseableByteSource) {
                    SwitchableDelegateCloseableByteSource.this.nonClosedStreams.remove(this);
                }
                super.close();
            }
        };
        this.nonClosedStreams.add(stream);
        return stream;
    }

    synchronized void switchSource(CloseableByteSource source) throws IOException {
        if (source == this.delegate) {
            return;
        }
        if (this.closed) {
            source.close();
            return;
        }
        ArrayList<InputStream> switchStreams = new ArrayList<InputStream>();
        for (int i2 = 0; i2 < this.nonClosedStreams.size(); ++i2) {
            switchStreams.add(source.openStream());
        }
        CloseableByteSource oldDelegate = this.delegate;
        this.delegate = source;
        try (Closer closer = Closer.create();){
            for (int i3 = 0; i3 < this.nonClosedStreams.size(); ++i3) {
                SwitchableDelegateInputStream nonClosedStream = this.nonClosedStreams.get(i3);
                InputStream switchStream = (InputStream)switchStreams.get(i3);
                closer.register(() -> nonClosedStream.switchStream(switchStream));
            }
            closer.register(oldDelegate);
        }
    }
}

