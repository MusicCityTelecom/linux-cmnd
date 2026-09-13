/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ByteBufferUtil;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

final class HandleProfiling
extends ChunkHandler {
    public static final int CHUNK_MPRS = HandleProfiling.type("MPRS");
    public static final int CHUNK_MPRE = HandleProfiling.type("MPRE");
    public static final int CHUNK_MPSS = HandleProfiling.type("MPSS");
    public static final int CHUNK_MPSE = HandleProfiling.type("MPSE");
    public static final int CHUNK_SPSS = HandleProfiling.type("SPSS");
    public static final int CHUNK_SPSE = HandleProfiling.type("SPSE");
    public static final int CHUNK_MPRQ = HandleProfiling.type("MPRQ");
    public static final int CHUNK_FAIL = HandleProfiling.type("FAIL");
    private static final HandleProfiling mInst = new HandleProfiling();

    private HandleProfiling() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_MPRE, mInst);
        mt.registerChunkHandler(CHUNK_MPSE, mInst);
        mt.registerChunkHandler(CHUNK_MPRQ, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-prof", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_MPRE) {
            this.handleMPRE(client, data);
        } else if (type == CHUNK_MPSE) {
            this.handleMPSE(client, data);
        } else if (type == CHUNK_MPRQ) {
            this.handleMPRQ(client, data);
        } else if (type == CHUNK_FAIL) {
            this.handleFAIL(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    public static void sendMPRS(Client client, String fileName, int bufferSize, int flags) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(12 + fileName.length() * 2);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        buf.putInt(bufferSize);
        buf.putInt(flags);
        buf.putInt(fileName.length());
        ByteBufferUtil.putString(buf, fileName);
        HandleProfiling.finishChunkPacket(packet, CHUNK_MPRS, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_MPRS) + " '" + fileName + "', size=" + bufferSize + ", flags=" + flags);
        client.send(packet, mInst);
        client.getClientData().setPendingMethodProfiling(fileName);
        HandleProfiling.sendMPRQ(client);
    }

    public static void sendMPRE(Client client) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        HandleProfiling.finishChunkPacket(packet, CHUNK_MPRE, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_MPRE));
        client.send(packet, mInst);
    }

    private void handleMPRE(Client client, ByteBuffer data) {
        String filename = client.getClientData().getPendingMethodProfiling();
        client.getClientData().setPendingMethodProfiling(null);
        byte result = data.get();
        ClientData.IMethodProfilingHandler handler = ClientData.getMethodProfilingHandler();
        if (handler != null) {
            if (result == 0) {
                handler.onSuccess(filename, client);
                Log.d("ddm-prof", "Method profiling has finished");
            } else {
                handler.onEndFailure(client, null);
                Log.w("ddm-prof", "Method profiling has failed (check device log)");
            }
        }
        client.getClientData().setMethodProfilingStatus(ClientData.MethodProfilingStatus.OFF);
        client.update(2048);
    }

    public static void sendMPSS(Client client, int bufferSize, int flags) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(8);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        buf.putInt(bufferSize);
        buf.putInt(flags);
        HandleProfiling.finishChunkPacket(packet, CHUNK_MPSS, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_MPSS) + "', size=" + bufferSize + ", flags=" + flags);
        client.send(packet, mInst);
        HandleProfiling.sendMPRQ(client);
    }

    public static void sendSPSS(Client client, int bufferSize, int samplingInterval, TimeUnit samplingIntervalTimeUnits) throws IOException {
        int interval = (int)samplingIntervalTimeUnits.toMicros(samplingInterval);
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(12);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        buf.putInt(bufferSize);
        buf.putInt(0);
        buf.putInt(interval);
        HandleProfiling.finishChunkPacket(packet, CHUNK_SPSS, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_SPSS) + "', size=" + bufferSize + ", flags=0, samplingInterval=" + interval);
        client.send(packet, mInst);
        HandleProfiling.sendMPRQ(client);
    }

    public static void sendMPSE(Client client) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        HandleProfiling.finishChunkPacket(packet, CHUNK_MPSE, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_MPSE));
        client.send(packet, mInst);
    }

    public static void sendSPSE(Client client) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        HandleProfiling.finishChunkPacket(packet, CHUNK_SPSE, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_SPSE));
        client.send(packet, mInst);
    }

    private void handleMPSE(Client client, ByteBuffer data) {
        ClientData.IMethodProfilingHandler handler = ClientData.getMethodProfilingHandler();
        if (handler != null) {
            byte[] stuff = new byte[data.capacity()];
            data.get(stuff, 0, stuff.length);
            Log.d("ddm-prof", "got trace file, size: " + stuff.length + " bytes");
            handler.onSuccess(stuff, client);
        }
        client.getClientData().setMethodProfilingStatus(ClientData.MethodProfilingStatus.OFF);
        client.update(2048);
    }

    public static void sendMPRQ(Client client) throws IOException {
        ByteBuffer rawBuf = HandleProfiling.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleProfiling.getChunkDataBuf(rawBuf);
        HandleProfiling.finishChunkPacket(packet, CHUNK_MPRQ, buf.position());
        Log.d("ddm-prof", "Sending " + HandleProfiling.name(CHUNK_MPRQ));
        client.send(packet, mInst);
    }

    private void handleMPRQ(Client client, ByteBuffer data) {
        byte result = data.get();
        if (result == 0) {
            client.getClientData().setMethodProfilingStatus(ClientData.MethodProfilingStatus.OFF);
            Log.d("ddm-prof", "Method profiling is not running");
        } else if (result == 1) {
            client.getClientData().setMethodProfilingStatus(ClientData.MethodProfilingStatus.TRACER_ON);
            Log.d("ddm-prof", "Method tracing is active");
        } else if (result == 2) {
            client.getClientData().setMethodProfilingStatus(ClientData.MethodProfilingStatus.SAMPLER_ON);
            Log.d("ddm-prof", "Sampler based profiling is active");
        }
        client.update(2048);
    }

    private void handleFAIL(Client client, ByteBuffer data) {
        ClientData.IMethodProfilingHandler handler;
        String filename;
        data.getInt();
        int length = data.getInt() * 2;
        String message = null;
        if (length > 0) {
            byte[] messageBuffer = new byte[length];
            data.get(messageBuffer, 0, length);
            message = new String(messageBuffer);
        }
        if ((filename = client.getClientData().getPendingMethodProfiling()) != null) {
            client.getClientData().setPendingMethodProfiling(null);
            handler = ClientData.getMethodProfilingHandler();
            if (handler != null) {
                handler.onStartFailure(client, message);
            }
        } else {
            handler = ClientData.getMethodProfilingHandler();
            if (handler != null) {
                handler.onEndFailure(client, message);
            }
        }
        try {
            HandleProfiling.sendMPRQ(client);
        }
        catch (IOException e2) {
            Log.e("HandleProfiling", e2);
        }
    }
}

