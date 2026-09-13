/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Client;
import com.android.ddmlib.DdmJdwpExtension;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.Debugger;
import com.android.ddmlib.JdwpPacket;
import com.android.ddmlib.Log;
import com.android.ddmlib.jdwp.JdwpExtension;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.BufferOverflowException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.NotYetBoundException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

final class MonitorThread
extends Thread {
    private final DdmJdwpExtension mDdmJdwpExtension;
    private volatile boolean mQuit = false;
    private final ArrayList<Client> mClientList = new ArrayList();
    private Selector mSelector;
    private final List<JdwpExtension> mJdwpExtensions;
    private ServerSocketChannel mDebugSelectedChan;
    private int mNewDebugSelectedPort = DdmPreferences.getSelectedDebugPort();
    private int mDebugSelectedPort = -1;
    private Client mSelectedClient = null;
    private static MonitorThread sInstance;

    private MonitorThread() {
        super("Monitor");
        this.mDdmJdwpExtension = new DdmJdwpExtension();
        this.mJdwpExtensions = new LinkedList<JdwpExtension>();
        this.mJdwpExtensions.add(this.mDdmJdwpExtension);
    }

    static MonitorThread createInstance() {
        sInstance = new MonitorThread();
        return sInstance;
    }

    static MonitorThread getInstance() {
        return sInstance;
    }

    synchronized void setDebugSelectedPort(int port) throws IllegalStateException {
        if (sInstance == null) {
            return;
        }
        if (!AndroidDebugBridge.getClientSupport()) {
            return;
        }
        if (this.mDebugSelectedChan != null) {
            Log.d("ddms", "Changing debug-selected port to " + port);
            this.mNewDebugSelectedPort = port;
            this.wakeup();
        } else {
            this.mNewDebugSelectedPort = port;
        }
    }

    synchronized void setSelectedClient(Client selectedClient) {
        if (sInstance == null) {
            return;
        }
        if (this.mSelectedClient != selectedClient) {
            Client oldClient = this.mSelectedClient;
            this.mSelectedClient = selectedClient;
            if (oldClient != null) {
                oldClient.update(4);
            }
            if (this.mSelectedClient != null) {
                this.mSelectedClient.update(4);
            }
        }
    }

    Client getSelectedClient() {
        return this.mSelectedClient;
    }

    boolean getRetryOnBadHandshake() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Client[] getClients() {
        ArrayList<Client> arrayList = this.mClientList;
        synchronized (arrayList) {
            return this.mClientList.toArray(new Client[this.mClientList.size()]);
        }
    }

    synchronized void registerChunkHandler(int type, ChunkHandler handler) {
        if (sInstance == null) {
            return;
        }
        this.mDdmJdwpExtension.registerHandler(type, handler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Log.d("ddms", "Monitor is up");
        try {
            this.mSelector = Selector.open();
        }
        catch (IOException ioe) {
            Log.logAndDisplay(Log.LogLevel.ERROR, "ddms", "Failed to initialize Monitor Thread: " + ioe.getMessage());
            return;
        }
        while (!this.mQuit) {
            int count;
            ArrayList<Client> ioe = this.mClientList;
            synchronized (ioe) {
            }
            try {
                if (AndroidDebugBridge.getClientSupport() && (this.mDebugSelectedChan == null || this.mNewDebugSelectedPort != this.mDebugSelectedPort) && this.mNewDebugSelectedPort != -1 && this.reopenDebugSelectedPort()) {
                    this.mDebugSelectedPort = this.mNewDebugSelectedPort;
                }
            }
            catch (IOException ioe2) {
                Log.e("ddms", "Failed to reopen debug port for Selected Client to: " + this.mNewDebugSelectedPort);
                Log.e("ddms", ioe2);
                this.mNewDebugSelectedPort = this.mDebugSelectedPort;
            }
            try {
                count = this.mSelector.select();
            }
            catch (IOException ioe2) {
                ioe2.printStackTrace();
                continue;
            }
            catch (CancelledKeyException cke) {
            }
            try {
                if (count == 0) continue;
                Set<SelectionKey> keys2 = this.mSelector.selectedKeys();
                Iterator<SelectionKey> iter = keys2.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    try {
                        if (key.attachment() instanceof Client) {
                            this.processClientActivity(key);
                            continue;
                        }
                        if (key.attachment() instanceof Debugger) {
                            this.processDebuggerActivity(key);
                            continue;
                        }
                        if (key.attachment() instanceof MonitorThread) {
                            this.processDebugSelectedActivity(key);
                            continue;
                        }
                        Log.e("ddms", "unknown activity key");
                    }
                    catch (Exception e2) {
                        Log.e("ddms", "Exception during activity from Selector.");
                        Log.e("ddms", e2);
                    }
                }
            }
            catch (Exception e3) {
                Log.e("ddms", "Exception MonitorThread.run()");
                Log.e("ddms", e3);
            }
        }
    }

    int getDebugSelectedPort() {
        return this.mDebugSelectedPort;
    }

    private void processClientActivity(SelectionKey key) {
        Client client = (Client)key.attachment();
        try {
            if (!key.isReadable() || !key.isValid()) {
                Log.d("ddms", "Invalid key from " + client + ". Dropping client.");
                this.dropClient(client, true);
                return;
            }
            client.read();
            JdwpPacket packet = client.getJdwpPacket();
            while (packet != null) {
                packet.log("Client: received jdwp packet");
                client.incoming(packet, client.getDebugger());
                packet.consume();
                packet = client.getJdwpPacket();
            }
        }
        catch (CancelledKeyException e2) {
            this.dropClient(client, true);
        }
        catch (IOException ex) {
            this.dropClient(client, true);
        }
        catch (Exception ex) {
            Log.e("ddms", ex);
            this.dropClient(client, true);
            if (ex instanceof BufferOverflowException) {
                Log.w("ddms", "Client data packet exceeded maximum buffer size " + client);
            }
            Log.e("ddms", ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void dropClient(Client client, boolean notify) {
        if (sInstance == null) {
            return;
        }
        ArrayList<Client> arrayList = this.mClientList;
        synchronized (arrayList) {
            if (!this.mClientList.remove(client)) {
                return;
            }
        }
        client.close(notify);
        this.mDdmJdwpExtension.broadcast(DdmJdwpExtension.Event.CLIENT_DISCONNECTED, client);
        this.wakeup();
    }

    synchronized void dropClients(Collection<? extends Client> clients, boolean notify) {
        for (Client client : clients) {
            this.dropClient(client, notify);
        }
    }

    private void processDebuggerActivity(SelectionKey key) {
        Debugger dbg = (Debugger)key.attachment();
        try {
            if (key.isAcceptable()) {
                try {
                    this.acceptNewDebugger(dbg, null);
                }
                catch (IOException ioe) {
                    Log.w("ddms", "debugger accept() failed");
                    ioe.printStackTrace();
                }
            } else if (key.isReadable()) {
                this.processDebuggerData(key);
            } else {
                Log.d("ddm-debugger", "key in unknown state");
            }
        }
        catch (CancelledKeyException cancelledKeyException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void acceptNewDebugger(Debugger dbg, ServerSocketChannel acceptChan) throws IOException {
        ArrayList<Client> arrayList = this.mClientList;
        synchronized (arrayList) {
            SocketChannel chan = acceptChan == null ? dbg.accept() : dbg.accept(acceptChan);
            if (chan != null) {
                chan.socket().setTcpNoDelay(true);
                this.wakeup();
                try {
                    chan.register(this.mSelector, 1, dbg);
                }
                catch (IOException ioe) {
                    dbg.closeData();
                    throw ioe;
                }
                catch (RuntimeException re) {
                    dbg.closeData();
                    throw re;
                }
            } else {
                Log.w("ddms", "ignoring duplicate debugger");
            }
        }
    }

    private void processDebuggerData(SelectionKey key) {
        Debugger dbg = (Debugger)key.attachment();
        dbg.processChannelData();
    }

    private void wakeup() {
        this.mSelector.wakeup();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void quit() {
        this.mQuit = true;
        this.wakeup();
        Log.d("ddms", "Waiting for Monitor thread");
        try {
            this.join();
            ArrayList<Client> arrayList = this.mClientList;
            synchronized (arrayList) {
                for (Client c2 : this.mClientList) {
                    c2.close(false);
                    this.mDdmJdwpExtension.broadcast(DdmJdwpExtension.Event.CLIENT_DISCONNECTED, c2);
                }
                this.mClientList.clear();
            }
            if (this.mDebugSelectedChan != null) {
                this.mDebugSelectedChan.close();
                this.mDebugSelectedChan.socket().close();
                this.mDebugSelectedChan = null;
            }
            this.mSelector.close();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        sInstance = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized void addClient(Client client) {
        if (sInstance == null) {
            return;
        }
        Log.d("ddms", "Adding new client " + client);
        ArrayList<Client> arrayList = this.mClientList;
        synchronized (arrayList) {
            this.mClientList.add(client);
            for (JdwpExtension extension : this.mJdwpExtensions) {
                extension.intercept(client);
            }
            try {
                this.wakeup();
                client.register(this.mSelector);
                Debugger dbg = client.getDebugger();
                if (dbg != null) {
                    dbg.registerListener(this.mSelector);
                }
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private boolean reopenDebugSelectedPort() throws IOException {
        Log.d("ddms", "reopen debug-selected port: " + this.mNewDebugSelectedPort);
        if (this.mDebugSelectedChan != null) {
            this.mDebugSelectedChan.close();
        }
        this.mDebugSelectedChan = ServerSocketChannel.open();
        this.mDebugSelectedChan.configureBlocking(false);
        InetSocketAddress addr = new InetSocketAddress(InetAddress.getByName("localhost"), this.mNewDebugSelectedPort);
        this.mDebugSelectedChan.socket().setReuseAddress(true);
        try {
            this.mDebugSelectedChan.socket().bind(addr);
            if (this.mSelectedClient != null) {
                this.mSelectedClient.update(4);
            }
            this.mDebugSelectedChan.register(this.mSelector, 16, this);
            return true;
        }
        catch (BindException e2) {
            this.displayDebugSelectedBindError(this.mNewDebugSelectedPort);
            this.mDebugSelectedChan = null;
            this.mNewDebugSelectedPort = -1;
            return false;
        }
    }

    private void processDebugSelectedActivity(SelectionKey key) {
        Debugger dbg;
        assert (key.isAcceptable());
        ServerSocketChannel acceptChan = (ServerSocketChannel)key.channel();
        if (this.mSelectedClient != null && (dbg = this.mSelectedClient.getDebugger()) != null) {
            Log.d("ddms", "Accepting connection on 'debug selected' port");
            try {
                this.acceptNewDebugger(dbg, acceptChan);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return;
        }
        Log.w("ddms", "Connection on 'debug selected' port, but none selected");
        try {
            SocketChannel chan = acceptChan.accept();
            chan.close();
        }
        catch (IOException chan) {
        }
        catch (NotYetBoundException e2) {
            this.displayDebugSelectedBindError(this.mDebugSelectedPort);
        }
    }

    private void displayDebugSelectedBindError(int port) {
        String message = String.format("Could not open Selected VM debug port (%1$d). Make sure you do not have another instance of DDMS or of the eclipse plugin running. If it's being used by something else, choose a new port number in the preferences.", port);
        Log.logAndDisplay(Log.LogLevel.ERROR, "ddms", message);
    }

    public DdmJdwpExtension getDdmExtension() {
        return this.mDdmJdwpExtension;
    }
}

