package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.pixel_shift.ToPixelShiftMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.pixel_shift.ToPixelShiftViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.PixelShiftViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class PixelShiftMapper {
   private PixelShiftMapper() {
   }

   public static PixelShiftViewModel toPixelShiftViewModel(PixelShift pixelShift) {
      Assert.notNull(pixelShift, ToPixelShiftViewModelMessages.PIXEL_SHIFT_CAN_NOT_BE_NULL);
      PixelShift.State domainState = pixelShift.getState();
      Assert.state(domainState != null, ToPixelShiftViewModelMessages.DOMAIN_STATE_CAN_NOT_BE_NULL);
      String viewModelState = String.valueOf(domainState);
      int value = pixelShift.getValue();
      return new PixelShiftViewModel(viewModelState, value);
   }

   public static PixelShift toPixelShift(PixelShiftViewModel pixelShiftViewModel) {
      Assert.notNull(pixelShiftViewModel, ToPixelShiftMessages.TILING_VIEW_MODEL_CAN_NOT_BE_NULL);
      String viewModelState = pixelShiftViewModel.getState();
      Assert.state(viewModelState != null, ToPixelShiftMessages.VIEW_MODEL_STATE_CAN_NOT_BE_NULL);
      PixelShift.State domainState = ValueUtilities.getEnumValue(PixelShift.State.class, viewModelState);
      Assert.state(domainState != null, ToPixelShiftMessages.DOMAIN_STATE_CAN_NOT_BE_NULL);
      int value = pixelShiftViewModel.getValue();
      return new PixelShift(domainState, value);
   }
}
