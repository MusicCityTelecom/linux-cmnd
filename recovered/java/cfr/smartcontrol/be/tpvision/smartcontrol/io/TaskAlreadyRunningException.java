/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

public class TaskAlreadyRunningException
extends IllegalStateException {
    private static final long serialVersionUID = 63743981741866426L;

    public TaskAlreadyRunningException(String taskName) {
        super("Task is already running: " + taskName + ".");
    }
}

