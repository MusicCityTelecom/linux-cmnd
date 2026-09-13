/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.BatteryFetcher;
import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.ClientTracker;
import com.android.ddmlib.CollectingOutputReceiver;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.InstallReceiver;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.NullOutputReceiver;
import com.android.ddmlib.PropertyFetcher;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ScreenRecorderOptions;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SplitApkInstaller;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.SyncService;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.log.LogReceiver;
import com.android.sdklib.AndroidVersion;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Atomics;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

final class Device
implements IDevice {
    static final String RE_EMULATOR_SN = "emulator-(\\d+)";
    private final String mSerialNumber;
    private String mAvdName = null;
    private IDevice.DeviceState mState = null;
    private boolean mIsRoot = false;
    private final PropertyFetcher mPropFetcher = new PropertyFetcher(this);
    private final Map<String, String> mMountPoints = new HashMap<String, String>();
    private final BatteryFetcher mBatteryFetcher = new BatteryFetcher(this);
    private final List<Client> mClients = new ArrayList<Client>();
    private final Map<Integer, String> mClientInfo = new ConcurrentHashMap<Integer, String>();
    private ClientTracker mClientTracer;
    private static final String LOG_TAG = "Device";
    private static final char SEPARATOR = '-';
    private static final String UNKNOWN_PACKAGE = "";
    private static final long GET_PROP_TIMEOUT_MS = 250L;
    private static final long INITIAL_GET_PROP_TIMEOUT_MS = 2000L;
    private static final int QUERY_IS_ROOT_TIMEOUT_MS = 1000;
    private static final long INSTALL_TIMEOUT_MINUTES;
    private SocketChannel mSocketChannel;
    private Integer mLastBatteryLevel = null;
    private long mLastBatteryCheckTime = 0L;
    private static final String SCREEN_RECORDER_DEVICE_PATH = "/system/bin/screenrecord";
    private static final long LS_TIMEOUT_SEC = 2L;
    private Boolean mHasScreenRecorder;
    private Set<String> mHardwareCharacteristics;
    private int mApiLevel;
    private AndroidVersion mVersion;
    private String mName;

    @Override
    public String getSerialNumber() {
        return this.mSerialNumber;
    }

    @Override
    public String getAvdName() {
        return this.mAvdName;
    }

    void setAvdName(String avdName) {
        if (!this.isEmulator()) {
            throw new IllegalArgumentException("Cannot set the AVD name of the device is not an emulator");
        }
        this.mAvdName = avdName;
    }

    @Override
    public String getName() {
        if (this.mName != null) {
            return this.mName;
        }
        if (this.isOnline()) {
            this.mName = this.constructName();
            return this.mName;
        }
        return this.constructName();
    }

    private String constructName() {
        if (this.isEmulator()) {
            String avdName = this.getAvdName();
            if (avdName != null) {
                return String.format("%s [%s]", avdName, this.getSerialNumber());
            }
            return this.getSerialNumber();
        }
        String manufacturer = null;
        String model = null;
        try {
            manufacturer = this.cleanupStringForDisplay(this.getProperty("ro.product.manufacturer"));
            model = this.cleanupStringForDisplay(this.getProperty("ro.product.model"));
        }
        catch (Exception exception) {
            // empty catch block
        }
        StringBuilder sb = new StringBuilder(20);
        if (manufacturer != null) {
            sb.append(manufacturer);
            sb.append('-');
        }
        if (model != null) {
            sb.append(model);
            sb.append('-');
        }
        sb.append(this.getSerialNumber());
        return sb.toString();
    }

    private String cleanupStringForDisplay(String s3) {
        if (s3 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(s3.length());
        for (int i2 = 0; i2 < s3.length(); ++i2) {
            char c2 = s3.charAt(i2);
            if (Character.isLetterOrDigit(c2)) {
                sb.append(Character.toLowerCase(c2));
                continue;
            }
            sb.append('_');
        }
        return sb.toString();
    }

    @Override
    public IDevice.DeviceState getState() {
        return this.mState;
    }

    void setState(IDevice.DeviceState state) {
        this.mState = state;
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(this.mPropFetcher.getProperties());
    }

    @Override
    public int getPropertyCount() {
        return this.mPropFetcher.getProperties().size();
    }

    @Override
    public String getProperty(String name) {
        Map<String, String> properties = this.mPropFetcher.getProperties();
        long timeout = properties.isEmpty() ? 2000L : 250L;
        Future<String> future = this.mPropFetcher.getProperty(name);
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
        }
        catch (ExecutionException executionException) {
        }
        catch (java.util.concurrent.TimeoutException timeoutException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public boolean arePropertiesSet() {
        return this.mPropFetcher.arePropertiesSet();
    }

    @Override
    public String getPropertyCacheOrSync(String name) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        Future<String> future = this.mPropFetcher.getProperty(name);
        try {
            return future.get();
        }
        catch (InterruptedException interruptedException) {
        }
        catch (ExecutionException executionException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public String getPropertySync(String name) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        Future<String> future = this.mPropFetcher.getProperty(name);
        try {
            return future.get();
        }
        catch (InterruptedException interruptedException) {
        }
        catch (ExecutionException executionException) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Future<String> getSystemProperty(String name) {
        return this.mPropFetcher.getProperty(name);
    }

    @Override
    public boolean supportsFeature(IDevice.Feature feature) {
        switch (feature) {
            case SCREEN_RECORD: {
                if (!this.getVersion().isGreaterOrEqualThan(19)) {
                    return false;
                }
                if (this.mHasScreenRecorder == null) {
                    this.mHasScreenRecorder = this.hasBinary(SCREEN_RECORDER_DEVICE_PATH);
                }
                return this.mHasScreenRecorder;
            }
            case PROCSTATS: {
                return this.getVersion().isGreaterOrEqualThan(19);
            }
        }
        return false;
    }

    @Override
    public boolean supportsFeature(IDevice.HardwareFeature feature) {
        if (this.mHardwareCharacteristics == null) {
            try {
                String characteristics = this.getProperty("ro.build.characteristics");
                if (characteristics == null) {
                    return false;
                }
                this.mHardwareCharacteristics = Sets.newHashSet(Splitter.on(',').split(characteristics));
            }
            catch (Exception e2) {
                this.mHardwareCharacteristics = Collections.emptySet();
            }
        }
        return this.mHardwareCharacteristics.contains(feature.getCharacteristic());
    }

    @Override
    public AndroidVersion getVersion() {
        if (this.mVersion != null) {
            return this.mVersion;
        }
        try {
            String buildApi = this.getProperty("ro.build.version.sdk");
            if (buildApi == null) {
                return AndroidVersion.DEFAULT;
            }
            int api = Integer.parseInt(buildApi);
            String codeName = this.getProperty("ro.build.version.codename");
            this.mVersion = new AndroidVersion(api, codeName);
            return this.mVersion;
        }
        catch (Exception e2) {
            return AndroidVersion.DEFAULT;
        }
    }

    private boolean hasBinary(String path) {
        CountDownLatch latch = new CountDownLatch(1);
        CollectingOutputReceiver receiver = new CollectingOutputReceiver(latch);
        try {
            this.executeShellCommand("ls " + path, receiver, 2L, TimeUnit.SECONDS);
        }
        catch (Exception e2) {
            return false;
        }
        try {
            latch.await(2L, TimeUnit.SECONDS);
        }
        catch (InterruptedException e3) {
            return false;
        }
        String value = receiver.getOutput().trim();
        return !value.endsWith("No such file or directory");
    }

    @Override
    public String getMountPoint(String name) {
        String mount = this.mMountPoints.get(name);
        if (mount == null) {
            try {
                mount = this.queryMountPoint(name);
                this.mMountPoints.put(name, mount);
            }
            catch (TimeoutException timeoutException) {
            }
            catch (AdbCommandRejectedException adbCommandRejectedException) {
            }
            catch (ShellCommandUnresponsiveException shellCommandUnresponsiveException) {
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return mount;
    }

    private String queryMountPoint(String name) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        final AtomicReference ref = Atomics.newReference();
        this.executeShellCommand("echo $" + name, new MultiLineReceiver(){

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public void processNewLines(String[] lines) {
                for (String line : lines) {
                    if (line.isEmpty()) continue;
                    ref.set(line);
                }
            }
        });
        return (String)ref.get();
    }

    public String toString() {
        return this.mSerialNumber;
    }

    @Override
    public boolean isOnline() {
        return this.mState == IDevice.DeviceState.ONLINE;
    }

    @Override
    public boolean isEmulator() {
        return this.mSerialNumber.matches(RE_EMULATOR_SN);
    }

    @Override
    public boolean isOffline() {
        return this.mState == IDevice.DeviceState.OFFLINE;
    }

    @Override
    public boolean isBootLoader() {
        return this.mState == IDevice.DeviceState.BOOTLOADER;
    }

    @Override
    public SyncService getSyncService() throws TimeoutException, AdbCommandRejectedException, IOException {
        SyncService syncService = new SyncService(AndroidDebugBridge.getSocketAddress(), this);
        if (syncService.openSync()) {
            return syncService;
        }
        return null;
    }

    @Override
    public FileListingService getFileListingService() {
        return new FileListingService(this);
    }

    @Override
    public RawImage getScreenshot() throws TimeoutException, AdbCommandRejectedException, IOException {
        return this.getScreenshot(0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public RawImage getScreenshot(long timeout, TimeUnit unit) throws TimeoutException, AdbCommandRejectedException, IOException {
        return AdbHelper.getFrameBuffer(AndroidDebugBridge.getSocketAddress(), this, timeout, unit);
    }

    @Override
    public void startScreenRecorder(String remoteFilePath, ScreenRecorderOptions options, IShellOutputReceiver receiver) throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException {
        this.executeShellCommand(Device.getScreenRecorderCommand(remoteFilePath, options), receiver, 0L, null);
    }

    static String getScreenRecorderCommand(String remoteFilePath, ScreenRecorderOptions options) {
        StringBuilder sb = new StringBuilder();
        sb.append("screenrecord");
        sb.append(' ');
        if (options.width > 0 && options.height > 0) {
            sb.append("--size ");
            sb.append(options.width);
            sb.append('x');
            sb.append(options.height);
            sb.append(' ');
        }
        if (options.bitrateMbps > 0) {
            sb.append("--bit-rate ");
            sb.append(options.bitrateMbps * 1000000);
            sb.append(' ');
        }
        if (options.timeLimit > 0L) {
            sb.append("--time-limit ");
            long seconds = TimeUnit.SECONDS.convert(options.timeLimit, options.timeLimitUnits);
            if (seconds > 180L) {
                seconds = 180L;
            }
            sb.append(seconds);
            sb.append(' ');
        }
        sb.append(remoteFilePath);
        return sb.toString();
    }

    @Override
    public void executeShellCommand(String command, IShellOutputReceiver receiver) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(AndroidDebugBridge.getSocketAddress(), command, this, receiver, DdmPreferences.getTimeOut());
    }

    @Override
    public void executeShellCommand(String command, IShellOutputReceiver receiver, int maxTimeToOutputResponse) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(AndroidDebugBridge.getSocketAddress(), command, this, receiver, maxTimeToOutputResponse);
    }

    @Override
    public void executeShellCommand(String command, IShellOutputReceiver receiver, long maxTimeToOutputResponse, TimeUnit maxTimeUnits) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(AndroidDebugBridge.getSocketAddress(), command, this, receiver, 0L, maxTimeToOutputResponse, maxTimeUnits);
    }

    @Override
    public void executeShellCommand(String command, IShellOutputReceiver receiver, long maxTimeout, long maxTimeToOutputResponse, TimeUnit maxTimeUnits) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(AndroidDebugBridge.getSocketAddress(), command, this, receiver, maxTimeout, maxTimeToOutputResponse, maxTimeUnits);
    }

    @Override
    public void runEventLogService(LogReceiver receiver) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.runEventLogService(AndroidDebugBridge.getSocketAddress(), this, receiver);
    }

    @Override
    public void runLogService(String logname, LogReceiver receiver) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.runLogService(AndroidDebugBridge.getSocketAddress(), this, logname, receiver);
    }

    @Override
    public void createForward(int localPort, int remotePort) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.createForward(AndroidDebugBridge.getSocketAddress(), this, String.format("tcp:%d", localPort), String.format("tcp:%d", remotePort));
    }

    @Override
    public void createForward(int localPort, String remoteSocketName, IDevice.DeviceUnixSocketNamespace namespace) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.createForward(AndroidDebugBridge.getSocketAddress(), this, String.format("tcp:%d", localPort), String.format("%s:%s", namespace.getType(), remoteSocketName));
    }

    @Override
    public void removeForward(int localPort, int remotePort) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.removeForward(AndroidDebugBridge.getSocketAddress(), this, String.format("tcp:%d", localPort), String.format("tcp:%d", remotePort));
    }

    @Override
    public void removeForward(int localPort, String remoteSocketName, IDevice.DeviceUnixSocketNamespace namespace) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.removeForward(AndroidDebugBridge.getSocketAddress(), this, String.format("tcp:%d", localPort), String.format("%s:%s", namespace.getType(), remoteSocketName));
    }

    Device(ClientTracker clientTracer, String serialNumber, IDevice.DeviceState deviceState) {
        this.mClientTracer = clientTracer;
        this.mSerialNumber = serialNumber;
        this.mState = deviceState;
    }

    ClientTracker getClientTracker() {
        return this.mClientTracer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasClients() {
        List<Client> list = this.mClients;
        synchronized (list) {
            return !this.mClients.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Client[] getClients() {
        List<Client> list = this.mClients;
        synchronized (list) {
            return this.mClients.toArray(new Client[this.mClients.size()]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Client getClient(String applicationName) {
        List<Client> list = this.mClients;
        synchronized (list) {
            for (Client c2 : this.mClients) {
                if (!applicationName.equals(c2.getClientData().getClientDescription())) continue;
                return c2;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addClient(Client client) {
        List<Client> list = this.mClients;
        synchronized (list) {
            this.mClients.add(client);
        }
        this.addClientInfo(client);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    List<Client> getClientList() {
        List<Client> list = this.mClients;
        synchronized (list) {
            return this.mClients;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void clearClientList() {
        List<Client> list = this.mClients;
        synchronized (list) {
            this.mClients.clear();
        }
        this.clearClientInfo();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeClient(Client client, boolean notify) {
        this.mClientTracer.trackDisconnectedClient(client);
        List<Client> list = this.mClients;
        synchronized (list) {
            this.mClients.remove(client);
        }
        if (notify) {
            AndroidDebugBridge.deviceChanged(this, 2);
        }
        this.removeClientInfo(client);
    }

    void setClientMonitoringSocket(SocketChannel socketChannel) {
        this.mSocketChannel = socketChannel;
    }

    SocketChannel getClientMonitoringSocket() {
        return this.mSocketChannel;
    }

    void update(int changeMask) {
        AndroidDebugBridge.deviceChanged(this, changeMask);
    }

    void update(Client client, int changeMask) {
        AndroidDebugBridge.clientChanged(client, changeMask);
        this.updateClientInfo(client, changeMask);
    }

    void setMountingPoint(String name, String value) {
        this.mMountPoints.put(name, value);
    }

    private void addClientInfo(Client client) {
        ClientData cd2 = client.getClientData();
        this.setClientInfo(cd2.getPid(), cd2.getClientDescription());
    }

    private void updateClientInfo(Client client, int changeMask) {
        if ((changeMask & 1) == 1) {
            this.addClientInfo(client);
        }
    }

    private void removeClientInfo(Client client) {
        int pid = client.getClientData().getPid();
        this.mClientInfo.remove(pid);
    }

    private void clearClientInfo() {
        this.mClientInfo.clear();
    }

    private void setClientInfo(int pid, String pkgName) {
        if (pkgName == null) {
            pkgName = UNKNOWN_PACKAGE;
        }
        this.mClientInfo.put(pid, pkgName);
    }

    @Override
    public String getClientName(int pid) {
        String pkgName = this.mClientInfo.get(pid);
        return pkgName == null ? UNKNOWN_PACKAGE : pkgName;
    }

    @Override
    public void pushFile(String local, String remote) throws IOException, AdbCommandRejectedException, TimeoutException, SyncException {
        block9: {
            try (SyncService sync = null;){
                String targetFileName = Device.getFileName(local);
                Log.d(targetFileName, String.format("Uploading %1$s onto device '%2$s'", targetFileName, this.getSerialNumber()));
                sync = this.getSyncService();
                if (sync != null) {
                    String message = String.format("Uploading file onto device '%1$s'", this.getSerialNumber());
                    Log.d(LOG_TAG, message);
                    sync.pushFile(local, remote, SyncService.getNullProgressMonitor());
                    break block9;
                }
                throw new IOException("Unable to open sync connection!");
            }
        }
    }

    @Override
    public void pullFile(String remote, String local) throws IOException, AdbCommandRejectedException, TimeoutException, SyncException {
        block9: {
            try (SyncService sync = null;){
                String targetFileName = Device.getFileName(remote);
                Log.d(targetFileName, String.format("Downloading %1$s from device '%2$s'", targetFileName, this.getSerialNumber()));
                sync = this.getSyncService();
                if (sync != null) {
                    String message = String.format("Downloading file from device '%1$s'", this.getSerialNumber());
                    Log.d(LOG_TAG, message);
                    sync.pullFile(remote, local, SyncService.getNullProgressMonitor());
                    break block9;
                }
                throw new IOException("Unable to open sync connection!");
            }
        }
    }

    @Override
    public void installPackage(String packageFilePath, boolean reinstall, String ... extraArgs) throws InstallException {
        this.installPackage(packageFilePath, reinstall, new InstallReceiver(), extraArgs);
    }

    @Override
    public void installPackage(String packageFilePath, boolean reinstall, InstallReceiver receiver, String ... extraArgs) throws InstallException {
        this.installPackage(packageFilePath, reinstall, receiver, 0L, INSTALL_TIMEOUT_MINUTES, TimeUnit.MINUTES, extraArgs);
    }

    @Override
    public void installPackage(String packageFilePath, boolean reinstall, InstallReceiver receiver, long maxTimeout, long maxTimeToOutputResponse, TimeUnit maxTimeUnits, String ... extraArgs) throws InstallException {
        try {
            String remoteFilePath = this.syncPackageToDevice(packageFilePath);
            this.installRemotePackage(remoteFilePath, reinstall, receiver, maxTimeout, maxTimeToOutputResponse, maxTimeUnits, extraArgs);
            this.removeRemotePackage(remoteFilePath);
        }
        catch (IOException e2) {
            throw new InstallException(e2);
        }
        catch (AdbCommandRejectedException e3) {
            throw new InstallException(e3);
        }
        catch (TimeoutException e4) {
            throw new InstallException(e4);
        }
        catch (SyncException e5) {
            throw new InstallException(e5);
        }
    }

    @Override
    public void installPackages(List<File> apks, boolean reinstall, List<String> installOptions, long timeout, TimeUnit timeoutUnit) throws InstallException {
        try {
            SplitApkInstaller.create(this, apks, reinstall, installOptions).install(timeout, timeoutUnit);
        }
        catch (InstallException e2) {
            throw e2;
        }
        catch (Exception e3) {
            throw new InstallException(e3);
        }
    }

    @Override
    public String syncPackageToDevice(String localFilePath) throws IOException, AdbCommandRejectedException, TimeoutException, SyncException {
        try (SyncService sync = null;){
            String packageFileName = Device.getFileName(localFilePath);
            String remoteFilePath = String.format("/data/local/tmp/%1$s", packageFileName);
            Log.d(packageFileName, String.format("Uploading %1$s onto device '%2$s'", packageFileName, this.getSerialNumber()));
            sync = this.getSyncService();
            if (sync == null) {
                throw new IOException("Unable to open sync connection!");
            }
            String message = String.format("Uploading file onto device '%1$s'", this.getSerialNumber());
            Log.d(LOG_TAG, message);
            sync.pushFile(localFilePath, remoteFilePath, SyncService.getNullProgressMonitor());
            String string = remoteFilePath;
            return string;
        }
    }

    private static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    @Override
    public void installRemotePackage(String remoteFilePath, boolean reinstall, String ... extraArgs) throws InstallException {
        this.installRemotePackage(remoteFilePath, reinstall, new InstallReceiver(), extraArgs);
    }

    @Override
    public void installRemotePackage(String remoteFilePath, boolean reinstall, InstallReceiver receiver, String ... extraArgs) throws InstallException {
        this.installRemotePackage(remoteFilePath, reinstall, receiver, 0L, INSTALL_TIMEOUT_MINUTES, TimeUnit.MINUTES, extraArgs);
    }

    @Override
    public void installRemotePackage(String remoteFilePath, boolean reinstall, InstallReceiver receiver, long maxTimeout, long maxTimeToOutputResponse, TimeUnit maxTimeUnits, String ... extraArgs) throws InstallException {
        try {
            StringBuilder optionString = new StringBuilder();
            if (reinstall) {
                optionString.append("-r ");
            }
            if (extraArgs != null) {
                optionString.append(Joiner.on(' ').join(extraArgs));
            }
            String cmd = String.format("pm install %1$s \"%2$s\"", optionString.toString(), remoteFilePath);
            this.executeShellCommand(cmd, receiver, maxTimeout, maxTimeToOutputResponse, maxTimeUnits);
            String error = receiver.getErrorMessage();
            if (error != null) {
                throw new InstallException(error);
            }
        }
        catch (TimeoutException e2) {
            throw new InstallException(e2);
        }
        catch (AdbCommandRejectedException e3) {
            throw new InstallException(e3);
        }
        catch (ShellCommandUnresponsiveException e4) {
            throw new InstallException(e4);
        }
        catch (IOException e5) {
            throw new InstallException(e5);
        }
    }

    @Override
    public void removeRemotePackage(String remoteFilePath) throws InstallException {
        try {
            this.executeShellCommand(String.format("rm \"%1$s\"", remoteFilePath), new NullOutputReceiver(), INSTALL_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        }
        catch (IOException e2) {
            throw new InstallException(e2);
        }
        catch (TimeoutException e3) {
            throw new InstallException(e3);
        }
        catch (AdbCommandRejectedException e4) {
            throw new InstallException(e4);
        }
        catch (ShellCommandUnresponsiveException e5) {
            throw new InstallException(e5);
        }
    }

    @Override
    public String uninstallPackage(String packageName) throws InstallException {
        try {
            InstallReceiver receiver = new InstallReceiver();
            this.executeShellCommand("pm uninstall " + packageName, receiver, INSTALL_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            return receiver.getErrorMessage();
        }
        catch (TimeoutException e2) {
            throw new InstallException(e2);
        }
        catch (AdbCommandRejectedException e3) {
            throw new InstallException(e3);
        }
        catch (ShellCommandUnresponsiveException e4) {
            throw new InstallException(e4);
        }
        catch (IOException e5) {
            throw new InstallException(e5);
        }
    }

    @Override
    public void reboot(String into) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.reboot(into, AndroidDebugBridge.getSocketAddress(), this);
    }

    @Override
    public boolean root() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException {
        if (!this.mIsRoot) {
            AdbHelper.root(AndroidDebugBridge.getSocketAddress(), this);
        }
        return this.isRoot();
    }

    @Override
    public boolean isRoot() throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        if (this.mIsRoot) {
            return true;
        }
        CollectingOutputReceiver receiver = new CollectingOutputReceiver();
        this.executeShellCommand("echo $USER_ID", receiver, 1000L, TimeUnit.MILLISECONDS);
        String userID = receiver.getOutput().trim();
        this.mIsRoot = userID.equals("0");
        return this.mIsRoot;
    }

    @Override
    public Integer getBatteryLevel() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException {
        return this.getBatteryLevel(300000L);
    }

    @Override
    public Integer getBatteryLevel(long freshnessMs) throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException {
        Future<Integer> futureBattery = this.getBattery(freshnessMs, TimeUnit.MILLISECONDS);
        try {
            return futureBattery.get();
        }
        catch (InterruptedException e2) {
            return null;
        }
        catch (ExecutionException e3) {
            return null;
        }
    }

    @Override
    public Future<Integer> getBattery() {
        return this.getBattery(5L, TimeUnit.MINUTES);
    }

    @Override
    public Future<Integer> getBattery(long freshnessTime, TimeUnit timeUnit) {
        return this.mBatteryFetcher.getBattery(freshnessTime, timeUnit);
    }

    @Override
    public List<String> getAbis() {
        String abiList = this.getProperty("ro.product.cpu.abilist");
        if (abiList != null) {
            return Lists.newArrayList(abiList.split(","));
        }
        ArrayList<String> abis = Lists.newArrayListWithExpectedSize(2);
        String abi = this.getProperty("ro.product.cpu.abi");
        if (abi != null) {
            abis.add(abi);
        }
        if ((abi = this.getProperty("ro.product.cpu.abi2")) != null) {
            abis.add(abi);
        }
        return abis;
    }

    @Override
    public int getDensity() {
        String densityValue = this.getProperty("ro.sf.lcd_density");
        if (densityValue == null) {
            densityValue = this.getProperty("qemu.sf.lcd_density");
        }
        if (densityValue != null) {
            try {
                return Integer.parseInt(densityValue);
            }
            catch (NumberFormatException e2) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public String getLanguage() {
        return this.getProperties().get("persist.sys.language");
    }

    @Override
    public String getRegion() {
        return this.getProperty("persist.sys.country");
    }

    static {
        String installTimeout = System.getenv("ADB_INSTALL_TIMEOUT");
        long time = 4L;
        if (installTimeout != null) {
            try {
                time = Long.parseLong(installTimeout);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        INSTALL_TIMEOUT_MINUTES = time;
    }
}

