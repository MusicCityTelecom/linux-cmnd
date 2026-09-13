/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.SocketClientCallback;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClientHelper {
    private static final Logger LOG = LoggerFactory.getLogger(SocketClientHelper.class);
    public static final int BUFFER_SIZE = 1024;
    private static SocketClientHelper sClientHelper;
    private static ConcurrentMap<String, SocketClient> mClientMap;

    private SocketClientHelper() {
    }

    public static synchronized SocketClientHelper getInstance() {
        if (null == sClientHelper) {
            sClientHelper = new SocketClientHelper();
        }
        return sClientHelper;
    }

    public static SocketClient createClient(String address, int port, SocketClientCallback callback) throws IOException {
        SocketClientHelper.removeClient(address, port);
        SocketClient client = new SocketClient(address, port, callback);
        client.connect();
        mClientMap.put(address + port, client);
        return client;
    }

    public static void removeClient(String address, int port) {
        SocketClient client = (SocketClient)mClientMap.get(address + port);
        if (client != null) {
            client.close();
            mClientMap.remove(address + port);
        }
    }

    public synchronized void sendData(String address, int port, byte[] data) {
        SocketClient client = (SocketClient)mClientMap.get(address + port);
        if (client != null) {
            client.sendData(data);
        }
    }

    public static boolean isClientAlive(String address, int port) {
        SocketClient client = (SocketClient)mClientMap.get(address + port);
        return null != client && client.isAlive();
    }

    static {
        mClientMap = new ConcurrentHashMap<String, SocketClient>();
    }

    public static class SocketBuffer {
        private static final byte START_FLAG = 2;
        private static final byte END_FLAG = 3;
        private List<Byte> bufferList = new ArrayList<Byte>();

        public void add(byte[] data) {
            for (int i = 0; i < data.length; ++i) {
                this.bufferList.add(data[i]);
            }
        }

        public void removeToIndex(int index) {
            for (int i = index; i >= 0; --i) {
                if (i >= this.bufferList.size()) continue;
                this.bufferList.remove(i);
            }
        }

        public boolean extractMessage(List<Byte> out) {
            boolean foundStart = false;
            out.clear();
            for (int i = 0; i < this.bufferList.size(); ++i) {
                byte b = this.bufferList.get(i);
                if (b == 2) {
                    foundStart = true;
                }
                if (!foundStart) continue;
                out.add(b);
                if (b != 3) continue;
                this.removeToIndex(i);
                return true;
            }
            out.clear();
            return false;
        }
    }

    public static class SocketClient
    implements Runnable {
        private final String address;
        private final int port;
        private final SocketClientCallback callback;
        private Socket socket;
        private SocketBuffer buffer = new SocketBuffer();

        SocketClient(String address, int port, SocketClientCallback callback) {
            this.address = address;
            this.port = port;
            this.callback = callback;
        }

        public boolean sendData(byte[] data) {
            try {
                OutputStream os = this.socket.getOutputStream();
                os.write(data);
                os.flush();
                this.onSent(data);
                return true;
            }
            catch (Exception e) {
                this.onError(e);
                LOG.error(e.getMessage(), e);
                return false;
            }
        }

        public boolean isAlive() {
            return this.socket != null && !this.socket.isClosed() && this.socket.isConnected();
        }

        public void close() {
            if (this.socket != null) {
                try {
                    this.socket.close();
                }
                catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

        public void connect() throws IOException {
            this.socket = new Socket(this.address, this.port);
            this.socket.setKeepAlive(true);
            LOG.info("Client {} connected to server", (Object)this.socket);
            this.onConnected();
            new Thread(this).start();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                InputStream is = this.socket.getInputStream();
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = is.read(b)) != -1) {
                    this.handleReceivedData(Arrays.copyOfRange(b, 0, len));
                    Thread.sleep(100L);
                }
            }
            catch (Exception e) {
                this.onError(e);
                LOG.error(e.getMessage(), e);
            }
            finally {
                this.close();
                this.onDisconnected();
            }
        }

        public void onError(Throwable e) {
            if (this.callback != null) {
                this.callback.onError(e);
            }
        }

        public void onConnected() {
            if (this.callback != null) {
                this.callback.onConnected();
            }
        }

        public void handleReceivedData(byte[] data) {
            try {
                this.buffer.add(data);
                ArrayList<Byte> out = new ArrayList<Byte>();
                while (this.buffer.extractMessage(out)) {
                    if (this.callback == null) continue;
                    Byte[] bytes = out.toArray(new Byte[out.size()]);
                    this.callback.onReceived(ArrayUtils.toPrimitive(bytes));
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

        public static String getHexBytes(byte[] array) {
            StringBuilder sb = new StringBuilder();
            byte[] byArray = array;
            int n = byArray.length;
            for (int i = 0; i < n; ++i) {
                Byte b = byArray[i];
                sb.append(String.format("0x%02X ", b));
            }
            return sb.toString();
        }

        public void onSent(byte[] request) {
            if (this.callback != null) {
                this.callback.onSent(request);
            }
        }

        public void onDisconnected() {
            if (this.callback != null) {
                this.callback.onDisconnected();
            }
        }
    }
}

