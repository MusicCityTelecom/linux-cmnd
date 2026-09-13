package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture.ToPictureInPictureMessages;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture.ToPictureInPictureViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class PictureInPictureMapper {
   private PictureInPictureMapper() {
   }

   public static PictureInPictureViewModel toPictureInPictureViewModel(final PictureInPicture pictureInPicture) {
      Assert.notNull(pictureInPicture, ToPictureInPictureViewModelMessages.PICTURE_IN_PICTURE_CAN_NOT_BE_NULL);
      PictureInPicture.Status status = pictureInPicture.getStatus();
      String statusString = String.valueOf(status);
      PictureInPicture.WindowPosition windowPosition = pictureInPicture.getWindowPosition();
      String windowPositionString = String.valueOf(windowPosition);
      return new PictureInPictureViewModel(statusString, windowPositionString);
   }

   public static PictureInPicture toPictureInPicture(final PictureInPictureViewModel pictureInPictureViewModel) {
      Assert.notNull(pictureInPictureViewModel, ToPictureInPictureMessages.PICTURE_IN_PICTURE_VIEW_MODEL_CAN_NOT_BE_NULL);
      String statusString = pictureInPictureViewModel.getStatus();
      PictureInPicture.Status status = ValueUtilities.getEnumValue(PictureInPicture.Status.class, statusString);
      String windowPositionString = pictureInPictureViewModel.getWindowPosition();
      PictureInPicture.WindowPosition windowPosition = ValueUtilities.getEnumValue(PictureInPicture.WindowPosition.class, windowPositionString);
      return new PictureInPicture(status, windowPosition);
   }
}
