/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import com.android.ddmlib.NativeAllocationInfo;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class HandleNativeHeap
extends ChunkHandler {
    public static final int CHUNK_NHGT = HandleNativeHeap.type("NHGT");
    public static final int CHUNK_NHSG = HandleNativeHeap.type("NHSG");
    public static final int CHUNK_NHST = HandleNativeHeap.type("NHST");
    public static final int CHUNK_NHEN = HandleNativeHeap.type("NHEN");
    private static final HandleNativeHeap mInst = new HandleNativeHeap();

    private HandleNativeHeap() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_NHGT, mInst);
        mt.registerChunkHandler(CHUNK_NHSG, mInst);
        mt.registerChunkHandler(CHUNK_NHST, mInst);
        mt.registerChunkHandler(CHUNK_NHEN, mInst);
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        Log.d("ddm-nativeheap", "handling " + ChunkHandler.name(type));
        if (type == CHUNK_NHGT) {
            this.handleNHGT(client, data);
        } else if (type == CHUNK_NHST) {
            client.getClientData().getNativeHeapData().clearHeapData();
        } else if (type == CHUNK_NHEN) {
            client.getClientData().getNativeHeapData().sealHeapData();
        } else if (type == CHUNK_NHSG) {
            this.handleNHSG(client, data);
        } else {
            this.handleUnknownChunk(client, type, data, isReply, msgId);
        }
        client.update(128);
    }

    public static void sendNHGT(Client client) throws IOException {
        ByteBuffer rawBuf = HandleNativeHeap.allocBuffer(0);
        JdwpPacket packet = new JdwpPacket(rawBuf);
        ByteBuffer buf = HandleNativeHeap.getChunkDataBuf(rawBuf);
        HandleNativeHeap.finishChunkPacket(packet, CHUNK_NHGT, buf.position());
        Log.d("ddm-nativeheap", "Sending " + HandleNativeHeap.name(CHUNK_NHGT));
        client.send(packet, mInst);
        rawBuf = HandleNativeHeap.allocBuffer(2);
        packet = new JdwpPacket(rawBuf);
        buf = HandleNativeHeap.getChunkDataBuf(rawBuf);
        buf.put((byte)0);
        buf.put((byte)1);
        HandleNativeHeap.finishChunkPacket(packet, CHUNK_NHSG, buf.position());
        Log.d("ddm-nativeheap", "Sending " + HandleNativeHeap.name(CHUNK_NHSG));
        client.send(packet, mInst);
    }

    private void handleNHGT(Client client, ByteBuffer data) {
        NativeBuffer buffer;
        ClientData clientData = client.getClientData();
        Log.d("ddm-nativeheap", "NHGT: " + data.limit() + " bytes");
        data.order(ByteOrder.LITTLE_ENDIAN);
        int signature = data.getInt(0);
        int pointerSize = 4;
        if (signature == -2128394787) {
            int ignore = data.getInt();
            short version = data.getShort();
            if (version != 2) {
                Log.e("ddms", "Unknown header version: " + version);
                return;
            }
            pointerSize = data.getShort();
        }
        if (pointerSize == 4) {
            buffer = new NativeBuffer32(data);
        } else if (pointerSize == 8) {
            buffer = new NativeBuffer64(data);
        } else {
            Log.e("ddms", "Unknown pointer size: " + pointerSize);
            return;
        }
        clientData.clearNativeAllocationInfo();
        int mapSize = buffer.getSizeT();
        int allocSize = buffer.getSizeT();
        int allocInfoSize = buffer.getSizeT();
        int totalMemory = buffer.getSizeT();
        int backtraceSize = buffer.getSizeT();
        Log.d("ddms", "mapSize: " + mapSize);
        Log.d("ddms", "allocSize: " + allocSize);
        Log.d("ddms", "allocInfoSize: " + allocInfoSize);
        Log.d("ddms", "totalMemory: " + totalMemory);
        clientData.setTotalNativeMemory(totalMemory);
        if (allocInfoSize == 0) {
            return;
        }
        if (mapSize > 0) {
            byte[] maps = new byte[mapSize];
            data.get(maps, 0, mapSize);
            this.parseMaps(clientData, maps);
        }
        int iterations = allocSize / allocInfoSize;
        for (int i2 = 0; i2 < iterations; ++i2) {
            NativeAllocationInfo info = new NativeAllocationInfo(buffer.getSizeT(), buffer.getSizeT());
            for (int j2 = 0; j2 < backtraceSize; ++j2) {
                long addr = buffer.getPtr();
                if (addr == 0L) continue;
                info.addStackCallAddress(addr);
            }
            clientData.addNativeAllocation(info);
        }
    }

    private void handleNHSG(Client client, ByteBuffer data) {
        byte[] dataCopy = new byte[data.limit()];
        data.rewind();
        data.get(dataCopy);
        data = ByteBuffer.wrap(dataCopy);
        client.getClientData().getNativeHeapData().addHeapData(data);
    }

    private void parseMaps(ClientData clientData, byte[] maps) {
        InputStreamReader input = new InputStreamReader(new ByteArrayInputStream(maps));
        BufferedReader reader = new BufferedReader(input);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String library;
                Log.d("ddms", "line: " + line);
                int library_start = line.lastIndexOf(32);
                if (library_start == -1 || !(library = line.substring(library_start + 1)).startsWith("/")) continue;
                int dashIndex = line.indexOf(45);
                int spaceIndex = line.indexOf(32, dashIndex);
                if (dashIndex == -1 || spaceIndex == -1) continue;
                long startAddr = 0L;
                long endAddr = 0L;
                try {
                    startAddr = Long.parseLong(line.substring(0, dashIndex), 16);
                    endAddr = Long.parseLong(line.substring(dashIndex + 1, spaceIndex), 16);
                }
                catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    continue;
                }
                clientData.addNativeLibraryMapInfo(startAddr, endAddr, library);
                Log.d("ddms", library + "(" + Long.toHexString(startAddr) + " - " + Long.toHexString(endAddr) + ")");
            }
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    final class NativeBuffer64
    extends NativeBuffer {
        public NativeBuffer64(ByteBuffer buffer) {
            super(buffer);
        }

        @Override
        public int getSizeT() {
            return (int)this.mBuffer.getLong();
        }

        @Override
        public long getPtr() {
            return this.mBuffer.getLong();
        }
    }

    final class NativeBuffer32
    extends NativeBuffer {
        public NativeBuffer32(ByteBuffer buffer) {
            super(buffer);
        }

        @Override
        public int getSizeT() {
            return this.mBuffer.getInt();
        }

        @Override
        public long getPtr() {
            return (long)this.mBuffer.getInt() & 0xFFFFFFFFL;
        }
    }

    abstract class NativeBuffer {
        protected ByteBuffer mBuffer;

        public NativeBuffer(ByteBuffer buffer) {
            this.mBuffer = buffer;
        }

        public abstract int getSizeT();

        public abstract long getPtr();
    }
}

