/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;

public enum RS232Routing implements MixedEnumDeviceSetting
{
    RS232("RS232"),
    LAN_RS232("LAN > RS232"),
    CARD_OPS_RS232("CARD-OPS > RS232"),
    RESERVED("Reserved");

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private RS232Routing(String name) {
        this.name = name;
    }

    @Override
    public String getOptionText() {
        return this.getName();
    }

    @Override
    public String getOptionValue() {
        return this.name();
    }
}

