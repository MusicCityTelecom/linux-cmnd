/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.twa;

import be.tpvision.smartcontrol.codecs.twa.BrightnessCodec;
import be.tpvision.smartcontrol.codecs.twa.CascadeModeCodec;
import be.tpvision.smartcontrol.codecs.twa.Codec;
import be.tpvision.smartcontrol.codecs.twa.DeviceAddressWrapperCodec;
import be.tpvision.smartcontrol.codecs.twa.MainboardOriginPositionCodec;
import be.tpvision.smartcontrol.codecs.twa.MainboardSignalOutputCodec;
import be.tpvision.smartcontrol.codecs.twa.NormalDisplayTestPatternCodec;
import be.tpvision.smartcontrol.codecs.twa.TestPatternColorCodec;
import be.tpvision.smartcontrol.domain.twa.Brightness;
import be.tpvision.smartcontrol.domain.twa.CascadeMode;
import be.tpvision.smartcontrol.domain.twa.DeviceAddress;
import be.tpvision.smartcontrol.domain.twa.DeviceAddressWrapper;
import be.tpvision.smartcontrol.domain.twa.MainboardOriginPosition;
import be.tpvision.smartcontrol.domain.twa.MainboardSignalOutput;
import be.tpvision.smartcontrol.domain.twa.ModuleAddress;
import be.tpvision.smartcontrol.domain.twa.NormalDisplayTestPattern;
import be.tpvision.smartcontrol.domain.twa.TestPatternColor;
import be.tpvision.smartcontrol.domain.twa.TwaDeviceSetting;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command_factory.GetDeviceAddressCommandMessages;
import be.tpvision.smartcontrol.messages.protocol.twa.twa_command_factory.GetTwaCommandMessages;
import be.tpvision.smartcontrol.protocol.twa.Command;
import be.tpvision.smartcontrol.protocol.twa.DeviceAddressCommand;
import be.tpvision.smartcontrol.protocol.twa.TwaCommand;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.util.Assert;

public class TwaCommandFactory {
    private static final byte COMMAND_CODE_MAINBOARD_ORIGIN_POSITION_BYTE_1 = 2;
    private static final byte COMMAND_CODE_MAINBOARD_ORIGIN_POSITION_BYTE_2 = 1;
    private static final byte COMMAND_CODE_MAINBOARD_SIGNAL_OUTPUT_BYTE_1 = 2;
    private static final byte COMMAND_CODE_MAINBOARD_SIGNAL_OUTPUT_BYTE_2 = 18;
    private static final byte COMMAND_CODE_CASCADE_MODE_BYTE_1 = 3;
    private static final byte COMMAND_CODE_CASCADE_MODE_BYTE_2 = 86;
    private static final byte COMMAND_CODE_NORMAL_DISPLAY_TEST_PATTERN_BYTE_1 = 2;
    private static final byte COMMAND_CODE_NORMAL_DISPLAY_TEST_PATTERN_BYTE_2 = 17;
    private static final byte COMMAND_CODE_TEST_PATTERN_COLOR_BYTE_1 = 3;
    private static final byte COMMAND_CODE_TEST_PATTERN_COLOR_BYTE_2 = 20;
    private static final byte COMMAND_CODE_BRIGHTNESS_BYTE_1 = 0;
    private static final byte COMMAND_CODE_BRIGHTNESS_BYTE_2 = 2;
    private static final int DEVICE_ADDRESS_X = 0;
    private static final int DEVICE_ADDRESS_Y = 0;
    private static final int MODULE_ADDRESS_X = 0;
    private static final int MODULE_ADDRESS_Y = 0;
    private static final Set<Command<? extends TwaDeviceSetting>> commands = new HashSet<Command<? extends TwaDeviceSetting>>();

    private TwaCommandFactory() {
    }

