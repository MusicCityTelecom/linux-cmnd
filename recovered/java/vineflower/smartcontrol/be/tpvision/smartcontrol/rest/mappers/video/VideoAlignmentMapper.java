package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.VideoAlignment;
import be.tpvision.smartcontrol.messages.mappers.video.video_alignment.ToVideoAlignmentMessages;
import be.tpvision.smartcontrol.rest.view_models.video.VideoAlignmentViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VideoAlignmentMapper {
   private VideoAlignmentMapper() {
   }

   public static VideoAlignment toVideoAlignment(final VideoAlignmentViewModel videoAlignmentViewModel) {
      Assert.notNull(videoAlignmentViewModel, ToVideoAlignmentMessages.VIDEO_ALIGNMENT_VIEW_MODEL_CAN_NOT_BE_NULL);
      String itemString = videoAlignmentViewModel.getItem();
      VideoAlignment.Item item = ValueUtilities.getEnumValue(VideoAlignment.Item.class, itemString);
      return new VideoAlignment(item);
   }
}
