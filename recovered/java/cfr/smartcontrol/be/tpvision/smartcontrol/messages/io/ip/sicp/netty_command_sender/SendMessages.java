/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.io.ip.sicp.netty_command_sender;

import be.tpvision.smartcontrol.messages.Messages;
import org.springframework.util.Assert;

public class SendMessages {
    private SendMessages() {
    }

    public static String getDestinationUnreachableMessage(String ip, int port) {
        String ipCanNotBeNullMessage = Messages.getCanNotBeNullMessage("IP");
        Assert.notNull((Object)ip, ipCanNotBeNullMessage);
        return String.format("Destination %s:%d unreachable.", ip, port);
    }
}

