package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.messages.mappers.video.video_parameters.ToVideoParametersMessages;
import be.tpvision.smartcontrol.messages.mappers.video.video_parameters.ToVideoParametersViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.VideoParametersViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class VideoParametersMapper {
   private VideoParametersMapper() {
   }

   public static VideoParametersViewModel toVideoParametersViewModel(final VideoParameters videoParameters) {
      Assert.notNull(videoParameters, ToVideoParametersViewModelMessages.VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
      int brightness = videoParameters.getBrightness();
      int color = videoParameters.getColor();
      int contrast = videoParameters.getContrast();
      int sharpness = videoParameters.getSharpness();
      int hue = videoParameters.getHue();
      int backlight = videoParameters.getBacklight();
      VideoParameters.GammaSelection domainGammaSelection = videoParameters.getGammaSelection();
      Assert.state(domainGammaSelection != null, ToVideoParametersViewModelMessages.GAMMA_SELECTION_CAN_NOT_BE_NULL);
      String viewModelGammaSelection = String.valueOf(domainGammaSelection);
      return new VideoParametersViewModel(brightness, color, contrast, sharpness, hue, backlight, viewModelGammaSelection);
   }

   public static VideoParameters toVideoParameters(final VideoParametersViewModel videoParametersViewModel) {
      Assert.notNull(videoParametersViewModel, ToVideoParametersMessages.VIDEO_PARAMETERS_VIEW_MODEL_CAN_NOT_BE_NULL);
      int brightness = videoParametersViewModel.getBrightness();
      int color = videoParametersViewModel.getColor();
      int contrast = videoParametersViewModel.getContrast();
      int sharpness = videoParametersViewModel.getSharpness();
      int hue = videoParametersViewModel.getHue();
      int backlight = videoParametersViewModel.getBacklight();
      String viewModelGammaSelection = videoParametersViewModel.getGammaSelection();
      Assert.state(viewModelGammaSelection != null, ToVideoParametersMessages.VIEW_MODEL_GAMMA_SELECTION_CAN_NOT_BE_NULL);
      VideoParameters.GammaSelection domainGammaSelection = ValueUtilities.getEnumValue(VideoParameters.GammaSelection.class, viewModelGammaSelection);
      Assert.state(domainGammaSelection != null, ToVideoParametersMessages.DOMAIN_GAMMA_SELECTION_CAN_NOT_BE_NULL);
      return new VideoParameters(brightness, color, contrast, sharpness, hue, backlight, domainGammaSelection);
   }
}
