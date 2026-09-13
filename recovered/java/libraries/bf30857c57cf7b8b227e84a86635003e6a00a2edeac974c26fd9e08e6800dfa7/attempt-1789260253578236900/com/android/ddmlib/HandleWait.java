/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.ByteBuffer;

final class HandleWait
extends ChunkHandler {
    public static final int CHUNK_WAIT = ChunkHandler.type("WAIT");
    private static final HandleWait mInst = new HandleWait();

    private HandleWait() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_WAIT, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-wait", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_WAIT) {
            assert (!isReply);
            HandleWait.handleWAIT(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void handleWAIT(Client client, ByteBuffer data) {
        ClientData cd2;
        byte reason = data.get();
        Log.d("ddm-wait", "WAIT: reason=" + reason);
        ClientData clientData = cd2 = client.getClientData();
        synchronized (clientData) {
            cd2.setDebuggerConnectionStatus(ClientData.DebuggerStatus.WAITING);
        }
        client.update(2);
    }
}

