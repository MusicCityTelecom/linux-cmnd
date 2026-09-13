/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.protocol.todelete.Miscellaneous;
import be.tpvision.smartcontrol.protocol.todelete.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.protocol.todelete.SchedulingParameters;
import be.tpvision.smartcontrol.protocol.todelete.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetCommand
extends SicpMessage {
    private Type commandType;
    private Convertible convertible;

    public GetCommand(int controlId, int groupId, Type commandType) {
        super(controlId, groupId);
        Objects.requireNonNull(commandType, MessageUtilities.GET_COMMAND_TYPE_NOT_NULL_MESSAGE);
        this.commandType = commandType;
    }

    public GetCommand(int controlId, int groupId, Type commandType, Convertible convertible) {
        this(controlId, groupId, commandType);
        Objects.requireNonNull(convertible, MessageUtilities.CONVERTIBLE_NOT_NULL_MESSAGE);
        Class<?> convertibleClass = convertible.getClass();
        Class commandTypeClass = commandType.typeClass;
        String notTheRightConvertibleMessage = String.format("Not the right convertible! Expected: %s and found %s", commandTypeClass, convertibleClass);
        Validate.isTrue(convertibleClass.equals(commandTypeClass), notTheRightConvertibleMessage, new Object[0]);
        this.convertible = convertible;
    }

    @Override
    protected byte[] getData() {
        byte[] data;
        byte commandTypeData = this.commandType.getData();
        if (this.convertible != null) {
            byte convertibleData = this.convertible.convert();
            data = new byte[]{commandTypeData, convertibleData};
        } else {
            data = new byte[]{commandTypeData};
        }
        return data;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GetCommand)) {
            return false;
        }
        GetCommand that = (GetCommand)object;
        return new EqualsBuilder().append(this.commandType, that.commandType).append(this.convertible, that.convertible).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.commandType, this.convertible);
    }

    public String toString() {
        return new ToStringBuilder(this).append("commandType", this.commandType).append("convertible", this.convertible).toString();
    }

    public static enum Type implements Convertible
    {
        SICP_VERSION_AND_PLATFORM_INFO(-94, SicpAndPlatformInfo.class),
        MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE(-95, ModelNumberFirmwareVersionBuildDateInfo.class),
        POWER_STATE(25),
        REMOTE_CONTROL_LOCK_STATE(29),
        KEYPAD_LOCK_STATE(27),
        POWER_STATE_AT_COLD_START(-92),
        INPUT_SOURCE(-83),
        AUTO_SIGNAL_DETECTING(-81),
        FAILOVERS(-90),
        VIDEO_PARAMETERS(51),
        COLOR_TEMPERATURE(53),
        COLOR_PARAMETERS(55),
        COLOR_TEMPERATURE_100K(18),
        PICTURE_FORMAT(59),
        VGA_VIDEO_PARAMETERS(57),
        PICTURE_IN_PICTURE(61),
        PICTURE_IN_PICTURE_SOURCE(-123),
        VOLUME(69),
        AUDIO_PARAMETERS(67),
        MISCELLANEOUS(15, Miscellaneous.Info.class),
        SMART_POWER(-34),
        TEMPERATURE_SENSOR(47),
        SERIAL_CODE(21),
        TILING(35),
        LIGHT_SENSOR(37),
        OSD_ROTATING(39),
        OSD_INFORMATION(45),
        MEMC_EFFECT(41),
        TOUCH(31),
        NOISE_REDUCTION(43),
        SCAN_MODE(81),
        SCAN_CONVERSION(83),
        SWITCH_ON_DELAY(85),
        POWER_ON_LOGO(63),
        FAN_SPEED(98),
        APM(-47),
        POWER_SAVING_MODE(-45),
        SCHEDULING_PARAMETERS(91, SchedulingParameters.Page.class),
        GROUP_ID(93);

        private byte data;
        private Class<? extends Convertible> typeClass;

        private Type(byte data) {
            this.data = data;
        }

        private Type(byte data, Class<? extends Convertible> typeClass) {
            this(data);
            this.typeClass = typeClass;
        }

        public byte getData() {
            return this.data;
        }

        public Class<? extends Convertible> getTypeClass() {
            return this.typeClass;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }
}

