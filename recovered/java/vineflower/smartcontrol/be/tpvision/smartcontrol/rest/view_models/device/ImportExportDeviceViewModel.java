package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.ImportExportItemListViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImportExportDeviceViewModel implements ImportExportItemListViewModel {
   private IpDestinationViewModel ipDestination;
   private String sicpVersion;
   private String serialCode;
   private String modelNumber;
   private String name;

   protected ImportExportDeviceViewModel() {
   }

   public ImportExportDeviceViewModel(
      final IpDestinationViewModel ipDestination, final String sicpVersion, final String serialCode, final String modelNumber, final String name
   ) {
      this.ipDestination = ipDestination;
      this.sicpVersion = sicpVersion;
      this.serialCode = serialCode;
      this.modelNumber = modelNumber;
      this.name = name;
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

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ImportExportDeviceViewModel)) {
         return false;
      }

      ImportExportDeviceViewModel that = (ImportExportDeviceViewModel)object;
      return Objects.equals(this.getIpDestination(), that.getIpDestination());
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.getIpDestination());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("ipDestination", this.getIpDestination())
         .append("sicpVersion", this.getSicpVersion())
         .append("serialCode", this.getSerialCode())
         .append("modelNumber", this.getModelNumber())
         .append("name", this.getName())
         .toString();
   }
}
