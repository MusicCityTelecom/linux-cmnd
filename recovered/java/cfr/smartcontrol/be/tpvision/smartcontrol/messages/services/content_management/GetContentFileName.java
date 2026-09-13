/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GetContentFileName {
    public static final String CONTENT_PREFIX_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content prefix");
    public static final String CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Content file extension");
    public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware");
    public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");

    private GetContentFileName() {
    }
}

