/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.JdwpHandshake;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.jdwp.JdwpAgent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Debugger
extends JdwpAgent {
    private static final int INITIAL_BUF_SIZE = 1024;
    private static final int MAX_BUF_SIZE = 0x1000000;
    private ByteBuffer mReadBuffer;
    private static final int PRE_DATA_BUF_SIZE = 256;
    private ByteBuffer mPreDataBuffer;
    private ConnectionState mConnState;
    private final Client mClient;
    private int mListenPort;
    private ServerSocketChannel mListenChannel;
    private SocketChannel mChannel;

    Debugger(Client client, int listenPort) throws IOException {
        super(client.getJdwpProtocol());
        this.mClient = client;
        this.mListenPort = listenPort;
        this.mListenChannel = ServerSocketChannel.open();
        this.mListenChannel.configureBlocking(false);
        InetSocketAddress addr = new InetSocketAddress(InetAddress.getByName("localhost"), listenPort);
        this.mListenChannel.socket().setReuseAddress(true);
        this.mListenChannel.socket().bind(addr);
        this.mListenPort = this.mListenChannel.socket().getLocalPort();
        this.mReadBuffer = ByteBuffer.allocate(1024);
        this.mPreDataBuffer = ByteBuffer.allocate(256);
        this.mConnState = ConnectionState.ST_NOT_CONNECTED;
        Log.d("ddms", "Created: " + this.toString());
    }

    int getListenPort() {
        return this.mListenPort;
    }

    int getReadBufferCapacity() {
        return this.mReadBuffer.capacity();
    }

    int getReadBufferInitialCapacity() {
        return 1024;
    }

    int getReadBufferMaximumCapacity() {
        return 0x1000000;
    }

    ConnectionState getConnectionState() {
        return this.mConnState;
    }

    boolean isDebuggerAttached() {
        return this.mChannel != null;
    }

    public String toString() {
        return "[Debugger " + this.mListenPort + "-->" + this.mClient.getClientData().getPid() + (this.mConnState != ConnectionState.ST_READY ? " inactive]" : " active]");
    }

    void registerListener(Selector sel) throws IOException {
        this.mListenChannel.register(sel, 16, this);
    }

    Client getClient() {
        return this.mClient;
    }

    synchronized SocketChannel accept() throws IOException {
        return this.accept(this.mListenChannel);
    }

    synchronized SocketChannel accept(ServerSocketChannel listenChan) throws IOException {
        if (listenChan != null) {
            SocketChannel newChan = listenChan.accept();
            if (this.mChannel != null) {
                Log.w("ddms", "debugger already talking to " + this.mClient + " on " + this.mListenPort);
                newChan.close();
                return null;
            }
            this.mChannel = newChan;
            this.mChannel.configureBlocking(false);
            this.mConnState = ConnectionState.ST_AWAIT_SHAKE;
            return this.mChannel;
        }
        return null;
    }

    synchronized void closeData() {
        try {
            if (this.mChannel != null) {
                this.mChannel.close();
                this.mChannel = null;
                this.mConnState = ConnectionState.ST_NOT_CONNECTED;
                ClientData cd2 = this.mClient.getClientData();
                cd2.setDebuggerConnectionStatus(ClientData.DebuggerStatus.DEFAULT);
                this.mClient.update(2);
            }
        }
        catch (IOException ioe) {
            Log.w("ddms", "Failed to close data " + this);
        }
    }

    synchronized void close() {
        try {
            if (this.mListenChannel != null) {
                this.mListenChannel.close();
            }
            this.mListenChannel = null;
            this.closeData();
        }
        catch (IOException ioe) {
            Log.w("ddms", "Failed to close listener " + this);
        }
    }

    void processChannelData() {
        try {
            this.read();
            JdwpPacket packet = this.getJdwpPacket();
            while (packet != null) {
                Log.v("ddms", "Forwarding dbg req 0x" + Integer.toHexString(packet.getId()) + " to " + this.getClient());
                packet.log("Debugger: forwarding jdwp packet from Java Debugger to Client");
                this.incoming(packet, this.getClient());
                packet.consume();
                packet = this.getJdwpPacket();
            }
        }
        catch (IOException | BufferOverflowException e2) {
            Log.d("ddms", "Closing connection to debugger " + this + " (recycling client connection as well)");
            this.closeData();
            Client client = this.getClient();
            client.getDeviceImpl().getClientTracker().trackClientToDropAndReopen(client, -1);
        }
    }

    void read() throws IOException {
        if (this.mReadBuffer.position() == 0 && this.mReadBuffer.capacity() > 1024) {
            Log.i("ddms", String.format("Shrinking buffer from %d bytes to %d bytes", this.mReadBuffer.capacity(), 1024));
            this.mReadBuffer = ByteBuffer.allocate(1024);
        }
        if (this.mReadBuffer.position() == this.mReadBuffer.capacity()) {
            int newCapacity = this.mReadBuffer.capacity() * 2;
            if (newCapacity > 0x1000000) {
                Log.w("ddms", String.format("Buffer has reached maximum size of %d", 0x1000000));
                throw new BufferOverflowException();
            }
            Log.d("ddms", "Expanding read buffer to " + newCapacity);
            ByteBuffer newBuffer = ByteBuffer.allocate(newCapacity);
            this.mReadBuffer.position(0);
            newBuffer.put(this.mReadBuffer);
            this.mReadBuffer = newBuffer;
        }
        int count = this.mChannel.read(this.mReadBuffer);
        if (Log.isAtLeast(Log.LogLevel.VERBOSE)) {
            Log.v("ddms", String.format("Read %d bytes from %s", count, this));
        }
        if (count < 0) {
            throw new IOException("read failed");
        }
    }

    JdwpPacket getJdwpPacket() throws IOException {
        if (this.mConnState == ConnectionState.ST_AWAIT_SHAKE) {
            int result = JdwpHandshake.findHandshake(this.mReadBuffer);
            switch (result) {
                case 1: {
                    Log.d("ddms", "Good handshake from debugger");
                    JdwpHandshake.consumeHandshake(this.mReadBuffer);
                    this.sendHandshake();
                    this.mConnState = ConnectionState.ST_READY;
                    ClientData cd2 = this.mClient.getClientData();
                    cd2.setDebuggerConnectionStatus(ClientData.DebuggerStatus.ATTACHED);
                    this.mClient.update(2);
                    return this.getJdwpPacket();
                }
                case 3: {
                    Log.d("ddms", "Bad handshake from debugger");
                    throw new IOException("bad handshake");
                }
                case 2: {
                    break;
                }
                default: {
                    Log.e("ddms", "Unknown packet while waiting for client handshake");
                }
            }
            return null;
        }
        if (this.mConnState == ConnectionState.ST_READY) {
            if (this.mReadBuffer.position() != 0) {
                Log.v("ddms", "Checking " + this.mReadBuffer.position() + " bytes");
            }
            return JdwpPacket.findPacket(this.mReadBuffer);
        }
        Log.e("ddms", "Receiving data in state = " + (Object)((Object)this.mConnState));
        return null;
    }

    private synchronized void sendHandshake() throws IOException {
        ByteBuffer tempBuffer = ByteBuffer.allocate(JdwpHandshake.HANDSHAKE_LEN);
        JdwpHandshake.putHandshake(tempBuffer);
        int expectedLength = tempBuffer.position();
        tempBuffer.flip();
        if (this.mChannel.write(tempBuffer) != expectedLength) {
            throw new IOException("partial handshake write");
        }
        expectedLength = this.mPreDataBuffer.position();
        if (expectedLength > 0) {
            Log.d("ddms", "Sending " + this.mPreDataBuffer.position() + " bytes of saved data");
            this.mPreDataBuffer.flip();
            if (this.mChannel.write(this.mPreDataBuffer) != expectedLength) {
                throw new IOException("partial pre-data write");
            }
            this.mPreDataBuffer.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void send(JdwpPacket packet) throws IOException {
        packet.log("Debugger: forwarding jdwp packet from Client to Java Debugger");
        Debugger debugger = this;
        synchronized (debugger) {
            if (this.mChannel == null) {
                Log.d("ddms", "Saving packet 0x" + Integer.toHexString(packet.getId()));
                packet.move(this.mPreDataBuffer);
            } else {
                packet.write(this.mChannel);
            }
        }
    }

    static enum ConnectionState {
        ST_NOT_CONNECTED,
        ST_AWAIT_SHAKE,
        ST_READY;

    }
}

