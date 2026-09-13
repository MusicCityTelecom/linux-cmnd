/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import be.tpvision.smartcontrol.protocol.todelete.APM;
import be.tpvision.smartcontrol.protocol.todelete.AudioParameters;
import be.tpvision.smartcontrol.protocol.todelete.AutoSignalDetecting;
import be.tpvision.smartcontrol.protocol.todelete.ColorParameters;
import be.tpvision.smartcontrol.protocol.todelete.ColorTemperature;
import be.tpvision.smartcontrol.protocol.todelete.ColorTemperature100K;
import be.tpvision.smartcontrol.protocol.todelete.Failovers;
import be.tpvision.smartcontrol.protocol.todelete.FanSpeed;
import be.tpvision.smartcontrol.protocol.todelete.GroupId;
import be.tpvision.smartcontrol.protocol.todelete.InputSource;
import be.tpvision.smartcontrol.protocol.todelete.KeypadLockState;
import be.tpvision.smartcontrol.protocol.todelete.LightSensor;
import be.tpvision.smartcontrol.protocol.todelete.MEMCEffect;
import be.tpvision.smartcontrol.protocol.todelete.NoiseReduction;
import be.tpvision.smartcontrol.protocol.todelete.OSDInformation;
import be.tpvision.smartcontrol.protocol.todelete.OSDRotating;
import be.tpvision.smartcontrol.protocol.todelete.PictureFormat;
import be.tpvision.smartcontrol.protocol.todelete.PictureInPicture;
import be.tpvision.smartcontrol.protocol.todelete.PictureInPictureSource;
import be.tpvision.smartcontrol.protocol.todelete.PowerOnLogo;
import be.tpvision.smartcontrol.protocol.todelete.PowerSavingMode;
import be.tpvision.smartcontrol.protocol.todelete.PowerState;
import be.tpvision.smartcontrol.protocol.todelete.PowerStateAtColdStart;
import be.tpvision.smartcontrol.protocol.todelete.RemoteControlLockState;
import be.tpvision.smartcontrol.protocol.todelete.ScanConversion;
import be.tpvision.smartcontrol.protocol.todelete.ScanMode;
import be.tpvision.smartcontrol.protocol.todelete.SchedulingParameters;
import be.tpvision.smartcontrol.protocol.todelete.SmartPower;
import be.tpvision.smartcontrol.protocol.todelete.SwitchOnDelay;
import be.tpvision.smartcontrol.protocol.todelete.Tiling;
import be.tpvision.smartcontrol.protocol.todelete.Touch;
import be.tpvision.smartcontrol.protocol.todelete.VGAVideoParameters;
import be.tpvision.smartcontrol.protocol.todelete.VideoAlignment;
import be.tpvision.smartcontrol.protocol.todelete.VideoParameters;
import be.tpvision.smartcontrol.protocol.todelete.Volume;
import be.tpvision.smartcontrol.protocol.todelete.VolumeLimits;
import be.tpvision.smartcontrol.protocol.todelete.VolumeUpDown;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.text.WordUtils;

