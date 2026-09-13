/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.ByteBufferUtil;
import com.android.ddmlib.Client;
import com.android.ddmlib.DebugPortManager;
import com.android.ddmlib.Device;
import com.android.ddmlib.DeviceMonitor;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import com.android.ddmlib.jdwp.JdwpAgent;
import com.android.ddmlib.jdwp.JdwpInterceptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ChunkHandler
extends JdwpInterceptor {
    public static final int CHUNK_HEADER_LEN = 8;
    public static final ByteOrder CHUNK_ORDER = ByteOrder.BIG_ENDIAN;
    public static final int CHUNK_FAIL = ChunkHandler.type("FAIL");
    public static final int DDMS_CMD_SET = 199;
    public static final int DDMS_CMD = 1;

    ChunkHandler() {
    }

    abstract void clientReady(Client var1) throws IOException;

    abstract void clientDisconnected(Client var1);

    abstract void handleChunk(Client var1, int var2, ByteBuffer var3, boolean var4, int var5);

    protected void handleUnknownChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
        if (type == CHUNK_FAIL) {
            int errorCode = data.getInt();
            int msgLen = data.getInt();
            String msg = ByteBufferUtil.getString(data, msgLen);
            Log.w("ddms", "WARNING: failure code=" + errorCode + " msg=" + msg);
        } else {
            Log.w("ddms", "WARNING: received unknown chunk " + ChunkHandler.name(type) + ": len=" + data.limit() + ", reply=" + isReply + ", msgId=0x" + Integer.toHexString(msgId));
        }
        Log.w("ddms", "         client " + client + ", handler " + this);
    }

    public static String getString(ByteBuffer buf, int len) {
        return ByteBufferUtil.getString(buf, len);
    }

    static int type(String typeName) {
        int val = 0;
        if (typeName.length() != 4) {
            Log.e("ddms", "Type name must be 4 letter long");
            throw new RuntimeException("Type name must be 4 letter long");
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            val <<= 8;
            val |= (byte)typeName.charAt(i2);
        }
        return val;
    }

    static String name(int type) {
        char[] ascii = new char[]{(char)(type >> 24 & 0xFF), (char)(type >> 16 & 0xFF), (char)(type >> 8 & 0xFF), (char)(type & 0xFF)};
        return new String(ascii);
    }

    static ByteBuffer allocBuffer(int maxChunkLen) {
        ByteBuffer buf = ByteBuffer.allocate(19 + maxChunkLen);
        buf.order(CHUNK_ORDER);
        return buf;
    }

    static ByteBuffer getChunkDataBuf(ByteBuffer jdwpBuf) {
        assert (jdwpBuf.position() == 0);
        jdwpBuf.position(19);
        ByteBuffer slice = jdwpBuf.slice();
        slice.order(CHUNK_ORDER);
        jdwpBuf.position(0);
        return slice;
    }

    static void finishChunkPacket(JdwpPacket packet, int type, int chunkLen) {
        ByteBuffer buf = packet.getPayload();
        buf.putInt(0, type);
        buf.putInt(4, chunkLen);
        packet.finishPacket(199, 1, 8 + chunkLen);
    }

    protected static Client checkDebuggerPortForAppName(Client client, String appName) {
        DeviceMonitor deviceMonitor;
        AndroidDebugBridge bridge;
        Device device;
        int newPort;
        DebugPortManager.IDebugPortProvider provider = DebugPortManager.getProvider();
        if (provider != null && (newPort = provider.getPort(device = client.getDeviceImpl(), appName)) != -1 && newPort != client.getDebuggerListenPort() && (bridge = AndroidDebugBridge.getBridge()) != null && (deviceMonitor = bridge.getDeviceMonitor()) != null) {
            deviceMonitor.trackClientToDropAndReopen(client, newPort);
            client = null;
        }
        return client;
    }

    void handlePacket(Client client, JdwpPacket packet) {
        ByteBuffer buf = packet.getPayload();
        int type = buf.getInt();
        int length = buf.getInt();
        Log.d("ddms", "Calling handler for " + ChunkHandler.name(type) + " [" + this + "] (len=" + length + ")");
        ByteBuffer ibuf = buf.slice();
        ByteBuffer roBuf = ibuf.asReadOnlyBuffer();
        roBuf.order(CHUNK_ORDER);
        this.handleChunk(client, type, roBuf, packet.isReply(), packet.getId());
    }

    @Override
    public JdwpPacket intercept(JdwpAgent agent, JdwpPacket packet) {
        if (agent instanceof Client) {
            Client client = (Client)agent;
            MonitorThread.getInstance().getDdmExtension().ddmSeen(client);
            if (packet.isError()) {
                client.packetFailed(packet);
            } else if (packet.isEmpty()) {
                Log.d("ddms", "Got empty reply for 0x" + Integer.toHexString(packet.getId()));
            } else {
                this.handlePacket(client, packet);
            }
            return null;
        }
        return packet;
    }
}

