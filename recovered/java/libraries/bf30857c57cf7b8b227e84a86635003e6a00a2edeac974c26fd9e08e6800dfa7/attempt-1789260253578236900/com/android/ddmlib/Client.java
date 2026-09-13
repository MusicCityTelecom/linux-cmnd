/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.ClientData;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.Debugger;
import com.android.ddmlib.Device;
import com.android.ddmlib.HandleExit;
import com.android.ddmlib.HandleHeap;
import com.android.ddmlib.HandleHello;
import com.android.ddmlib.HandleNativeHeap;
import com.android.ddmlib.HandleProfiling;
import com.android.ddmlib.HandleThread;
import com.android.ddmlib.HandleViewDebug;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.JdwpHandshake;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import com.android.ddmlib.jdwp.JdwpAgent;
import com.android.ddmlib.jdwp.JdwpProtocol;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client
extends JdwpAgent {
    private static final int SERVER_PROTOCOL_VERSION = 1;
    public static final int CHANGE_NAME = 1;
    public static final int CHANGE_DEBUGGER_STATUS = 2;
    public static final int CHANGE_PORT = 4;
    public static final int CHANGE_THREAD_MODE = 8;
    public static final int CHANGE_THREAD_DATA = 16;
    public static final int CHANGE_HEAP_MODE = 32;
    public static final int CHANGE_HEAP_DATA = 64;
    public static final int CHANGE_NATIVE_HEAP_DATA = 128;
    public static final int CHANGE_THREAD_STACKTRACE = 256;
    public static final int CHANGE_HEAP_ALLOCATIONS = 512;
    public static final int CHANGE_HEAP_ALLOCATION_STATUS = 1024;
    public static final int CHANGE_METHOD_PROFILING_STATUS = 2048;
    public static final int CHANGE_HPROF = 4096;
    public static final int CHANGE_INFO = 7;
    private SocketChannel mChan;
    private Debugger mDebugger;
    private int mDebuggerListenPort;
    private ClientData mClientData;
    private boolean mThreadUpdateEnabled;
    private boolean mHeapInfoUpdateEnabled;
    private boolean mHeapSegmentUpdateEnabled;
    private static final int INITIAL_BUF_SIZE = 2048;
    private static final int MAX_BUF_SIZE = 0x32000000;
    private ByteBuffer mReadBuffer;
    private Device mDevice;
    private int mConnState;
    private static final int ST_INIT = 1;
    private static final int ST_NOT_JDWP = 2;
    private static final int ST_AWAIT_SHAKE = 10;
    private static final int ST_NEED_DDM_PKT = 11;
    private static final int ST_NOT_DDM = 12;
    private static final int ST_READY = 13;
    private static final int ST_ERROR = 20;
    private static final int ST_DISCONNECTED = 21;

    Client(Device device, SocketChannel chan, int pid) {
        super(new JdwpProtocol());
        this.mDevice = device;
        this.mChan = chan;
        this.mReadBuffer = ByteBuffer.allocate(2048);
        this.mConnState = 1;
        this.mClientData = new ClientData(pid);
        this.mThreadUpdateEnabled = DdmPreferences.getInitialThreadUpdate();
        this.mHeapInfoUpdateEnabled = DdmPreferences.getInitialHeapUpdate();
        this.mHeapSegmentUpdateEnabled = DdmPreferences.getInitialHeapUpdate();
    }

    public String toString() {
        return "[Client pid: " + this.mClientData.getPid() + "]";
    }

    public IDevice getDevice() {
        return this.mDevice;
    }

    Device getDeviceImpl() {
        return this.mDevice;
    }

    public int getDebuggerListenPort() {
        return this.mDebuggerListenPort;
    }

    public boolean isDdmAware() {
        switch (this.mConnState) {
            case 1: 
            case 2: 
            case 10: 
            case 11: 
            case 12: 
            case 20: 
            case 21: {
                return false;
            }
            case 13: {
                return true;
            }
        }
        assert (false);
        return false;
    }

    public boolean isDebuggerAttached() {
        return this.mDebugger.isDebuggerAttached();
    }

    public Debugger getDebugger() {
        return this.mDebugger;
    }

    public ClientData getClientData() {
        return this.mClientData;
    }

    public void executeGarbageCollector() {
        try {
            HandleHeap.sendHPGC(this);
        }
        catch (IOException ioe) {
            Log.w("ddms", "Send of HPGC message failed");
        }
    }

    public void dumpHprof() {
        boolean canStream = this.mClientData.hasFeature("hprof-heap-dump-streaming");
        try {
            if (canStream) {
                HandleHeap.sendHPDS(this);
            } else {
                String file = "/sdcard/" + this.mClientData.getClientDescription().replaceAll("\\:.*", "") + ".hprof";
                HandleHeap.sendHPDU(this, file);
            }
        }
        catch (IOException e2) {
            Log.w("ddms", "Send of HPDU message failed");
        }
    }

    @Deprecated
    public void toggleMethodProfiling() {
        try {
            switch (this.mClientData.getMethodProfilingStatus()) {
                case TRACER_ON: {
                    this.stopMethodTracer();
                    break;
                }
                case SAMPLER_ON: {
                    this.stopSamplingProfiler();
                    break;
                }
                case OFF: {
                    this.startMethodTracer();
                }
            }
        }
        catch (IOException e2) {
            Log.w("ddms", "Toggle method profiling failed");
        }
    }

    private int getProfileBufferSize() {
        return DdmPreferences.getProfilerBufferSizeMb() * 1024 * 1024;
    }

    public void startMethodTracer() throws IOException {
        boolean canStream = this.mClientData.hasFeature("method-trace-profiling-streaming");
        int bufferSize = this.getProfileBufferSize();
        if (canStream) {
            HandleProfiling.sendMPSS(this, bufferSize, 0);
        } else {
            String file = "/sdcard/" + this.mClientData.getClientDescription().replaceAll("\\:.*", "") + ".trace";
            HandleProfiling.sendMPRS(this, file, bufferSize, 0);
        }
    }

    public void stopMethodTracer() throws IOException {
        boolean canStream = this.mClientData.hasFeature("method-trace-profiling-streaming");
        if (canStream) {
            HandleProfiling.sendMPSE(this);
        } else {
            HandleProfiling.sendMPRE(this);
        }
    }

    public void startSamplingProfiler(int samplingInterval, TimeUnit timeUnit) throws IOException {
        int bufferSize = this.getProfileBufferSize();
        HandleProfiling.sendSPSS(this, bufferSize, samplingInterval, timeUnit);
    }

    public void stopSamplingProfiler() throws IOException {
        HandleProfiling.sendSPSE(this);
    }

    public boolean startOpenGlTracing() {
        boolean canTraceOpenGl = this.mClientData.hasFeature("opengl-tracing");
        if (!canTraceOpenGl) {
            return false;
        }
        try {
            HandleViewDebug.sendStartGlTracing(this);
            return true;
        }
        catch (IOException e2) {
            Log.w("ddms", "Start OpenGL Tracing failed");
            return false;
        }
    }

    public boolean stopOpenGlTracing() {
        boolean canTraceOpenGl = this.mClientData.hasFeature("opengl-tracing");
        if (!canTraceOpenGl) {
            return false;
        }
        try {
            HandleViewDebug.sendStopGlTracing(this);
            return true;
        }
        catch (IOException e2) {
            Log.w("ddms", "Stop OpenGL Tracing failed");
            return false;
        }
    }

    public void requestMethodProfilingStatus() {
        try {
            HandleHeap.sendREAQ(this);
        }
        catch (IOException e2) {
            Log.e("ddmlib", e2);
        }
    }

    public void setThreadUpdateEnabled(boolean enabled) {
        this.mThreadUpdateEnabled = enabled;
        if (!enabled) {
            this.mClientData.clearThreads();
        }
        try {
            HandleThread.sendTHEN(this, enabled);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.update(8);
    }

    public boolean isThreadUpdateEnabled() {
        return this.mThreadUpdateEnabled;
    }

    public void requestThreadUpdate() {
        HandleThread.requestThreadUpdate(this);
    }

    public void requestThreadStackTrace(int threadId) {
        HandleThread.requestThreadStackCallRefresh(this, threadId);
    }

    public void setHeapUpdateEnabled(boolean enabled) {
        this.setHeapInfoUpdateEnabled(enabled);
        this.setHeapSegmentUpdateEnabled(enabled);
    }

    public void setHeapInfoUpdateEnabled(boolean enabled) {
        this.mHeapInfoUpdateEnabled = enabled;
        try {
            HandleHeap.sendHPIF(this, enabled ? 3 : 0);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.update(32);
    }

    public void setHeapSegmentUpdateEnabled(boolean enabled) {
        this.mHeapSegmentUpdateEnabled = enabled;
        try {
            HandleHeap.sendHPSG(this, enabled ? 1 : 0, 0);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.update(32);
    }

    void initializeHeapUpdateStatus() throws IOException {
        this.setHeapInfoUpdateEnabled(this.mHeapInfoUpdateEnabled);
    }

    public void updateHeapInfo() {
        try {
            HandleHeap.sendHPIF(this, 1);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public boolean isHeapUpdateEnabled() {
        return this.mHeapInfoUpdateEnabled || this.mHeapSegmentUpdateEnabled;
    }

    public boolean requestNativeHeapInformation() {
        try {
            HandleNativeHeap.sendNHGT(this);
            return true;
        }
        catch (IOException e2) {
            Log.e("ddmlib", e2);
            return false;
        }
    }

    public void enableAllocationTracker(boolean enable) {
        try {
            HandleHeap.sendREAE(this, enable);
        }
        catch (IOException e2) {
            Log.e("ddmlib", e2);
        }
    }

    public void requestAllocationStatus() {
        try {
            HandleHeap.sendREAQ(this);
        }
        catch (IOException e2) {
            Log.w("ddmlib", "IO Error while obtaining allocation status");
        }
    }

    public void requestAllocationDetails() {
        try {
            HandleHeap.sendREAL(this);
        }
        catch (IOException e2) {
            Log.e("ddmlib", e2);
        }
    }

    public void kill() {
        try {
            HandleExit.sendEXIT(this, 1);
        }
        catch (IOException ioe) {
            Log.w("ddms", "Send of EXIT message failed");
        }
    }

    void register(Selector sel) throws IOException {
        if (this.mChan != null) {
            this.mChan.register(sel, 1, this);
        }
    }

    public void setAsSelectedClient() {
        MonitorThread monitorThread = MonitorThread.getInstance();
        if (monitorThread != null) {
            monitorThread.setSelectedClient(this);
        }
    }

    public boolean isSelectedClient() {
        MonitorThread monitorThread = MonitorThread.getInstance();
        if (monitorThread != null) {
            return monitorThread.getSelectedClient() == this;
        }
        return false;
    }

    void listenForDebugger(int listenPort) throws IOException {
        this.mDebuggerListenPort = listenPort;
        this.mDebugger = new Debugger(this, listenPort);
    }

    boolean sendHandshake() {
        ByteBuffer tempBuffer = ByteBuffer.allocate(JdwpHandshake.HANDSHAKE_LEN);
        try {
            JdwpHandshake.putHandshake(tempBuffer);
            int expectedLen = tempBuffer.position();
            tempBuffer.flip();
            if (this.mChan.write(tempBuffer) != expectedLen) {
                throw new IOException("partial handshake write");
            }
        }
        catch (IOException ioe) {
            Log.e("ddms-client", "IO error during handshake: " + ioe.getMessage());
            this.mConnState = 20;
            this.close(true);
            return false;
        }
        this.mConnState = 10;
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void send(JdwpPacket packet) throws IOException {
        SocketChannel chan = this.mChan;
        if (chan == null) {
            Log.v("ddms", "Not sending packet -- client is closed");
            return;
        }
        packet.log("Client: sending jdwp packet to Android Device");
        SocketChannel socketChannel = chan;
        synchronized (socketChannel) {
            try {
                packet.write(chan);
            }
            catch (IOException ioe) {
                this.removeReplyInterceptor(packet.getId());
                throw ioe;
            }
        }
    }

    void read() throws IOException, BufferOverflowException {
        int count;
        if (this.mReadBuffer.position() == this.mReadBuffer.capacity()) {
            if (this.mReadBuffer.capacity() * 2 > 0x32000000) {
                Log.e("ddms", "Exceeded MAX_BUF_SIZE!");
                throw new BufferOverflowException();
            }
            Log.d("ddms", "Expanding read buffer to " + this.mReadBuffer.capacity() * 2);
            ByteBuffer newBuffer = ByteBuffer.allocate(this.mReadBuffer.capacity() * 2);
            this.mReadBuffer.position(0);
            newBuffer.put(this.mReadBuffer);
            this.mReadBuffer = newBuffer;
        }
        if ((count = this.mChan.read(this.mReadBuffer)) < 0) {
            throw new IOException("read failed");
        }
        Log.v("ddms", "Read " + count + " bytes from " + this);
    }

    JdwpPacket getJdwpPacket() throws IOException {
        if (this.mConnState == 10) {
            int result = JdwpHandshake.findHandshake(this.mReadBuffer);
            switch (result) {
                case 1: {
                    Log.d("ddms", "Good handshake from client, sending HELO to " + this.mClientData.getPid());
                    JdwpHandshake.consumeHandshake(this.mReadBuffer);
                    this.mConnState = 11;
                    HandleHello.sendHelloCommands(this, 1);
                    return this.getJdwpPacket();
                }
                case 3: {
                    Log.d("ddms", "Bad handshake from client");
                    if (MonitorThread.getInstance().getRetryOnBadHandshake()) {
                        this.mDevice.getClientTracker().trackClientToDropAndReopen(this, -1);
                        break;
                    }
                    this.mConnState = 2;
                    this.close(true);
                    break;
                }
                case 2: {
                    Log.d("ddms", "No handshake from client yet.");
                    break;
                }
                default: {
                    Log.e("ddms", "Unknown packet while waiting for client handshake");
                }
            }
            return null;
        }
        if (this.mConnState == 11 || this.mConnState == 12 || this.mConnState == 13) {
            if (this.mReadBuffer.position() != 0) {
                Log.v("ddms", "Checking " + this.mReadBuffer.position() + " bytes");
            }
            return JdwpPacket.findPacket(this.mReadBuffer);
        }
        Log.e("ddms", "Receiving data in state = " + this.mConnState);
        return null;
    }

    void packetFailed(JdwpPacket reply) {
        if (this.mConnState == 11) {
            Log.d("ddms", "Marking " + this + " as non-DDM client");
            this.mConnState = 12;
        } else if (this.mConnState != 12) {
            Log.w("ddms", "WEIRD: got JDWP failure packet on DDM req");
        }
    }

    synchronized boolean ddmSeen() {
        if (this.mConnState == 11) {
            this.mConnState = 13;
            return false;
        }
        if (this.mConnState != 13) {
            Log.w("ddms", "WEIRD: in ddmSeen with state=" + this.mConnState);
        }
        return true;
    }

    void close(boolean notify) {
        Log.d("ddms", "Closing " + this.toString());
        this.clear();
        try {
            if (this.mChan != null) {
                this.mChan.close();
                this.mChan = null;
            }
            if (this.mDebugger != null) {
                this.mDebugger.close();
                this.mDebugger = null;
            }
        }
        catch (IOException ioe) {
            Log.w("ddms", "failed to close " + this);
        }
        this.mDevice.removeClient(this, notify);
    }

    public boolean isValid() {
        return this.mChan != null;
    }

    void update(int changeMask) {
        this.mDevice.update(this, changeMask);
    }
}

