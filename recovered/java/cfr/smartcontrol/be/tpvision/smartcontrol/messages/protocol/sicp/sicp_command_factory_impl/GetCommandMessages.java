/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl;

import be.tpvision.smartcontrol.messages.Messages;

public class GetCommandMessages {
    public static final String SICP_VERSION_STRING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("SICP version string");
    public static final String TYPE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Type");
    public static final String SETTING_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Setting");

    private GetCommandMessages() {
    }

    public static String getCommandNotFoundMessage(String sicpVersion, String type, String setting) {
        Messages.assertIsNotNullOrEmpty(sicpVersion);
        Messages.assertIsNotNullOrEmpty(type);
        Messages.assertIsNotNullOrEmpty(setting);
        return String.format("No SICP %s command found for %s %s.", sicpVersion, type, setting);
    }
}

