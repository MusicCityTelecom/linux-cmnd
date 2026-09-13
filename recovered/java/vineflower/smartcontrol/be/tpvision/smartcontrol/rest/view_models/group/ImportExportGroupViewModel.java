package be.tpvision.smartcontrol.rest.view_models.group;

import be.tpvision.smartcontrol.rest.view_models.ImportExportItemListViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImportExportGroupViewModel implements ImportExportItemListViewModel {
   private String name;
   private List<ImportExportDeviceViewModel> devices;

   protected ImportExportGroupViewModel() {
   }

   public ImportExportGroupViewModel(final String name, final List<ImportExportDeviceViewModel> devices) {
      this.name = name;
      this.devices = devices;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public List<ImportExportDeviceViewModel> getDevices() {
      return this.devices;
   }

   public void setDevices(final List<ImportExportDeviceViewModel> devices) {
      this.devices = devices;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ImportExportGroupViewModel)) {
         return false;
      }

      ImportExportGroupViewModel that = (ImportExportGroupViewModel)object;
      return new EqualsBuilder().append(this.getName(), that.getName()).append(this.getDevices(), that.getDevices()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getName(), this.getDevices());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("name", this.getName()).append("devices", this.getDevices()).toString();
   }
}
