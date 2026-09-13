/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;

public enum Failover implements Convertible
{
    HDMI_1(0),
    COMPONENT(1),
    COMPOSITE(2),
    DISPLAY_PORT(3),
    DVI_D(4),
    VGA(5),
    OPS(6),
    USB(7),
    BROWSER(8),
    SMART_CMS(9),
    INTERNAL_STORAGE(10),
    DIGITAL_MEDIA_SERVER(11),
    HDMI_2(12),
    HDMI_3(13);

    private byte data;

    private Failover(byte data) {
        this.data = data;
    }

    public byte getData() {
        return this.data;
    }

    @Override
    public byte convert() {
        return this.data;
    }
}

