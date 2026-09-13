/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.twa;

import be.tpvision.smartcontrol.codecs.twa.Codec;
import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;
import be.tpvision.smartcontrol.messages.protocol.twa.command.ConstructorMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Command<T extends TwaDeviceSetting> {
    final Class<T> twaDeviceSettingClass;
    final byte[] commandCode;
    final Codec<T> codec;

    public Command(Class<T> twaDeviceSettingClass, byte[] commandCode, Codec<T> codec) {
        Assert.notNull(twaDeviceSettingClass, ConstructorMessages.TWA_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
        Assert.notNull((Object)commandCode, ConstructorMessages.COMMAND_CODE_CAN_NOT_BE_NULL);
        Assert.notNull(codec, ConstructorMessages.CODEC_CAN_NOT_BE_NULL);
        this.twaDeviceSettingClass = twaDeviceSettingClass;
        this.commandCode = commandCode;
        this.codec = codec;
    }

    public Class<T> getTwaDeviceSettingClass() {
        return this.twaDeviceSettingClass;
    }

    public byte[] getCommandCode() {
        return this.commandCode;
    }

    public Codec<T> getCodec() {
        return this.codec;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Command)) {
            return false;
        }
        Command that = (Command)object;
        return new EqualsBuilder().append(this.getTwaDeviceSettingClass(), that.getTwaDeviceSettingClass()).append(this.getCommandCode(), that.getCommandCode()).append(this.getCodec(), that.getCodec()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getTwaDeviceSettingClass(), this.getCommandCode(), this.getCodec());
    }

    public String toString() {
        return new ToStringBuilder(this).append("twaDeviceSettingClass", this.getTwaDeviceSettingClass()).append("commandCode", this.getCommandCode()).append("codec", this.getCodec()).toString();
    }
}