    public static TwaCommand getTwaCommand(TwaDeviceSetting twaDeviceSetting) {
        Assert.notNull((Object)twaDeviceSetting, GetTwaCommandMessages.TWA_DEVICE_SETTING_CAN_NOT_BE_NULL);
        DeviceAddress deviceAddress = new DeviceAddress(0, 0);
        ModuleAddress moduleAddress = new ModuleAddress(0, 0);
        return TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, twaDeviceSetting);
    }

    public static TwaCommand getTwaCommand(DeviceAddress deviceAddress, ModuleAddress moduleAddress, TwaDeviceSetting twaDeviceSetting) {
        Assert.notNull((Object)deviceAddress, GetTwaCommandMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
        int domainDeviceAddressX = deviceAddress.getX();
        byte protocolDeviceAddressX = ValueUtilities.getByteValueFromUnsigned(domainDeviceAddressX);
        int domainDeviceAddressY = deviceAddress.getY();
        byte protocolDeviceAddressY = ValueUtilities.getByteValueFromUnsigned(domainDeviceAddressY);
        Assert.notNull((Object)moduleAddress, GetTwaCommandMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
        int domainModuleAddressX = moduleAddress.getX();
        byte protocolModuleAddressX = ValueUtilities.getByteValueFromUnsigned(domainModuleAddressX);
        int domainModuleAddressY = moduleAddress.getY();
        byte protocolModuleAddressY = ValueUtilities.getByteValueFromUnsigned(domainModuleAddressY);
        Assert.notNull((Object)twaDeviceSetting, GetTwaCommandMessages.TWA_DEVICE_SETTING_CAN_NOT_BE_NULL);
        Class<?> twaDeviceSettingClass = twaDeviceSetting.getClass();
        Assert.state(twaDeviceSettingClass != null, GetTwaCommandMessages.TWA_DEVICE_SETTING_CLASS_CAN_NOT_BE_NULL);
        Optional<Command> commandOptional = commands.stream().filter(command -> command.getTwaDeviceSettingClass().equals(twaDeviceSettingClass)).findFirst();
        Assert.state(commandOptional.isPresent(), GetTwaCommandMessages.getNoCommandFoundForTwaDeviceSettingMessage(twaDeviceSettingClass));
        Command command2 = commandOptional.get();
        byte[] commandCode = command2.getCommandCode();
        Assert.state(commandCode != null, GetTwaCommandMessages.COMMAND_CODE_CAN_NOT_BE_NULL);
        Codec codec = command2.getCodec();
        Assert.state(codec != null, GetTwaCommandMessages.CODEC_CAN_NOT_BE_NULL);
        byte[] protocolTwaDeviceSetting = codec.toProtocol(twaDeviceSetting);
        Assert.state(protocolTwaDeviceSetting != null, GetTwaCommandMessages.PROTOCOL_TWA_DEVICE_SETTING_CAN_NOT_BE_NULL);
        return new TwaCommand(protocolDeviceAddressX, protocolDeviceAddressY, protocolModuleAddressX, protocolModuleAddressY, commandCode, protocolTwaDeviceSetting);
    }

    public static DeviceAddressCommand getDeviceAddressCommand(DeviceAddressWrapper deviceAddressWrapper) {
        Assert.notNull((Object)deviceAddressWrapper, GetDeviceAddressCommandMessages.DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL);
        DeviceAddressWrapperCodec deviceAddressWrapperCodec = DeviceAddressWrapperCodec.getInstance();
        Assert.state(deviceAddressWrapperCodec != null, GetDeviceAddressCommandMessages.DEVICE_ADDRESS_WRAPPER_CODEC_CAN_NOT_BE_NULL);
        byte[] protocolDeviceAddressWrapper = deviceAddressWrapperCodec.toProtocol(deviceAddressWrapper);
        Assert.state(protocolDeviceAddressWrapper != null, GetDeviceAddressCommandMessages.PROTOCOL_DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL);
        return new DeviceAddressCommand(protocolDeviceAddressWrapper);
    }

    static {
        commands.add(new Command<MainboardOriginPosition>(MainboardOriginPosition.class, new byte[]{2, 1}, MainboardOriginPositionCodec.getInstance()));
        commands.add(new Command<MainboardSignalOutput>(MainboardSignalOutput.class, new byte[]{2, 18}, MainboardSignalOutputCodec.getInstance()));
        commands.add(new Command<CascadeMode>(CascadeMode.class, new byte[]{3, 86}, CascadeModeCodec.getInstance()));
        commands.add(new Command<NormalDisplayTestPattern>(NormalDisplayTestPattern.class, new byte[]{2, 17}, NormalDisplayTestPatternCodec.getInstance()));
        commands.add(new Command<TestPatternColor>(TestPatternColor.class, new byte[]{3, 20}, TestPatternColorCodec.getInstance()));
        commands.add(new Command<Brightness>(Brightness.class, new byte[]{0, 2}, BrightnessCodec.getInstance()));
    }
}

