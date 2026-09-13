/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.messages.protocol.sicp.sicp_version.ConstructorMessages;
import org.springframework.util.Assert;

public enum SicpVersion {
    _188("1.88"),
    _197("1.97"),
    _199("1.99"),
    _200("2.00"),
    _201("2.01"),
    _202("2.02"),
    _203("2.03"),
    _204("2.04"),
    _205("2.05"),
    _207("2.07"),
    _208("2.08");

    private final String name;

    private SicpVersion(String name) {
        Assert.notNull((Object)name, ConstructorMessages.NAME_CAN_NOT_BE_NULL);
        Assert.isTrue(!name.isEmpty(), ConstructorMessages.NAME_CAN_NOT_BE_EMPTY);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

