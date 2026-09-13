/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.domain.device_settings.CommunicationControl;
import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.AutoTimeSync;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.domain.device_settings.general.ClockParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.DateParameter;
import be.tpvision.smartcontrol.domain.device_settings.general.ForceRestartCustomApp;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.LanguageOSD;
import be.tpvision.smartcontrol.domain.device_settings.general.MonitorSystem;
import be.tpvision.smartcontrol.domain.device_settings.general.OpsSdmSettings;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerLED;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.TimeZone;
import be.tpvision.smartcontrol.domain.device_settings.general.WOL;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.HdmiOneWire;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateGet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateSet;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PortStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.RS232Routing;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SicpSerialPortForwarding;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TeamviewerStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoAlignment;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.messages.protocol.sicp.command.SetSettingMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.command.SetTypeMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Command {
    private Type type;
    private Setting setting;
    private byte settingByte;
    private Codec<? extends DeviceSetting> encoder;
    private Codec<? extends DeviceSetting> decoder;

    public Command(Type type, Setting setting, byte settingByte) {
        this(type, setting, settingByte, null, null);
    }

    public Command(Type type, Setting setting, byte settingByte, Codec<? extends DeviceSetting> codec) {
        this(type, setting, settingByte, codec, codec);
    }

    public Command(Type type, Setting setting, byte settingByte, Codec<? extends DeviceSetting> encoder, Codec<? extends DeviceSetting> decoder) {
        this.setType(type);
        this.setSetting(setting);
        this.settingByte = settingByte;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        Assert.notNull((Object)type, SetTypeMessages.TYPE_CAN_NOT_BE_NULL);
        this.type = type;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public void setSetting(Setting setting) {
        Assert.notNull((Object)setting, SetSettingMessages.SETTING_CAN_NOT_BE_NULL);
        this.setting = setting;
    }

    public byte getSettingByte() {
        return this.settingByte;
    }

    public void setSettingByte(byte settingByte) {
        this.settingByte = settingByte;
    }

    public Codec<? extends DeviceSetting> getEncoder() {
        return this.encoder;
    }

    public void setEncoder(Codec<? extends DeviceSetting> encoder) {
        this.encoder = encoder;
    }

    public Codec<? extends DeviceSetting> getDecoder() {
        return this.decoder;
    }

    public void setDecoder(Codec<? extends DeviceSetting> decoder) {
        this.decoder = decoder;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Command)) {
            return false;
        }
        Command that = (Command)object;
        return new EqualsBuilder().append(this.getSettingByte(), that.getSettingByte()).append((Object)this.getType(), (Object)that.getType()).append((Object)this.getSetting(), (Object)that.getSetting()).append(this.getEncoder(), that.getDecoder()).append(this.getDecoder(), that.getDecoder()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getType(), this.getSetting(), this.getSettingByte(), this.getEncoder(), this.getDecoder()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("type", (Object)this.getType()).append("setting", (Object)this.getSetting()).append("settingByte", this.getSettingByte()).append("encoder", this.getEncoder()).append("decoder", this.getDecoder()).toString();
    }

    public static enum Type {
        GET,
        SET;

    }

    public static enum Setting {
        SICP_VERSION_AND_PLATFORM_INFO(SicpAndPlatformInfo.class),
        MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE(ModelNumberFirmwareVersionBuildDateInfo.class),
        POWER_STATE(PowerState.class),
        REMOTE_CONTROL_LOCK_STATE(RemoteControlLockState.class),
        KEYPAD_LOCK_STATE(KeypadLockState.class),
        POWER_STATE_AT_COLD_START(PowerStateAtColdStart.class),
        INPUT_SOURCE(InputSource.class),
        AUTO_SIGNAL_DETECTING(AutoSignalDetecting.class),
        FAILOVERS(Failovers.class),
        VIDEO_PARAMETERS(VideoParameters.class),
        COLOR_TEMPERATURE(ColorTemperature.class),
        COLOR_PARAMETERS(ColorParameters.class),
        COLOR_TEMPERATURE_100K(ColorTemperature100K.class),
        PICTURE_FORMAT(PictureFormat.class),
        VGA_VIDEO_PARAMETERS(VGAVideoParameters.class),
        PICTURE_IN_PICTURE(PictureInPicture.class),
        PICTURE_IN_PICTURE_SOURCE(PictureInPictureSource.class),
        VOLUME(IntWrapper.class),
        VOLUME_UP_DOWN(VolumeUpDown.class),
        VOLUME_LIMITS_SPEAKER_OUT(VolumeLimits.class),
        VOLUME_LIMITS_AUDIO_OUT(VolumeLimits.class),
        AUDIO_PARAMETERS(AudioParameters.class),
        MISCELLANEOUS(Miscellaneous.class),
        SMART_POWER(SmartPower.class),
        VIDEO_ALIGNMENT(VideoAlignment.class),
        TEMPERATURE_SENSOR(TemperatureSensor.class),
        SERIAL_CODE(StringWrapper.class),
        TILING(Tiling.class),
        FRAME_COMPENSATION_HORIZONTAL(IntWrapper.class),
        FRAME_COMPENSATION_VERTICAL(IntWrapper.class),
        LIGHT_SENSOR(LightSensor.class),
        OSD_ROTATING(OSDRotating.class),
        OSD_INFORMATION(IntWrapper.class),
        MEMC_EFFECT(MEMCEffect.class),
        TOUCH(Touch.class),
        NOISE_REDUCTION(NoiseReduction.class),
        SCAN_MODE(ScanMode.class),
        SCAN_CONVERSION(ScanConversion.class),
        SWITCH_ON_DELAY(SwitchOnDelay.class),
        FACTORY_RESET,
        POWER_ON_LOGO(PowerOnLogo.class),
        FAN_SPEED(FanSpeed.class),
        APM(APM.class),
        POWER_SAVING_MODE(PowerSavingMode.class),
        SCHEDULING_PARAMETERS(Page.class),
        GROUP_ID(IntWrapper.class),
        COMMUNICATION_CONTROL(CommunicationControl.class),
        DISPLAY_ORIENTATION(DisplayOrientation.class),
        LED_STRIPS(LedStrips.class),
        PORT_STATUS(PortStatus.class),
        OFF_TIMER(IntWrapper.class),
        LOCK_USB(LockUsb.class),
        HUMAN_SENSOR(IntWrapper.class),
        PIXEL_SHIFT(PixelShift.class),
        ECO_MODE(EcoMode.class),
        MUTE(Mute.class),
        PICTURE_STYLE(PictureStyle.class),
        MONITOR_RESTART(MonitorSystem.class),
        TAKE_SCREENSHOT,
        VIDEO_PRESENT(VideoPresent.class),
        BACKLIGHT(Backlight.class),
        OPEN_ANDROID_MENU,
        NAVIGATION_BAR(NavigationBar.class),
        BOOT_ON_SOURCE(BootOnSource.class),
        CLOCK(ClockParameter.class),
        DATE(DateParameter.class),
        AUTO_TIME_SYNC(AutoTimeSync.class),
        TIME_ZONE(TimeZone.class),
        AUTO_RESTART(AutoRestartParameter.class),
        LANGUAGE_OSD(LanguageOSD.class),
        OPS_SDM_SETTINGS(OpsSdmSettings.class),
        POWER_LED(PowerLED.class),
        FORCE_RESTART_CUSTOM_APP(ForceRestartCustomApp.class),
        AUDIO_SYNC(AudioSync.class),
        SPEAKERS_STATUS(SpeakersStatus.class),
        TEAMVIEWER_STATUS(TeamviewerStatus.class),
        RS232_ROUTING(RS232Routing.class),
        SICP_SERIAL_PORT_FORWARDING(SicpSerialPortForwarding.class),
        WOL(WOL.class),
        HDMI_ONE_WIRE(HdmiOneWire.class),
        OTA_UPDATE_SET(OtaUpdateSet.class),
        OTA_UPDATE_GET(OtaUpdateGet.class),
        OTA_GET_FW_VERSION(StringWrapper.class),
        GET_NUMBER_OF_INPUT_SOURCES(StringWrapper.class);

        private Class<? extends DeviceSetting> domainClass;

        private Setting() {
        }

        private Setting(Class<? extends DeviceSetting> domainClass) {
            this.domainClass = domainClass;
        }

        public Class<? extends DeviceSetting> getDomainClass() {
            return this.domainClass;
        }
    }
}

