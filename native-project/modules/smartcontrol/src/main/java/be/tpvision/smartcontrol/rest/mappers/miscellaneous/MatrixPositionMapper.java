package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.MatrixPosition;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.matrix_position.ToMatrixPositionMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.matrix_position.ToMatrixPositionViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MatrixPositionViewModel;
import org.springframework.util.Assert;

public class MatrixPositionMapper {
   private MatrixPositionMapper() {
   }

   public static MatrixPositionViewModel toMatrixPositionViewModel(final MatrixPosition matrixPosition) {
      Assert.notNull(matrixPosition, ToMatrixPositionViewModelMessages.MATRIX_POSITION_CAN_NOT_BE_NULL);
      int x = matrixPosition.getX();
      int y = matrixPosition.getY();
      int sizeX = matrixPosition.getSizeX();
      int sizeY = matrixPosition.getSizeY();
      return new MatrixPositionViewModel(x, y, sizeX, sizeY);
   }

   public static MatrixPosition toMatrixPosition(final MatrixPositionViewModel matrixPositionViewModel) {
      Assert.notNull(matrixPositionViewModel, ToMatrixPositionMessages.MATRIX_POSITION_VIEW_MODEL_CAN_NOT_BE_NULL);
      int x = matrixPositionViewModel.getX();
      int y = matrixPositionViewModel.getY();
      int sizeX = matrixPositionViewModel.getSizeX();
      int sizeY = matrixPositionViewModel.getSizeY();
      return new MatrixPosition(x, y, sizeX, sizeY);
   }
}
