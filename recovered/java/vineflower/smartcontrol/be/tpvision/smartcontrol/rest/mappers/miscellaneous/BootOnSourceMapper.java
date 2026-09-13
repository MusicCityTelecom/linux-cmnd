package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.boot_on_source.ToBootOnSourceMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.boot_on_source.ToBootOnSourceViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.BootOnSourceViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class BootOnSourceMapper {
   private BootOnSourceMapper() {
   }

   public static BootOnSourceViewModel toBootOnSourceViewModel(final BootOnSource bootOnSource) {
      Assert.notNull(bootOnSource, ToBootOnSourceViewModelMessages.BOOT_ON_SOURCE_CAN_NOT_BE_NULL);
      BootOnSource.VideoSourceType videoSourceType = bootOnSource.getVideoSourceType();
      String videoSourceTypeString = String.valueOf(videoSourceType);
      BootOnSource.Tag tag = bootOnSource.getTag();
      String tagString = String.valueOf(tag);
      return new BootOnSourceViewModel(videoSourceTypeString, tagString);
   }

   public static BootOnSource toBootOnSource(final BootOnSourceViewModel bootOnSourceViewModel) {
      Assert.notNull(bootOnSourceViewModel, ToBootOnSourceMessages.BOOT_ON_SOURCE_VIEW_MODEL_CAN_NOT_BE_NULL);
      String videoSourceTypeString = bootOnSourceViewModel.getVideoSourceType();
      BootOnSource.VideoSourceType videoSourceType = ValueUtilities.getEnumValue(BootOnSource.VideoSourceType.class, videoSourceTypeString);
      BootOnSource.Tag tag = null;
      String tagString = bootOnSourceViewModel.getTag();
      if (tagString != null) {
         tag = ValueUtilities.getEnumValue(BootOnSource.Tag.class, tagString);
      }

      return new BootOnSource(videoSourceType, tag);
   }
}
