/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.CanceledException;

public class SyncException
extends CanceledException {
    private static final long serialVersionUID = 1L;
    private final SyncError mError;

    public SyncException(SyncError error) {
        super(error.getMessage());
        this.mError = error;
    }

    public SyncException(SyncError error, String message) {
        super(message);
        this.mError = error;
    }

    public SyncException(SyncError error, Throwable cause) {
        super(error.getMessage(), cause);
        this.mError = error;
    }

    public SyncError getErrorCode() {
        return this.mError;
    }

    @Override
    public boolean wasCanceled() {
        return this.mError == SyncError.CANCELED;
    }

    public static enum SyncError {
        CANCELED("Operation was canceled by the user."),
        TRANSFER_PROTOCOL_ERROR("Adb Transfer Protocol Error."),
        NO_REMOTE_OBJECT("Remote object doesn't exist!"),
        TARGET_IS_FILE("Target object is a file."),
        NO_DIR_TARGET("Target directory doesn't exist."),
        REMOTE_PATH_ENCODING("Remote Path encoding is not supported."),
        REMOTE_PATH_LENGTH("Remote path is too long."),
        FILE_READ_ERROR("Reading local file failed!"),
        FILE_WRITE_ERROR("Writing local file failed!"),
        LOCAL_IS_DIRECTORY("Local path is a directory."),
        NO_LOCAL_FILE("Local path doesn't exist."),
        REMOTE_IS_FILE("Remote path is a file."),
        BUFFER_OVERRUN("Receiving too much data.");

        private final String mMessage;

        private SyncError(String message) {
            this.mMessage = message;
        }

        public String getMessage() {
            return this.mMessage;
        }
    }
}

