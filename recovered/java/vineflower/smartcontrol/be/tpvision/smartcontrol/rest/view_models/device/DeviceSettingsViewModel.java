package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.audio.AudioParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.DeviceLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.input_sources.InputSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.BootOnSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.DisplayOrientationViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.PixelShiftViewModel;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.TilingViewModel;
import be.tpvision.smartcontrol.rest.view_models.scheduling.SchedulingParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.ColorParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureSourceViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VGAVideoParametersViewModel;
import be.tpvision.smartcontrol.rest.view_models.video.VideoParametersViewModel;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceSettingsViewModel {
   private Long id;
   private String name;
   private String modelNumber;
   private String powerState;
   private String remoteControlLockState;
   private String keypadLockState;
   private String powerStateAtColdStart;
   private String backlight;
   private InputSourceViewModel inputSource;
   private String autoSignalDetecting;
   private List<String> failovers;
   private VideoParametersViewModel videoParameters;
   private String colorTemperature;
   private ColorParametersViewModel colorParameters;
   private String colorTemperature100K;
   private String pictureFormat;
   private VGAVideoParametersViewModel vgaVideoParameters;
   private PictureInPictureViewModel pictureInPicture;
   private PictureInPictureSourceViewModel pictureInPictureSource;
   private VolumeViewModel volume;
   private VolumeLimitsViewModel volumeLimitsSpeakerOut;
   private VolumeLimitsViewModel volumeLimitsAudioOut;
   private AudioParametersViewModel audioParameters;
   private String smartPower;
   private TilingViewModel tiling;
   private Integer frameCompensationHorizontal;
   private Integer frameCompensationVertical;
   private String lightSensor;
   private String osdRotating;
   private Integer osdInformation;
   private String memcEffect;
   private String touch;
   private String noiseReduction;
   private String scanMode;
   private String scanConversion;
   private String switchOnDelay;
   private String powerOnLogo;
   private String fanSpeed;
   private String apm;
   private String powerSavingMode;
   private DisplayOrientationViewModel displayOrientation;
   private String lockUsb;
   private String ecoMode;
   private String mute;
   private String pictureStyle;
   private Integer offTimer;
   private Integer humanSensor;
   private PixelShiftViewModel pixelShift;
   private String videoPresent;
   private String navigationBar;
   private BootOnSourceViewModel bootOnSource;
   private SchedulingParametersViewModel schedulingParameters;
   private DeviceLimitsViewModel deviceLimits = new DeviceLimitsViewModel();

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(final String modelNumber) {
      this.modelNumber = modelNumber;
   }

   public String getPowerState() {
      return this.powerState;
   }

   public void setPowerState(final String powerState) {
      this.powerState = powerState;
   }

   public String getRemoteControlLockState() {
      return this.remoteControlLockState;
   }

   public void setRemoteControlLockState(final String remoteControlLockState) {
      this.remoteControlLockState = remoteControlLockState;
   }

   public String getKeypadLockState() {
      return this.keypadLockState;
   }

   public void setKeypadLockState(final String keypadLockState) {
      this.keypadLockState = keypadLockState;
   }

   public String getPowerStateAtColdStart() {
      return this.powerStateAtColdStart;
   }

   public void setPowerStateAtColdStart(final String powerStateAtColdStart) {
      this.powerStateAtColdStart = powerStateAtColdStart;
   }

   public String getBacklight() {
      return this.backlight;
   }

   public void setBacklight(String backlight) {
      this.backlight = backlight;
   }

   public InputSourceViewModel getInputSource() {
      return this.inputSource;
   }

   public void setInputSource(InputSourceViewModel inputSource) {
      this.inputSource = inputSource;
   }

   public String getAutoSignalDetecting() {
      return this.autoSignalDetecting;
   }

   public void setAutoSignalDetecting(final String autoSignalDetecting) {
      this.autoSignalDetecting = autoSignalDetecting;
   }

   public List<String> getFailovers() {
      return this.failovers;
   }

   public void setFailovers(final List<String> failovers) {
      this.failovers = failovers;
   }

   public VideoParametersViewModel getVideoParameters() {
      return this.videoParameters;
   }

   public void setVideoParameters(final VideoParametersViewModel videoParameters) {
      this.videoParameters = videoParameters;
   }

   public String getColorTemperature() {
      return this.colorTemperature;
   }

   public void setColorTemperature(final String colorTemperature) {
      this.colorTemperature = colorTemperature;
   }

   public ColorParametersViewModel getColorParameters() {
      return this.colorParameters;
   }

   public void setColorParameters(final ColorParametersViewModel colorParameters) {
      this.colorParameters = colorParameters;
   }

   public String getColorTemperature100K() {
      return this.colorTemperature100K;
   }

   public void setColorTemperature100K(final String colorTemperature100K) {
      this.colorTemperature100K = colorTemperature100K;
   }

   public String getPictureFormat() {
      return this.pictureFormat;
   }

   public void setPictureFormat(final String pictureFormat) {
      this.pictureFormat = pictureFormat;
   }

   public VGAVideoParametersViewModel getVgaVideoParameters() {
      return this.vgaVideoParameters;
   }

   public void setVgaVideoParameters(final VGAVideoParametersViewModel vgaVideoParameters) {
      this.vgaVideoParameters = vgaVideoParameters;
   }

   public PictureInPictureViewModel getPictureInPicture() {
      return this.pictureInPicture;
   }

   public void setPictureInPicture(final PictureInPictureViewModel pictureInPicture) {
      this.pictureInPicture = pictureInPicture;
   }

   public PictureInPictureSourceViewModel getPictureInPictureSource() {
      return this.pictureInPictureSource;
   }

   public void setPictureInPictureSource(final PictureInPictureSourceViewModel pictureInPictureSource) {
      this.pictureInPictureSource = pictureInPictureSource;
   }

   public VolumeViewModel getVolume() {
      return this.volume;
   }

   public void setVolume(final VolumeViewModel volume) {
      this.volume = volume;
   }

   public VolumeLimitsViewModel getVolumeLimitsSpeakerOut() {
      return this.volumeLimitsSpeakerOut;
   }

   public void setVolumeLimitsSpeakerOut(final VolumeLimitsViewModel volumeLimitsSpeakerOut) {
      this.volumeLimitsSpeakerOut = volumeLimitsSpeakerOut;
   }

   public VolumeLimitsViewModel getVolumeLimitsAudioOut() {
      return this.volumeLimitsAudioOut;
   }

   public void setVolumeLimitsAudioOut(final VolumeLimitsViewModel volumeLimitsAudioOut) {
      this.volumeLimitsAudioOut = volumeLimitsAudioOut;
   }

   public AudioParametersViewModel getAudioParameters() {
      return this.audioParameters;
   }

   public void setAudioParameters(final AudioParametersViewModel audioParameters) {
      this.audioParameters = audioParameters;
   }

   public String getSmartPower() {
      return this.smartPower;
   }

   public void setSmartPower(final String smartPower) {
      this.smartPower = smartPower;
   }

   public TilingViewModel getTiling() {
      return this.tiling;
   }

   public void setTiling(final TilingViewModel tiling) {
      this.tiling = tiling;
   }

   public Integer getFrameCompensationHorizontal() {
      return this.frameCompensationHorizontal;
   }

   public void setFrameCompensationHorizontal(Integer frameCompensationHorizontal) {
      this.frameCompensationHorizontal = frameCompensationHorizontal;
   }

   public Integer getFrameCompensationVertical() {
      return this.frameCompensationVertical;
   }

   public void setFrameCompensationVertical(Integer frameCompensationVertical) {
      this.frameCompensationVertical = frameCompensationVertical;
   }

   public String getLightSensor() {
      return this.lightSensor;
   }

   public void setLightSensor(final String lightSensor) {
      this.lightSensor = lightSensor;
   }

   public String getOsdRotating() {
      return this.osdRotating;
   }

   public void setOsdRotating(final String osdRotating) {
      this.osdRotating = osdRotating;
   }

   public Integer getOsdInformation() {
      return this.osdInformation;
   }

   public void setOsdInformation(final Integer osdInformation) {
      this.osdInformation = osdInformation;
   }

   public String getMemcEffect() {
      return this.memcEffect;
   }

   public void setMemcEffect(final String memcEffect) {
      this.memcEffect = memcEffect;
   }

   public String getTouch() {
      return this.touch;
   }

   public void setTouch(final String touch) {
      this.touch = touch;
   }

   public String getNoiseReduction() {
      return this.noiseReduction;
   }

   public void setNoiseReduction(final String noiseReduction) {
      this.noiseReduction = noiseReduction;
   }

   public String getScanMode() {
      return this.scanMode;
   }

   public void setScanMode(final String scanMode) {
      this.scanMode = scanMode;
   }

   public String getScanConversion() {
      return this.scanConversion;
   }

   public void setScanConversion(final String scanConversion) {
      this.scanConversion = scanConversion;
   }

   public String getSwitchOnDelay() {
      return this.switchOnDelay;
   }

   public void setSwitchOnDelay(final String switchOnDelay) {
      this.switchOnDelay = switchOnDelay;
   }

   public String getPowerOnLogo() {
      return this.powerOnLogo;
   }

   public void setPowerOnLogo(final String powerOnLogo) {
      this.powerOnLogo = powerOnLogo;
   }

   public String getFanSpeed() {
      return this.fanSpeed;
   }

   public void setFanSpeed(final String fanSpeed) {
      this.fanSpeed = fanSpeed;
   }

   public String getApm() {
      return this.apm;
   }

   public void setApm(final String apm) {
      this.apm = apm;
   }

   public String getPowerSavingMode() {
      return this.powerSavingMode;
   }

   public void setPowerSavingMode(final String powerSavingMode) {
      this.powerSavingMode = powerSavingMode;
   }

   public DisplayOrientationViewModel getDisplayOrientation() {
      return this.displayOrientation;
   }

   public void setDisplayOrientation(final DisplayOrientationViewModel displayOrientation) {
      this.displayOrientation = displayOrientation;
   }

   public String getLockUsb() {
      return this.lockUsb;
   }

   public void setLockUsb(String lockUsb) {
      this.lockUsb = lockUsb;
   }

   public String getEcoMode() {
      return this.ecoMode;
   }

   public void setEcoMode(String ecoMode) {
      this.ecoMode = ecoMode;
   }

   public String getMute() {
      return this.mute;
   }

   public void setMute(String mute) {
      this.mute = mute;
   }

   public String getPictureStyle() {
      return this.pictureStyle;
   }

   public void setPictureStyle(String pictureStyle) {
      this.pictureStyle = pictureStyle;
   }

   public Integer getOffTimer() {
      return this.offTimer;
   }

   public void setOffTimer(Integer offTimer) {
      this.offTimer = offTimer;
   }

   public Integer getHumanSensor() {
      return this.humanSensor;
   }

   public void setHumanSensor(Integer humanSensor) {
      this.humanSensor = humanSensor;
   }

   public PixelShiftViewModel getPixelShift() {
      return this.pixelShift;
   }

   public void setPixelShift(PixelShiftViewModel pixelShift) {
      this.pixelShift = pixelShift;
   }

   public String getVideoPresent() {
      return this.videoPresent;
   }

   public void setVideoPresent(String videoPresent) {
      this.videoPresent = videoPresent;
   }

   public String getNavigationBar() {
      return this.navigationBar;
   }

   public void setNavigationBar(String navigationBar) {
      this.navigationBar = navigationBar;
   }

   public SchedulingParametersViewModel getSchedulingParameters() {
      return this.schedulingParameters;
   }

   public void setSchedulingParameters(final SchedulingParametersViewModel schedulingParameters) {
      this.schedulingParameters = schedulingParameters;
   }

   public DeviceLimitsViewModel getDeviceLimits() {
      return this.deviceLimits;
   }

   public void setDeviceLimits(final DeviceLimitsViewModel deviceLimits) {
      this.deviceLimits = deviceLimits;
   }

   public BootOnSourceViewModel getBootOnSource() {
      return this.bootOnSource;
   }

   public void setBootOnSource(BootOnSourceViewModel bootOnSource) {
      this.bootOnSource = bootOnSource;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceSettingsViewModel)) {
         return false;
      }

      DeviceSettingsViewModel that = (DeviceSettingsViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getName(), that.getName())
         .append(this.getModelNumber(), that.getModelNumber())
         .append(this.getPowerState(), that.getPowerState())
         .append(this.getRemoteControlLockState(), that.getRemoteControlLockState())
         .append(this.getKeypadLockState(), that.getKeypadLockState())
         .append(this.getPowerStateAtColdStart(), that.getPowerStateAtColdStart())
         .append(this.getBacklight(), that.getBacklight())
         .append(this.getInputSource(), that.getInputSource())
         .append(this.getAutoSignalDetecting(), that.getAutoSignalDetecting())
         .append(this.getFailovers(), that.getFailovers())
         .append(this.getVideoParameters(), that.getVideoParameters())
         .append(this.getColorTemperature(), that.getColorTemperature())
         .append(this.getColorParameters(), that.getColorParameters())
         .append(this.getColorTemperature100K(), that.getColorTemperature100K())
         .append(this.getPictureFormat(), that.getPictureFormat())
         .append(this.getVgaVideoParameters(), that.getVgaVideoParameters())
         .append(this.getPictureInPicture(), that.getPictureInPicture())
         .append(this.getPictureInPictureSource(), that.getPictureInPictureSource())
         .append(this.getVolume(), that.getVolume())
         .append(this.getVolumeLimitsSpeakerOut(), that.getVolumeLimitsSpeakerOut())
         .append(this.getVolumeLimitsAudioOut(), that.getVolumeLimitsAudioOut())
         .append(this.getAudioParameters(), that.getAudioParameters())
         .append(this.getSmartPower(), that.getSmartPower())
         .append(this.getTiling(), that.getTiling())
         .append(this.getFrameCompensationHorizontal(), that.getFrameCompensationHorizontal())
         .append(this.getFrameCompensationVertical(), that.getFrameCompensationVertical())
         .append(this.getLightSensor(), that.getLightSensor())
         .append(this.getOsdRotating(), that.getOsdRotating())
         .append(this.getOsdInformation(), that.getOsdInformation())
         .append(this.getMemcEffect(), that.getMemcEffect())
         .append(this.getTouch(), that.getTouch())
         .append(this.getNoiseReduction(), that.getNoiseReduction())
         .append(this.getScanMode(), that.getScanMode())
         .append(this.getScanConversion(), that.getScanConversion())
         .append(this.getSwitchOnDelay(), that.getSwitchOnDelay())
         .append(this.getPowerOnLogo(), that.getPowerOnLogo())
         .append(this.getFanSpeed(), that.getFanSpeed())
         .append(this.getApm(), that.getApm())
         .append(this.getPowerSavingMode(), that.getPowerSavingMode())
         .append(this.getDisplayOrientation(), that.getDisplayOrientation())
         .append(this.getLockUsb(), that.getLockUsb())
         .append(this.getEcoMode(), that.getEcoMode())
         .append(this.getMute(), this.getMute())
         .append(this.getPictureStyle(), that.getPictureStyle())
         .append(this.getOffTimer(), that.getOffTimer())
         .append(this.getHumanSensor(), that.getHumanSensor())
         .append(this.getPixelShift(), that.getPixelShift())
         .append(this.getVideoPresent(), that.getVideoPresent())
         .append(this.getNavigationBar(), that.getNavigationBar())
         .append(this.getSchedulingParameters(), that.getSchedulingParameters())
         .append(this.getDeviceLimits(), that.getDeviceLimits())
         .append(this.getBootOnSource(), that.getBootOnSource())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getId(),
         this.getName(),
         this.getModelNumber(),
         this.getPowerState(),
         this.getRemoteControlLockState(),
         this.getKeypadLockState(),
         this.getPowerStateAtColdStart(),
         this.getBacklight(),
         this.getInputSource(),
         this.getAutoSignalDetecting(),
         this.getFailovers(),
         this.getVideoParameters(),
         this.getColorTemperature(),
         this.getColorParameters(),
         this.getColorTemperature100K(),
         this.getPictureFormat(),
         this.getVgaVideoParameters(),
         this.getPictureInPicture(),
         this.getPictureInPictureSource(),
         this.getVolume(),
         this.getVolumeLimitsSpeakerOut(),
         this.getVolumeLimitsAudioOut(),
         this.getAudioParameters(),
         this.getSmartPower(),
         this.getTiling(),
         this.getFrameCompensationHorizontal(),
         this.getFrameCompensationVertical(),
         this.getLightSensor(),
         this.getOsdRotating(),
         this.getOsdInformation(),
         this.getMemcEffect(),
         this.getTouch(),
         this.getNoiseReduction(),
         this.getScanMode(),
         this.getScanConversion(),
         this.getSwitchOnDelay(),
         this.getPowerOnLogo(),
         this.getFanSpeed(),
         this.getApm(),
         this.getPowerSavingMode(),
         this.getDisplayOrientation(),
         this.getLockUsb(),
         this.getEcoMode(),
         this.getMute(),
         this.getPictureStyle(),
         this.getOffTimer(),
         this.getHumanSensor(),
         this.getPixelShift(),
         this.getVideoPresent(),
         this.getNavigationBar(),
         this.getSchedulingParameters(),
         this.getBootOnSource(),
         this.getDeviceLimits()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("name", this.getName())
         .append("modelNumber", this.getModelNumber())
         .append("powerState", this.getPowerState())
         .append("remoteControlLockState", this.getRemoteControlLockState())
         .append("keypadLockState", this.getKeypadLockState())
         .append("powerStateAtColdStart", this.getPowerStateAtColdStart())
         .append("backlight", this.getBacklight())
         .append("inputSource", this.getInputSource())
         .append("autoSignalDetecting", this.getAutoSignalDetecting())
         .append("failovers", this.getFailovers())
         .append("videoParameters", this.getVideoParameters())
         .append("colorTemperature", this.getColorTemperature())
         .append("colorParameters", this.getColorParameters())
         .append("colorTemperature100K", this.getColorTemperature100K())
         .append("pictureFormat", this.getPictureFormat())
         .append("vgaVideoParameters", this.getVgaVideoParameters())
         .append("pictureInPicture", this.getPictureInPicture())
         .append("pictureInPictureSource", this.getPictureInPictureSource())
         .append("volume", this.getVolume())
         .append("volumeLimitsSpeakerOut", this.getVolumeLimitsSpeakerOut())
         .append("volumeLimitsAudioOut", this.getVolumeLimitsAudioOut())
         .append("audioParameters", this.getAudioParameters())
         .append("smartPower", this.getSmartPower())
         .append("tiling", this.getTiling())
         .append("frameCompensationHorizontal", this.getFrameCompensationHorizontal())
         .append("frameCompensationVertical", this.getFrameCompensationVertical())
         .append("lightSensor", this.getLightSensor())
         .append("osdRotating", this.getOsdRotating())
         .append("osdInformation", this.getOsdInformation())
         .append("memcEffect", this.getMemcEffect())
         .append("touch", this.getTouch())
         .append("noiseReduction", this.getNoiseReduction())
         .append("scanMode", this.getScanMode())
         .append("scanConversion", this.getScanConversion())
         .append("switchOnDelay", this.getSwitchOnDelay())
         .append("powerOnLogo", this.getPowerOnLogo())
         .append("fanSpeed", this.getFanSpeed())
         .append("apm", this.getApm())
         .append("powerSavingMode", this.getPowerSavingMode())
         .append("displayOrientation", this.getDisplayOrientation())
         .append("lockUsb", this.getLockUsb())
         .append("ecoMode", this.getEcoMode())
         .append("mute", this.getMute())
         .append("pictureStyle", this.getPictureStyle())
         .append("offTime", this.getOffTimer())
         .append("humanSensor", this.getHumanSensor())
         .append("pixelShift", this.getPixelShift())
         .append("videoPresent", this.getVideoPresent())
         .append("navigationBar", this.getNavigationBar())
         .append("schedulingParameters", this.getSchedulingParameters())
         .append("deviceLimits", this.getDeviceLimits())
         .append("bootOnSource", this.getBootOnSource())
         .toString();
   }
}
