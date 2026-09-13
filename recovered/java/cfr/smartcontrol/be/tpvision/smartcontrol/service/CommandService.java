/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.protocol.sicp.Command;

public interface CommandService {
    public void send(Device var1, Command.Type var2, Command.Setting var3);

    default public <T> T send(Device device, Command.Type type, Command.Setting setting, Class<T> responseClass) {
        return this.send(device, type, setting, null, responseClass);
    }

    public void send(Device var1, Command.Type var2, Command.Setting var3, DeviceSetting var4);

    public <T> T send(Device var1, Command.Type var2, Command.Setting var3, DeviceSetting var4, Class<T> var5);
}

