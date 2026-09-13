/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

import be.tpvision.smartcontrol.io.TaskAlreadyRunningException;

public class FetchInfoDataTaskAlreadyRunningException
extends TaskAlreadyRunningException {
    private static final long serialVersionUID = 1303756259250257125L;

    public FetchInfoDataTaskAlreadyRunningException() {
        super("Fetch info data");
    }
}

