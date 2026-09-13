/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.prefs.AndroidLocation;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.InvalidParameterException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmulatorConsole {
    private static final String DEFAULT_ENCODING = "ISO-8859-1";
    private static final int WAIT_TIME = 5;
    private static final int STD_TIMEOUT = 5000;
    private static final String HOST = "127.0.0.1";
    private static final String COMMAND_PING = "help\r\n";
    private static final String COMMAND_AVD_NAME = "avd name\r\n";
    private static final String COMMAND_KILL = "kill\r\n";
    private static final String COMMAND_GSM_STATUS = "gsm status\r\n";
    private static final String COMMAND_GSM_CALL = "gsm call %1$s\r\n";
    private static final String COMMAND_GSM_CANCEL_CALL = "gsm cancel %1$s\r\n";
    private static final String COMMAND_GSM_DATA = "gsm data %1$s\r\n";
    private static final String COMMAND_GSM_VOICE = "gsm voice %1$s\r\n";
    private static final String COMMAND_SMS_SEND = "sms send %1$s %2$s\r\n";
    private static final String COMMAND_NETWORK_STATUS = "network status\r\n";
    private static final String COMMAND_NETWORK_SPEED = "network speed %1$s\r\n";
    private static final String COMMAND_NETWORK_LATENCY = "network delay %1$s\r\n";
    private static final String COMMAND_GPS = "geo fix %1$f %2$f %3$f\r\n";
    private static final String COMMAND_AUTH = "auth %1$s\r\n";
    private static final String COMMAND_SCREENRECORD_START = "screenrecord start %1$s\r\n";
    private static final String COMMAND_SCREENRECORD_STOP = "screenrecord stop\r\n";
    private static final Pattern RE_KO = Pattern.compile("KO:\\s+(.*)");
    private static final String RE_AUTH_REQUIRED = "Android Console: Authentication required";
    private static final String EMULATOR_CONSOLE_AUTH_TOKEN = ".emulator_console_auth_token";
    public static final int[] MIN_LATENCIES = new int[]{0, 150, 80, 35};
    public static final int[] DOWNLOAD_SPEEDS = new int[]{0, 14400, 43200, 80000, 236800, 1920000, 14400000};
    public static final String[] NETWORK_SPEEDS = new String[]{"full", "gsm", "hscsd", "gprs", "edge", "umts", "hsdpa"};
    public static final String[] NETWORK_LATENCIES = new String[]{"none", "gprs", "edge", "umts"};
    public static final String RESULT_OK = null;
    private static final Pattern sEmulatorRegexp = Pattern.compile("emulator-(\\d+)");
    private static final Pattern sVoiceStatusRegexp = Pattern.compile("gsm\\s+voice\\s+state:\\s*([a-z]+)", 2);
    private static final Pattern sDataStatusRegexp = Pattern.compile("gsm\\s+data\\s+state:\\s*([a-z]+)", 2);
    private static final Pattern sDownloadSpeedRegexp = Pattern.compile("\\s+download\\s+speed:\\s+(\\d+)\\s+bits.*", 2);
    private static final Pattern sMinLatencyRegexp = Pattern.compile("\\s+minimum\\s+latency:\\s+(\\d+)\\s+ms", 2);
    private static final HashMap<Integer, EmulatorConsole> sEmulators = new HashMap();
    private static final String LOG_TAG = "EmulatorConsole";
    private int mPort = -1;
    private SocketChannel mSocketChannel;
    private byte[] mBuffer = new byte[1024];

    public static EmulatorConsole getConsole(IDevice d2) {
        Integer port = EmulatorConsole.getEmulatorPort(d2.getSerialNumber());
        if (port == null) {
            Log.w(LOG_TAG, "Failed to find emulator port from serial: " + d2.getSerialNumber());
            return null;
        }
        EmulatorConsole console = EmulatorConsole.retrieveConsole(port);
        if (!console.checkConnection()) {
            EmulatorConsole.removeConsole(console.mPort);
            console = null;
        }
        return console;
    }

    public static Integer getEmulatorPort(String serialNumber) {
        Matcher m3 = sEmulatorRegexp.matcher(serialNumber);
        if (m3.matches()) {
            try {
                int port = Integer.parseInt(m3.group(1));
                if (port > 0) {
                    return port;
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static EmulatorConsole retrieveConsole(int port) {
        HashMap<Integer, EmulatorConsole> hashMap = sEmulators;
        synchronized (hashMap) {
            EmulatorConsole console = sEmulators.get(port);
            if (console == null) {
                Log.v(LOG_TAG, "Creating emulator console for " + Integer.toString(port));
                console = new EmulatorConsole(port);
                sEmulators.put(port, console);
            }
            return console;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void removeConsole(int port) {
        HashMap<Integer, EmulatorConsole> hashMap = sEmulators;
        synchronized (hashMap) {
            Log.v(LOG_TAG, "Removing emulator console for " + Integer.toString(port));
            EmulatorConsole console = sEmulators.get(port);
            if (console != null) {
                console.closeConnection();
                sEmulators.remove(port);
            }
        }
    }

    private EmulatorConsole(int port) {
        this.mPort = port;
    }

    private synchronized boolean checkConnection() {
        if (this.mSocketChannel == null) {
            try {
                InetAddress hostAddr = InetAddress.getByName(HOST);
                InetSocketAddress socketAddr = new InetSocketAddress(hostAddr, this.mPort);
                this.mSocketChannel = SocketChannel.open(socketAddr);
                this.mSocketChannel.configureBlocking(false);
                String[] welcome = this.readLines();
                if (welcome[0].endsWith(RE_AUTH_REQUIRED) && RESULT_OK != this.sendAuthentication()) {
                    this.closeConnection();
                    Log.w(LOG_TAG, "Emulator console auth failed (is the emulator running as a different user?)");
                    return false;
                }
            }
            catch (IOException e2) {
                Log.w(LOG_TAG, "Failed to start Emulator console for " + Integer.toString(this.mPort));
                return false;
            }
            catch (AndroidLocation.AndroidLocationException e3) {
                Log.w(LOG_TAG, "Failed to get emulator console auth token");
                return false;
            }
        }
        return this.ping();
    }

    private synchronized boolean ping() {
        if (this.sendCommand(COMMAND_PING)) {
            return this.readLines() != null;
        }
        return false;
    }

    private synchronized void closeConnection() {
        try {
            if (this.mSocketChannel != null) {
                this.mSocketChannel.close();
            }
            this.mSocketChannel = null;
            this.mPort = -1;
        }
        catch (IOException e2) {
            Log.w(LOG_TAG, "Failed to close EmulatorConsole channel");
        }
    }

    public synchronized void kill() {
        if (this.sendCommand(COMMAND_KILL)) {
            this.close();
        }
    }

    public synchronized void close() {
        if (this.mPort == -1) {
            return;
        }
        EmulatorConsole.removeConsole(this.mPort);
    }

    public synchronized String getAvdName() {
        if (this.sendCommand(COMMAND_AVD_NAME)) {
            String[] result = this.readLines();
            if (result != null && result.length >= 2) {
                return result[result.length - 2];
            }
            Matcher m3 = RE_KO.matcher(result[result.length - 1]);
            if (m3.matches()) {
                return m3.group(1);
            }
            Log.w(LOG_TAG, "avd name result did not match expected");
            for (int i2 = 0; i2 < result.length; ++i2) {
                Log.d(LOG_TAG, result[i2]);
            }
        }
        return null;
    }

    public synchronized NetworkStatus getNetworkStatus() {
        String[] result;
        if (this.sendCommand(COMMAND_NETWORK_STATUS) && this.isValid(result = this.readLines())) {
            NetworkStatus status = new NetworkStatus();
            for (String line : result) {
                String value;
                Matcher m3 = sDownloadSpeedRegexp.matcher(line);
                if (m3.matches()) {
                    value = m3.group(1);
                    status.speed = this.getSpeedIndex(value);
                    continue;
                }
                m3 = sMinLatencyRegexp.matcher(line);
                if (!m3.matches()) continue;
                value = m3.group(1);
                status.latency = this.getLatencyIndex(value);
            }
            return status;
        }
        return null;
    }

    public synchronized GsmStatus getGsmStatus() {
        String[] result;
        if (this.sendCommand(COMMAND_GSM_STATUS) && this.isValid(result = this.readLines())) {
            GsmStatus status = new GsmStatus();
            for (String line : result) {
                String value;
                Matcher m3 = sVoiceStatusRegexp.matcher(line);
                if (m3.matches()) {
                    value = m3.group(1);
                    status.voice = GsmMode.getEnum(value.toLowerCase(Locale.US));
                    continue;
                }
                m3 = sDataStatusRegexp.matcher(line);
                if (!m3.matches()) continue;
                value = m3.group(1);
                status.data = GsmMode.getEnum(value.toLowerCase(Locale.US));
            }
            return status;
        }
        return null;
    }

    public synchronized String setGsmVoiceMode(GsmMode mode) throws InvalidParameterException {
        if (mode == GsmMode.UNKNOWN) {
            throw new InvalidParameterException();
        }
        String command = String.format(COMMAND_GSM_VOICE, mode.getTag());
        return this.processCommand(command);
    }

    public synchronized String setGsmDataMode(GsmMode mode) throws InvalidParameterException {
        if (mode == GsmMode.UNKNOWN) {
            throw new InvalidParameterException();
        }
        String command = String.format(COMMAND_GSM_DATA, mode.getTag());
        return this.processCommand(command);
    }

    public synchronized String call(String number) {
        String command = String.format(COMMAND_GSM_CALL, number);
        return this.processCommand(command);
    }

    public synchronized String cancelCall(String number) {
        String command = String.format(COMMAND_GSM_CANCEL_CALL, number);
        return this.processCommand(command);
    }

    public synchronized String sendSms(String number, String message) {
        String command = String.format(COMMAND_SMS_SEND, number, message);
        return this.processCommand(command);
    }

    public synchronized String setNetworkSpeed(int selectionIndex) {
        String command = String.format(COMMAND_NETWORK_SPEED, NETWORK_SPEEDS[selectionIndex]);
        return this.processCommand(command);
    }

    public synchronized String setNetworkLatency(int selectionIndex) {
        String command = String.format(COMMAND_NETWORK_LATENCY, NETWORK_LATENCIES[selectionIndex]);
        return this.processCommand(command);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized String sendLocation(double longitude, double latitude, double elevation) {
        try (Formatter formatter = new Formatter(Locale.US);){
            formatter.format(COMMAND_GPS, longitude, latitude, elevation);
            String string = this.processCommand(formatter.toString());
            return string;
        }
    }

    public synchronized String sendAuthentication() throws IOException, AndroidLocation.AndroidLocationException {
        File emulatorConsoleAuthTokenFile = new File(AndroidLocation.getUserHomeFolder(), EMULATOR_CONSOLE_AUTH_TOKEN);
        String authToken = Files.toString(emulatorConsoleAuthTokenFile, Charsets.UTF_8).trim();
        String command = String.format(COMMAND_AUTH, authToken);
        return this.processCommand(command);
    }

    public synchronized String startEmulatorScreenRecording(String args) {
        String command = String.format(COMMAND_SCREENRECORD_START, args);
        return this.processCommand(command);
    }

    public synchronized String stopScreenRecording() {
        return this.processCommand(COMMAND_SCREENRECORD_STOP);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean sendCommand(String command) {
        boolean result = false;
        try {
            byte[] bCommand;
            try {
                bCommand = command.getBytes(DEFAULT_ENCODING);
            }
            catch (UnsupportedEncodingException e2) {
                Log.w(LOG_TAG, "wrong encoding when sending " + command + " to " + Integer.toString(this.mPort));
                boolean bl = result;
                if (!result) {
                    EmulatorConsole.removeConsole(this.mPort);
                }
                return bl;
            }
            AdbHelper.write(this.mSocketChannel, bCommand, bCommand.length, DdmPreferences.getTimeOut());
            result = true;
        }
        catch (Exception e3) {
            Log.d(LOG_TAG, "Exception sending command " + command + " to " + Integer.toString(this.mPort));
            boolean bl = false;
            return bl;
        }
        finally {
            if (!result) {
                EmulatorConsole.removeConsole(this.mPort);
            }
        }
        return result;
    }

    private String processCommand(String command) {
        if (this.sendCommand(command)) {
            String[] result = this.readLines();
            if (result != null && result.length > 0) {
                Matcher m3 = RE_KO.matcher(result[result.length - 1]);
                if (m3.matches()) {
                    return m3.group(1);
                }
                return RESULT_OK;
            }
            return "Unable to communicate with the emulator";
        }
        return "Unable to send command to the emulator";
    }

    private String[] readLines() {
        try {
            ByteBuffer buf = ByteBuffer.wrap(this.mBuffer, 0, this.mBuffer.length);
            int numWaits = 0;
            boolean stop = false;
            while (buf.position() != buf.limit() && !stop) {
                int pos;
                int count = this.mSocketChannel.read(buf);
                if (count < 0) {
                    return null;
                }
                if (count == 0) {
                    if (numWaits * 5 > 5000) {
                        return null;
                    }
                    try {
                        Thread.sleep(5L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    ++numWaits;
                } else {
                    numWaits = 0;
                }
                if (buf.position() < 4 || !this.endsWithOK(pos = buf.position()) && !this.lastLineIsKO(pos)) continue;
                stop = true;
            }
            String msg = new String(this.mBuffer, 0, buf.position(), DEFAULT_ENCODING);
            return msg.split("\r\n");
        }
        catch (IOException e2) {
            Log.d(LOG_TAG, "Exception reading lines for " + Integer.toString(this.mPort));
            return null;
        }
    }

    private boolean endsWithOK(int currentPosition) {
        return this.mBuffer[currentPosition - 1] == 10 && this.mBuffer[currentPosition - 2] == 13 && this.mBuffer[currentPosition - 3] == 75 && this.mBuffer[currentPosition - 4] == 79;
    }

    private boolean lastLineIsKO(int currentPosition) {
        if (this.mBuffer[currentPosition - 1] != 10 || this.mBuffer[currentPosition - 2] != 13) {
            return false;
        }
        int i2 = 0;
        for (i2 = currentPosition - 3; i2 >= 0 && (this.mBuffer[i2] != 10 || i2 <= 0 || this.mBuffer[i2 - 1] != 13); --i2) {
        }
        return this.mBuffer[i2 + 1] == 75 && this.mBuffer[i2 + 2] == 79;
    }

    private boolean isValid(String[] result) {
        if (result != null && result.length > 0) {
            return !RE_KO.matcher(result[result.length - 1]).matches();
        }
        return false;
    }

    private int getLatencyIndex(String value) {
        try {
            int latency = Integer.parseInt(value);
            for (int i2 = 0; i2 < MIN_LATENCIES.length; ++i2) {
                if (MIN_LATENCIES[i2] != latency) continue;
                return i2;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return -1;
    }

    private int getSpeedIndex(String value) {
        try {
            int speed = Integer.parseInt(value);
            for (int i2 = 0; i2 < DOWNLOAD_SPEEDS.length; ++i2) {
                if (DOWNLOAD_SPEEDS[i2] != speed) continue;
                return i2;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return -1;
    }

    public static class NetworkStatus {
        public int speed = -1;
        public int latency = -1;
    }

    public static class GsmStatus {
        public GsmMode voice = GsmMode.UNKNOWN;
        public GsmMode data = GsmMode.UNKNOWN;
    }

    public static enum GsmMode {
        UNKNOWN((String)null),
        UNREGISTERED(new String[]{"unregistered", "off"}),
        HOME(new String[]{"home", "on"}),
        ROAMING("roaming"),
        SEARCHING("searching"),
        DENIED("denied");

        private final String[] tags;

        private GsmMode(String tag) {
            this.tags = tag != null ? new String[]{tag} : new String[0];
        }

        private GsmMode(String[] tags) {
            this.tags = tags;
        }

        public static GsmMode getEnum(String tag) {
            for (GsmMode mode : GsmMode.values()) {
                for (String t3 : mode.tags) {
                    if (!t3.equals(tag)) continue;
                    return mode;
                }
            }
            return UNKNOWN;
        }

        public String getTag() {
            if (this.tags.length > 0) {
                return this.tags[0];
            }
            return null;
        }
    }
}

