/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.MatrixPosition;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.general.Backlight;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LightSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LockUsb;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OSDRotating;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PortStatus;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Touch;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.SchedulingParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.repository.converters.PowerStateConverter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="devices", uniqueConstraints={@UniqueConstraint(name="uq_destination", columnNames={"address", "groupId", "controlId"})})
public class Device
implements DeviceListItem,
Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @Column(nullable=false)
    private IpDestination address;
    private String name;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="modelNumber"))})
    private StringWrapper modelNumber;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="firmwareVersionScaler"))})
    private StringWrapper firmwareVersion;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="buildDate"))})
    private StringWrapper buildDate;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="firmwareVersionAndroid"))})
    private StringWrapper firmwareVersionAndroid;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="serialCode"))})
    private StringWrapper serialCode;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="sicpVersion"))})
    private StringWrapper sicpVersion;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="platformLabel"))})
    private StringWrapper platformLabel;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="platformVersion"))})
    private StringWrapper platformVersion;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="value", column=@Column(name="supportSources"))})
    private StringWrapper supportSources;
    @Transient
    private APM apm;
    @Transient
    private AudioParameters audioParameters;
    @Transient
    private AutoSignalDetecting autoSignalDetecting;
    @Transient
    private ColorParameters colorParameters;
    @Transient
    private ColorTemperature colorTemperature;
    @Transient
    private ColorTemperature100K colorTemperature100K;
    @Transient
    private Failovers failovers;
    @Transient
    private FanSpeed fanSpeed;
    @Transient
    private LockUsb lockUsb;
    @Transient
    private PixelShift pixelShift;
    @Transient
    private IntWrapper offTimer;
    @Transient
    private IntWrapper humanSensor;
    @Transient
    private EcoMode ecoMode;
    @Transient
    private PictureStyle pictureStyle;
    @Transient
    private Mute mute;
    @Embedded
    private InputSource inputSource;
    @Transient
    private KeypadLockState keypadLockState;
    @Transient
    private LightSensor lightSensor;
    @Transient
    private MEMCEffect memcEffect;
    @Embedded
    private Miscellaneous miscellaneous;
    @Transient
    private NoiseReduction noiseReduction;
    @Transient
    private IntWrapper osdInformation;
    @Transient
    private OSDRotating osdRotating;
    @Transient
    private PictureFormat pictureFormat;
    @Transient
    private PictureInPicture pictureInPicture;
    @Transient
    private PictureInPictureSource pictureInPictureSource;
    @Transient
    private PowerOnLogo powerOnLogo;
    @Transient
    private PowerSavingMode powerSavingMode;
    @Transient
    private DisplayOrientation displayOrientation;
    @Transient
    private PortStatus portStatus;
    @Transient
    private LedStrips ledStrips;
    @Convert(converter=PowerStateConverter.class)
    private PowerState powerState;
    @Transient
    private PowerStateAtColdStart powerStateAtColdStart;
    @Transient
    private RemoteControlLockState remoteControlLockState;
    @Transient
    private ScanConversion scanConversion;
    @Transient
    private ScanMode scanMode;
    @Transient
    private SmartPower smartPower;
    @Transient
    private SwitchOnDelay switchOnDelay;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="temperatureOne", column=@Column(name="temperature_one")), @AttributeOverride(name="temperatureTwo", column=@Column(name="temperature_two"))})
    private TemperatureSensor temperature;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="enable", column=@Column(name="tiling_enable")), @AttributeOverride(name="frameComp", column=@Column(name="tiling_frame_comp")), @AttributeOverride(name="position", column=@Column(name="tiling_position")), @AttributeOverride(name="numberOfHorizontalMonitors", column=@Column(name="tiling_number_of_horizontal_monitors")), @AttributeOverride(name="numberOfVerticalMonitors", column=@Column(name="tiling_number_of_vertical_monitors"))})
    private Tiling tiling;
    @Transient
    private IntWrapper frameCompensationHorizontal;
    @Transient
    private IntWrapper frameCompensationVertical;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="x", column=@Column(name="matrix_position_x")), @AttributeOverride(name="y", column=@Column(name="matrix_position_y")), @AttributeOverride(name="sizeX", column=@Column(name="matrix_position_size_x")), @AttributeOverride(name="sizeY", column=@Column(name="matrix_position_size_y"))})
    private MatrixPosition matrixPosition;
    @Transient
    private Touch touch;
    @Transient
    private VGAVideoParameters vgaVideoParameters;
    @Transient
    private VideoParameters videoParameters;
    @Transient
    private Volume volume;
    @Transient
    private VolumeLimits volumeLimitsSpeakerOut;
    @Transient
    private VolumeLimits volumeLimitsAudioOut;
    @Transient
    private SchedulingParameters schedulingParameters;
    @Transient
    private Map<String, Object> orignalData = new HashMap<String, Object>();
    private boolean contentRotated;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="useDefault", column=@Column(name="ftp_settings_use_default")), @AttributeOverride(name="port", column=@Column(name="ftp_settings_port")), @AttributeOverride(name="username", column=@Column(name="ftp_settings_username")), @AttributeOverride(name="password", column=@Column(name="ftp_settings_password"))})
    private FtpSettings ftpSettings;
    @Transient
    private VideoPresent videoPresent;
    @Transient
    private Backlight backlight;
    @Transient
    private NavigationBar navigationBar;
    @Transient
    private BootOnSource bootOnSource;

    private void putOrignalData(String key, Object obj) {
        if (!this.orignalData.containsKey(key)) {
            this.orignalData.put(key, obj);
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IpDestination getAddress() {
        return this.address;
    }

    public void setAddress(IpDestination address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringWrapper getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(StringWrapper modelNumber) {
        this.modelNumber = modelNumber;
    }

    public StringWrapper getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(StringWrapper firmwareVersion) {
        this.putOrignalData("FirmwareVersion", this.firmwareVersion);
        this.firmwareVersion = firmwareVersion;
    }

    public StringWrapper getBuildDate() {
        return this.buildDate;
    }

    public void setBuildDate(StringWrapper buildDate) {
        this.buildDate = buildDate;
    }

    public StringWrapper getFirmwareVersionAndroid() {
        return this.firmwareVersionAndroid;
    }

    public void setFirmwareVersionAndroid(StringWrapper firmwareVersionAndroid) {
        this.putOrignalData("FirmwareVersionAndroid", this.firmwareVersionAndroid);
        this.firmwareVersionAndroid = firmwareVersionAndroid;
    }

    public StringWrapper getSerialCode() {
        return this.serialCode;
    }

    public void setSerialCode(StringWrapper serialCode) {
        this.serialCode = serialCode;
    }

    public StringWrapper getSicpVersion() {
        return this.sicpVersion;
    }

    public void setSicpVersion(StringWrapper sicpVersion) {
        this.sicpVersion = sicpVersion;
    }

    public StringWrapper getPlatformLabel() {
        return this.platformLabel;
    }

    public void setPlatformLabel(StringWrapper platformLabel) {
        this.platformLabel = platformLabel;
    }

    public StringWrapper getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(StringWrapper platformVersion) {
        this.platformVersion = platformVersion;
    }

    public StringWrapper getSupportSources() {
        return this.supportSources;
    }

    public void setSupportSources(StringWrapper supportSources) {
        this.supportSources = supportSources;
    }

    public APM getApm() {
        return this.apm;
    }

    public void setApm(APM apm) {
        this.apm = apm;
    }

    public AudioParameters getAudioParameters() {
        return this.audioParameters;
    }

    public void setAudioParameters(AudioParameters audioParameters) {
        this.audioParameters = audioParameters;
    }

    public AutoSignalDetecting getAutoSignalDetecting() {
        return this.autoSignalDetecting;
    }

    public void setAutoSignalDetecting(AutoSignalDetecting autoSignalDetecting) {
        this.autoSignalDetecting = autoSignalDetecting;
    }

    public ColorParameters getColorParameters() {
        return this.colorParameters;
    }

    public void setColorParameters(ColorParameters colorParameters) {
        this.colorParameters = colorParameters;
    }

    public ColorTemperature getColorTemperature() {
        return this.colorTemperature;
    }

    public void setColorTemperature(ColorTemperature colorTemperature) {
        this.colorTemperature = colorTemperature;
    }

    public ColorTemperature100K getColorTemperature100K() {
        return this.colorTemperature100K;
    }

    public void setColorTemperature100K(ColorTemperature100K colorTemperature100K) {
        this.colorTemperature100K = colorTemperature100K;
    }

    public Failovers getFailovers() {
        return this.failovers;
    }

    public void setFailovers(Failovers failovers) {
        this.failovers = failovers;
    }

    public FanSpeed getFanSpeed() {
        return this.fanSpeed;
    }

    public void setFanSpeed(FanSpeed fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public InputSource getInputSource() {
        return this.inputSource;
    }

    public void setInputSource(InputSource inputSource) {
        this.putOrignalData("InputSource", this.inputSource);
        this.inputSource = inputSource;
    }

    public KeypadLockState getKeypadLockState() {
        return this.keypadLockState;
    }

    public void setKeypadLockState(KeypadLockState keypadLockState) {
        this.keypadLockState = keypadLockState;
    }

    public LightSensor getLightSensor() {
        return this.lightSensor;
    }

    public void setLightSensor(LightSensor lightSensor) {
        this.lightSensor = lightSensor;
    }

    public MEMCEffect getMemcEffect() {
        return this.memcEffect;
    }

    public void setMemcEffect(MEMCEffect memcEffect) {
        this.memcEffect = memcEffect;
    }

    public Miscellaneous getMiscellaneous() {
        return this.miscellaneous;
    }

    public void setMiscellaneous(Miscellaneous miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public NoiseReduction getNoiseReduction() {
        return this.noiseReduction;
    }

    public void setNoiseReduction(NoiseReduction noiseReduction) {
        this.noiseReduction = noiseReduction;
    }

    public IntWrapper getOsdInformation() {
        return this.osdInformation;
    }

    public void setOsdInformation(IntWrapper osdInformation) {
        this.osdInformation = osdInformation;
    }

    public OSDRotating getOsdRotating() {
        return this.osdRotating;
    }

    public void setOsdRotating(OSDRotating osdRotating) {
        this.osdRotating = osdRotating;
    }

    public PictureFormat getPictureFormat() {
        return this.pictureFormat;
    }

    public void setPictureFormat(PictureFormat pictureFormat) {
        this.pictureFormat = pictureFormat;
    }

    public PictureInPicture getPictureInPicture() {
        return this.pictureInPicture;
    }

    public void setPictureInPicture(PictureInPicture pictureInPicture) {
        this.pictureInPicture = pictureInPicture;
    }

    public PictureInPictureSource getPictureInPictureSource() {
        return this.pictureInPictureSource;
    }

    public void setPictureInPictureSource(PictureInPictureSource pictureInPictureSource) {
        this.pictureInPictureSource = pictureInPictureSource;
    }

    public PowerOnLogo getPowerOnLogo() {
        return this.powerOnLogo;
    }

    public void setPowerOnLogo(PowerOnLogo powerOnLogo) {
        this.powerOnLogo = powerOnLogo;
    }

    public PowerSavingMode getPowerSavingMode() {
        return this.powerSavingMode;
    }

    public void setPowerSavingMode(PowerSavingMode powerSavingMode) {
        this.powerSavingMode = powerSavingMode;
    }

    public DisplayOrientation getDisplayOrientation() {
        return this.displayOrientation;
    }

    public void setDisplayOrientation(DisplayOrientation displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    public LedStrips getLedStrips() {
        return this.ledStrips;
    }

    public void setLedStrips(LedStrips ledStrips) {
        this.ledStrips = ledStrips;
    }

    public PortStatus getPortStatus() {
        return this.portStatus;
    }

    public void setPortStatus(PortStatus portStatus) {
        this.portStatus = portStatus;
    }

    public PowerState getPowerState() {
        return this.powerState;
    }

    public void setPowerState(PowerState powerState) {
        this.putOrignalData("PowerState", this.powerState);
        this.powerState = powerState;
    }

    public PowerStateAtColdStart getPowerStateAtColdStart() {
        return this.powerStateAtColdStart;
    }

    public void setPowerStateAtColdStart(PowerStateAtColdStart powerStateAtColdStart) {
        this.powerStateAtColdStart = powerStateAtColdStart;
    }

    public RemoteControlLockState getRemoteControlLockState() {
        return this.remoteControlLockState;
    }

    public void setRemoteControlLockState(RemoteControlLockState remoteControlLockState) {
        this.remoteControlLockState = remoteControlLockState;
    }

    public ScanConversion getScanConversion() {
        return this.scanConversion;
    }

    public void setScanConversion(ScanConversion scanConversion) {
        this.scanConversion = scanConversion;
    }

    public ScanMode getScanMode() {
        return this.scanMode;
    }

    public void setScanMode(ScanMode scanMode) {
        this.scanMode = scanMode;
    }

    public SmartPower getSmartPower() {
        return this.smartPower;
    }

    public void setSmartPower(SmartPower smartPower) {
        this.smartPower = smartPower;
    }

    public SwitchOnDelay getSwitchOnDelay() {
        return this.switchOnDelay;
    }

    public void setSwitchOnDelay(SwitchOnDelay switchOnDelay) {
        this.switchOnDelay = switchOnDelay;
    }

    public TemperatureSensor getTemperature() {
        return this.temperature;
    }

    public void setTemperature(TemperatureSensor temperature) {
        this.temperature = temperature;
    }

    public Tiling getTiling() {
        return this.tiling;
    }

    public void setTiling(Tiling tiling) {
        this.tiling = tiling;
    }

    public IntWrapper getFrameCompensationHorizontal() {
        return this.frameCompensationHorizontal;
    }

    public void setFrameCompensationHorizontal(IntWrapper frameCompensationHorizontal) {
        this.frameCompensationHorizontal = frameCompensationHorizontal;
    }

    public IntWrapper getFrameCompensationVertical() {
        return this.frameCompensationVertical;
    }

    public void setFrameCompensationVertical(IntWrapper frameCompensationVertical) {
        this.frameCompensationVertical = frameCompensationVertical;
    }

    public MatrixPosition getMatrixPosition() {
        return this.matrixPosition;
    }

    public void setMatrixPosition(MatrixPosition matrixPosition) {
        this.matrixPosition = matrixPosition;
    }

    public Touch getTouch() {
        return this.touch;
    }

    public void setTouch(Touch touch) {
        this.touch = touch;
    }

    public VGAVideoParameters getVgaVideoParameters() {
        return this.vgaVideoParameters;
    }

    public void setVgaVideoParameters(VGAVideoParameters vgaVideoParameters) {
        this.vgaVideoParameters = vgaVideoParameters;
    }

    public VideoParameters getVideoParameters() {
        return this.videoParameters;
    }

    public void setVideoParameters(VideoParameters videoParameters) {
        this.videoParameters = videoParameters;
    }

    public Volume getVolume() {
        return this.volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public VolumeLimits getVolumeLimitsSpeakerOut() {
        return this.volumeLimitsSpeakerOut;
    }

    public void setVolumeLimitsSpeakerOut(VolumeLimits volumeLimitsSpeakerOut) {
        this.volumeLimitsSpeakerOut = volumeLimitsSpeakerOut;
    }

    public VolumeLimits getVolumeLimitsAudioOut() {
        return this.volumeLimitsAudioOut;
    }

    public void setVolumeLimitsAudioOut(VolumeLimits volumeLimitsAudioOut) {
        this.volumeLimitsAudioOut = volumeLimitsAudioOut;
    }

    public LockUsb getLockUsb() {
        return this.lockUsb;
    }

    public void setLockUsb(LockUsb lockUsb) {
        this.lockUsb = lockUsb;
    }

    public PixelShift getPixelShift() {
        return this.pixelShift;
    }

    public void setPixelShift(PixelShift pixelShift) {
        this.pixelShift = pixelShift;
    }

    public IntWrapper getOffTimer() {
        return this.offTimer;
    }

    public void setOffTimer(IntWrapper offTimer) {
        this.offTimer = offTimer;
    }

    public IntWrapper getHumanSensor() {
        return this.humanSensor;
    }

    public void setHumanSensor(IntWrapper humanSensor) {
        this.humanSensor = humanSensor;
    }

    public EcoMode getEcoMode() {
        return this.ecoMode;
    }

    public void setEcoMode(EcoMode ecoMode) {
        this.ecoMode = ecoMode;
    }

    public PictureStyle getPictureStyle() {
        return this.pictureStyle;
    }

    public void setPictureStyle(PictureStyle pictureStyle) {
        this.pictureStyle = pictureStyle;
    }

    public Mute getMute() {
        return this.mute;
    }

    public void setMute(Mute mute) {
        this.mute = mute;
    }

    public SchedulingParameters getSchedulingParameters() {
        return this.schedulingParameters;
    }

    public void setSchedulingParameters(SchedulingParameters schedulingParameters) {
        this.schedulingParameters = schedulingParameters;
    }

    public boolean isContentRotated() {
        return this.contentRotated;
    }

    public void setContentRotated(boolean contentRotated) {
        this.contentRotated = contentRotated;
    }

    public FtpSettings getFtpSettings() {
        return this.ftpSettings;
    }

    public void setFtpSettings(FtpSettings ftpSettings) {
        this.ftpSettings = ftpSettings;
    }

    public VideoPresent getVideoPresent() {
        return this.videoPresent;
    }

    public void setVideoPresent(VideoPresent videoPresent) {
        this.videoPresent = videoPresent;
    }

    public Backlight getBacklight() {
        return this.backlight;
    }

    public void setBacklight(Backlight backlight) {
        this.backlight = backlight;
    }

    public NavigationBar getNavigationBar() {
        return this.navigationBar;
    }

    public void setNavigationBar(NavigationBar navigationBar) {
        this.navigationBar = navigationBar;
    }

    public BootOnSource getBootOnSource() {
        return this.bootOnSource;
    }

    public void setBootOnSource(BootOnSource bootOnSource) {
        this.bootOnSource = bootOnSource;
    }

    public boolean isNeedWriteMetricsLog() {
        boolean isNeedLog = this.checkNeedWriteMetricsLog();
        this.orignalData.clear();
        return isNeedLog;
    }

    private boolean checkNeedWriteMetricsLog() {
        for (Map.Entry<String, Object> entry : this.orignalData.entrySet()) {
            PowerState oriPowerState;
            boolean isSame;
            String key = entry.getKey();
            if ("FirmwareVersion".equalsIgnoreCase(key)) {
                StringWrapper oriFirmwareVersion = (StringWrapper)entry.getValue();
                isSame = false;
                if (this.firmwareVersion == null && oriFirmwareVersion == null) {
                    isSame = true;
                } else if (this.firmwareVersion != null && oriFirmwareVersion != null) {
                    isSame = this.firmwareVersion.equals(oriFirmwareVersion);
                }
                if (isSame) continue;
                return true;
            }
            if ("FirmwareVersionAndroid".equalsIgnoreCase(key)) {
                StringWrapper oriFirmwareVersionAndroid = (StringWrapper)entry.getValue();
                isSame = false;
                if (this.firmwareVersionAndroid == null && oriFirmwareVersionAndroid == null) {
                    isSame = true;
                } else if (this.firmwareVersionAndroid != null && oriFirmwareVersionAndroid != null) {
                    isSame = this.firmwareVersionAndroid.equals(oriFirmwareVersionAndroid);
                }
                if (isSame) continue;
                return true;
            }
            if ("InputSource".equalsIgnoreCase(key)) {
                InputSource oriInputSource = (InputSource)entry.getValue();
                isSame = false;
                if (this.inputSource == null && oriInputSource == null) {
                    isSame = true;
                } else if (this.inputSource != null && oriInputSource != null) {
                    boolean bl = isSame = this.inputSource.getSourceType() == oriInputSource.getSourceType();
                }
                if (isSame) continue;
                return true;
            }
            if (!"PowerState".equalsIgnoreCase(key) || (isSame = this.powerState == (oriPowerState = (PowerState)entry.getValue()))) continue;
            return true;
        }
        return false;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Device)) {
            return false;
        }
        Device device = (Device)object;
        return new EqualsBuilder().append(this.address, device.address).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.address);
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("address", this.address).append("name", this.name).append("modelNumber", this.modelNumber).append("firmwareVersion", this.firmwareVersion).append("buildDate", this.buildDate).append("firmwareVersionAndroid", this.firmwareVersionAndroid).append("serialCode", this.serialCode).append("sicpVersion", this.sicpVersion).append("platformLabel", this.platformLabel).append("platformVersion", this.platformVersion).append("apm", this.apm).append("audioParameters", this.audioParameters).append("autoSignalDetecting", this.autoSignalDetecting).append("colorParameters", this.colorParameters).append("colorTemperature", this.colorTemperature).append("colorTemperature100K", this.colorTemperature100K).append("failovers", this.failovers).append("fanSpeed", this.fanSpeed).append("inputSource", this.inputSource).append("keypadLockState", this.keypadLockState).append("lightSensor", this.lightSensor).append("memcEffect", this.memcEffect).append("miscellaneous", this.miscellaneous).append("noiseReduction", this.noiseReduction).append("osdInformation", this.osdInformation).append("osdRotating", this.osdRotating).append("pictureFormat", this.pictureFormat).append("pictureInPicture", this.pictureInPicture).append("pictureInPictureSource", this.pictureInPictureSource).append("powerOnLogo", this.powerOnLogo).append("powerSavingMode", this.powerSavingMode).append("displayOrientation", this.displayOrientation).append("portStatus", this.portStatus).append("ledStrips", this.ledStrips).append("powerState", this.powerState).append("powerStateAtColdStart", this.powerStateAtColdStart).append("remoteControlLockState", this.remoteControlLockState).append("scanConversion", this.scanConversion).append("scanMode", this.scanMode).append("smartPower", this.smartPower).append("switchOnDelay", this.switchOnDelay).append("temperature", this.temperature).append("tiling", this.tiling).append("frameCompensationHorizontal", this.frameCompensationHorizontal).append("frameCompensationVertical", this.frameCompensationVertical).append("matrixPosition", this.matrixPosition).append("touch", this.touch).append("vgaVideoParameters", this.vgaVideoParameters).append("videoParameters", this.videoParameters).append("volume", this.volume).append("volumeLimitsSpeakerOut", this.volumeLimitsSpeakerOut).append("volumeLimitsAudioOut", this.volumeLimitsAudioOut).append("schedulingParameters", this.schedulingParameters).append("contentRotated", this.contentRotated).append("ftpSettings", this.ftpSettings).append("ecoMode", this.ecoMode).append("pictureStyle", this.pictureStyle).append("mute", this.mute).append("lockUsb", this.lockUsb).append("pixelShift", this.pixelShift).append("offTime", this.offTimer).append("humanSensor", this.humanSensor).append("videoPresent", this.videoPresent).append("backlight", this.backlight).append("navigationBar", this.navigationBar).append("bootOnSource", this.bootOnSource).toString();
    }
}

