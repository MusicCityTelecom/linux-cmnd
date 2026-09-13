/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.jdwp.JdwpAgent;
import com.android.ddmlib.jdwp.JdwpExtension;
import com.android.ddmlib.jdwp.JdwpInterceptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DdmJdwpExtension
extends JdwpExtension {
    private final ConcurrentMap<Integer, ChunkHandler> mHandlerMap = new ConcurrentHashMap<Integer, ChunkHandler>();

    @Override
    public void intercept(Client client) {
        client.addJdwpInterceptor(new DdmInterceptor(client));
    }

    public void registerHandler(int type, ChunkHandler handler) {
        this.mHandlerMap.putIfAbsent(type, handler);
    }

    void broadcast(Event event, Client client) {
        Log.d("ddms", "broadcast " + (Object)((Object)event) + ": " + client);
        HashSet set = new HashSet(this.mHandlerMap.values());
        block6: for (ChunkHandler handler : set) {
            switch (event) {
                case CLIENT_READY: {
                    try {
                        handler.clientReady(client);
                        continue block6;
                    }
                    catch (IOException ioe) {
                        Log.w("ddms", "Got exception while broadcasting 'ready'");
                        return;
                    }
                }
                case CLIENT_DISCONNECTED: {
                    handler.clientDisconnected(client);
                    continue block6;
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    void ddmSeen(Client client) {
        if (!client.ddmSeen()) {
            this.broadcast(Event.CLIENT_READY, client);
        }
    }

    static boolean isDdmPacket(JdwpPacket packet) {
        return !packet.isReply() && packet.is(199, 1);
    }

    public class DdmInterceptor
    extends JdwpInterceptor {
        private final Client mClient;

        public DdmInterceptor(Client client) {
            this.mClient = client;
        }

        @Override
        public JdwpPacket intercept(JdwpAgent agent, JdwpPacket packet) {
            if (DdmJdwpExtension.isDdmPacket(packet)) {
                DdmJdwpExtension.this.ddmSeen(this.mClient);
                ByteBuffer buf = packet.getPayload();
                int type = buf.getInt(buf.position());
                ChunkHandler handler = (ChunkHandler)DdmJdwpExtension.this.mHandlerMap.get(type);
                if (handler == null) {
                    Log.w("ddms", "Received unsupported chunk type ChunkHandler.name(type)");
                } else {
                    handler.handlePacket(this.mClient, packet);
                }
                return null;
            }
            return packet;
        }
    }

    static enum Event {
        CLIENT_READY,
        CLIENT_DISCONNECTED;

    }
}

