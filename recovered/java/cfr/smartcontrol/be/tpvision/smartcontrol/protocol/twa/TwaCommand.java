/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.twa;

import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.ConstructorMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.GetBytesMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.GetCommandCodeMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.SetBytesMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.SetCommandCodeMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command.ToStringMessages;
import be.tpvision.smartcontrol.protocol.twa.TwaMessage;
import be.tpvision.smartcontrol.util.ByteArrayUtilities;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class TwaCommand
extends TwaMessage {
    public static final byte DIRECTION = 0;
    private static final int COMMAND_CODE_LENGTH = 2;
    private byte[] commandCode;
    private byte[] bytes;

    public TwaCommand(byte deviceAddressX, byte deviceAddressY, byte[] commandCode, byte[] bytes) {
        this(deviceAddressX, deviceAddressY, deviceAddressX, deviceAddressY, commandCode, bytes);
    }

    public TwaCommand(byte deviceAddressX, byte deviceAddressY, byte moduleAddressX, byte moduleAddressY, byte[] commandCode, byte[] bytes) {
        super((byte)0, deviceAddressX, deviceAddressY, moduleAddressX, moduleAddressY);
        Assert.notNull((Object)commandCode, ConstructorMessages.COMMAND_CODE_CAN_NOT_BE_NULL);
        Assert.isTrue(commandCode.length == 2, "Command code has to have a length of exactly 2");
        this.commandCode = commandCode;
        this.setBytes(bytes);
    }

    public byte[] getCommandCode() {
        Assert.state(this.commandCode != null, GetCommandCodeMessages.COMMAND_CODE_CAN_NOT_BE_NULL);
        Assert.state(this.commandCode.length == 2, "Command code has to have a length of exactly 2");
        return this.commandCode;
    }

    public void setCommandCode(byte[] commandCode) {
        Assert.notNull((Object)commandCode, SetCommandCodeMessages.COMMAND_CODE_CAN_NOT_BE_NULL);
        Assert.isTrue(commandCode.length == 2, "Command code has to have a length of exactly 2");
        this.commandCode = commandCode;
    }

    public byte[] getBytes() {
        Assert.state(this.bytes != null, GetBytesMessages.BYTES_CAN_NOT_BE_NULL);
        Assert.state(this.bytes.length == 20, "Bytes has to have a length of exactly 20");
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        Assert.notNull((Object)bytes, SetBytesMessages.BYTES_CAN_NOT_BE_NULL);
        Assert.isTrue(bytes.length == 20, "Bytes has to have a length of exactly 20");
        this.bytes = bytes;
    }

    @Override
    public byte[] getData() {
        return ArrayUtils.addAll(this.getCommandCode(), this.getBytes());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TwaCommand)) {
            return false;
        }
        TwaCommand that = (TwaCommand)object;
        return new EqualsBuilder().append(super.getDirection(), that.getDirection()).append(super.getDeviceAddressX(), that.getDeviceAddressX()).append(super.getDeviceAddressY(), that.getDeviceAddressY()).append(super.getModuleAddressX(), that.getModuleAddressX()).append(super.getModuleAddressY(), that.getModuleAddressY()).append(this.getCommandCode(), that.getCommandCode()).append(this.getBytes(), that.getBytes()).isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getDirection(), super.getDeviceAddressX(), super.getDeviceAddressY(), super.getModuleAddressX(), super.getModuleAddressY(), this.getCommandCode(), this.getBytes());
    }

    @Override
    public String toString() {
        byte[] commandCode = this.getCommandCode();
        String commandCodeHex = ByteArrayUtilities.newBytesToHex(commandCode);
        Assert.state(StringUtils.hasText(commandCodeHex), ToStringMessages.COMMAND_CODE_HEX_CAN_NOT_BE_EMPTY);
        byte[] bytes = this.getBytes();
        String bytesHex = ByteArrayUtilities.newBytesToHex(bytes);
        Assert.state(StringUtils.hasText(bytesHex), ToStringMessages.BYTES_HEX_CAN_NOT_BE_EMPTY);
        return new ToStringBuilder(this).append(super.toString()).append("commandCode", commandCodeHex).append("bytes", bytesHex).toString();
    }
}

