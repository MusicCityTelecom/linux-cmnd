/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.io.TaskAlreadyRunningException;

public class FetchOverviewDataTaskAlreadyRunningException
extends TaskAlreadyRunningException {
    private static final long serialVersionUID = 312746151619184781L;

    public FetchOverviewDataTaskAlreadyRunningException() {
        super("Fetch overview data");
    }
}

