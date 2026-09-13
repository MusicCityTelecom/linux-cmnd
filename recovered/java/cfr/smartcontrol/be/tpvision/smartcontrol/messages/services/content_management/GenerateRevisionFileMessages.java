/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.services.content_management;

import be.tpvision.smartcontrol.messages.Messages;

public class GenerateRevisionFileMessages {
    public static final String HARDWARE_KEY_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware key");
    public static final String HARDWARE_KEY_CAN_NOT_BE_EMPTY = Messages.getCanNotBeEmptyMessage("Hardware key");
    public static final String HARDWARE_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Hardware path");
    public static final String CMS_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Cms path");
    public static final String CMS_PATH_DOES_NOT_EXIST = "Cms path does not exist";
    public static final String REVISION_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Revision path");

    private GenerateRevisionFileMessages() {
    }
}

