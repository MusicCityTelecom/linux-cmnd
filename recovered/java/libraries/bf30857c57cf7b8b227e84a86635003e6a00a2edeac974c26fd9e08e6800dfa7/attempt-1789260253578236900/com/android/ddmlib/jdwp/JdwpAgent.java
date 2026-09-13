/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp;

import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.jdwp.JdwpInterceptor;
import com.android.ddmlib.jdwp.JdwpProtocol;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class JdwpAgent {
    private final ConcurrentMap<Integer, JdwpInterceptor> mReplyInterceptors = new ConcurrentHashMap<Integer, JdwpInterceptor>();
    private final List<JdwpInterceptor> mInterceptors = new LinkedList<JdwpInterceptor>();
    private final JdwpProtocol mProtocol;

    public JdwpAgent(JdwpProtocol protocol) {
        this.mProtocol = protocol;
    }

    protected void addReplyInterceptor(int id, JdwpInterceptor interceptor) {
        this.mReplyInterceptors.put(id, interceptor);
    }

    protected void removeReplyInterceptor(int id) {
        this.mReplyInterceptors.remove(id);
    }

    public void clear() {
        this.mReplyInterceptors.clear();
    }

    public void addJdwpInterceptor(JdwpInterceptor interceptor) {
        this.mInterceptors.add(interceptor);
    }

    public void removeJdwpInterceptor(JdwpInterceptor interceptor) {
        this.mInterceptors.remove(interceptor);
    }

    public void incoming(JdwpPacket packet, JdwpAgent target) throws IOException {
        JdwpInterceptor interceptor;
        this.mProtocol.incoming(packet, target);
        int id = packet.getId();
        if (packet.isReply() && (interceptor = (JdwpInterceptor)this.mReplyInterceptors.remove(id)) != null) {
            packet = interceptor.intercept(this, packet);
        }
        for (JdwpInterceptor interceptor2 : this.mInterceptors) {
            if (packet == null) break;
            packet = interceptor2.intercept(this, packet);
        }
        if (target != null && packet != null) {
            target.send(packet);
        }
    }

    public void send(JdwpPacket packet, JdwpInterceptor interceptor) throws IOException {
        this.mReplyInterceptors.put(packet.getId(), interceptor);
        this.send(packet);
    }

    protected abstract void send(JdwpPacket var1) throws IOException;

    public JdwpProtocol getJdwpProtocol() {
        return this.mProtocol;
    }
}

