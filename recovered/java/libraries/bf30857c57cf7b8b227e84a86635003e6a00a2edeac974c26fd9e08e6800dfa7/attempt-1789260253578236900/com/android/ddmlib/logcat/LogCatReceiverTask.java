/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmlib.logcat.LogCatMessageParser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogCatReceiverTask
implements Runnable {
    private static final String LOGCAT_COMMAND = "logcat -v long";
    private static final int DEVICE_POLL_INTERVAL_MSEC = 1000;
    private static final LogCatMessage sDeviceDisconnectedMsg = new LogCatMessage(Log.LogLevel.ERROR, "Device disconnected: 1");
    private static final LogCatMessage sConnectionTimeoutMsg = new LogCatMessage(Log.LogLevel.ERROR, "LogCat Connection timed out");
    private static final LogCatMessage sConnectionErrorMsg = new LogCatMessage(Log.LogLevel.ERROR, "LogCat Connection error");
    private final IDevice mDevice;
    private final LogCatOutputReceiver mReceiver;
    private final LogCatMessageParser mParser;
    private final AtomicBoolean mCancelled;
    private final Set<LogCatListener> mListeners = new HashSet<LogCatListener>();

    public LogCatReceiverTask(IDevice device) {
        this.mDevice = device;
        this.mReceiver = new LogCatOutputReceiver();
        this.mParser = new LogCatMessageParser();
        this.mCancelled = new AtomicBoolean();
    }

    @Override
    public void run() {
        while (!this.mDevice.isOnline()) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e2) {
                return;
            }
        }
        try {
            this.mDevice.executeShellCommand(LOGCAT_COMMAND, this.mReceiver, 0);
        }
        catch (TimeoutException e3) {
            this.notifyListeners(Collections.singletonList(sConnectionTimeoutMsg));
        }
        catch (AdbCommandRejectedException e3) {
        }
        catch (ShellCommandUnresponsiveException e3) {
        }
        catch (IOException e4) {
            this.notifyListeners(Collections.singletonList(sConnectionErrorMsg));
        }
        this.notifyListeners(Collections.singletonList(sDeviceDisconnectedMsg));
    }

    public void stop() {
        this.mCancelled.set(true);
    }

    public synchronized void addLogCatListener(LogCatListener l2) {
        this.mListeners.add(l2);
    }

    public synchronized void removeLogCatListener(LogCatListener l2) {
        this.mListeners.remove(l2);
    }

    private synchronized void notifyListeners(List<LogCatMessage> messages) {
        for (LogCatListener l2 : this.mListeners) {
            l2.log(messages);
        }
    }

    private class LogCatOutputReceiver
    extends MultiLineReceiver {
        public LogCatOutputReceiver() {
            this.setTrimLine(false);
        }

        @Override
        public boolean isCancelled() {
            return LogCatReceiverTask.this.mCancelled.get();
        }

        @Override
        public void processNewLines(String[] lines) {
            if (!LogCatReceiverTask.this.mCancelled.get()) {
                this.processLogLines(lines);
            }
        }

        private void processLogLines(String[] lines) {
            List<LogCatMessage> newMessages = LogCatReceiverTask.this.mParser.processLogLines(lines, LogCatReceiverTask.this.mDevice);
            if (!newMessages.isEmpty()) {
                LogCatReceiverTask.this.notifyListeners(newMessages);
            }
        }
    }
}

