package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture_source.ToPictureInPictureSourceMessages;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture_source.ToPictureInPictureSourceViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureSourceViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class PictureInPictureSourceMapper {
   private PictureInPictureSourceMapper() {
   }

   public static PictureInPictureSourceViewModel toPictureInPictureSourceViewModel(final PictureInPictureSource pictureInPictureSource) {
      Assert.notNull(pictureInPictureSource, ToPictureInPictureSourceViewModelMessages.PICTURE_IN_PICTURE_SOURCE_CAN_NOT_BE_NULL);
      PictureInPictureSource.SourceType pictureInPictureSourceSourceType = pictureInPictureSource.getPictureInPictureSourceSourceType();
      String sourceTypeString;
      if (pictureInPictureSourceSourceType != null) {
         sourceTypeString = String.valueOf(pictureInPictureSourceSourceType);
      } else {
         sourceTypeString = null;
      }

      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2 = pictureInPictureSource.getInputSourceSourceTypeQ2();
      String inputSourceSourceTypeQ2String;
      if (inputSourceSourceTypeQ2 != null) {
         inputSourceSourceTypeQ2String = String.valueOf(inputSourceSourceTypeQ2);
      } else {
         inputSourceSourceTypeQ2String = null;
      }

      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3 = pictureInPictureSource.getInputSourceSourceTypeQ3();
      String inputSourceSourceTypeQ3String;
      if (inputSourceSourceTypeQ3 != null) {
         inputSourceSourceTypeQ3String = String.valueOf(inputSourceSourceTypeQ3);
      } else {
         inputSourceSourceTypeQ3String = null;
      }

      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4 = pictureInPictureSource.getInputSourceSourceTypeQ4();
      String inputSourceSourceTypeQ4String;
      if (inputSourceSourceTypeQ4 != null) {
         inputSourceSourceTypeQ4String = String.valueOf(inputSourceSourceTypeQ4);
      } else {
         inputSourceSourceTypeQ4String = null;
      }

      return new PictureInPictureSourceViewModel(sourceTypeString, inputSourceSourceTypeQ2String, inputSourceSourceTypeQ3String, inputSourceSourceTypeQ4String);
   }

   public static PictureInPictureSource toPictureInPictureSource(final PictureInPictureSourceViewModel pictureInPictureSourceViewModel) {
      Assert.notNull(pictureInPictureSourceViewModel, ToPictureInPictureSourceMessages.PICTURE_IN_PICTURE_SOURCE_VIEW_MODEL_CAN_NOT_BE_NULL);
      String pictureInPictureSourceSourceTypeString = pictureInPictureSourceViewModel.getPictureInPictureSourceSourceType();
      PictureInPictureSource.SourceType sourceType;
      if (pictureInPictureSourceSourceTypeString != null) {
         sourceType = ValueUtilities.getEnumValue(PictureInPictureSource.SourceType.class, pictureInPictureSourceSourceTypeString);
      } else {
         sourceType = null;
      }

      String inputSourceSourceTypeQ2String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ2();
      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2;
      if (inputSourceSourceTypeQ2String != null) {
         inputSourceSourceTypeQ2 = ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ2String);
      } else {
         inputSourceSourceTypeQ2 = null;
      }

      String inputSourceSourceTypeQ3String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ3();
      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3;
      if (inputSourceSourceTypeQ3String != null) {
         inputSourceSourceTypeQ3 = ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ3String);
      } else {
         inputSourceSourceTypeQ3 = null;
      }

      String inputSourceSourceTypeQ4String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ4();
      PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4;
      if (inputSourceSourceTypeQ4String != null) {
         inputSourceSourceTypeQ4 = ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ4String);
      } else {
         inputSourceSourceTypeQ4 = null;
      }

      return new PictureInPictureSource(sourceType, inputSourceSourceTypeQ2, inputSourceSourceTypeQ3, inputSourceSourceTypeQ4);
   }
}
