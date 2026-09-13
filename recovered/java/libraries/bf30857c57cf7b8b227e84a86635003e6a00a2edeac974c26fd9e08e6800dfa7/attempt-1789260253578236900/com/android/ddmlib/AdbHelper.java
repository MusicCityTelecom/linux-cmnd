/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.Device;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.Log;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.log.LogReceiver;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

final class AdbHelper {
    static final int WAIT_TIME = 5;
    static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private AdbHelper() {
    }

    public static SocketChannel open(InetSocketAddress adbSockAddr, Device device, int devicePort) throws IOException, TimeoutException, AdbCommandRejectedException {
        SocketChannel adbChan = SocketChannel.open(adbSockAddr);
        try {
            adbChan.socket().setTcpNoDelay(true);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            byte[] req = AdbHelper.createAdbForwardRequest(null, devicePort);
            AdbHelper.write(adbChan, req);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                throw new AdbCommandRejectedException(resp.message);
            }
            adbChan.configureBlocking(true);
        }
        catch (TimeoutException e2) {
            adbChan.close();
            throw e2;
        }
        catch (IOException e3) {
            adbChan.close();
            throw e3;
        }
        catch (AdbCommandRejectedException e4) {
            adbChan.close();
            throw e4;
        }
        return adbChan;
    }

    public static SocketChannel createPassThroughConnection(InetSocketAddress adbSockAddr, Device device, int pid) throws TimeoutException, AdbCommandRejectedException, IOException {
        SocketChannel adbChan = SocketChannel.open(adbSockAddr);
        try {
            adbChan.socket().setTcpNoDelay(true);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            byte[] req = AdbHelper.createJdwpForwardRequest(pid);
            AdbHelper.write(adbChan, req);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                throw new AdbCommandRejectedException(resp.message);
            }
            adbChan.configureBlocking(true);
        }
        catch (TimeoutException e2) {
            adbChan.close();
            throw e2;
        }
        catch (IOException e3) {
            adbChan.close();
            throw e3;
        }
        catch (AdbCommandRejectedException e4) {
            adbChan.close();
            throw e4;
        }
        return adbChan;
    }

    private static byte[] createAdbForwardRequest(String addrStr, int port) {
        String reqStr = addrStr == null ? "tcp:" + port : "tcp:" + port + ":" + addrStr;
        return AdbHelper.formAdbRequest(reqStr);
    }

    private static byte[] createJdwpForwardRequest(int pid) {
        String reqStr = String.format("jdwp:%1$d", pid);
        return AdbHelper.formAdbRequest(reqStr);
    }

    public static byte[] formAdbRequest(String req) {
        String resultStr = String.format("%04X%s", req.length(), req);
        byte[] result = resultStr.getBytes(DEFAULT_CHARSET);
        assert (result.length == req.length() + 4);
        return result;
    }

    static AdbResponse readAdbResponse(SocketChannel chan, boolean readDiagString) throws TimeoutException, IOException {
        AdbResponse resp;
        block6: {
            resp = new AdbResponse();
            byte[] reply = new byte[4];
            AdbHelper.read(chan, reply);
            if (AdbHelper.isOkay(reply)) {
                resp.okay = true;
            } else {
                readDiagString = true;
                resp.okay = false;
            }
            try {
                int len;
                if (!readDiagString) break block6;
                byte[] lenBuf = new byte[4];
                AdbHelper.read(chan, lenBuf);
                String lenStr = AdbHelper.replyToString(lenBuf);
                try {
                    len = Integer.parseInt(lenStr, 16);
                }
                catch (NumberFormatException nfe) {
                    Log.w("ddms", "Expected digits, got '" + lenStr + "': " + lenBuf[0] + " " + lenBuf[1] + " " + lenBuf[2] + " " + lenBuf[3]);
                    Log.w("ddms", "reply was " + AdbHelper.replyToString(reply));
                    break block6;
                }
                byte[] msg = new byte[len];
                AdbHelper.read(chan, msg);
                resp.message = AdbHelper.replyToString(msg);
                Log.v("ddms", "Got reply '" + AdbHelper.replyToString(reply) + "', diag='" + resp.message + "'");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return resp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static RawImage getFrameBuffer(InetSocketAddress adbSockAddr, Device device, long timeout, TimeUnit unit) throws TimeoutException, AdbCommandRejectedException, IOException {
        RawImage imageParams = new RawImage();
        byte[] request = AdbHelper.formAdbRequest("framebuffer:");
        byte[] nudge = new byte[]{0};
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            AdbHelper.write(adbChan, request);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                throw new AdbCommandRejectedException(resp.message);
            }
            byte[] reply = new byte[4];
            AdbHelper.read(adbChan, reply);
            ByteBuffer buf = ByteBuffer.wrap(reply);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            int version = buf.getInt();
            int headerSize = RawImage.getHeaderSize(version);
            reply = new byte[headerSize * 4];
            AdbHelper.read(adbChan, reply);
            buf = ByteBuffer.wrap(reply);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            if (!imageParams.readHeader(version, buf)) {
                Log.e("Screenshot", "Unsupported protocol: " + version);
                RawImage rawImage = null;
                return rawImage;
            }
            Log.d("ddms", "image params: bpp=" + imageParams.bpp + ", size=" + imageParams.size + ", width=" + imageParams.width + ", height=" + imageParams.height);
            AdbHelper.write(adbChan, nudge);
            reply = new byte[imageParams.size];
            AdbHelper.read(adbChan, reply, imageParams.size, unit.toMillis(timeout));
            imageParams.data = reply;
        }
        return imageParams;
    }

    @Deprecated
    static void executeRemoteCommand(InetSocketAddress adbSockAddr, String command, IDevice device, IShellOutputReceiver rcvr, int maxTimeToOutputResponse) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(adbSockAddr, command, device, rcvr, maxTimeToOutputResponse, TimeUnit.MILLISECONDS);
    }

    static void executeRemoteCommand(InetSocketAddress adbSockAddr, String command, IDevice device, IShellOutputReceiver rcvr, long maxTimeout, long maxTimeToOutputResponse, TimeUnit maxTimeUnits) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(adbSockAddr, AdbService.SHELL, command, device, rcvr, maxTimeout, maxTimeToOutputResponse, maxTimeUnits, null);
    }

    static void executeRemoteCommand(InetSocketAddress adbSockAddr, String command, IDevice device, IShellOutputReceiver rcvr, long maxTimeToOutputResponse, TimeUnit maxTimeUnits) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(adbSockAddr, AdbService.SHELL, command, device, rcvr, maxTimeToOutputResponse, maxTimeUnits, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void executeRemoteCommand(InetSocketAddress adbSockAddr, AdbService adbService, String command, IDevice device, IShellOutputReceiver rcvr, long maxTimeout, long maxTimeToOutputResponse, TimeUnit maxTimeUnits, InputStream is) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        block21: {
            long maxTimeToOutputMs = 0L;
            if (maxTimeToOutputResponse > 0L) {
                if (maxTimeUnits == null) {
                    throw new NullPointerException("Time unit must not be null for non-zero max.");
                }
                maxTimeToOutputMs = maxTimeUnits.toMillis(maxTimeToOutputResponse);
            }
            long maxTimeoutMs = 0L;
            if (maxTimeout > 0L) {
                if (maxTimeUnits == null) {
                    throw new NullPointerException("Time unit must not be null for non-zero max.");
                }
                maxTimeoutMs = maxTimeUnits.toMillis(maxTimeout);
            }
            Log.v("ddms", "execute: running " + command);
            SocketChannel adbChan = null;
            try {
                long startTime = System.currentTimeMillis();
                adbChan = SocketChannel.open(adbSockAddr);
                adbChan.configureBlocking(false);
                AdbHelper.setDevice(adbChan, device);
                byte[] request = AdbHelper.formAdbRequest(adbService.name().toLowerCase() + ":" + command);
                AdbHelper.write(adbChan, request);
                AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
                if (!resp.okay) {
                    Log.e("ddms", "ADB rejected shell command (" + command + "): " + resp.message);
                    throw new AdbCommandRejectedException(resp.message);
                }
                byte[] data = new byte[16384];
                if (is != null) {
                    int read;
                    while ((read = is.read(data)) != -1) {
                        ByteBuffer buf = ByteBuffer.wrap(data, 0, read);
                        int written = 0;
                        while (buf.hasRemaining()) {
                            written += adbChan.write(buf);
                        }
                        if (written == read) continue;
                        Log.e("ddms", "ADB write inconsistency, wrote " + written + "expected " + read);
                        throw new AdbCommandRejectedException("write failed");
                    }
                }
                ByteBuffer buf = ByteBuffer.wrap(data);
                buf.clear();
                long timeToResponseCount = 0L;
                do {
                    if (rcvr != null && rcvr.isCancelled()) {
                        Log.v("ddms", "execute: cancelled");
                        break block21;
                    }
                    int count = adbChan.read(buf);
                    if (count < 0) {
                        rcvr.flush();
                        Log.v("ddms", "execute '" + command + "' on '" + device + "' : EOF hit. Read: " + count);
                        break block21;
                    }
                    if (count == 0) {
                        try {
                            int wait = 25;
                            if (maxTimeToOutputMs > 0L && (timeToResponseCount += (long)wait) > maxTimeToOutputMs) {
                                throw new ShellCommandUnresponsiveException();
                            }
                            Thread.sleep(wait);
                        }
                        catch (InterruptedException e2) {
                            Thread.currentThread().interrupt();
                            throw new TimeoutException("executeRemoteCommand interrupted with immediate timeout via interruption.");
                        }
                    } else {
                        timeToResponseCount = 0L;
                        if (rcvr != null) {
                            rcvr.addOutput(buf.array(), buf.arrayOffset(), buf.position());
                        }
                        buf.rewind();
                    }
                } while (maxTimeoutMs <= 0L || System.currentTimeMillis() - startTime <= maxTimeoutMs);
                throw new TimeoutException(String.format("executeRemoteCommand timed out after %sms", maxTimeoutMs));
            }
            finally {
                if (adbChan != null) {
                    adbChan.close();
                }
                Log.v("ddms", "execute: returning");
            }
        }
    }

    static void executeRemoteCommand(InetSocketAddress adbSockAddr, AdbService adbService, String command, IDevice device, IShellOutputReceiver rcvr, long maxTimeToOutputResponse, TimeUnit maxTimeUnits, InputStream is) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        AdbHelper.executeRemoteCommand(adbSockAddr, adbService, command, device, rcvr, 0L, maxTimeToOutputResponse, maxTimeUnits, is);
    }

    public static void runEventLogService(InetSocketAddress adbSockAddr, Device device, LogReceiver rcvr) throws TimeoutException, AdbCommandRejectedException, IOException {
        AdbHelper.runLogService(adbSockAddr, device, "events", rcvr);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void runLogService(InetSocketAddress adbSockAddr, Device device, String logName, LogReceiver rcvr) throws TimeoutException, AdbCommandRejectedException, IOException {
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            byte[] request = AdbHelper.formAdbRequest("log:" + logName);
            AdbHelper.write(adbChan, request);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                throw new AdbCommandRejectedException(resp.message);
            }
            byte[] data = new byte[16384];
            ByteBuffer buf = ByteBuffer.wrap(data);
            while (rcvr == null || !rcvr.isCancelled()) {
                int count = adbChan.read(buf);
                if (count < 0) {
                    break;
                }
                if (count == 0) {
                    try {
                        Thread.sleep(25L);
                    }
                    catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                        throw new TimeoutException("runLogService interrupted with immediate timeout via interruption.");
                    }
                }
                if (rcvr != null) {
                    rcvr.parseNewData(buf.array(), buf.arrayOffset(), buf.position());
                }
                buf.rewind();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createForward(InetSocketAddress adbSockAddr, Device device, String localPortSpec, String remotePortSpec) throws TimeoutException, AdbCommandRejectedException, IOException {
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            byte[] request = AdbHelper.formAdbRequest(String.format("host-serial:%1$s:forward:%2$s;%3$s", device.getSerialNumber(), localPortSpec, remotePortSpec));
            AdbHelper.write(adbChan, request);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                Log.w("create-forward", "Error creating forward: " + resp.message);
                throw new AdbCommandRejectedException(resp.message);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeForward(InetSocketAddress adbSockAddr, Device device, String localPortSpec, String remotePortSpec) throws TimeoutException, AdbCommandRejectedException, IOException {
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            byte[] request = AdbHelper.formAdbRequest(String.format("host-serial:%1$s:killforward:%2$s", device.getSerialNumber(), localPortSpec));
            AdbHelper.write(adbChan, request);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                Log.w("remove-forward", "Error creating forward: " + resp.message);
                throw new AdbCommandRejectedException(resp.message);
            }
        }
    }

    static boolean isOkay(byte[] reply) {
        return reply[0] == 79 && reply[1] == 75 && reply[2] == 65 && reply[3] == 89;
    }

    static String replyToString(byte[] reply) {
        return new String(reply, DEFAULT_CHARSET);
    }

    static void read(SocketChannel chan, byte[] data) throws TimeoutException, IOException {
        AdbHelper.read(chan, data, -1, DdmPreferences.getTimeOut());
    }

    static void read(SocketChannel chan, byte[] data, int length, long timeout) throws TimeoutException, IOException {
        ByteBuffer buf = ByteBuffer.wrap(data, 0, length != -1 ? length : data.length);
        int numWaits = 0;
        while (buf.position() != buf.limit()) {
            int count = chan.read(buf);
            if (count < 0) {
                Log.d("ddms", "read: channel EOF");
                throw new IOException("EOF");
            }
            if (count == 0) {
                if (timeout != 0L && (long)(numWaits * 5) > timeout) {
                    Log.d("ddms", "read: timeout");
                    throw new TimeoutException();
                }
                try {
                    Thread.sleep(5L);
                }
                catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                    throw new TimeoutException("Read interrupted with immediate timeout via interruption.");
                }
                ++numWaits;
                continue;
            }
            numWaits = 0;
        }
    }

    static void write(SocketChannel chan, byte[] data) throws TimeoutException, IOException {
        AdbHelper.write(chan, data, -1, DdmPreferences.getTimeOut());
    }

    static void write(SocketChannel chan, byte[] data, int length, int timeout) throws TimeoutException, IOException {
        ByteBuffer buf = ByteBuffer.wrap(data, 0, length != -1 ? length : data.length);
        int numWaits = 0;
        while (buf.position() != buf.limit()) {
            int count = chan.write(buf);
            if (count < 0) {
                Log.d("ddms", "write: channel EOF");
                throw new IOException("channel EOF");
            }
            if (count == 0) {
                if (timeout != 0 && numWaits * 5 > timeout) {
                    Log.d("ddms", "write: timeout");
                    throw new TimeoutException();
                }
                try {
                    Thread.sleep(5L);
                }
                catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                    throw new TimeoutException("Write interrupted with immediate timeout via interruption.");
                }
                ++numWaits;
                continue;
            }
            numWaits = 0;
        }
    }

    static void setDevice(SocketChannel adbChan, IDevice device) throws TimeoutException, AdbCommandRejectedException, IOException {
        if (device != null) {
            String msg = "host:transport:" + device.getSerialNumber();
            byte[] device_query = AdbHelper.formAdbRequest(msg);
            AdbHelper.write(adbChan, device_query);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                throw new AdbCommandRejectedException(resp.message, true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void reboot(String into, InetSocketAddress adbSockAddr, Device device) throws TimeoutException, AdbCommandRejectedException, IOException {
        byte[] request = into == null ? AdbHelper.formAdbRequest("reboot:") : AdbHelper.formAdbRequest("reboot:" + into);
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            AdbHelper.write(adbChan, request);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void root(InetSocketAddress adbSockAddr, Device device) throws TimeoutException, AdbCommandRejectedException, IOException {
        byte[] request = AdbHelper.formAdbRequest("root:");
        try (SocketChannel adbChan = null;){
            adbChan = SocketChannel.open(adbSockAddr);
            adbChan.configureBlocking(false);
            AdbHelper.setDevice(adbChan, device);
            AdbHelper.write(adbChan, request);
            AdbResponse resp = AdbHelper.readAdbResponse(adbChan, false);
            if (!resp.okay) {
                Log.w("root", "Error setting root: " + resp.message);
                throw new AdbCommandRejectedException(resp.message);
            }
        }
    }

    public static enum AdbService {
        SHELL,
        EXEC;

    }

    static class AdbResponse {
        public boolean okay;
        public String message = "";
    }
}

