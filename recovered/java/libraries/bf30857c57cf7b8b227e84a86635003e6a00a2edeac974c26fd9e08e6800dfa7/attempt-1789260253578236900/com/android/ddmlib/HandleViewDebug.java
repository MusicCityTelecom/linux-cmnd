/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ByteBufferUtil;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class HandleViewDebug
extends ChunkHandler {
    public static final int CHUNK_VUGL = HandleViewDebug.type("VUGL");
    public static final int CHUNK_VULW = HandleViewDebug.type("VULW");
    public static final int CHUNK_VURT = HandleViewDebug.type("VURT");
    private static final int VURT_DUMP_HIERARCHY = 1;
    private static final int VURT_CAPTURE_LAYERS = 2;
    private static final int VURT_DUMP_THEME = 3;
    public static final int CHUNK_VUOP = HandleViewDebug.type("VUOP");
    private static final int VUOP_CAPTURE_VIEW = 1;
    private static final int VUOP_DUMP_DISPLAYLIST = 2;
    private static final int VUOP_PROFILE_VIEW = 3;
    private static final int VUOP_INVOKE_VIEW_METHOD = 4;
    private static final int VUOP_SET_LAYOUT_PARAMETER = 5;
    private static final String TAG = "ddmlib";
    private static final HandleViewDebug sInstance = new HandleViewDebug();
    private static final ViewDumpHandler sViewOpNullChunkHandler = new NullChunkHandler(CHUNK_VUOP);

    private HandleViewDebug() {
    }

    public static void register(MonitorThread mt) {
        mt.registerChunkHandler(CHUNK_VUGL, sInstance);
        mt.registerChunkHandler(CHUNK_VULW, sInstance);
        mt.registerChunkHandler(CHUNK_VUOP, sInstance);
        mt.registerChunkHandler(CHUNK_VURT, sInstance);
    }

    @Override
    public void clientReady(Client client) throws IOException {
    }

    @Override
    public void clientDisconnected(Client client) {
    }

    public static void listViewRoots(Client client, ViewDumpHandler replyHandler) throws IOException {
        ByteBuffer buf = HandleViewDebug.allocBuffer(8);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(1);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VULW, chunkBuf.position());
        client.send(packet, replyHandler);
    }

    public static void dumpViewHierarchy(Client client, String viewRoot, boolean skipChildren, boolean includeProperties, boolean useV2, ViewDumpHandler handler) throws IOException {
        ByteBuffer buf = HandleViewDebug.allocBuffer(8 + viewRoot.length() * 2 + 4 + 4 + 4);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(1);
        chunkBuf.putInt(viewRoot.length());
        ByteBufferUtil.putString(chunkBuf, viewRoot);
        chunkBuf.putInt(skipChildren ? 1 : 0);
        chunkBuf.putInt(includeProperties ? 1 : 0);
        chunkBuf.putInt(useV2 ? 1 : 0);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VURT, chunkBuf.position());
        client.send(packet, handler);
    }

    public static void captureLayers(Client client, String viewRoot, ViewDumpHandler handler) throws IOException {
        int bufLen = 8 + viewRoot.length() * 2;
        ByteBuffer buf = HandleViewDebug.allocBuffer(bufLen);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(2);
        chunkBuf.putInt(viewRoot.length());
        ByteBufferUtil.putString(chunkBuf, viewRoot);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VURT, chunkBuf.position());
        client.send(packet, handler);
    }

    private static void sendViewOpPacket(Client client, int op, String viewRoot, String view, byte[] extra, ViewDumpHandler handler) throws IOException {
        int bufLen = 8 + viewRoot.length() * 2 + 4 + view.length() * 2;
        if (extra != null) {
            bufLen += extra.length;
        }
        ByteBuffer buf = HandleViewDebug.allocBuffer(bufLen);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(op);
        chunkBuf.putInt(viewRoot.length());
        ByteBufferUtil.putString(chunkBuf, viewRoot);
        chunkBuf.putInt(view.length());
        ByteBufferUtil.putString(chunkBuf, view);
        if (extra != null) {
            chunkBuf.put(extra);
        }
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VUOP, chunkBuf.position());
        client.send(packet, handler);
    }

    public static void profileView(Client client, String viewRoot, String view, ViewDumpHandler handler) throws IOException {
        HandleViewDebug.sendViewOpPacket(client, 3, viewRoot, view, null, handler);
    }

    public static void captureView(Client client, String viewRoot, String view, ViewDumpHandler handler) throws IOException {
        HandleViewDebug.sendViewOpPacket(client, 1, viewRoot, view, null, handler);
    }

    public static void invalidateView(Client client, String viewRoot, String view) throws IOException {
        HandleViewDebug.invokeMethod(client, viewRoot, view, "invalidate", new Object[0]);
    }

    public static void requestLayout(Client client, String viewRoot, String view) throws IOException {
        HandleViewDebug.invokeMethod(client, viewRoot, view, "requestLayout", new Object[0]);
    }

    public static void dumpDisplayList(Client client, String viewRoot, String view) throws IOException {
        HandleViewDebug.sendViewOpPacket(client, 2, viewRoot, view, null, sViewOpNullChunkHandler);
    }

    public static void dumpTheme(Client client, String viewRoot, ViewDumpHandler handler) throws IOException {
        ByteBuffer buf = HandleViewDebug.allocBuffer(8 + viewRoot.length() * 2);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(3);
        chunkBuf.putInt(viewRoot.length());
        ByteBufferUtil.putString(chunkBuf, viewRoot);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VURT, chunkBuf.position());
        client.send(packet, handler);
    }

    public static void invokeMethod(Client client, String viewRoot, String view, String method, Object ... args) throws IOException {
        int len = 4 + method.length() * 2;
        if (args != null) {
            len += 4;
            len += 10 * args.length;
        }
        byte[] extra = new byte[len];
        ByteBuffer b2 = ByteBuffer.wrap(extra);
        b2.putInt(method.length());
        ByteBufferUtil.putString(b2, method);
        if (args != null) {
            b2.putInt(args.length);
            for (int i2 = 0; i2 < args.length; ++i2) {
                Object arg = args[i2];
                if (arg instanceof Boolean) {
                    b2.putChar('Z');
                    b2.put((byte)((Boolean)arg != false ? 1 : 0));
                    continue;
                }
                if (arg instanceof Byte) {
                    b2.putChar('B');
                    b2.put((Byte)arg);
                    continue;
                }
                if (arg instanceof Character) {
                    b2.putChar('C');
                    b2.putChar(((Character)arg).charValue());
                    continue;
                }
                if (arg instanceof Short) {
                    b2.putChar('S');
                    b2.putShort((Short)arg);
                    continue;
                }
                if (arg instanceof Integer) {
                    b2.putChar('I');
                    b2.putInt((Integer)arg);
                    continue;
                }
                if (arg instanceof Long) {
                    b2.putChar('J');
                    b2.putLong((Long)arg);
                    continue;
                }
                if (arg instanceof Float) {
                    b2.putChar('F');
                    b2.putFloat(((Float)arg).floatValue());
                    continue;
                }
                if (arg instanceof Double) {
                    b2.putChar('D');
                    b2.putDouble((Double)arg);
                    continue;
                }
                Log.e(TAG, "View method invocation only supports primitive arguments, supplied: " + arg);
                return;
            }
        }
        HandleViewDebug.sendViewOpPacket(client, 4, viewRoot, view, extra, sViewOpNullChunkHandler);
    }

    public static void setLayoutParameter(Client client, String viewRoot, String view, String parameter, int value) throws IOException {
        int len = 4 + parameter.length() * 2 + 4;
        byte[] extra = new byte[len];
        ByteBuffer b2 = ByteBuffer.wrap(extra);
        b2.putInt(parameter.length());
        ByteBufferUtil.putString(b2, parameter);
        b2.putInt(value);
        HandleViewDebug.sendViewOpPacket(client, 5, viewRoot, view, extra, sViewOpNullChunkHandler);
    }

    @Override
    public void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
    }

    public static void sendStartGlTracing(Client client) throws IOException {
        ByteBuffer buf = HandleViewDebug.allocBuffer(4);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(1);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VUGL, chunkBuf.position());
        client.send(packet, null);
    }

    public static void sendStopGlTracing(Client client) throws IOException {
        ByteBuffer buf = HandleViewDebug.allocBuffer(4);
        JdwpPacket packet = new JdwpPacket(buf);
        ByteBuffer chunkBuf = HandleViewDebug.getChunkDataBuf(buf);
        chunkBuf.putInt(0);
        HandleViewDebug.finishChunkPacket(packet, CHUNK_VUGL, chunkBuf.position());
        client.send(packet, null);
    }

    private static class NullChunkHandler
    extends ViewDumpHandler {
        public NullChunkHandler(int chunkType) {
            super(chunkType);
        }

        @Override
        protected void handleViewDebugResult(ByteBuffer data) {
        }
    }

    public static abstract class ViewDumpHandler
    extends ChunkHandler {
        private final CountDownLatch mLatch = new CountDownLatch(1);
        private final int mChunkType;

        public ViewDumpHandler(int chunkType) {
            this.mChunkType = chunkType;
        }

        @Override
        void clientReady(Client client) throws IOException {
        }

        @Override
        void clientDisconnected(Client client) {
        }

        @Override
        void handleChunk(Client client, int type, ByteBuffer data, boolean isReply, int msgId) {
            if (type != this.mChunkType) {
                this.handleUnknownChunk(client, type, data, isReply, msgId);
                return;
            }
            this.handleViewDebugResult(data);
            this.mLatch.countDown();
        }

        protected abstract void handleViewDebugResult(ByteBuffer var1);

        protected void waitForResult(long timeout, TimeUnit unit) {
            try {
                this.mLatch.await(timeout, unit);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }
}

