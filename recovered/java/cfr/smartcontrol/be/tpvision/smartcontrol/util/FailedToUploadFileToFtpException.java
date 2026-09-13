/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

public class FailedToUploadFileToFtpException
extends RuntimeException {
    private static final long serialVersionUID = -4853394052090145037L;

    public FailedToUploadFileToFtpException(String message) {
        super(message);
    }

    public FailedToUploadFileToFtpException(String message, Throwable cause) {
        super(message, cause);
    }
}

