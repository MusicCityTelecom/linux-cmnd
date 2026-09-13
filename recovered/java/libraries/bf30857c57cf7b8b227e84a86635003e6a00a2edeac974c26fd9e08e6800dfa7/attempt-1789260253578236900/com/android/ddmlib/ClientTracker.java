/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.Client;

interface ClientTracker {
    public void trackDisconnectedClient(Client var1);

    public void trackClientToDropAndReopen(Client var1, int var2);
}

