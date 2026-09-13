/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.repositories.jdbc.hardware;

import be.tpvision.smartcontrol.messages.Messages;

public class GetHardwareByContentIdMessages {
    public static final String HARDWARE_ROW_MAPPER_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware row mapper");
    public static final String CONTENT_ID_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content id");
    public static final String CONTENT_ID_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Content id");

    private GetHardwareByContentIdMessages() {
    }
}

