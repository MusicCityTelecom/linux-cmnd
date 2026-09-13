/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbVersion;
import com.android.ddmlib.Client;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.DeviceMonitor;
import com.android.ddmlib.HandleAppName;
import com.android.ddmlib.HandleHeap;
import com.android.ddmlib.HandleHello;
import com.android.ddmlib.HandleNativeHeap;
import com.android.ddmlib.HandleProfiling;
import com.android.ddmlib.HandleTest;
import com.android.ddmlib.HandleThread;
import com.android.ddmlib.HandleViewDebug;
import com.android.ddmlib.HandleWait;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MonitorThread;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AndroidDebugBridge {
    private static final AdbVersion MIN_ADB_VERSION = AdbVersion.parseFrom("1.0.20");
    private static final String ADB = "adb";
    private static final String DDMS = "ddms";
    private static final String SERVER_PORT_ENV_VAR = "ANDROID_ADB_SERVER_PORT";
    static final String DEFAULT_ADB_HOST = "localhost";
    static final int DEFAULT_ADB_PORT = 5037;
    private static boolean sUnitTestMode;
    private static int sAdbServerPort;
    private static InetAddress sHostAddr;
    private static InetSocketAddress sSocketAddr;
    private static AndroidDebugBridge sThis;
    private static boolean sInitialized;
    private static boolean sClientSupport;
    private static boolean sUseLibusb;
    private static Map<String, String> sEnv;
    private String mAdbOsLocation = null;
    private boolean mVersionCheck;
    private boolean mStarted = false;
    private DeviceMonitor mDeviceMonitor;
    private static final Object sLock;
    private static final Set<IDebugBridgeChangeListener> sBridgeListeners;
    private static final Set<IDeviceChangeListener> sDeviceListeners;
    private static final Set<IClientChangeListener> sClientListeners;

    public static synchronized void initIfNeeded(boolean clientSupport) {
        if (sInitialized) {
            return;
        }
        AndroidDebugBridge.init(clientSupport);
    }

    public static synchronized void init(boolean clientSupport) {
        AndroidDebugBridge.init(clientSupport, false, ImmutableMap.of());
    }

    public static synchronized void init(boolean clientSupport, boolean useLibusb, Map<String, String> env) {
        Preconditions.checkState(!sInitialized, "AndroidDebugBridge.init() has already been called.");
        sInitialized = true;
        sClientSupport = clientSupport;
        sUseLibusb = useLibusb;
        sEnv = env;
        AndroidDebugBridge.initAdbSocketAddr();
        MonitorThread monitorThread = MonitorThread.createInstance();
        monitorThread.start();
        HandleHello.register(monitorThread);
        HandleAppName.register(monitorThread);
        HandleTest.register(monitorThread);
        HandleThread.register(monitorThread);
        HandleHeap.register(monitorThread);
        HandleWait.register(monitorThread);
        HandleProfiling.register(monitorThread);
        HandleNativeHeap.register(monitorThread);
        HandleViewDebug.register(monitorThread);
    }

    public static void enableFakeAdbServerMode(int port) {
        Preconditions.checkState(!sInitialized, "AndroidDebugBridge.init() has already been called or terminate() has not been called yet.");
        sUnitTestMode = true;
        sAdbServerPort = port;
    }

    public static void disableFakeAdbServerMode() {
        Preconditions.checkState(!sInitialized, "AndroidDebugBridge.init() has already been called or terminate() has not been called yet.");
        sUnitTestMode = false;
        sAdbServerPort = 0;
    }

    public static synchronized void terminate() {
        MonitorThread monitorThread;
        if (sThis != null && AndroidDebugBridge.sThis.mDeviceMonitor != null) {
            AndroidDebugBridge.sThis.mDeviceMonitor.stop();
            AndroidDebugBridge.sThis.mDeviceMonitor = null;
        }
        if ((monitorThread = MonitorThread.getInstance()) != null) {
            monitorThread.quit();
        }
        sInitialized = false;
    }

    static boolean getClientSupport() {
        return sClientSupport;
    }

    public static InetSocketAddress getSocketAddress() {
        return sSocketAddr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AndroidDebugBridge createBridge() {
        Object object = sLock;
        synchronized (object) {
            if (sThis != null) {
                return sThis;
            }
            try {
                sThis = new AndroidDebugBridge();
                sThis.start();
            }
            catch (InvalidParameterException e2) {
                sThis = null;
            }
            for (IDebugBridgeChangeListener listener : sBridgeListeners) {
                try {
                    listener.bridgeChanged(sThis);
                }
                catch (Exception e3) {
                    Log.e(DDMS, e3);
                }
            }
            return sThis;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AndroidDebugBridge createBridge(String osLocation, boolean forceNewBridge) {
        Object object = sLock;
        synchronized (object) {
            if (!sUnitTestMode && sThis != null) {
                if (AndroidDebugBridge.sThis.mAdbOsLocation != null && AndroidDebugBridge.sThis.mAdbOsLocation.equals(osLocation) && !forceNewBridge) {
                    return sThis;
                }
                sThis.stop();
            }
            try {
                sThis = new AndroidDebugBridge(osLocation);
                if (!sThis.start()) {
                    return null;
                }
            }
            catch (InvalidParameterException e2) {
                sThis = null;
            }
            for (IDebugBridgeChangeListener listener : sBridgeListeners) {
                try {
                    listener.bridgeChanged(sThis);
                }
                catch (Exception e3) {
                    Log.e(DDMS, e3);
                }
            }
            return sThis;
        }
    }

    public static AndroidDebugBridge getBridge() {
        return sThis;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void disconnectBridge() {
        Object object = sLock;
        synchronized (object) {
            if (sThis != null) {
                sThis.stop();
                sThis = null;
                for (IDebugBridgeChangeListener listener : sBridgeListeners) {
                    try {
                        listener.bridgeChanged(sThis);
                    }
                    catch (Exception e2) {
                        Log.e(DDMS, e2);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addDebugBridgeChangeListener(IDebugBridgeChangeListener listener) {
        Object object = sLock;
        synchronized (object) {
            sBridgeListeners.add(listener);
            if (sThis != null) {
                try {
                    listener.bridgeChanged(sThis);
                }
                catch (Exception e2) {
                    Log.e(DDMS, e2);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeDebugBridgeChangeListener(IDebugBridgeChangeListener listener) {
        Object object = sLock;
        synchronized (object) {
            sBridgeListeners.remove(listener);
        }
    }

    public static void addDeviceChangeListener(IDeviceChangeListener listener) {
        sDeviceListeners.add(listener);
    }

    public static void removeDeviceChangeListener(IDeviceChangeListener listener) {
        sDeviceListeners.remove(listener);
    }

    public static void addClientChangeListener(IClientChangeListener listener) {
        sClientListeners.add(listener);
    }

    public static void removeClientChangeListener(IClientChangeListener listener) {
        sClientListeners.remove(listener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IDevice[] getDevices() {
        Object object = sLock;
        synchronized (object) {
            if (this.mDeviceMonitor != null) {
                return this.mDeviceMonitor.getDevices();
            }
        }
        return new IDevice[0];
    }

    public boolean hasInitialDeviceList() {
        if (this.mDeviceMonitor != null) {
            return this.mDeviceMonitor.hasInitialDeviceList();
        }
        return false;
    }

    public void setSelectedClient(Client selectedClient) {
        MonitorThread monitorThread = MonitorThread.getInstance();
        if (monitorThread != null) {
            monitorThread.setSelectedClient(selectedClient);
        }
    }

    public boolean isConnected() {
        MonitorThread monitorThread = MonitorThread.getInstance();
        if (this.mDeviceMonitor != null && monitorThread != null) {
            return this.mDeviceMonitor.isMonitoring() && monitorThread.getState() != Thread.State.TERMINATED;
        }
        return false;
    }

    public int getConnectionAttemptCount() {
        if (this.mDeviceMonitor != null) {
            return this.mDeviceMonitor.getConnectionAttemptCount();
        }
        return -1;
    }

    public int getRestartAttemptCount() {
        if (this.mDeviceMonitor != null) {
            return this.mDeviceMonitor.getRestartAttemptCount();
        }
        return -1;
    }

    private AndroidDebugBridge(String osLocation) throws InvalidParameterException {
        if (osLocation == null || osLocation.isEmpty()) {
            throw new InvalidParameterException();
        }
        this.mAdbOsLocation = osLocation;
        try {
            this.checkAdbVersion();
        }
        catch (IOException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    private AndroidDebugBridge() {
    }

    private void checkAdbVersion() throws IOException {
        AdbVersion version;
        this.mVersionCheck = false;
        if (this.mAdbOsLocation == null) {
            return;
        }
        File adb = new File(this.mAdbOsLocation);
        ListenableFuture<AdbVersion> future = AndroidDebugBridge.getAdbVersion(adb);
        try {
            version = (AdbVersion)future.get(DdmPreferences.getTimeOut(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e2) {
            return;
        }
        catch (TimeoutException e3) {
            String msg = "Unable to obtain result of 'adb version'";
            Log.logAndDisplay(Log.LogLevel.ERROR, ADB, msg);
            return;
        }
        catch (ExecutionException e4) {
            Log.logAndDisplay(Log.LogLevel.ERROR, ADB, e4.getCause().getMessage());
            Throwables.propagateIfInstanceOf(e4.getCause(), IOException.class);
            return;
        }
        if (version.compareTo(MIN_ADB_VERSION) > 0) {
            this.mVersionCheck = true;
        } else {
            String message = String.format("Required minimum version of adb: %1$s.Current version is %2$s", MIN_ADB_VERSION, version);
            Log.logAndDisplay(Log.LogLevel.ERROR, ADB, message);
        }
    }

    public static ListenableFuture<AdbVersion> getAdbVersion(final File adb) {
        final SettableFuture<AdbVersion> future = SettableFuture.create();
        new Thread(new Runnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                ProcessBuilder pb = new ProcessBuilder(adb.getPath(), "version");
                pb.redirectErrorStream(true);
                Process p3 = null;
                try {
                    p3 = pb.start();
                }
                catch (IOException e2) {
                    future.setException(e2);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(p3.getInputStream()));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        AdbVersion version = AdbVersion.parseFrom(line);
                        if (version != AdbVersion.UNKNOWN) {
                            future.set(version);
                            return;
                        }
                        sb.append(line);
                        sb.append('\n');
                    }
                }
                catch (IOException e3) {
                    future.setException(e3);
                    return;
                }
                finally {
                    try {
                        br.close();
                    }
                    catch (IOException e4) {
                        future.setException(e4);
                    }
                }
                future.setException(new RuntimeException("Unable to detect adb version, adb output: " + sb.toString()));
            }
        }, "Obtaining adb version").start();
        return future;
    }

    boolean start() {
        if (!(this.mAdbOsLocation == null || sAdbServerPort == 0 || this.mVersionCheck && this.startAdb())) {
            return false;
        }
        this.mStarted = true;
        this.mDeviceMonitor = new DeviceMonitor(this);
        this.mDeviceMonitor.start();
        return true;
    }

    boolean stop() {
        if (!this.mStarted) {
            return false;
        }
        if (this.mDeviceMonitor != null) {
            this.mDeviceMonitor.stop();
            this.mDeviceMonitor = null;
        }
        if (!this.stopAdb()) {
            return false;
        }
        this.mStarted = false;
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean restart() {
        boolean restart;
        if (this.mAdbOsLocation == null) {
            Log.e(ADB, "Cannot restart adb when AndroidDebugBridge is created without the location of adb.");
            return false;
        }
        if (sAdbServerPort == 0) {
            Log.e(ADB, "ADB server port for restarting AndroidDebugBridge is not set.");
            return false;
        }
        if (!this.mVersionCheck) {
            Log.logAndDisplay(Log.LogLevel.ERROR, ADB, "Attempting to restart adb, but version check failed!");
            return false;
        }
        Object object = sLock;
        synchronized (object) {
            for (IDebugBridgeChangeListener listener : sBridgeListeners) {
                listener.restartInitiated();
            }
        }
        Object object2 = this;
        synchronized (object2) {
            this.stopAdb();
            restart = this.startAdb();
            if (restart && this.mDeviceMonitor == null) {
                this.mDeviceMonitor = new DeviceMonitor(this);
                this.mDeviceMonitor.start();
            }
        }
        object2 = sLock;
        synchronized (object2) {
            for (IDebugBridgeChangeListener listener : sBridgeListeners) {
                listener.restartCompleted(restart);
            }
        }
        return restart;
    }

    static void deviceConnected(IDevice device) {
        for (IDeviceChangeListener listener : sDeviceListeners) {
            try {
                listener.deviceConnected(device);
            }
            catch (Exception e2) {
                Log.e(DDMS, e2);
            }
        }
    }

    static void deviceDisconnected(IDevice device) {
        for (IDeviceChangeListener listener : sDeviceListeners) {
            try {
                listener.deviceDisconnected(device);
            }
            catch (Exception e2) {
                Log.e(DDMS, e2);
            }
        }
    }

    static void deviceChanged(IDevice device, int changeMask) {
        for (IDeviceChangeListener listener : sDeviceListeners) {
            try {
                listener.deviceChanged(device, changeMask);
            }
            catch (Exception e2) {
                Log.e(DDMS, e2);
            }
        }
    }

    static void clientChanged(Client client, int changeMask) {
        for (IClientChangeListener listener : sClientListeners) {
            try {
                listener.clientChanged(client, changeMask);
            }
            catch (Exception e2) {
                Log.e(DDMS, e2);
            }
        }
    }

    DeviceMonitor getDeviceMonitor() {
        return this.mDeviceMonitor;
    }

    synchronized boolean startAdb() {
        if (sUnitTestMode) {
            return true;
        }
        if (this.mAdbOsLocation == null) {
            Log.e(ADB, "Cannot start adb when AndroidDebugBridge is created without the location of adb.");
            return false;
        }
        if (sAdbServerPort == 0) {
            Log.w(ADB, "ADB server port for starting AndroidDebugBridge is not set.");
            return false;
        }
        int status = -1;
        Object[] command = this.getAdbLaunchCommand("start-server");
        String commandString = Joiner.on(' ').join(command);
        try {
            String adbHostValue;
            Log.d(DDMS, String.format("Launching '%1$s' to ensure ADB is running.", commandString));
            ProcessBuilder processBuilder = new ProcessBuilder((String[])command);
            Map<String, String> env = processBuilder.environment();
            env.put("ADB_LIBUSB", sUseLibusb ? "1" : "0");
            sEnv.forEach(env::put);
            if (DdmPreferences.getUseAdbHost() && (adbHostValue = DdmPreferences.getAdbHostValue()) != null && !adbHostValue.isEmpty()) {
                env.put("ADBHOST", adbHostValue);
            }
            Process proc = processBuilder.start();
            ArrayList<String> errorOutput = new ArrayList<String>();
            ArrayList<String> stdOutput = new ArrayList<String>();
            status = AndroidDebugBridge.grabProcessOutput(proc, errorOutput, stdOutput, false);
        }
        catch (IOException ioe) {
            Log.e(DDMS, "Unable to run 'adb': " + ioe.getMessage());
        }
        catch (InterruptedException ie) {
            Log.e(DDMS, "Unable to run 'adb': " + ie.getMessage());
        }
        if (status != 0) {
            Log.e(DDMS, String.format("'%1$s' failed -- run manually if necessary", commandString));
            return false;
        }
        Log.d(DDMS, String.format("'%1$s' succeeded", commandString));
        return true;
    }

    private String[] getAdbLaunchCommand(String option) {
        ArrayList<String> command = new ArrayList<String>(4);
        command.add(this.mAdbOsLocation);
        if (sAdbServerPort != 5037) {
            command.add("-P");
            command.add(Integer.toString(sAdbServerPort));
        }
        command.add(option);
        return command.toArray(new String[command.size()]);
    }

    private synchronized boolean stopAdb() {
        if (this.mAdbOsLocation == null) {
            Log.e(ADB, "Cannot stop adb when AndroidDebugBridge is created without the location of adb.");
            return false;
        }
        if (sAdbServerPort == 0) {
            Log.e(ADB, "ADB server port for restarting AndroidDebugBridge is not set");
            return false;
        }
        int status = -1;
        Object[] command = this.getAdbLaunchCommand("kill-server");
        try {
            Process proc = Runtime.getRuntime().exec((String[])command);
            status = proc.waitFor();
        }
        catch (IOException iOException) {
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        String commandString = Joiner.on(' ').join(command);
        if (status != 0) {
            Log.w(DDMS, String.format("'%1$s' failed -- run manually if necessary", commandString));
            return false;
        }
        Log.d(DDMS, String.format("'%1$s' succeeded", commandString));
        return true;
    }

    private static int grabProcessOutput(final Process process, final ArrayList<String> errorOutput, final ArrayList<String> stdOutput, boolean waitForReaders) throws InterruptedException {
        assert (errorOutput != null);
        assert (stdOutput != null);
        Thread t1 = new Thread("adb:stderr reader"){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                InputStreamReader is = new InputStreamReader(process.getErrorStream(), Charsets.UTF_8);
                BufferedReader errReader = new BufferedReader(is);
                try {
                    String line;
                    while ((line = errReader.readLine()) != null) {
                        Log.e(AndroidDebugBridge.ADB, line);
                        errorOutput.add(line);
                    }
                }
                catch (IOException iOException) {
                }
                finally {
                    Closeables.closeQuietly(errReader);
                }
            }
        };
        Thread t22 = new Thread("adb:stdout reader"){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                InputStreamReader is = new InputStreamReader(process.getInputStream(), Charsets.UTF_8);
                BufferedReader outReader = new BufferedReader(is);
                try {
                    String line;
                    while ((line = outReader.readLine()) != null) {
                        Log.d(AndroidDebugBridge.ADB, line);
                        stdOutput.add(line);
                    }
                }
                catch (IOException iOException) {
                }
                finally {
                    Closeables.closeQuietly(outReader);
                }
            }
        };
        t1.start();
        t22.start();
        if (waitForReaders) {
            try {
                t1.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            try {
                t22.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return process.waitFor();
    }

    private static Object getLock() {
        return sLock;
    }

    private static void initAdbSocketAddr() {
        try {
            if (!sUnitTestMode) {
                sAdbServerPort = AndroidDebugBridge.getAdbServerPort();
            }
            sHostAddr = InetAddress.getByName(DEFAULT_ADB_HOST);
            sSocketAddr = new InetSocketAddress(sHostAddr, sAdbServerPort);
        }
        catch (UnknownHostException e2) {
            Log.e(DDMS, "Unable to resolve: localhost, due to:" + e2);
        }
    }

    private static int getAdbServerPort() {
        String msg;
        Integer prop = Integer.getInteger(SERVER_PORT_ENV_VAR);
        if (prop != null) {
            try {
                return AndroidDebugBridge.validateAdbServerPort(prop.toString());
            }
            catch (IllegalArgumentException e2) {
                msg = String.format("Invalid value (%1$s) for ANDROID_ADB_SERVER_PORT system property.", prop);
                Log.w(DDMS, msg);
            }
        }
        try {
            String env = System.getenv(SERVER_PORT_ENV_VAR);
            if (env != null) {
                return AndroidDebugBridge.validateAdbServerPort(env);
            }
        }
        catch (SecurityException ex) {
            Log.w(DDMS, "No access to env variables allowed by current security manager. If you've set ANDROID_ADB_SERVER_PORT: it's being ignored.");
        }
        catch (IllegalArgumentException e3) {
            msg = String.format("Invalid value (%1$s) for ANDROID_ADB_SERVER_PORT environment variable (%2$s).", prop, e3.getMessage());
            Log.w(DDMS, msg);
        }
        return 5037;
    }

    private static int validateAdbServerPort(String adbServerPort) throws IllegalArgumentException {
        try {
            int port = Integer.decode(adbServerPort);
            if (port <= 0 || port >= 65535) {
                throw new IllegalArgumentException("Should be > 0 and < 65535");
            }
            return port;
        }
        catch (NumberFormatException e2) {
            throw new IllegalArgumentException("Not a valid port number");
        }
    }

    static {
        sAdbServerPort = 0;
        sInitialized = false;
        sLock = new Object();
        sBridgeListeners = Sets.newCopyOnWriteArraySet();
        sDeviceListeners = Sets.newCopyOnWriteArraySet();
        sClientListeners = Sets.newCopyOnWriteArraySet();
    }

    public static interface IClientChangeListener {
        public void clientChanged(Client var1, int var2);
    }

    public static interface IDeviceChangeListener {
        public void deviceConnected(IDevice var1);

        public void deviceDisconnected(IDevice var1);

        public void deviceChanged(IDevice var1, int var2);
    }

    public static interface IDebugBridgeChangeListener {
        public void bridgeChanged(AndroidDebugBridge var1);

        default public void restartInitiated() {
        }

        default public void restartCompleted(boolean isSuccessful) {
        }
    }
}

