package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;
import be.tpvision.smartcontrol.messages.services.audio.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.audio.SetAudioParametersMessages;
import be.tpvision.smartcontrol.messages.services.audio.SetMuteMessages;
import be.tpvision.smartcontrol.messages.services.audio.SetVolumeLimitsAudioOutMessages;
import be.tpvision.smartcontrol.messages.services.audio.SetVolumeLimitsSpeakerOutMessages;
import be.tpvision.smartcontrol.messages.services.audio.SetVolumeMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile("production")
public class AudioServiceImpl implements AudioService {
   private final CommandService commandService;

   @Autowired
   AudioServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   @Override
   public Volume getVolume(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.VOLUME, Volume.class);
   }

   @Override
   public void setVolume(final Device device, final Volume volume) {
      Assert.notNull(device, SetVolumeMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(volume, SetVolumeMessages.VOLUME_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VOLUME, volume);
   }

   @Override
   public void setVolume(final Device device, final VolumeUpDown volumeUpDown) {
      Assert.notNull(device, SetVolumeMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(volumeUpDown, SetVolumeMessages.VOLUME_UP_DOWN_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VOLUME_UP_DOWN, VolumeUpDown.class);
   }

   @Override
   public VolumeLimits getVolumeLimitsSpeakerOut(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, VolumeLimits.class);
   }

   @Override
   public void setVolumeLimitsSpeakerOut(final Device device, final VolumeLimits volumeLimits) {
      Assert.notNull(device, SetVolumeLimitsSpeakerOutMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(volumeLimits, SetVolumeLimitsSpeakerOutMessages.VOLUME_LIMITS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VOLUME_LIMITS_SPEAKER_OUT, volumeLimits);
   }

   @Override
   public VolumeLimits getVolumeLimitsAudioOut(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, VolumeLimits.class);
   }

   @Override
   public void setVolumeLimitsAudioOut(final Device device, final VolumeLimits volumeLimits) {
      Assert.notNull(device, SetVolumeLimitsAudioOutMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(volumeLimits, SetVolumeLimitsAudioOutMessages.VOLUME_LIMITS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VOLUME_LIMITS_AUDIO_OUT, volumeLimits);
   }

   @Override
   public AudioParameters getAudioParameters(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.AUDIO_PARAMETERS, AudioParameters.class);
   }

   @Override
   public void setAudioParameters(final Device device, final AudioParameters audioParameters) {
      Assert.notNull(device, SetAudioParametersMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(audioParameters, SetAudioParametersMessages.AUDIO_PARAMETERS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.AUDIO_PARAMETERS, audioParameters);
   }

   @Override
   public Mute getMute(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.MUTE, Mute.class);
   }

   @Override
   public void setMute(Device device, Mute mute) {
      Assert.notNull(device, SetMuteMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(mute, SetMuteMessages.MUTE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.MUTE, mute);
   }

   @Override
   public AudioSync getAudioSync(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.AUDIO_SYNC, AudioSync.class);
   }

   @Override
   public void setAudioSync(Device device, AudioSync audioSync) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.AUDIO_SYNC, audioSync);
   }

   @Override
   public SpeakersStatus getSpeakersStatus(Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.SPEAKERS_STATUS, SpeakersStatus.class);
   }

   @Override
   public void setSpeakersStatus(Device device, SpeakersStatus speakersStatus) {
      this.commandService.send(device, Command.Type.SET, Command.Setting.SPEAKERS_STATUS, speakersStatus);
   }
}
