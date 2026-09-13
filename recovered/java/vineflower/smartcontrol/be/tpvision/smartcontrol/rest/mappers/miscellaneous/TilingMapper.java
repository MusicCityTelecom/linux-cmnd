package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.tiling.ToTilingMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.tiling.ToTilingViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.TilingViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class TilingMapper {
   private TilingMapper() {
   }

   public static TilingViewModel toTilingViewModel(final Tiling tiling) {
      Assert.notNull(tiling, ToTilingViewModelMessages.TILING_CAN_NOT_BE_NULL);
      Tiling.Enable domainEnable = tiling.getEnable();
      Assert.state(domainEnable != null, ToTilingViewModelMessages.DOMAIN_ENABLE_CAN_NOT_BE_NULL);
      String viewModelEnable = String.valueOf(domainEnable);
      Tiling.FrameComp domainFrameComp = tiling.getFrameComp();
      Assert.state(domainFrameComp != null, ToTilingViewModelMessages.DOMAIN_FRAME_COMP_CAN_NOT_BE_NULL);
      String viewModelFrameComp = String.valueOf(domainFrameComp);
      int position = tiling.getPosition();
      int horizontalMonitors = tiling.getNumberOfHorizontalMonitors();
      int verticalMonitors = tiling.getNumberOfVerticalMonitors();
      return new TilingViewModel(viewModelEnable, viewModelFrameComp, position, horizontalMonitors, verticalMonitors);
   }

   public static Tiling toTiling(final TilingViewModel tilingViewModel) {
      Assert.notNull(tilingViewModel, ToTilingMessages.TILING_VIEW_MODEL_CAN_NOT_BE_NULL);
      String viewModelEnable = tilingViewModel.getEnable();
      Assert.state(viewModelEnable != null, ToTilingMessages.VIEW_MODEL_ENABLE_CAN_NOT_BE_NULL);
      Tiling.Enable domainEnable = ValueUtilities.getEnumValue(Tiling.Enable.class, viewModelEnable);
      Assert.state(domainEnable != null, ToTilingMessages.DOMAIN_ENABLE_CAN_NOT_BE_NULL);
      String viewModelFrameComp = tilingViewModel.getFrameComp();
      Assert.state(viewModelFrameComp != null, ToTilingMessages.VIEW_MODEL_FRAME_COMP_CAN_NOT_BE_NULL);
      Tiling.FrameComp domainFrameComp = ValueUtilities.getEnumValue(Tiling.FrameComp.class, viewModelFrameComp);
      Assert.state(domainEnable != null, ToTilingMessages.DOMAIN_FRAME_COMP_CAN_NOT_BE_NULL);
      int position = tilingViewModel.getPosition();
      int numberOfHorizontalMonitors = tilingViewModel.getNumberOfHorizontalMonitors();
      int numberOfVerticalMonitors = tilingViewModel.getNumberOfVerticalMonitors();
      return new Tiling(domainEnable, domainFrameComp, position, numberOfHorizontalMonitors, numberOfVerticalMonitors);
   }
}
