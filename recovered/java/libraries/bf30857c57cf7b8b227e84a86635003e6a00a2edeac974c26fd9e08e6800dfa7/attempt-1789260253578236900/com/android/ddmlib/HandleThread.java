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
import com.android.ddmlib.ThreadInfo;
import java.io.IOException;
import java.nio.ByteBuffer;

final class HandleThread
extends ChunkHandler {
    public static final int CHUNK_THEN = HandleThread.type("THEN");
    public static final int CHUNK_THCR = HandleThread.type("THCR");
    public static final int CHUNK_THDE = HandleThread.type("THDE");
    public static final int CHUNK_THST = HandleThread.type("THST");
    public static final int CHUNK_THNM = HandleThread.type("THNM");
    public static final int CHUNK_STKL = HandleThread.type("STKL");
    private static final HandleThread mInst = new HandleThread();
    private static volatile boolean sThreadStatusReqRunning = false;
    private static volatile boolean sThreadStackTraceReqRunning = false;

    private HandleThread() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_THCR, mInst);
        mt.registerChunkHandler(CHUNK_THDE, mInst);
        mt.registerChunkHandler(CHUNK_THST, mInst);
        mt.registerChunkHandler(CHUNK_THNM, mInst);
        mt.registerChunkHandler(CHUNK_STKL, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
        Log.d("ddm-thread", "Now ready: " + client);
        if (client.isThreadUpdateEnabled()) {
            HandleThread.sendTHEN(client, true);
        }
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-thread", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_THCR) {
            this.handleTHCR(client, data);
        } else if (type == CHUNK_THDE) {
            this.handleTHDE(client, data);
        } else if (type == CHUNK_THST) {
            this.handleTHST(client, data);
        } else if (type == CHUNK_THNM) {
            this.handleTHNM(client, data);
        } else if (type == CHUNK_STKL) {
            this.handleSTKL(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    private void handleTHCR(Client client, ByteBuffer data) {
        int threadId = data.getInt();
        int nameLen = data.getInt();
        String name = ByteBufferUtil.getString(data, nameLen);
        Log.v("ddm-thread", "THCR: " + threadId + " '" + name + "'");
        client.getClientData().addThread(threadId, name);
        client.update(16);
    }

    private void handleTHDE(Client client, ByteBuffer data) {
        int threadId = data.getInt();
        Log.v("ddm-thread", "THDE: " + threadId);
        client.getClientData().removeThread(threadId);
        client.update(16);
    }

    private void handleTHST(Client client, ByteBuffer data) {
        int headerLen = data.get() & 0xFF;
        int bytesPerEntry = data.get() & 0xFF;
        int threadCount = data.getShort();
        headerLen -= 4;
        while (headerLen-- > 0) {
            data.get();
        }
        int extraPerEntry = bytesPerEntry - 18;
        Log.v("ddm-thread", "THST: threadCount=" + threadCount);
        for (int i2 = 0; i2 < threadCount; ++i2) {
            boolean isDaemon = false;
            int threadId = data.getInt();
            byte status = data.get();
            int tid = data.getInt();
            int utime = data.getInt();
            int stime = data.getInt();
            if (bytesPerEntry >= 18) {
                isDaemon = data.get() != 0;
            }
            Log.v("ddm-thread", "  id=" + threadId + ", status=" + status + ", tid=" + tid + ", utime=" + utime + ", stime=" + stime);
            ClientData cd2 = client.getClientData();
            ThreadInfo threadInfo = cd2.getThread(threadId);
            if (threadInfo != null) {
                threadInfo.updateThread(status, tid, utime, stime, isDaemon);
            } else {
                Log.d("ddms", "Thread with id=" + threadId + " not found");
            }
            for (int slurp = extraPerEntry; slurp > 0; --slurp) {
                data.get();
            }
        }
        client.update(16);
    }

    private void handleTHNM(Client client, ByteBuffer data) {
        int threadId = data.getInt();
        int nameLen = data.getInt();
        String name = ByteBufferUtil.getString(data, nameLen);
        Log.v("ddm-thread", "THNM: " + threadId + " '" + name + "'");
        ThreadInfo threadInfo = client.getClientData().getThread(threadId);
        if (threadInfo != null) {
            threadInfo.setThreadName(name);
            client.update(16);
        } else {
            Log.d("ddms", "Thread with id=" + threadId + " not found");
        }
    }

    private void handleSTKL(Client client, ByteBuffer data) {
        int future = data.getInt();
        int threadId = data.getInt();
        Log.v("ddms", "STKL: " + threadId);
        int stackDepth = data.getInt();
        StackTraceElement[] trace = new StackTraceElement[stackDepth];
        for (int i2 = 0; i2 < stackDepth; ++i2) {
            int len = data.getInt();
            String className = ByteBufferUtil.getString(data, len);
            len = data.getInt();
            String methodName = ByteBufferUtil.getString(data, len);
            len = data.getInt();
            String fileName = len == 0 ? null : ByteBufferUtil.getString(data, len);
            int lineNumber = data.getInt();
            trace[i2] = new StackTraceElement(className, methodName, fileName, lineNumber);
        }
        ThreadInfo threadInfo = client.getClientData().getThread(threadId);
        if (threadInfo != null) {
            threadInfo.setStackCall(trace);
            client.update(256);
        } else {
            Log.d("STKL", String.format("Got stackcall for thread %1$d, which does not exists (anymore?).", threadId));
        }
    }

    public static void sendTHEN(Client client, boolean enable) throws IOException {
        ByteBuffer rawBuf = HandleThread.allocBuffer(1);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleThread.getChunkDataBuf(rawBuf);
        if (enable) {
            buf.put((byte)1);
        } else {
            buf.put((byte)0);
        }
        HandleThread.finishChunkPacket(packet, CHUNK_THEN, buf.position());
        Log.d("ddm-thread", "Sending " + HandleThread.name(CHUNK_THEN) + ": " + enable);
        client.send(packet, mInst);
    }

    public static void sendSTKL(Client client, int threadId) throws IOException {
        ByteBuffer rawBuf = HandleThread.allocBuffer(4);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleThread.getChunkDataBuf(rawBuf);
        buf.putInt(threadId);
        HandleThread.finishChunkPacket(packet, CHUNK_STKL, buf.position());
        Log.d("ddm-thread", "Sending " + HandleThread.name(CHUNK_STKL) + ": " + threadId);
        client.send(packet, mInst);
    }

    static void requestThreadUpdate(final Client client) {
        if (client.isDdmAware() && client.isThreadUpdateEnabled()) {
            if (sThreadStatusReqRunning) {
                Log.w("ddms", "Waiting for previous thread update req to finish");
                return;
            }
            new Thread("Thread Status Req"){

                @Override
                public void run() {
                    sThreadStatusReqRunning = true;
                    try {
                        HandleThread.sendTHST(client);
                    }
                    catch (IOException ioe) {
                        Log.d("ddms", "Unable to request thread updates from " + client + ": " + ioe.getMessage());
                    }
                    finally {
                        sThreadStatusReqRunning = false;
                    }
                }
            }.start();
        }
    }

    static void requestThreadStackCallRefresh(final Client client, final int threadId) {
        if (client.isDdmAware() && client.isThreadUpdateEnabled()) {
            if (sThreadStackTraceReqRunning) {
                Log.w("ddms", "Waiting for previous thread stack call req to finish");
                return;
            }
            new Thread("Thread Status Req"){

                @Override
                public void run() {
                    sThreadStackTraceReqRunning = true;
                    try {
                        HandleThread.sendSTKL(client, threadId);
                    }
                    catch (IOException ioe) {
                        Log.d("ddms", "Unable to request thread stack call updates from " + client + ": " + ioe.getMessage());
                    }
                    finally {
                        sThreadStackTraceReqRunning = false;
                    }
                }
            }.start();
        }
    }

    private static void sendTHST(Client client) throws IOException {
        ByteBuffer rawBuf = HandleThread.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleThread.getChunkDataBuf(rawBuf);
        HandleThread.finishChunkPacket(packet, CHUNK_THST, buf.position());
        Log.d("ddm-thread", "Sending " + HandleThread.name(CHUNK_THST));
        client.send(packet, mInst);
    }
}

