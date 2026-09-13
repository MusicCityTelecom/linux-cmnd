/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.sicp.sicp_command_factory_impl;

import be.tpvision.smartcontrol.messages.Messages;

public class ConstructorMessages {
    public static final String SICP_VERSION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Sicp version");
    public static final String COMMANDS_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Commands");

    private ConstructorMessages() {
    }
}