public class SetCommand
extends SicpMessage {
    private Type commandType;
    private Convertible convertible;
    private Convertibles convertibles;

    private void validate(Type commandType, Object convertible, ConvertType convertType) {
        Objects.requireNonNull(commandType, MessageUtilities.SET_COMMAND_TYPE_NOT_NULL_MESSAGE);
        Objects.requireNonNull(convertType, MessageUtilities.SET_COMMAND_CONVERT_TYPE_NOT_NULL_MESSAGE);
        String convertTypeString = String.valueOf((Object)convertType).toLowerCase();
        String capitalizedConvertType = WordUtils.capitalize(convertTypeString);
        String convertibleNotNullMessage = String.format("%s of SetCommand can't be null.", capitalizedConvertType);
        Objects.requireNonNull(convertible, convertibleNotNullMessage);
        Class<?> convertibleClass = convertible.getClass();
        Class commandTypeClass = commandType.typeClass;
        String notTheRightConvertibleMessage = String.format("Not the right %s! Expected: %s and found %s", convertTypeString, commandTypeClass, convertibleClass);
        Validate.isTrue(convertibleClass.equals(commandTypeClass), notTheRightConvertibleMessage, new Object[0]);
    }

    public SetCommand(int controlId, int groupId, Type commandType) {
        super(controlId, groupId);
        Objects.requireNonNull(commandType, MessageUtilities.SET_COMMAND_TYPE_NOT_NULL_MESSAGE);
        this.commandType = commandType;
    }

    public SetCommand(int control, int group, Type commandType, Convertible convertible) {
        super(control, group);
        this.validate(commandType, convertible, ConvertType.CONVERTIBLE);
        this.commandType = commandType;
        this.convertible = convertible;
    }

    public SetCommand(int control, int group, Type commandType, Convertibles convertibles) {
        super(control, group);
        this.validate(commandType, convertibles, ConvertType.CONVERTIBLES);
        this.commandType = commandType;
        this.convertibles = convertibles;
    }

    @Override
    protected byte[] getData() {
        byte[] data;
        byte commandTypeData = this.commandType.convert();
        if (this.convertible != null) {
            data = new byte[]{commandTypeData, this.convertible.convert()};
        } else if (this.convertibles != null) {
            byte[] convertiblesData = this.convertibles.convert();
            int capacity = 1 + convertiblesData.length;
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            byteBuffer.put(commandTypeData);
            byteBuffer.put(convertiblesData);
            data = byteBuffer.array();
        } else {
            data = new byte[]{commandTypeData};
        }
        return data;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SetCommand)) {
            return false;
        }
        SetCommand that = (SetCommand)object;
        return new EqualsBuilder().append(this.commandType, that.commandType).append(this.convertible, that.convertible).append(this.convertibles, that.convertibles).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.commandType, this.convertible, this.convertibles);
    }

    public String toString() {
        return new ToStringBuilder(this).append("commandType", this.commandType).append("convertible", this.convertible).append("convertibles", this.convertibles).toString();
    }

    private static enum ConvertType {
        CONVERTIBLE,
        CONVERTIBLES;

    }

    public static enum Type implements Convertible
    {
        POWER_STATE(24, PowerState.class),
        REMOTE_CONTROL_LOCK_STATE(28, RemoteControlLockState.class),
        KEYPAD_LOCK_STATE(26, KeypadLockState.class),
        POWER_STATE_AT_COLD_START(-93, PowerStateAtColdStart.class),
        INPUT_SOURCE(-84, InputSource.class),
        AUTO_SIGNAL_DETECTING(-82, AutoSignalDetecting.class),
        FAILOVERS(-91, Failovers.class),
        VIDEO_PARAMETERS(50, VideoParameters.class),
        COLOR_TEMPERATURE(52, ColorTemperature.class),
        COLOR_PARAMETERS(54, ColorParameters.class),
        COLOR_TEMPERATURE_100K(17, ColorTemperature100K.class),
        PICTURE_FORMAT(58, PictureFormat.class),
        VGA_VIDEO_PARAMETERS(56, VGAVideoParameters.class),
        PICTURE_IN_PICTURE(60, PictureInPicture.class),
        PICTURE_IN_PICTURE_SOURCE(-124, PictureInPictureSource.class),
        VOLUME(68, Volume.class),
        VOLUME_UP_DOWN(65, VolumeUpDown.class),
        VOLUME_LIMITS(-72, VolumeLimits.class),
        AUDIO_PARAMETERS(66, AudioParameters.class),
        SMART_POWER(-35, SmartPower.class),
        VIDEO_ALIGNMENT(112, VideoAlignment.class),
        TILING(34, Tiling.class),
        LIGHT_SENSOR(36, LightSensor.class),
        OSD_ROTATING(38, OSDRotating.class),
        OSD_INFORMATION(44, OSDInformation.class),
        MEMC_EFFECT(40, MEMCEffect.class),
        TOUCH(30, Touch.class),
        NOISE_REDUCTION(42, NoiseReduction.class),
        SCAN_MODE(80, ScanMode.class),
        SCAN_CONVERSION(82, ScanConversion.class),
        SWITCH_ON_DELAY(84, SwitchOnDelay.class),
        FACTORY_RESET(86),
        POWER_ON_LOGO(62, PowerOnLogo.class),
        FAN_SPEED(97, FanSpeed.class),
        APM(-48, APM.class),
        POWER_SAVING_MODE(-46, PowerSavingMode.class),
        SCHEDULING_PARAMETERS(90, SchedulingParameters.class),
        GROUP_ID(92, GroupId.class);

        private byte data;
        private Class<?> typeClass;

        private Type(byte data) {
            this.data = data;
        }

        private Type(byte data, Class<?> typeClass) {
            this(data);
            this.typeClass = typeClass;
        }

        public byte getData() {
            return this.data;
        }

        public Class<?> getTypeClass() {
            return this.typeClass;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }
}

