/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IShellOutputReceiver;
import com.google.common.base.Charsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class CollectingOutputReceiver
implements IShellOutputReceiver {
    private CountDownLatch mCompletionLatch;
    private StringBuffer mOutputBuffer = new StringBuffer();
    private AtomicBoolean mIsCanceled = new AtomicBoolean(false);

    public CollectingOutputReceiver() {
    }

    public CollectingOutputReceiver(CountDownLatch commandCompleteLatch) {
        this.mCompletionLatch = commandCompleteLatch;
    }

    public String getOutput() {
        return this.mOutputBuffer.toString();
    }

    @Override
    public boolean isCancelled() {
        return this.mIsCanceled.get();
    }

    public void cancel() {
        this.mIsCanceled.set(true);
    }

    @Override
    public void addOutput(byte[] data, int offset, int length) {
        if (!this.isCancelled()) {
            String s3 = new String(data, offset, length, Charsets.UTF_8);
            this.mOutputBuffer.append(s3);
        }
    }

    @Override
    public void flush() {
        if (this.mCompletionLatch != null) {
            this.mCompletionLatch.countDown();
        }
    }
}

