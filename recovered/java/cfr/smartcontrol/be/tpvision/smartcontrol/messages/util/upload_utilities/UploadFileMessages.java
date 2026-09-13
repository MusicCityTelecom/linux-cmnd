/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.util.upload_utilities;

import be.tpvision.smartcontrol.messages.Messages;
import java.nio.file.Path;

public class UploadFileMessages {
    public static final String FILE_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("File path");
    public static final String IP_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("IP");
    public static final String PORT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Port");
    public static final String USERNAME_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Username");
    public static final String PASSWORD_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Password");
    public static final String FILE_NAME_PATH_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("File name path");

    private UploadFileMessages() {
    }

    public static String getFailedToUploadFileMessage(Path filePath) {
        return String.format("Failed to upload %s.", filePath);
    }
}

