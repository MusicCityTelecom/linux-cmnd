/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.utils;

import java.util.ArrayList;
import java.util.List;

public class DebuggerPorts {
    private final List<Integer> mDebuggerPorts = new ArrayList<Integer>();

    public DebuggerPorts(int basePort) {
        this.mDebuggerPorts.add(basePort);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int next() {
        List<Integer> list = this.mDebuggerPorts;
        synchronized (list) {
            if (!this.mDebuggerPorts.isEmpty()) {
                int port = this.mDebuggerPorts.get(0);
                this.mDebuggerPorts.remove(0);
                if (this.mDebuggerPorts.isEmpty()) {
                    this.mDebuggerPorts.add(port + 1);
                }
                return port;
            }
        }
        return -1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void free(int port) {
        if (port <= 0) {
            return;
        }
        List<Integer> list = this.mDebuggerPorts;
        synchronized (list) {
            if (this.mDebuggerPorts.indexOf(port) == -1) {
                int count = this.mDebuggerPorts.size();
                for (int i2 = 0; i2 < count; ++i2) {
                    if (port >= this.mDebuggerPorts.get(i2)) continue;
                    this.mDebuggerPorts.add(i2, port);
                    break;
                }
            }
        }
    }
}

