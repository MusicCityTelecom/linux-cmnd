package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioParameters;
import be.tpvision.smartcontrol.domain.device_settings.audio.AudioSync;
import be.tpvision.smartcontrol.domain.device_settings.audio.Mute;
import be.tpvision.smartcontrol.domain.device_settings.audio.SpeakersStatus;
import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;

public interface AudioService {
   Volume getVolume(Device device);

   void setVolume(Device device, Volume volume);

   void setVolume(Device device, VolumeUpDown volumeUpDown);

   VolumeLimits getVolumeLimitsSpeakerOut(Device device);

   void setVolumeLimitsSpeakerOut(Device device, VolumeLimits volumeLimits);

   VolumeLimits getVolumeLimitsAudioOut(Device device);

   void setVolumeLimitsAudioOut(Device device, VolumeLimits volumeLimits);

   AudioParameters getAudioParameters(Device device);

   void setAudioParameters(Device device, AudioParameters audioParameters);

   Mute getMute(Device device);

   void setMute(Device device, Mute mute);

   AudioSync getAudioSync(Device device);

   void setAudioSync(Device device, AudioSync audioSync);

   SpeakersStatus getSpeakersStatus(Device device);

   void setSpeakersStatus(Device device, SpeakersStatus speakersStatus);
}
