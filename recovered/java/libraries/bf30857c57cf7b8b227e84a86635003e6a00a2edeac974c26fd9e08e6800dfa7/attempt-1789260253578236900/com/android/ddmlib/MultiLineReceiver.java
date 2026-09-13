/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IShellOutputReceiver;
import com.google.common.base.Charsets;
import java.util.ArrayList;
import java.util.Collection;

public abstract class MultiLineReceiver
implements IShellOutputReceiver {
    private boolean mTrimLines = true;
    private String mUnfinishedLine = null;
    private final Collection<String> mArray = new ArrayList<String>();

    public void setTrimLine(boolean trim) {
        this.mTrimLines = trim;
    }

    @Override
    public final void addOutput(byte[] data, int offset, int length) {
        if (!this.isCancelled()) {
            String s3 = new String(data, offset, length, Charsets.UTF_8);
            if (this.mUnfinishedLine != null) {
                s3 = this.mUnfinishedLine + s3;
                this.mUnfinishedLine = null;
            }
            this.mArray.clear();
            int start = 0;
            while (true) {
                int index;
                if ((index = s3.indexOf(10, start)) == -1) break;
                int newlineLength = 1;
                if (index > 0 && s3.charAt(index - 1) == '\r') {
                    --index;
                    newlineLength = 2;
                }
                String line = s3.substring(start, index);
                if (this.mTrimLines) {
                    line = line.trim();
                }
                this.mArray.add(line);
                start = index + newlineLength;
            }
            this.mUnfinishedLine = s3.substring(start);
            if (!this.mArray.isEmpty()) {
                String[] lines = this.mArray.toArray(new String[0]);
                this.processNewLines(lines);
            }
        }
    }

    @Override
    public void flush() {
        if (this.mUnfinishedLine != null) {
            this.processNewLines(new String[]{this.mUnfinishedLine});
        }
        this.done();
    }

    public void done() {
    }

    public abstract void processNewLines(String[] var1);
}

