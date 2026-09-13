/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.ByteBuffer;

final class HandleExit
extends ChunkHandler {
    public static final int CHUNK_EXIT = HandleExit.type("EXIT");
    private static final HandleExit mInst = new HandleExit();

    private HandleExit() {
    }

    public static void register(MonitorThread mt) {
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        this.handleUnknownChunk(client, type, data, isReply, msgId);
    }

    public static void sendEXIT(Client client, int status) throws IOException {
        ByteBuffer rawBuf = HandleExit.allocBuffer(4);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleExit.getChunkDataBuf(rawBuf);
        buf.putInt(status);
        HandleExit.finishChunkPacket(packet, CHUNK_EXIT, buf.position());
        Log.d("ddm-exit", "Sending " + HandleExit.name(CHUNK_EXIT) + ": " + status);
        client.send(packet, mInst);
    }
}

