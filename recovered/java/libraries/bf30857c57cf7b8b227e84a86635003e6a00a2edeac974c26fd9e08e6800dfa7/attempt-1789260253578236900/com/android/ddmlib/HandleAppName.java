/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ByteBufferUtil;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

final class HandleAppName
extends ChunkHandler {
    public static final int CHUNK_APNM = ChunkHandler.type("APNM");
    private static final HandleAppName mInst = new HandleAppName();

    private HandleAppName() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_APNM, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-appname", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_APNM) {
            assert (!isReply);
            HandleAppName.handleAPNM(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void handleAPNM(Client client, ByteBuffer data) {
        ClientData cd2;
        int appNameLen = data.getInt();
        String appName = ByteBufferUtil.getString(data, appNameLen);
        int userId = -1;
        boolean validUserId = false;
        if (data.hasRemaining()) {
            try {
                userId = data.getInt();
                validUserId = true;
            }
            catch (BufferUnderflowException e2) {
                int expectedPacketLength = 8 + appNameLen * 2;
                Log.e("ddm-appname", "Insufficient data in APNM chunk to retrieve user id.");
                Log.e("ddm-appname", "Actual chunk length: " + data.capacity());
                Log.e("ddm-appname", "Expected chunk length: " + expectedPacketLength);
            }
        }
        Log.d("ddm-appname", "APNM: app='" + appName + "'");
        ClientData clientData = cd2 = client.getClientData();
        synchronized (clientData) {
            cd2.setClientDescription(appName);
            if (validUserId) {
                cd2.setUserId(userId);
            }
        }
        client = HandleAppName.checkDebuggerPortForAppName(client, appName);
        if (client != null) {
            client.update(1);
        }
    }
}

