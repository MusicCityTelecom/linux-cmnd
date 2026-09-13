package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MatrixPositionViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GroupDeviceViewModel {
   private Long id;
   private IpDestinationViewModel ipDestination;
   private String name;
   private String modelNumber;
   private MatrixPositionViewModel matrixPosition;

   protected GroupDeviceViewModel() {
   }

   public GroupDeviceViewModel(
      final Long id,
      final IpDestinationViewModel ipDestinationViewModel,
      final String name,
      final String modelNumber,
      final MatrixPositionViewModel matrixPositionViewModel
   ) {
      this.id = id;
      this.ipDestination = ipDestinationViewModel;
      this.name = name;
      this.modelNumber = modelNumber;
      this.matrixPosition = matrixPositionViewModel;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public IpDestinationViewModel getIpDestination() {
      return this.ipDestination;
   }

   public void setIpDestination(final IpDestinationViewModel ipDestination) {
      this.ipDestination = ipDestination;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(final String modelNumber) {
      this.modelNumber = modelNumber;
   }

   public MatrixPositionViewModel getMatrixPosition() {
      return this.matrixPosition;
   }

   public void setMatrixPosition(final MatrixPositionViewModel matrixPosition) {
      this.matrixPosition = matrixPosition;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof GroupDeviceViewModel)) {
         return false;
      }

      GroupDeviceViewModel that = (GroupDeviceViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getIpDestination(), that.getIpDestination())
         .append(this.getName(), that.getName())
         .append(this.getModelNumber(), that.getModelNumber())
         .append(this.getMatrixPosition(), that.getMatrixPosition())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getIpDestination(), this.getName(), this.getModelNumber(), this.getMatrixPosition());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("ipDestination", this.getIpDestination())
         .append("name", this.getName())
         .append("modelNumber", this.getModelNumber())
         .append("matrixPosition", this.getMatrixPosition())
         .toString();
   }
}
