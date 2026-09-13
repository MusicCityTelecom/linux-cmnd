/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages.protocol.sicp.sicp_factory_provider_impl;

import be.tpvision.smartcontrol.messages.Messages;
import org.springframework.util.Assert;

public class GetSicpFactoryMessages {
    private GetSicpFactoryMessages() {
    }

    public static String getDeviceNotFoundMessage(long deviceId) {
        String hasToBePositiveNumberMessage = Messages.getHasToBePositiveNumberMessage("Device id");
        Assert.isTrue(deviceId >= 0L, hasToBePositiveNumberMessage);
        return String.format("Device not found with id %d.", deviceId);
    }
}

