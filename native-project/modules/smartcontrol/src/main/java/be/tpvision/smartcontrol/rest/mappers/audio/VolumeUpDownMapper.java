package be.tpvision.smartcontrol.rest.mappers.audio;

import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeUpDown;
import be.tpvision.smartcontrol.messages.mappers.audio.volume_up_down.ToVolumeUpDownMessages;
import be.tpvision.smartcontrol.messages.mappers.audio.volume_up_down.ToVolumeUpDownViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeUpDownViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VolumeUpDownMapper {
   private VolumeUpDownMapper() {
   }

   public static VolumeUpDownViewModel toVolumeUpDownViewModel(final VolumeUpDown volumeUpDown) {
      Assert.notNull(volumeUpDown, ToVolumeUpDownViewModelMessages.VOLUME_UP_DOWN_CAN_NOT_BE_NULL);
      VolumeUpDown.Volume domainSpeakerOut = volumeUpDown.getSpeakerOut();
      String viewModelSpeakerOut = String.valueOf(domainSpeakerOut);
      VolumeUpDown.Volume domainAudioOut = volumeUpDown.getAudioOut();
      String viewModelAudioOut = String.valueOf(domainAudioOut);
      return new VolumeUpDownViewModel(viewModelSpeakerOut, viewModelAudioOut);
   }

   public static VolumeUpDown toVolumeUpDown(final VolumeUpDownViewModel volumeUpDownViewModel) {
      Assert.notNull(volumeUpDownViewModel, ToVolumeUpDownMessages.VOLUME_UP_DOWN_VIEW_MODEL_CAN_NOT_BE_NULL);
      String viewModelSpeakersOut = volumeUpDownViewModel.getSpeakerOut();
      VolumeUpDown.Volume domainSpeakersOut = ValueUtilities.getEnumValue(VolumeUpDown.Volume.class, viewModelSpeakersOut);
      String viewModelAudioOut = volumeUpDownViewModel.getAudioOut();
      VolumeUpDown.Volume domainAudioOut = ValueUtilities.getEnumValue(VolumeUpDown.Volume.class, viewModelAudioOut);
      return new VolumeUpDown(domainSpeakersOut, domainAudioOut);
   }
}
