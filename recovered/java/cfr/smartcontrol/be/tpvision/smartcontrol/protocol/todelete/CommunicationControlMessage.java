/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.protocol.todelete.CommunicationControl;

public class CommunicationControlMessage
extends SicpMessage {
    public static final byte IDENTIFYING_BYTE = 0;
    private CommunicationControl communicationControl;

    public CommunicationControlMessage(int controlId, int groupId, CommunicationControl communicationControl) {
        super(controlId, groupId);
        this.communicationControl = communicationControl;
    }

    @Override
    protected byte[] getData() {
        return new byte[]{0, this.communicationControl.convert()};
    }
}

