/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.Client;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.IShellEnabledDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.InstallReceiver;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ScreenRecorderOptions;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.SyncService;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.log.LogReceiver;
import com.android.sdklib.AndroidVersion;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface IDevice
extends IShellEnabledDevice {
    public static final String PROP_BUILD_VERSION = "ro.build.version.release";
    public static final String PROP_BUILD_API_LEVEL = "ro.build.version.sdk";
    public static final String PROP_BUILD_CODENAME = "ro.build.version.codename";
    public static final String PROP_BUILD_TAGS = "ro.build.tags";
    public static final String PROP_BUILD_TYPE = "ro.build.type";
    public static final String PROP_DEVICE_MODEL = "ro.product.model";
    public static final String PROP_DEVICE_MANUFACTURER = "ro.product.manufacturer";
    public static final String PROP_DEVICE_CPU_ABI_LIST = "ro.product.cpu.abilist";
    public static final String PROP_DEVICE_CPU_ABI = "ro.product.cpu.abi";
    public static final String PROP_DEVICE_CPU_ABI2 = "ro.product.cpu.abi2";
    public static final String PROP_BUILD_CHARACTERISTICS = "ro.build.characteristics";
    public static final String PROP_DEVICE_DENSITY = "ro.sf.lcd_density";
    public static final String PROP_DEVICE_EMULATOR_DENSITY = "qemu.sf.lcd_density";
    public static final String PROP_DEVICE_LANGUAGE = "persist.sys.language";
    public static final String PROP_DEVICE_REGION = "persist.sys.country";
    public static final String PROP_DEBUGGABLE = "ro.debuggable";
    public static final String FIRST_EMULATOR_SN = "emulator-5554";
    public static final int CHANGE_STATE = 1;
    public static final int CHANGE_CLIENT_LIST = 2;
    public static final int CHANGE_BUILD_INFO = 4;
    @Deprecated
    public static final String PROP_BUILD_VERSION_NUMBER = "ro.build.version.sdk";
    public static final String MNT_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    public static final String MNT_ROOT = "ANDROID_ROOT";
    public static final String MNT_DATA = "ANDROID_DATA";

    public String getSerialNumber();

    public String getAvdName();

    public DeviceState getState();

    @Deprecated
    public Map<String, String> getProperties();

    @Deprecated
    public int getPropertyCount();

    public String getProperty(String var1);

    public boolean arePropertiesSet();

    @Deprecated
    public String getPropertySync(String var1) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    @Deprecated
    public String getPropertyCacheOrSync(String var1) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public boolean supportsFeature(Feature var1);

    public boolean supportsFeature(HardwareFeature var1);

    public String getMountPoint(String var1);

    public boolean isOnline();

    public boolean isEmulator();

    public boolean isOffline();

    public boolean isBootLoader();

    public boolean hasClients();

    public Client[] getClients();

    public Client getClient(String var1);

    public SyncService getSyncService() throws TimeoutException, AdbCommandRejectedException, IOException;

    public FileListingService getFileListingService();

    public RawImage getScreenshot() throws TimeoutException, AdbCommandRejectedException, IOException;

    public RawImage getScreenshot(long var1, TimeUnit var3) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void startScreenRecorder(String var1, ScreenRecorderOptions var2, IShellOutputReceiver var3) throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException;

    @Deprecated
    public void executeShellCommand(String var1, IShellOutputReceiver var2, int var3) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public void executeShellCommand(String var1, IShellOutputReceiver var2) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException;

    public void runEventLogService(LogReceiver var1) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void runLogService(String var1, LogReceiver var2) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void createForward(int var1, int var2) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void createForward(int var1, String var2, DeviceUnixSocketNamespace var3) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void removeForward(int var1, int var2) throws TimeoutException, AdbCommandRejectedException, IOException;

    public void removeForward(int var1, String var2, DeviceUnixSocketNamespace var3) throws TimeoutException, AdbCommandRejectedException, IOException;

    public String getClientName(int var1);

    public void pushFile(String var1, String var2) throws IOException, AdbCommandRejectedException, TimeoutException, SyncException;

    public void pullFile(String var1, String var2) throws IOException, AdbCommandRejectedException, TimeoutException, SyncException;

    public void installPackage(String var1, boolean var2, String ... var3) throws InstallException;

    public void installPackage(String var1, boolean var2, InstallReceiver var3, String ... var4) throws InstallException;

    public void installPackage(String var1, boolean var2, InstallReceiver var3, long var4, long var6, TimeUnit var8, String ... var9) throws InstallException;

    public void installPackages(List<File> var1, boolean var2, List<String> var3, long var4, TimeUnit var6) throws InstallException;

    public String syncPackageToDevice(String var1) throws TimeoutException, AdbCommandRejectedException, IOException, SyncException;

    public void installRemotePackage(String var1, boolean var2, String ... var3) throws InstallException;

    public void installRemotePackage(String var1, boolean var2, InstallReceiver var3, String ... var4) throws InstallException;

    public void installRemotePackage(String var1, boolean var2, InstallReceiver var3, long var4, long var6, TimeUnit var8, String ... var9) throws InstallException;

    public void removeRemotePackage(String var1) throws InstallException;

    public String uninstallPackage(String var1) throws InstallException;

    public void reboot(String var1) throws TimeoutException, AdbCommandRejectedException, IOException;

    public boolean root() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException;

    public boolean isRoot() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException;

    @Deprecated
    public Integer getBatteryLevel() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException;

    @Deprecated
    public Integer getBatteryLevel(long var1) throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException;

    public Future<Integer> getBattery();

    public Future<Integer> getBattery(long var1, TimeUnit var3);

    public List<String> getAbis();

    public int getDensity();

    public String getLanguage();

    public String getRegion();

    public AndroidVersion getVersion();

    public static enum DeviceUnixSocketNamespace {
        ABSTRACT("localabstract"),
        FILESYSTEM("localfilesystem"),
        RESERVED("localreserved");

        private String mType;

        private DeviceUnixSocketNamespace(String type) {
            this.mType = type;
        }

        String getType() {
            return this.mType;
        }
    }

    public static enum DeviceState {
        BOOTLOADER("bootloader"),
        OFFLINE("offline"),
        ONLINE("device"),
        RECOVERY("recovery"),
        SIDELOAD("sideload"),
        UNAUTHORIZED("unauthorized"),
        DISCONNECTED("disconnected");

        private String mState;

        private DeviceState(String state) {
            this.mState = state;
        }

        public static DeviceState getState(String state) {
            for (DeviceState deviceState : DeviceState.values()) {
                if (!deviceState.mState.equals(state)) continue;
                return deviceState;
            }
            return null;
        }

        public String getState() {
            return this.mState;
        }
    }

    public static enum HardwareFeature {
        WATCH("watch"),
        EMBEDDED("embedded"),
        TV("tv");

        private final String mCharacteristic;

        private HardwareFeature(String characteristic) {
            this.mCharacteristic = characteristic;
        }

        public String getCharacteristic() {
            return this.mCharacteristic;
        }
    }

    public static enum Feature {
        SCREEN_RECORD,
        PROCSTATS;

    }
}

