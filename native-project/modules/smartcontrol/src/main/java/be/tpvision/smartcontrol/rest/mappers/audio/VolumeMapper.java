package be.tpvision.smartcontrol.rest.mappers.audio;

import be.tpvision.smartcontrol.domain.device_settings.audio.Volume;
import be.tpvision.smartcontrol.messages.mappers.audio.volume.ToVolumeMessages;
import be.tpvision.smartcontrol.messages.mappers.audio.volume.ToVolumeViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeViewModel;
import org.springframework.util.Assert;

public class VolumeMapper {
   private VolumeMapper() {
   }

   public static VolumeViewModel toVolumeViewModel(final Volume volume) {
      Assert.notNull(volume, ToVolumeViewModelMessages.VOLUME_CAN_NOT_BE_NULL);
      int speakerOut = volume.getSpeakerOut();
      int audioOut = volume.getAudioOut();
      return new VolumeViewModel(speakerOut, audioOut);
   }

   public static Volume toVolume(final VolumeViewModel volumeViewModel) {
      Assert.notNull(volumeViewModel, ToVolumeMessages.VOLUME_VIEW_MODEL_CAN_NOT_BE_NULL);
      int speakerOut = volumeViewModel.getSpeakerOut();
      int audioOut = volumeViewModel.getAudioOut();
      return new Volume(speakerOut, audioOut);
   }
}
