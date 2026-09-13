/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.ClientTracker;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.Device;
import com.android.ddmlib.EmulatorConsole;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.utils.DebuggerPorts;
import com.android.utils.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

final class DeviceMonitor
implements ClientTracker {
    private static final String ADB_TRACK_DEVICES_COMMAND = "host:track-devices";
    private static final String ADB_TRACK_JDWP_COMMAND = "track-jdwp";
    private final byte[] mLengthBuffer2 = new byte[4];
    private volatile boolean mQuit = false;
    private final AndroidDebugBridge mServer;
    private DeviceListMonitorTask mDeviceListMonitorTask;
    private Selector mSelector;
    private final List<Device> mDevices = Lists.newCopyOnWriteArrayList();
    private final DebuggerPorts mDebuggerPorts = new DebuggerPorts(DdmPreferences.getDebugPortBase());
    private final Map<Client, Integer> mClientsToReopen = new HashMap<Client, Integer>();
    private final BlockingQueue<Pair<SocketChannel, Device>> mChannelsToRegister = Queues.newLinkedBlockingQueue();

    DeviceMonitor(AndroidDebugBridge server) {
        this.mServer = server;
    }

    void start() {
        this.mDeviceListMonitorTask = new DeviceListMonitorTask(this.mServer, new DeviceListUpdateListener());
        new Thread((Runnable)this.mDeviceListMonitorTask, "Device List Monitor").start();
    }

    void stop() {
        this.mQuit = true;
        if (this.mDeviceListMonitorTask != null) {
            this.mDeviceListMonitorTask.stop();
        }
        if (this.mSelector != null) {
            this.mSelector.wakeup();
        }
    }

    boolean isMonitoring() {
        return this.mDeviceListMonitorTask != null && this.mDeviceListMonitorTask.isMonitoring();
    }

    int getConnectionAttemptCount() {
        return this.mDeviceListMonitorTask == null ? 0 : this.mDeviceListMonitorTask.getConnectionAttemptCount();
    }

    int getRestartAttemptCount() {
        return this.mDeviceListMonitorTask == null ? 0 : this.mDeviceListMonitorTask.getRestartAttemptCount();
    }

    boolean hasInitialDeviceList() {
        return this.mDeviceListMonitorTask != null && this.mDeviceListMonitorTask.hasInitialDeviceList();
    }

    Device[] getDevices() {
        return this.mDevices.toArray(new Device[0]);
    }

    AndroidDebugBridge getServer() {
        return this.mServer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void trackClientToDropAndReopen(Client client, int port) {
        Map<Client, Integer> map = this.mClientsToReopen;
        synchronized (map) {
            Log.d("DeviceMonitor", "Adding " + client + " to list of client to reopen (" + port + ").");
            if (this.mClientsToReopen.get(client) == null) {
                this.mClientsToReopen.put(client, port);
            }
        }
        this.mSelector.wakeup();
    }

    private static SocketChannel openAdbConnection() {
        try {
            SocketChannel adbChannel = SocketChannel.open(AndroidDebugBridge.getSocketAddress());
            adbChannel.socket().setTcpNoDelay(true);
            return adbChannel;
        }
        catch (IOException e2) {
            Log.e("DeviceMonitor", "Unable to open connection to: " + AndroidDebugBridge.getSocketAddress() + ", due to: " + e2);
            return null;
        }
    }

    private void updateDevices(List<Device> newList) {
        DeviceListComparisonResult result = DeviceListComparisonResult.compare(this.mDevices, newList);
        for (IDevice device : result.removed) {
            this.removeDevice((Device)device);
            AndroidDebugBridge.deviceDisconnected(device);
        }
        ArrayList<Device> newlyOnline = Lists.newArrayListWithExpectedSize(this.mDevices.size());
        for (Map.Entry<IDevice, IDevice.DeviceState> entry : result.updated.entrySet()) {
            Device device = (Device)entry.getKey();
            device.setState(entry.getValue());
            device.update(1);
            if (!device.isOnline()) continue;
            newlyOnline.add(device);
        }
        for (IDevice iDevice : result.added) {
            this.mDevices.add((Device)iDevice);
            AndroidDebugBridge.deviceConnected(iDevice);
            if (!iDevice.isOnline()) continue;
            newlyOnline.add((Device)iDevice);
        }
        if (AndroidDebugBridge.getClientSupport()) {
            for (Device device : newlyOnline) {
                if (this.startMonitoringDevice(device)) continue;
                Log.e("DeviceMonitor", "Failed to start monitoring " + device.getSerialNumber());
            }
        }
        for (Device device : newlyOnline) {
            DeviceMonitor.queryAvdName(device);
            device.getSystemProperty("ro.build.version.sdk");
        }
    }

    private void removeDevice(Device device) {
        device.setState(IDevice.DeviceState.DISCONNECTED);
        device.clearClientList();
        this.mDevices.remove(device);
        SocketChannel channel = device.getClientMonitoringSocket();
        if (channel != null) {
            try {
                channel.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private static void queryAvdName(Device device) {
        if (!device.isEmulator()) {
            return;
        }
        EmulatorConsole console = EmulatorConsole.getConsole(device);
        if (console != null) {
            device.setAvdName(console.getAvdName());
            console.close();
        }
    }

    private boolean startMonitoringDevice(Device device) {
        block14: {
            SocketChannel socketChannel = DeviceMonitor.openAdbConnection();
            if (socketChannel != null) {
                try {
                    boolean result = DeviceMonitor.sendDeviceMonitoringRequest(socketChannel, device);
                    if (!result) break block14;
                    if (this.mSelector == null) {
                        this.startDeviceMonitorThread();
                    }
                    device.setClientMonitoringSocket(socketChannel);
                    socketChannel.configureBlocking(false);
                    try {
                        this.mChannelsToRegister.put(Pair.of(socketChannel, device));
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    this.mSelector.wakeup();
                    return true;
                }
                catch (TimeoutException e2) {
                    try {
                        socketChannel.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    Log.d("DeviceMonitor", "Connection Failure when starting to monitor device '" + device + "' : timeout");
                }
                catch (AdbCommandRejectedException e3) {
                    try {
                        socketChannel.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    Log.d("DeviceMonitor", "Adb refused to start monitoring device '" + device + "' : " + e3.getMessage());
                }
                catch (IOException e4) {
                    try {
                        socketChannel.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    Log.d("DeviceMonitor", "Connection Failure when starting to monitor device '" + device + "' : " + e4.getMessage());
                }
            }
        }
        return false;
    }

    private void startDeviceMonitorThread() throws IOException {
        this.mSelector = Selector.open();
        new Thread("Device Client Monitor"){

            @Override
            public void run() {
                DeviceMonitor.this.deviceClientMonitorLoop();
            }
        }.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deviceClientMonitorLoop() {
        do {
            try {
                int count = this.mSelector.select();
                if (this.mQuit) {
                    return;
                }
                Map<Client, Integer> map = this.mClientsToReopen;
                synchronized (map) {
                    if (!this.mClientsToReopen.isEmpty()) {
                        Set<Client> clients = this.mClientsToReopen.keySet();
                        MonitorThread monitorThread = MonitorThread.getInstance();
                        for (Client client : clients) {
                            Device device = client.getDeviceImpl();
                            int pid = client.getClientData().getPid();
                            monitorThread.dropClient(client, false);
                            Uninterruptibles.sleepUninterruptibly(1L, TimeUnit.SECONDS);
                            int port = this.mClientsToReopen.get(client);
                            if (port == -1) {
                                port = this.getNextDebuggerPort();
                            }
                            Log.d("DeviceMonitor", "Reopening " + client);
                            DeviceMonitor.openClient(device, pid, port, monitorThread);
                            device.update(2);
                        }
                        this.mClientsToReopen.clear();
                    }
                }
                while (!this.mChannelsToRegister.isEmpty()) {
                    try {
                        Pair<SocketChannel, Device> data = this.mChannelsToRegister.take();
                        data.getFirst().register(this.mSelector, 1, data.getSecond());
                    }
                    catch (InterruptedException data) {}
                }
                if (count == 0) continue;
                Set<SelectionKey> keys2 = this.mSelector.selectedKeys();
                Iterator<SelectionKey> iter = keys2.iterator();
                while (iter.hasNext()) {
                    Device device;
                    SocketChannel socket;
                    Object attachment;
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (!key.isValid() || !key.isReadable() || !((attachment = key.attachment()) instanceof Device) || (socket = (device = (Device)attachment).getClientMonitoringSocket()) == null) continue;
                    try {
                        int length = DeviceMonitor.readLength(socket, this.mLengthBuffer2);
                        this.processIncomingJdwpData(device, socket, length);
                    }
                    catch (IOException ioe) {
                        Log.d("DeviceMonitor", "Error reading jdwp list: " + ioe.getMessage());
                        socket.close();
                        if (!this.mDevices.contains(device)) continue;
                        Log.d("DeviceMonitor", "Restarting monitoring service for " + device);
                        this.startMonitoringDevice(device);
                    }
                }
            }
            catch (IOException e2) {
                Log.e("DeviceMonitor", "Connection error while monitoring clients.");
            }
        } while (!this.mQuit);
    }

    private static boolean sendDeviceMonitoringRequest(SocketChannel socket, Device device) throws TimeoutException, AdbCommandRejectedException, IOException {
        try {
            AdbHelper.setDevice(socket, device);
            AdbHelper.write(socket, AdbHelper.formAdbRequest(ADB_TRACK_JDWP_COMMAND));
            AdbHelper.AdbResponse resp = AdbHelper.readAdbResponse(socket, false);
            if (!resp.okay) {
                Log.e("DeviceMonitor", "adb refused request: " + resp.message);
            }
            return resp.okay;
        }
        catch (TimeoutException e2) {
            Log.e("DeviceMonitor", "Sending jdwp tracking request timed out!");
            throw e2;
        }
        catch (IOException e3) {
            Log.e("DeviceMonitor", "Sending jdwp tracking request failed!");
            throw e3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void processIncomingJdwpData(Device device, SocketChannel monitorSocket, int length) throws IOException {
        String[] stringArray;
        if (length < 0) return;
        HashSet<Integer> newPids = new HashSet<Integer>();
        if (length > 0) {
            void var10_12;
            String[] pids;
            byte[] buffer = new byte[length];
            String result = DeviceMonitor.read(monitorSocket, buffer);
            stringArray = pids = result == null ? new String[]{} : result.split("\n");
            int n2 = stringArray.length;
            boolean bl = false;
            while (var10_12 < n2) {
                String pid = stringArray[var10_12];
                try {
                    newPids.add(Integer.valueOf(pid));
                }
                catch (NumberFormatException nfe) {
                    // empty catch block
                }
                ++var10_12;
            }
        }
        MonitorThread monitorThread = MonitorThread.getInstance();
        String[] clients = device.getClientList();
        HashMap<Integer, Client> existingClients = new HashMap<Integer, Client>();
        stringArray = clients;
        synchronized (clients) {
            for (Client client : clients) {
                existingClients.put(client.getClientData().getPid(), client);
            }
            // ** MonitorExit[var8_8] (shouldn't be in output)
            HashSet clientsToRemove = new HashSet();
            for (Integer n3 : existingClients.keySet()) {
                if (newPids.contains(n3)) continue;
                clientsToRemove.add(existingClients.get(n3));
            }
            HashSet pidsToAdd = new HashSet(newPids);
            pidsToAdd.removeAll(existingClients.keySet());
            monitorThread.dropClients(clientsToRemove, false);
            Iterator iterator2 = pidsToAdd.iterator();
            while (iterator2.hasNext()) {
                int newPid = (Integer)iterator2.next();
                DeviceMonitor.openClient(device, newPid, this.getNextDebuggerPort(), monitorThread);
            }
            if (pidsToAdd.isEmpty() && clientsToRemove.isEmpty()) return;
            AndroidDebugBridge.deviceChanged(device, 2);
            return;
        }
    }

    private static void openClient(Device device, int pid, int port, MonitorThread monitorThread) {
        SocketChannel clientSocket;
        try {
            clientSocket = AdbHelper.createPassThroughConnection(AndroidDebugBridge.getSocketAddress(), device, pid);
            clientSocket.configureBlocking(false);
        }
        catch (UnknownHostException uhe) {
            Log.d("DeviceMonitor", "Unknown Jdwp pid: " + pid);
            return;
        }
        catch (TimeoutException e2) {
            Log.w("DeviceMonitor", "Failed to connect to client '" + pid + "': timeout");
            return;
        }
        catch (AdbCommandRejectedException e3) {
            Log.w("DeviceMonitor", "Adb rejected connection to client '" + pid + "': " + e3.getMessage());
            return;
        }
        catch (IOException ioe) {
            Log.w("DeviceMonitor", "Failed to connect to client '" + pid + "': " + ioe.getMessage());
            return;
        }
        DeviceMonitor.createClient(device, pid, clientSocket, port, monitorThread);
    }

    private static void createClient(Device device, int pid, SocketChannel socket, int debuggerPort, MonitorThread monitorThread) {
        Client client = new Client(device, socket, pid);
        if (client.sendHandshake()) {
            try {
                if (AndroidDebugBridge.getClientSupport()) {
                    client.listenForDebugger(debuggerPort);
                    String msg = String.format(Locale.US, "Opening a debugger listener at port %1$d for client with pid %2$d", debuggerPort, pid);
                    Log.i("ddms", msg);
                }
            }
            catch (IOException ioe) {
                client.getClientData().setDebuggerConnectionStatus(ClientData.DebuggerStatus.ERROR);
                Log.e("ddms", "Can't bind to local " + debuggerPort + " for debugger");
            }
            client.requestAllocationStatus();
        } else {
            Log.e("ddms", "Handshake with " + client + " failed!");
        }
        if (client.isValid()) {
            device.addClient(client);
            monitorThread.addClient(client);
        }
    }

    private int getNextDebuggerPort() {
        return this.mDebuggerPorts.next();
    }

    @Override
    public void trackDisconnectedClient(Client client) {
        this.mDebuggerPorts.free(client.getDebuggerListenPort());
    }

    private static int readLength(SocketChannel socket, byte[] buffer) throws IOException {
        String msg = DeviceMonitor.read(socket, buffer);
        if (msg != null) {
            try {
                return Integer.parseInt(msg, 16);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        throw new IOException("Unable to read length");
    }

    private static String read(SocketChannel socket, byte[] buffer) throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(buffer, 0, buffer.length);
        while (buf.position() != buf.limit()) {
            int count = socket.read(buf);
            if (count >= 0) continue;
            throw new IOException("EOF");
        }
        return new String(buffer, 0, buf.position(), AdbHelper.DEFAULT_CHARSET);
    }

    static class DeviceListMonitorTask
    implements Runnable {
        private final byte[] mLengthBuffer = new byte[4];
        private final AndroidDebugBridge mBridge;
        private final UpdateListener mListener;
        private SocketChannel mAdbConnection = null;
        private boolean mMonitoring = false;
        private int mConnectionAttempt = 0;
        private int mRestartAttemptCount = 0;
        private boolean mInitialDeviceListDone = false;
        private volatile boolean mQuit;

        public DeviceListMonitorTask(AndroidDebugBridge bridge, UpdateListener listener) {
            this.mBridge = bridge;
            this.mListener = listener;
        }

        @Override
        public void run() {
            do {
                if (this.mAdbConnection == null) {
                    Log.d("DeviceMonitor", "Opening adb connection");
                    this.mAdbConnection = DeviceMonitor.openAdbConnection();
                    if (this.mAdbConnection == null) {
                        ++this.mConnectionAttempt;
                        Log.e("DeviceMonitor", "Connection attempts: " + this.mConnectionAttempt);
                        if (this.mConnectionAttempt > 10) {
                            if (!this.mBridge.startAdb()) {
                                ++this.mRestartAttemptCount;
                                Log.e("DeviceMonitor", "adb restart attempts: " + this.mRestartAttemptCount);
                            } else {
                                Log.i("DeviceMonitor", "adb restarted");
                                this.mRestartAttemptCount = 0;
                            }
                        }
                        Uninterruptibles.sleepUninterruptibly(1L, TimeUnit.SECONDS);
                    } else {
                        Log.d("DeviceMonitor", "Connected to adb for device monitoring");
                        this.mConnectionAttempt = 0;
                    }
                }
                try {
                    int length;
                    if (this.mAdbConnection != null && !this.mMonitoring) {
                        this.mMonitoring = this.sendDeviceListMonitoringRequest();
                    }
                    if (!this.mMonitoring || (length = DeviceMonitor.readLength(this.mAdbConnection, this.mLengthBuffer)) < 0) continue;
                    this.processIncomingDeviceData(length);
                    this.mInitialDeviceListDone = true;
                }
                catch (AsynchronousCloseException length) {
                }
                catch (TimeoutException ioe) {
                    this.handleExceptionInMonitorLoop(ioe);
                }
                catch (IOException ioe) {
                    this.handleExceptionInMonitorLoop(ioe);
                }
            } while (!this.mQuit);
        }

        private boolean sendDeviceListMonitoringRequest() throws TimeoutException, IOException {
            byte[] request = AdbHelper.formAdbRequest(DeviceMonitor.ADB_TRACK_DEVICES_COMMAND);
            try {
                AdbHelper.write(this.mAdbConnection, request);
                AdbHelper.AdbResponse resp = AdbHelper.readAdbResponse(this.mAdbConnection, false);
                if (!resp.okay) {
                    Log.e("DeviceMonitor", "adb refused request: " + resp.message);
                }
                return resp.okay;
            }
            catch (IOException e2) {
                Log.e("DeviceMonitor", "Sending Tracking request failed!");
                this.mAdbConnection.close();
                throw e2;
            }
        }

        private void handleExceptionInMonitorLoop(Exception e2) {
            if (!this.mQuit) {
                if (e2 instanceof TimeoutException) {
                    Log.e("DeviceMonitor", "Adb connection Error: timeout");
                } else {
                    Log.e("DeviceMonitor", "Adb connection Error:" + e2.getMessage());
                }
                this.mMonitoring = false;
                if (this.mAdbConnection != null) {
                    try {
                        this.mAdbConnection.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    this.mAdbConnection = null;
                    this.mListener.connectionError(e2);
                }
            }
        }

        private void processIncomingDeviceData(int length) throws IOException {
            Map<String, IDevice.DeviceState> result;
            if (length <= 0) {
                result = Collections.emptyMap();
            } else {
                String response = DeviceMonitor.read(this.mAdbConnection, new byte[length]);
                result = DeviceListMonitorTask.parseDeviceListResponse(response);
            }
            this.mListener.deviceListUpdate(result);
        }

        static Map<String, IDevice.DeviceState> parseDeviceListResponse(String result) {
            String[] devices;
            HashMap<String, IDevice.DeviceState> deviceStateMap = Maps.newHashMap();
            for (String d2 : devices = result == null ? new String[]{} : result.split("\n")) {
                String[] param = d2.split("\t");
                if (param.length != 2) continue;
                deviceStateMap.put(param[0], IDevice.DeviceState.getState(param[1]));
            }
            return deviceStateMap;
        }

        boolean isMonitoring() {
            return this.mMonitoring;
        }

        boolean hasInitialDeviceList() {
            return this.mInitialDeviceListDone;
        }

        int getConnectionAttemptCount() {
            return this.mConnectionAttempt;
        }

        int getRestartAttemptCount() {
            return this.mRestartAttemptCount;
        }

        public void stop() {
            this.mQuit = true;
            if (this.mAdbConnection != null) {
                try {
                    this.mAdbConnection.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }

        private static interface UpdateListener {
            public void connectionError(Exception var1);

            public void deviceListUpdate(Map<String, IDevice.DeviceState> var1);
        }
    }

    static class DeviceListComparisonResult {
        public final Map<IDevice, IDevice.DeviceState> updated;
        public final List<IDevice> added;
        public final List<IDevice> removed;

        private DeviceListComparisonResult(Map<IDevice, IDevice.DeviceState> updated, List<IDevice> added, List<IDevice> removed) {
            this.updated = updated;
            this.added = added;
            this.removed = removed;
        }

        public static DeviceListComparisonResult compare(List<? extends IDevice> previous, List<? extends IDevice> current) {
            current = Lists.newArrayList(current);
            HashMap<IDevice, IDevice.DeviceState> updated = Maps.newHashMapWithExpectedSize(current.size());
            ArrayList<IDevice> added = Lists.newArrayListWithExpectedSize(1);
            ArrayList<IDevice> removed = Lists.newArrayListWithExpectedSize(1);
            for (IDevice iDevice : previous) {
                IDevice currentDevice = DeviceListComparisonResult.find(current, iDevice);
                if (currentDevice != null) {
                    if (currentDevice.getState() != iDevice.getState()) {
                        updated.put(iDevice, currentDevice.getState());
                    }
                    current.remove(currentDevice);
                    continue;
                }
                removed.add(iDevice);
            }
            added.addAll(current);
            return new DeviceListComparisonResult(updated, added, removed);
        }

        private static IDevice find(List<? extends IDevice> devices, IDevice device) {
            for (IDevice iDevice : devices) {
                if (!iDevice.getSerialNumber().equals(device.getSerialNumber())) continue;
                return iDevice;
            }
            return null;
        }
    }

    private class DeviceListUpdateListener
    implements DeviceListMonitorTask.UpdateListener {
        private DeviceListUpdateListener() {
        }

        @Override
        public void connectionError(Exception e2) {
            for (Device device : DeviceMonitor.this.mDevices) {
                DeviceMonitor.this.removeDevice(device);
                AndroidDebugBridge.deviceDisconnected(device);
            }
        }

        @Override
        public void deviceListUpdate(Map<String, IDevice.DeviceState> devices) {
            ArrayList<Device> l2 = Lists.newArrayListWithExpectedSize(devices.size());
            for (Map.Entry<String, IDevice.DeviceState> entry : devices.entrySet()) {
                l2.add(new Device(DeviceMonitor.this, entry.getKey(), entry.getValue()));
            }
            DeviceMonitor.this.updateDevices(l2);
        }
    }
}

