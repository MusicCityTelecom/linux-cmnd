/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.codecs.sicp.single_value.SetDeviceSettingsMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp.single_value.SetProtocolSettingsMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp.single_value.ToDomainMessages;
import be.tpvision.smartcontrol.messages.codecs.sicp.single_value.ToProtocolMessages;
import be.tpvision.smartcontrol.util.MapUtilities;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public abstract class SingleValueCodec<D extends DeviceSetting>
extends Codec<D> {
    private Map<D, Byte> deviceSettings;
    private Map<Byte, D> protocolSettings;

    public SingleValueCodec(Class<D> deviceSettingClass) {
        super(deviceSettingClass);
        this.initializeDeviceSettings();
        this.initializeProtocolSettings();
    }

    public Map<D, Byte> getDeviceSettings() {
        return this.deviceSettings;
    }

    protected final void setDeviceSettings(Map<D, Byte> deviceSettings) {
        Assert.notNull(deviceSettings, SetDeviceSettingsMessages.DEVICE_SETTINGS_CAN_NOT_BE_NULL);
        this.deviceSettings = deviceSettings;
    }

    public Map<Byte, D> getProtocolSettings() {
        return this.protocolSettings;
    }

    protected final void setProtocolSettings(Map<Byte, D> protocolSettings) {
        Assert.notNull(protocolSettings, SetProtocolSettingsMessages.PROTOCOL_SETTINGS_CAN_NOT_BE_NULL);
        this.protocolSettings = protocolSettings;
    }

    protected abstract void initializeDeviceSettings();

    protected void initializeProtocolSettings() {
        Map<Byte, D> inverseDeviceSettings = MapUtilities.inverse(this.deviceSettings);
        this.setProtocolSettings(inverseDeviceSettings);
    }

    @Override
    public byte[] toProtocol(D deviceSetting) {
        Assert.notNull(deviceSetting, ToProtocolMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
        Byte protocolSetting = this.deviceSettings.get(deviceSetting);
        Assert.state(protocolSetting != null, ToProtocolMessages.PROTOCOL_SETTING_CAN_NOT_BE_NULL);
        return new byte[]{protocolSetting};
    }

    @Override
    public D toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return null;
        }
        byte protocolSetting = bytes[0];
        DeviceSetting deviceSetting = (DeviceSetting)this.protocolSettings.get(protocolSetting);
        Assert.state(deviceSetting != null, ToDomainMessages.DEVICE_SETTING_CAN_NOT_BE_NULL);
        return (D)deviceSetting;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SingleValueCodec)) {
            return false;
        }
        SingleValueCodec that = (SingleValueCodec)object;
        return new EqualsBuilder().append(this.getDeviceSettings(), that.getDeviceSettings()).append(this.getProtocolSettings(), that.getProtocolSettings()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getDeviceSettings(), this.getProtocolSettings());
    }

    public String toString() {
        return new ToStringBuilder(this).append("deviceSettings", this.getDeviceSettings()).append("protocolSettings", this.getProtocolSettings()).toString();
    }
}

