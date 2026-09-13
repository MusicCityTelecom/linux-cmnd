package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.DeviceListItemViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DetectDevicesViewModel implements DeviceListItemViewModel {
   private IpDestinationViewModel ipDestination;
   private String sicpVersion;
   private String serialCode;
   private String modelNumber;

   protected DetectDevicesViewModel() {
   }

   public DetectDevicesViewModel(
      final IpDestinationViewModel ipDestinationViewModel, final String sicpVersion, final String serialCode, final String modelNumber
   ) {
      this.ipDestination = ipDestinationViewModel;
      this.sicpVersion = sicpVersion;
      this.serialCode = serialCode;
      this.modelNumber = modelNumber;
   }

   public IpDestinationViewModel getIpDestination() {
      return this.ipDestination;
   }

   public void setIpDestination(final IpDestinationViewModel ipDestination) {
      this.ipDestination = ipDestination;
   }

   public String getSicpVersion() {
      return this.sicpVersion;
   }

   public void setSicpVersion(final String sicpVersion) {
      this.sicpVersion = sicpVersion;
   }

   public String getSerialCode() {
      return this.serialCode;
   }

   public void setSerialCode(final String serialCode) {
      this.serialCode = serialCode;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(final String modelNumber) {
      this.modelNumber = modelNumber;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DetectDevicesViewModel)) {
         return false;
      }

      DetectDevicesViewModel that = (DetectDevicesViewModel)object;
      return Objects.equals(this.getIpDestination(), that.getIpDestination());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getIpDestination());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("ipDestination", this.getIpDestination())
         .append("sicpVersion", this.getSicpVersion())
         .append("serialCode", this.getSerialCode())
         .append("modelNumber", this.getModelNumber())
         .toString();
   }
}
