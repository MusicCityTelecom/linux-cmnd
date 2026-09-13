package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.general.KeypadLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.LanguageOSD;
import be.tpvision.smartcontrol.domain.device_settings.general.MonitorSystem;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerStateAtColdStart;
import be.tpvision.smartcontrol.domain.device_settings.general.RemoteControlLockState;
import be.tpvision.smartcontrol.domain.device_settings.general.TimeZone;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failover;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.APM;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.EcoMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.FanSpeed;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.HdmiOneWire;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.MEMCEffect;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NavigationBar;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.NoiseReduction;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerOnLogo;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PowerSavingMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.RS232Routing;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanConversion;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.ScanMode;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SmartPower;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.SwitchOnDelay;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoPresent;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources.InputSourceLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.scheduling.scheduling_parameters.SchedulingParametersLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceLimitsViewModel {
   private AudioParametersLimitsViewModel audioParameters = new AudioParametersLimitsViewModel();
   private VideoParametersLimitsViewModel videoParameters = new VideoParametersLimitsViewModel();
   private VGAVideoParametersLimitsViewModel vgaVideoParameters = new VGAVideoParametersLimitsViewModel();
   private LimitsViewModel osdInformation = new LimitsViewModel(0, 60);
   private LimitsViewModel offTimer;
   private IncrementalLimitsViewModel humanSensor;
   private PixelShiftLimitsViewModel pixelShift;
   private ColorParametersLimitsViewModel colorParameters = new ColorParametersLimitsViewModel();
   private EnumLimitsViewModel<PictureFormat> pictureFormat = new EnumLimitsViewModel<>(PictureFormat.class);
   private EnumLimitsViewModel<NoiseReduction> noiseReduction = new EnumLimitsViewModel<>(NoiseReduction.class);
   private EnumLimitsViewModel<MEMCEffect> memcEffect = new EnumLimitsViewModel<>(MEMCEffect.class);
   private EnumLimitsViewModel<RemoteControlLockState> remoteControlLockState = new EnumLimitsViewModel<>(RemoteControlLockState.class);
   private EnumLimitsViewModel<KeypadLockState> keypadLockState = new EnumLimitsViewModel<>(KeypadLockState.class);
   private EnumLimitsViewModel<AutoSignalDetecting> autoSignalDetecting = new EnumLimitsViewModel<>(AutoSignalDetecting.class);
   private EnumLimitsViewModel<PowerStateAtColdStart> powerStateAtColdStart = new EnumLimitsViewModel<>(PowerStateAtColdStart.class);
   private EnumLimitsViewModel<PowerSavingMode> powerSavingMode = new EnumLimitsViewModel<>(PowerSavingMode.class);
   private DisplayOrientationLimitsViewModel displayOrientation = new DisplayOrientationLimitsViewModel();
   private EnumLimitsViewModel<SmartPower> smartPower = new EnumLimitsViewModel<>(SmartPower.class);
   private EnumLimitsViewModel<SwitchOnDelay> switchOnDelay = new EnumLimitsViewModel<>(SwitchOnDelay.class);
   private EnumLimitsViewModel<APM> apm;
   private EnumLimitsViewModel<EcoMode> ecoMode;
   private EnumLimitsViewModel<PowerOnLogo> powerOnLogo = new EnumLimitsViewModel<>(PowerOnLogo.class);
   private EnumLimitsViewModel<FanSpeed> fanSpeed;
   private EnumLimitsViewModel<PictureStyle> pictureStyle;
   private PictureInPictureLimitsViewModel pictureInPicture;
   private PictureInPictureSourceLimitsViewModel pictureInPictureSource;
   private EnumLimitsViewModel<ColorTemperature> colorTemperature;
   private EnumLimitsViewModel<ColorTemperature100K> colorTemperature100K;
   private EnumLimitsViewModel<ScanConversion> scanConversion;
   private EnumLimitsViewModel<ScanMode> scanMode;
   private InputSourceLimitsViewModel inputSource;
   private TilingLimitsViewModel tiling;
   private LimitsViewModel frameCompensationHorizontal;
   private LimitsViewModel frameCompensationVertical;
   private SchedulingParametersLimitsViewModel schedulingParameters;
   private EnumLimitsViewModel<MonitorSystem> monitorRestart;
   private EnumLimitsViewModel<VideoPresent> videoPresent;
   private EnumLimitsViewModel<NavigationBar> navigationBar;
   private EnumLimitsViewModel<Failover> failover;
   private BootOnSourceLimitsViewModel bootOnSource;
   private MixedEnumLimitsViewModel<TimeZone> timeZone;
   private MixedEnumLimitsViewModel<LanguageOSD> languageOSD;
   private MixedEnumLimitsViewModel<RS232Routing> rS232Routing;
   private MixedEnumLimitsViewModel<HdmiOneWire> hdmiOneWire;

   public DeviceLimitsViewModel() {
      this.apm = new EnumLimitsViewModel<>(APM.class);
      this.ecoMode = new EnumLimitsViewModel<>(EcoMode.class);
      this.fanSpeed = new EnumLimitsViewModel<>(FanSpeed.class);
      this.pictureInPicture = new PictureInPictureLimitsViewModel();
      this.pictureInPictureSource = new PictureInPictureSourceLimitsViewModel();
      this.colorTemperature = new EnumLimitsViewModel<>(ColorTemperature.class);
      this.colorTemperature100K = new EnumLimitsViewModel<>(ColorTemperature100K.class);
      this.scanConversion = new EnumLimitsViewModel<>(ScanConversion.class);
      this.scanMode = new EnumLimitsViewModel<>(ScanMode.class);
      this.inputSource = new InputSourceLimitsViewModel();
      this.tiling = new TilingLimitsViewModel();
      this.frameCompensationHorizontal = new LimitsViewModel(0, 255);
      this.frameCompensationVertical = new LimitsViewModel(0, 255);
      this.schedulingParameters = new SchedulingParametersLimitsViewModel();
      this.offTimer = new LimitsViewModel(0, 24);
      this.humanSensor = new IncrementalLimitsViewModel(0, 6, 10);
      this.pixelShift = new PixelShiftLimitsViewModel(0, 90, 10);
      this.pictureStyle = new EnumLimitsViewModel<>(PictureStyle.class);
      this.monitorRestart = new EnumLimitsViewModel<>(MonitorSystem.class);
      this.videoPresent = new EnumLimitsViewModel<>(VideoPresent.class);
      this.navigationBar = new EnumLimitsViewModel<>(NavigationBar.class);
      this.failover = new EnumLimitsViewModel<>(Failover.class);
      this.bootOnSource = new BootOnSourceLimitsViewModel();
      this.timeZone = new MixedEnumLimitsViewModel(TimeZone.class);
      this.languageOSD = new MixedEnumLimitsViewModel(LanguageOSD.class);
      this.rS232Routing = new MixedEnumLimitsViewModel(RS232Routing.class);
      this.hdmiOneWire = new MixedEnumLimitsViewModel(HdmiOneWire.class);
   }

   public AudioParametersLimitsViewModel getAudioParameters() {
      return this.audioParameters;
   }

   public void setAudioParameters(final AudioParametersLimitsViewModel audioParametersLimitsViewModel) {
      this.audioParameters = audioParametersLimitsViewModel;
   }

   public VideoParametersLimitsViewModel getVideoParameters() {
      return this.videoParameters;
   }

   public void setVideoParameters(final VideoParametersLimitsViewModel videoParameters) {
      this.videoParameters = videoParameters;
   }

   public VGAVideoParametersLimitsViewModel getVgaVideoParameters() {
      return this.vgaVideoParameters;
   }

   public void setVgaVideoParameters(final VGAVideoParametersLimitsViewModel vgaVideoParameters) {
      this.vgaVideoParameters = vgaVideoParameters;
   }

   public LimitsViewModel getOsdInformation() {
      return this.osdInformation;
   }

   public void setOsdInformation(final LimitsViewModel osdInformation) {
      this.osdInformation = osdInformation;
   }

   public LimitsViewModel getOffTimer() {
      return this.offTimer;
   }

   public void setOffTimer(LimitsViewModel offTimer) {
      this.offTimer = offTimer;
   }

   public IncrementalLimitsViewModel getHumanSensor() {
      return this.humanSensor;
   }

   public void setHumanSensor(IncrementalLimitsViewModel humanSensor) {
      this.humanSensor = humanSensor;
   }

   public PixelShiftLimitsViewModel getPixelShift() {
      return this.pixelShift;
   }

   public void setPixelShift(PixelShiftLimitsViewModel pixelShift) {
      this.pixelShift = pixelShift;
   }

   public ColorParametersLimitsViewModel getColorParameters() {
      return this.colorParameters;
   }

   public void setColorParameters(final ColorParametersLimitsViewModel colorParameters) {
      this.colorParameters = colorParameters;
   }

   public EnumLimitsViewModel<PictureFormat> getPictureFormat() {
      return this.pictureFormat;
   }

   public void setPictureFormat(final EnumLimitsViewModel<PictureFormat> pictureFormat) {
      this.pictureFormat = pictureFormat;
   }

   public EnumLimitsViewModel<NoiseReduction> getNoiseReduction() {
      return this.noiseReduction;
   }

   public void setNoiseReduction(final EnumLimitsViewModel<NoiseReduction> noiseReduction) {
      this.noiseReduction = noiseReduction;
   }

   public EnumLimitsViewModel<MEMCEffect> getMemcEffect() {
      return this.memcEffect;
   }

   public void setMemcEffect(final EnumLimitsViewModel<MEMCEffect> memcEffect) {
      this.memcEffect = memcEffect;
   }

   public EnumLimitsViewModel<RemoteControlLockState> getRemoteControlLockState() {
      return this.remoteControlLockState;
   }

   public void setRemoteControlLockState(final EnumLimitsViewModel<RemoteControlLockState> remoteControlLockState) {
      this.remoteControlLockState = remoteControlLockState;
   }

   public EnumLimitsViewModel<KeypadLockState> getKeypadLockState() {
      return this.keypadLockState;
   }

   public void setKeypadLockState(final EnumLimitsViewModel<KeypadLockState> keypadLockState) {
      this.keypadLockState = keypadLockState;
   }

   public EnumLimitsViewModel<AutoSignalDetecting> getAutoSignalDetecting() {
      return this.autoSignalDetecting;
   }

   public void setAutoSignalDetecting(final EnumLimitsViewModel<AutoSignalDetecting> autoSignalDetecting) {
      this.autoSignalDetecting = autoSignalDetecting;
   }

   public EnumLimitsViewModel<PowerStateAtColdStart> getPowerStateAtColdStart() {
      return this.powerStateAtColdStart;
   }

   public void setPowerStateAtColdStart(final EnumLimitsViewModel<PowerStateAtColdStart> powerStateAtColdStart) {
      this.powerStateAtColdStart = powerStateAtColdStart;
   }

   public EnumLimitsViewModel<PowerSavingMode> getPowerSavingMode() {
      return this.powerSavingMode;
   }

   public void setPowerSavingMode(final EnumLimitsViewModel<PowerSavingMode> powerSavingMode) {
      this.powerSavingMode = powerSavingMode;
   }

   public EnumLimitsViewModel<PictureStyle> getPictureStyle() {
      return this.pictureStyle;
   }

   public void setPictureStyle(EnumLimitsViewModel<PictureStyle> pictureStyle) {
      this.pictureStyle = pictureStyle;
   }

   public DisplayOrientationLimitsViewModel getDisplayOrientation() {
      return this.displayOrientation;
   }

   public void setDisplayOrientation(final DisplayOrientationLimitsViewModel displayOrientation) {
      this.displayOrientation = displayOrientation;
   }

   public EnumLimitsViewModel<SmartPower> getSmartPower() {
      return this.smartPower;
   }

   public void setSmartPower(final EnumLimitsViewModel<SmartPower> smartPower) {
      this.smartPower = smartPower;
   }

   public EnumLimitsViewModel<SwitchOnDelay> getSwitchOnDelay() {
      return this.switchOnDelay;
   }

   public void setSwitchOnDelay(final EnumLimitsViewModel<SwitchOnDelay> switchOnDelay) {
      this.switchOnDelay = switchOnDelay;
   }

   public EnumLimitsViewModel<PowerOnLogo> getPowerOnLogo() {
      return this.powerOnLogo;
   }

   public void setPowerOnLogo(final EnumLimitsViewModel<PowerOnLogo> powerOnLogo) {
      this.powerOnLogo = powerOnLogo;
   }

   public EnumLimitsViewModel<APM> getApm() {
      return this.apm;
   }

   public void setApm(EnumLimitsViewModel<APM> apm) {
      this.apm = apm;
   }

   public EnumLimitsViewModel<EcoMode> getEcoMode() {
      return this.ecoMode;
   }

   public void setEcoMode(EnumLimitsViewModel<EcoMode> ecoMode) {
      this.ecoMode = ecoMode;
   }

   public EnumLimitsViewModel<FanSpeed> getFanSpeed() {
      return this.fanSpeed;
   }

   public void setFanSpeed(final EnumLimitsViewModel<FanSpeed> fanSpeed) {
      this.fanSpeed = fanSpeed;
   }

   public PictureInPictureLimitsViewModel getPictureInPicture() {
      return this.pictureInPicture;
   }

   public void setPictureInPicture(final PictureInPictureLimitsViewModel pictureInPicture) {
      this.pictureInPicture = pictureInPicture;
   }

   public PictureInPictureSourceLimitsViewModel getPictureInPictureSource() {
      return this.pictureInPictureSource;
   }

   public void setPictureInPictureSource(final PictureInPictureSourceLimitsViewModel pictureInPictureSource) {
      this.pictureInPictureSource = pictureInPictureSource;
   }

   public EnumLimitsViewModel<ColorTemperature> getColorTemperature() {
      return this.colorTemperature;
   }

   public void setColorTemperature(final EnumLimitsViewModel<ColorTemperature> colorTemperature) {
      this.colorTemperature = colorTemperature;
   }

   public EnumLimitsViewModel<ColorTemperature100K> getColorTemperature100K() {
      return this.colorTemperature100K;
   }

   public void setColorTemperature100K(final EnumLimitsViewModel<ColorTemperature100K> colorTemperature100K) {
      this.colorTemperature100K = colorTemperature100K;
   }

   public EnumLimitsViewModel<ScanConversion> getScanConversion() {
      return this.scanConversion;
   }

   public void setScanConversion(final EnumLimitsViewModel<ScanConversion> scanConversion) {
      this.scanConversion = scanConversion;
   }

   public EnumLimitsViewModel<ScanMode> getScanMode() {
      return this.scanMode;
   }

   public void setScanMode(final EnumLimitsViewModel<ScanMode> scanMode) {
      this.scanMode = scanMode;
   }

   public InputSourceLimitsViewModel getInputSource() {
      return this.inputSource;
   }

   public void setInputSource(final InputSourceLimitsViewModel inputSource) {
      this.inputSource = inputSource;
   }

   public TilingLimitsViewModel getTiling() {
      return this.tiling;
   }

   public void setTiling(final TilingLimitsViewModel tiling) {
      this.tiling = tiling;
   }

   public LimitsViewModel getFrameCompensationHorizontal() {
      return this.frameCompensationHorizontal;
   }

   public void setFrameCompensationHorizontal(LimitsViewModel frameCompensationHorizontal) {
      this.frameCompensationHorizontal = frameCompensationHorizontal;
   }

   public LimitsViewModel getFrameCompensationVertical() {
      return this.frameCompensationVertical;
   }

   public void setFrameCompensationVertical(LimitsViewModel frameCompensationVertical) {
      this.frameCompensationVertical = frameCompensationVertical;
   }

   public SchedulingParametersLimitsViewModel getSchedulingParameters() {
      return this.schedulingParameters;
   }

   public void setSchedulingParameters(SchedulingParametersLimitsViewModel schedulingParameters) {
      this.schedulingParameters = schedulingParameters;
   }

   public EnumLimitsViewModel<MonitorSystem> getMonitorRestart() {
      return this.monitorRestart;
   }

   public void setMonitorRestart(EnumLimitsViewModel<MonitorSystem> monitorRestart) {
      this.monitorRestart = monitorRestart;
   }

   public EnumLimitsViewModel<VideoPresent> getVideoPresent() {
      return this.videoPresent;
   }

   public void setVideoPresent(EnumLimitsViewModel<VideoPresent> videoPresent) {
      this.videoPresent = videoPresent;
   }

   public EnumLimitsViewModel<NavigationBar> getNavigationBar() {
      return this.navigationBar;
   }

   public void setNavigationBar(EnumLimitsViewModel<NavigationBar> navigationBar) {
      this.navigationBar = navigationBar;
   }

   public EnumLimitsViewModel<Failover> getFailover() {
      return this.failover;
   }

   public void setFailover(EnumLimitsViewModel<Failover> failover) {
      this.failover = failover;
   }

   public BootOnSourceLimitsViewModel getBootOnSource() {
      return this.bootOnSource;
   }

   public void setBootOnSource(BootOnSourceLimitsViewModel bootOnSource) {
      this.bootOnSource = bootOnSource;
   }

   public MixedEnumLimitsViewModel<TimeZone> getTimeZone() {
      return this.timeZone;
   }

   public void setTimeZone(MixedEnumLimitsViewModel<TimeZone> timeZone) {
      this.timeZone = timeZone;
   }

   public MixedEnumLimitsViewModel<LanguageOSD> getLanguageOSD() {
      return this.languageOSD;
   }

   public void setLanguageOSD(MixedEnumLimitsViewModel<LanguageOSD> languageOSD) {
      this.languageOSD = languageOSD;
   }

   public MixedEnumLimitsViewModel<RS232Routing> getrS232Routing() {
      return this.rS232Routing;
   }

   public void setrS232Routing(MixedEnumLimitsViewModel<RS232Routing> rS232Routing) {
      this.rS232Routing = rS232Routing;
   }

   public MixedEnumLimitsViewModel<HdmiOneWire> getHdmiOneWire() {
      return this.hdmiOneWire;
   }

   public void setHdmiOneWire(MixedEnumLimitsViewModel<HdmiOneWire> hdmiOneWire) {
      this.hdmiOneWire = hdmiOneWire;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceLimitsViewModel)) {
         return false;
      }

      DeviceLimitsViewModel that = (DeviceLimitsViewModel)object;
      return new EqualsBuilder()
         .append(this.audioParameters, that.audioParameters)
         .append(this.videoParameters, that.videoParameters)
         .append(this.vgaVideoParameters, that.vgaVideoParameters)
         .append(this.osdInformation, that.osdInformation)
         .append(this.offTimer, that.offTimer)
         .append(this.humanSensor, that.humanSensor)
         .append(this.pixelShift, that.pixelShift)
         .append(this.colorParameters, that.colorParameters)
         .append(this.pictureFormat, that.pictureFormat)
         .append(this.pictureStyle, that.pictureStyle)
         .append(this.noiseReduction, that.noiseReduction)
         .append(this.memcEffect, that.memcEffect)
         .append(this.remoteControlLockState, that.remoteControlLockState)
         .append(this.keypadLockState, that.keypadLockState)
         .append(this.autoSignalDetecting, that.autoSignalDetecting)
         .append(this.powerStateAtColdStart, that.powerStateAtColdStart)
         .append(this.powerSavingMode, that.powerSavingMode)
         .append(this.displayOrientation, that.displayOrientation)
         .append(this.smartPower, that.smartPower)
         .append(this.switchOnDelay, that.switchOnDelay)
         .append(this.powerOnLogo, that.powerOnLogo)
         .append(this.apm, that.apm)
         .append(this.ecoMode, that.ecoMode)
         .append(this.fanSpeed, that.fanSpeed)
         .append(this.pictureInPicture, that.pictureInPicture)
         .append(this.pictureInPictureSource, that.pictureInPictureSource)
         .append(this.colorTemperature, that.colorTemperature)
         .append(this.colorTemperature100K, that.colorTemperature100K)
         .append(this.scanConversion, that.scanConversion)
         .append(this.scanMode, that.scanMode)
         .append(this.inputSource, that.inputSource)
         .append(this.tiling, that.tiling)
         .append(this.frameCompensationHorizontal, that.frameCompensationHorizontal)
         .append(this.frameCompensationVertical, that.frameCompensationVertical)
         .append(this.schedulingParameters, that.schedulingParameters)
         .append(this.monitorRestart, that.monitorRestart)
         .append(this.videoPresent, that.videoPresent)
         .append(this.navigationBar, that.navigationBar)
         .append(this.failover, that.failover)
         .append(this.bootOnSource, that.bootOnSource)
         .append(this.timeZone, that.timeZone)
         .append(this.languageOSD, that.languageOSD)
         .append(this.rS232Routing, that.rS232Routing)
         .append(this.hdmiOneWire, that.hdmiOneWire)
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.audioParameters,
         this.videoParameters,
         this.vgaVideoParameters,
         this.osdInformation,
         this.offTimer,
         this.humanSensor,
         this.pixelShift,
         this.colorParameters,
         this.pictureFormat,
         this.pictureStyle,
         this.noiseReduction,
         this.memcEffect,
         this.remoteControlLockState,
         this.keypadLockState,
         this.autoSignalDetecting,
         this.powerStateAtColdStart,
         this.powerSavingMode,
         this.displayOrientation,
         this.smartPower,
         this.switchOnDelay,
         this.powerOnLogo,
         this.apm,
         this.ecoMode,
         this.fanSpeed,
         this.pictureInPicture,
         this.pictureInPictureSource,
         this.colorTemperature,
         this.colorTemperature100K,
         this.scanConversion,
         this.scanMode,
         this.inputSource,
         this.tiling,
         this.frameCompensationHorizontal,
         this.frameCompensationVertical,
         this.schedulingParameters,
         this.monitorRestart,
         this.videoPresent,
         this.navigationBar,
         this.failover,
         this.bootOnSource,
         this.timeZone,
         this.languageOSD,
         this.rS232Routing,
         this.hdmiOneWire
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("audioParameters", this.audioParameters)
         .append("videoParameters", this.videoParameters)
         .append("vgaVideoParameters", this.vgaVideoParameters)
         .append("osdInformation", this.osdInformation)
         .append("offTimer", this.offTimer)
         .append("humanSensor", this.humanSensor)
         .append("pixelShift", this.pixelShift)
         .append("colorParameters", this.colorParameters)
         .append("pictureFormat", this.pictureFormat)
         .append("pictureStyle", this.pictureStyle)
         .append("noiseReduction", this.noiseReduction)
         .append("memcEffect", this.memcEffect)
         .append("remoteControlLockState", this.remoteControlLockState)
         .append("keypadLockState", this.keypadLockState)
         .append("autoSignalDetecting", this.autoSignalDetecting)
         .append("powerStateAtColdStart", this.powerStateAtColdStart)
         .append("powerSavingMode", this.powerSavingMode)
         .append("displayOrientation", this.displayOrientation)
         .append("smartPower", this.smartPower)
         .append("switchOnDelay", this.switchOnDelay)
         .append("powerOnLogo", this.powerOnLogo)
         .append("apm", this.apm)
         .append("ecoMode", this.ecoMode)
         .append("fanSpeed", this.fanSpeed)
         .append("pictureInPicture", this.pictureInPicture)
         .append("pictureInPictureSource", this.pictureInPictureSource)
         .append("colorTemperature", this.colorTemperature)
         .append("colorTemperature100K", this.colorTemperature100K)
         .append("scanConversion", this.scanConversion)
         .append("scanMode", this.scanMode)
         .append("inputSource", this.inputSource)
         .append("tiling", this.tiling)
         .append("frameCompensationHorizontal", this.frameCompensationHorizontal)
         .append("frameCompensationVertical", this.frameCompensationVertical)
         .append("schedulingParameters", this.schedulingParameters)
         .append("monitorRestart", this.monitorRestart)
         .append("videoPresent", this.videoPresent)
         .append("navigationBar", this.navigationBar)
         .append("failover", this.failover)
         .append("bootOnSource", this.bootOnSource)
         .append("timeZone", this.timeZone)
         .append("languageOSD", this.languageOSD)
         .append("rS232Routing", this.rS232Routing)
         .append("hdmiOneWire", this.hdmiOneWire)
         .toString();
   }
}
