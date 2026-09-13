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
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

final class HandleHeap
extends ChunkHandler {
    public static final int CHUNK_HPIF = HandleHeap.type("HPIF");
    public static final int CHUNK_HPST = HandleHeap.type("HPST");
    public static final int CHUNK_HPEN = HandleHeap.type("HPEN");
    public static final int CHUNK_HPSG = HandleHeap.type("HPSG");
    public static final int CHUNK_HPGC = HandleHeap.type("HPGC");
    public static final int CHUNK_HPDU = HandleHeap.type("HPDU");
    public static final int CHUNK_HPDS = HandleHeap.type("HPDS");
    public static final int CHUNK_REAE = HandleHeap.type("REAE");
    public static final int CHUNK_REAQ = HandleHeap.type("REAQ");
    public static final int CHUNK_REAL = HandleHeap.type("REAL");
    public static final int WHEN_DISABLE = 0;
    public static final int WHEN_GC = 1;
    public static final int WHAT_MERGE = 0;
    public static final int WHAT_OBJ = 1;
    public static final int HPIF_WHEN_NEVER = 0;
    public static final int HPIF_WHEN_NOW = 1;
    public static final int HPIF_WHEN_NEXT_GC = 2;
    public static final int HPIF_WHEN_EVERY_GC = 3;
    private static final HandleHeap mInst = new HandleHeap();

    private HandleHeap() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_HPIF, mInst);
        mt.registerChunkHandler(CHUNK_HPST, mInst);
        mt.registerChunkHandler(CHUNK_HPEN, mInst);
        mt.registerChunkHandler(CHUNK_HPSG, mInst);
        mt.registerChunkHandler(CHUNK_HPDS, mInst);
        mt.registerChunkHandler(CHUNK_REAQ, mInst);
        mt.registerChunkHandler(CHUNK_REAL, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
        client.initializeHeapUpdateStatus();
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-heap", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_HPIF) {
            this.handleHPIF(client, data);
        } else if (type == CHUNK_HPST) {
            this.handleHPST(client, data);
        } else if (type == CHUNK_HPEN) {
            this.handleHPEN(client, data);
        } else if (type == CHUNK_HPSG) {
            this.handleHPSG(client, data);
        } else if (type == CHUNK_HPDU) {
            this.handleHPDU(client, data);
        } else if (type == CHUNK_HPDS) {
            this.handleHPDS(client, data);
        } else if (type == CHUNK_REAQ) {
            this.handleREAQ(client, data);
        } else if (type == CHUNK_REAL) {
            this.handleREAL(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
    }

    private void handleHPIF(Client client, ByteBuffer data) {
        Log.d("ddm-heap", "HPIF!");
        try {
            int numHeaps = data.getInt();
            for (int i2 = 0; i2 < numHeaps; ++i2) {
                int heapId = data.getInt();
                long timeStamp = data.getLong();
                byte reason = data.get();
                long maxHeapSize = (long)data.getInt() & 0xFFFFFFFFFFFFFFFFL;
                long heapSize = (long)data.getInt() & 0xFFFFFFFFFFFFFFFFL;
                long bytesAllocated = (long)data.getInt() & 0xFFFFFFFFFFFFFFFFL;
                long objectsAllocated = (long)data.getInt() & 0xFFFFFFFFFFFFFFFFL;
                client.getClientData().setHeapInfo(heapId, maxHeapSize, heapSize, bytesAllocated, objectsAllocated, timeStamp, reason);
                client.update(64);
            }
        }
        catch (BufferUnderflowException ex) {
            Log.w("ddm-heap", "malformed HPIF chunk from client");
        }
    }

    public static void sendHPIF(Client client, int when) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(1);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        buf.put((byte)when);
        HandleHeap.finishChunkPacket(packet, CHUNK_HPIF, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_HPIF) + ": when=" + when);
        client.send(packet, mInst);
    }

    private void handleHPST(Client client, ByteBuffer data) {
        client.getClientData().getVmHeapData().clearHeapData();
    }

    private void handleHPEN(Client client, ByteBuffer data) {
        client.getClientData().getVmHeapData().sealHeapData();
        client.update(64);
    }

    private void handleHPSG(Client client, ByteBuffer data) {
        byte[] dataCopy = new byte[data.limit()];
        data.rewind();
        data.get(dataCopy);
        data = ByteBuffer.wrap(dataCopy);
        client.getClientData().getVmHeapData().addHeapData(data);
    }

    public static void sendHPSG(Client client, int when, int what) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(2);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        buf.put((byte)when);
        buf.put((byte)what);
        HandleHeap.finishChunkPacket(packet, CHUNK_HPSG, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_HPSG) + ": when=" + when + ", what=" + what);
        client.send(packet, mInst);
    }

    public static void sendHPGC(Client client) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        HandleHeap.finishChunkPacket(packet, CHUNK_HPGC, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_HPGC));
        client.send(packet, mInst);
    }

    public static void sendHPDU(Client client, String fileName) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(4 + fileName.length() * 2);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        buf.putInt(fileName.length());
        ByteBufferUtil.putString(buf, fileName);
        HandleHeap.finishChunkPacket(packet, CHUNK_HPDU, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_HPDU) + " '" + fileName + "'");
        client.send(packet, mInst);
        client.getClientData().setPendingHprofDump(fileName);
    }

    public static void sendHPDS(Client client) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        HandleHeap.finishChunkPacket(packet, CHUNK_HPDS, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_HPDS));
        client.send(packet, mInst);
    }

    private void handleHPDU(Client client, ByteBuffer data) {
        String filename = client.getClientData().getPendingHprofDump();
        client.getClientData().setPendingHprofDump(null);
        byte result = data.get();
        ClientData.IHprofDumpHandler handler = ClientData.getHprofDumpHandler();
        if (result == 0) {
            if (handler != null) {
                handler.onSuccess(filename, client);
            }
            client.getClientData().setHprofData(filename);
            Log.d("ddm-heap", "Heap dump request has finished");
        } else {
            if (handler != null) {
                handler.onEndFailure(client, null);
            }
            client.getClientData().clearHprofData();
            Log.w("ddm-heap", "Heap dump request failed (check device log)");
        }
        client.update(4096);
        client.getClientData().clearHprofData();
    }

    private void handleHPDS(Client client, ByteBuffer data) {
        byte[] stuff = new byte[data.capacity()];
        data.get(stuff, 0, stuff.length);
        Log.d("ddm-hprof", "got hprof file, size: " + data.capacity() + " bytes");
        client.getClientData().setHprofData(stuff);
        ClientData.IHprofDumpHandler handler = ClientData.getHprofDumpHandler();
        if (handler != null) {
            handler.onSuccess(stuff, client);
        }
        client.update(4096);
        client.getClientData().clearHprofData();
    }

    public static void sendREAE(Client client, boolean enable) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(1);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        buf.put((byte)(enable ? 1 : 0));
        HandleHeap.finishChunkPacket(packet, CHUNK_REAE, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_REAE) + ": " + enable);
        client.send(packet, mInst);
    }

    public static void sendREAQ(Client client) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        HandleHeap.finishChunkPacket(packet, CHUNK_REAQ, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_REAQ));
        client.send(packet, mInst);
    }

    public static void sendREAL(Client client) throws IOException {
        ByteBuffer rawBuf = HandleHeap.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleHeap.getChunkDataBuf(rawBuf);
        HandleHeap.finishChunkPacket(packet, CHUNK_REAL, buf.position());
        Log.d("ddm-heap", "Sending " + HandleHeap.name(CHUNK_REAL));
        client.send(packet, mInst);
    }

    private void handleREAQ(Client client, ByteBuffer data) {
        boolean enabled = data.get() != 0;
        Log.d("ddm-heap", "REAQ says: enabled=" + enabled);
        client.getClientData().setAllocationStatus(enabled ? ClientData.AllocationTrackingStatus.ON : ClientData.AllocationTrackingStatus.OFF);
        client.update(1024);
    }

    private void handleREAL(Client client, ByteBuffer data) {
        Log.e("ddm-heap", "*** Received " + HandleHeap.name(CHUNK_REAL));
        byte[] stuff = new byte[data.capacity()];
        data.get(stuff, 0, stuff.length);
        data.rewind();
        ClientData.IAllocationTrackingHandler handler = ClientData.getAllocationTrackingHandler();
        if (handler != null) {
            Log.d("ddm-prof", "got allocations file, size: " + stuff.length + " bytes");
            handler.onSuccess(stuff, client);
        }
        client.getClientData().setAllocationsData(stuff);
        client.update(512);
        client.getClientData().setAllocationsData(null);
    }
}

