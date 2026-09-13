/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.io;

public class CommandNotAcknowledgedException
extends RuntimeException {
    private static final long serialVersionUID = -1276770723650878759L;

    public CommandNotAcknowledgedException(String message) {
        super(message);
    }
}

