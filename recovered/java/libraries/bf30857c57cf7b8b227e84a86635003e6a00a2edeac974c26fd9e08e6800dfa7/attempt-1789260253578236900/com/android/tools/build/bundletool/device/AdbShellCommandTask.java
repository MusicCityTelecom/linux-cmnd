/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AdbShellCommandTask {
    private final String command;
    private final Device device;

    public AdbShellCommandTask(Device device, String commandToExecute) {
        this.device = device;
        this.command = commandToExecute;
    }

    public ImmutableList<String> execute() {
        return this.execute(0L, TimeUnit.SECONDS);
    }

    public ImmutableList<String> execute(long deadline, TimeUnit deadlineUnits) {
        final ImmutableList.Builder outputLines = ImmutableList.builder();
        try {
            this.device.executeShellCommand(this.command, new MultiLineReceiver(){

                @Override
                public boolean isCancelled() {
                    return false;
                }

                @Override
                public void processNewLines(String[] strings) {
                    outputLines.add(strings);
                }
            }, deadline, deadlineUnits);
            return outputLines.build();
        }
        catch (IOException e2) {
            throw CommandExecutionException.builder().withMessage("I/O error while executing 'adb shell %s' on device '%s'.", this.command, this.device.getSerialNumber()).withCause(e2).build();
        }
        catch (TimeoutException e3) {
            throw CommandExecutionException.builder().withMessage("Timeout while executing 'adb shell %s' on device '%s'.", this.command, this.device.getSerialNumber()).withCause(e3).build();
        }
        catch (ShellCommandUnresponsiveException e4) {
            throw CommandExecutionException.builder().withMessage("Unresponsive shell command while executing 'adb shell %s' on device '%s'.", this.command, this.device.getSerialNumber()).withCause(e4).build();
        }
        catch (AdbCommandRejectedException e5) {
            throw CommandExecutionException.builder().withMessage("Rejected 'adb shell %s' command on device '%s'.", this.command, this.device.getSerialNumber()).withCause(e5).build();
        }
    }
}

