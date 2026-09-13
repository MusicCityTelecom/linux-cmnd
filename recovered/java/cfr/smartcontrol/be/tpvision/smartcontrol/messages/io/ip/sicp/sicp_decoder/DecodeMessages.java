/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.io.ip.sicp.sicp_decoder;

import be.tpvision.smartcontrol.messages.Messages;

public class DecodeMessages {
    public static final String CODEC_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Codec");

    private DecodeMessages() {
    }

    public static String getChecksumVerificationFailedMessage(byte expectedChecksum, byte receivedChecksum) {
        return "Checksum verification failed for received message: expected [" + expectedChecksum + "], but received [" + receivedChecksum + "].";
    }
}

