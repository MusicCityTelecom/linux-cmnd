/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ByteBufferUtil;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.HandleProfiling;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

final class HandleHello
extends ChunkHandler {
    public static final int CHUNK_HELO = ChunkHandler.type("HELO");
    public static final int CHUNK_FEAT = ChunkHandler.type("FEAT");
    private static final HandleHello mInst = new HandleHello();

    private HandleHello() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_HELO, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
        Log.d("ddm-hello", "Now ready: " + client);
    }

    @Override
    public void clientDisconnected(Client client) {
        Log.d("ddm-hello", "Now disconnected: " + client);
    }

    public static void sendHelloCommands(Client client, int serverProtocolVersion) throws IOException {
        HandleHello.sendHELO(client, serverProtocolVersion);
        HandleHello.sendFEAT(client);
        HandleProfiling.sendMPRQ(client);
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-hello", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_HELO) {
            assert (isReply);
            HandleHello.handleHELO(client, data);
        } else if (type == CHUNK_FEAT) {
            HandleHello.handleFEAT(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    private static void handleHELO(Client client, ByteBuffer data) {
        int version = data.getInt();
        int pid = data.getInt();
        int vmIdentLen = data.getInt();
        int appNameLen = data.getInt();
        String vmIdent = ByteBufferUtil.getString(data, vmIdentLen);
        String appName = ByteBufferUtil.getString(data, appNameLen);
        int userId = -1;
        boolean validUserId = false;
        if (data.hasRemaining()) {
            try {
                userId = data.getInt();
                validUserId = true;
            }
            catch (BufferUnderflowException e2) {
                int expectedPacketLength = 20 + appNameLen * 2 + vmIdentLen * 2;
                Log.e("ddm-hello", "Insufficient data in HELO chunk to retrieve user id.");
                Log.e("ddm-hello", "Actual chunk length: " + data.capacity());
                Log.e("ddm-hello", "Expected chunk length: " + expectedPacketLength);
            }
        }
        boolean validAbi = false;
        String abi = null;
        if (data.hasRemaining()) {
            try {
                int abiLength = data.getInt();
                abi = ByteBufferUtil.getString(data, abiLength);
                validAbi = true;
            }
            catch (BufferUnderflowException e3) {
                Log.e("ddm-hello", "Insufficient data in HELO chunk to retrieve ABI.");
            }
        }
        boolean hasJvmFlags = false;
        String jvmFlags = null;
        if (data.hasRemaining()) {
            try {
                int jvmFlagsLength = data.getInt();
                jvmFlags = ByteBufferUtil.getString(data, jvmFlagsLength);
                hasJvmFlags = true;
            }
            catch (BufferUnderflowException e4) {
                Log.e("ddm-hello", "Insufficient data in HELO chunk to retrieve JVM flags");
            }
        }
        boolean nativeDebuggable = false;
        if (data.hasRemaining()) {
            try {
                byte nativeDebuggableByte = data.get();
                nativeDebuggable = nativeDebuggableByte == 1;
            }
            catch (BufferUnderflowException e5) {
                Log.e("ddm-hello", "Insufficient data in HELO chunk to retrieve nativeDebuggable");
            }
        }
        Log.d("ddm-hello", "HELO: v=" + version + ", pid=" + pid + ", vm='" + vmIdent + "', app='" + appName + "'");
        ClientData cd2 = client.getClientData();
        if (cd2.getPid() == pid) {
            cd2.setVmIdentifier(vmIdent);
            cd2.setClientDescription(appName);
            cd2.isDdmAware(true);
            if (validUserId) {
                cd2.setUserId(userId);
            }
            if (validAbi) {
                cd2.setAbi(abi);
            }
            if (hasJvmFlags) {
                cd2.setJvmFlags(jvmFlags);
            }
            cd2.setNativeDebuggable(nativeDebuggable);
        } else {
            Log.e("ddm-hello", "Received pid (" + pid + ") does not match client pid (" + cd2.getPid() + ")");
        }
        client = HandleHello.checkDebuggerPortForAppName(client, appName);
        if (client != null) {
            client.update(1);
        }
    }

    public static void sendHELO(Client client, int serverProtocolVersion) throws IOException {
        ByteBuffer rawBuf = HandleHello.allocBuffer(4);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHello.getChunkDataBuf(rawBuf);
        buf.putInt(serverProtocolVersion);
        HandleHello.finishChunkPacket(packet, CHUNK_HELO, buf.position());
        Log.d("ddm-hello", "Sending " + HandleHello.name(CHUNK_HELO) + " ID=0x" + Integer.toHexString(packet.getId()));
        client.send(packet, mInst);
    }

    private static void handleFEAT(Client client, ByteBuffer data) {
        int featureCount = data.getInt();
        for (int i2 = 0; i2 < featureCount; ++i2) {
            int len = data.getInt();
            String feature = ByteBufferUtil.getString(data, len);
            client.getClientData().addFeature(feature);
            Log.d("ddm-hello", "Feature: " + feature);
        }
    }

    public static void sendFEAT(Client client) throws IOException {
        ByteBuffer rawBuf = HandleHello.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHello.getChunkDataBuf(rawBuf);
        HandleHello.finishChunkPacket(packet, CHUNK_FEAT, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHello.name(CHUNK_FEAT));
        client.send(packet, mInst);
    }
}

