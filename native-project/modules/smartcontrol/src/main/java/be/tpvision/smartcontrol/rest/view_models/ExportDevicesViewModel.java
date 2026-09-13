package be.tpvision.smartcontrol.rest.view_models;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ExportDevicesViewModel {
   private List<Long> devices;
   private List<Long> groups;

   public ExportDevicesViewModel() {
   }

   public ExportDevicesViewModel(final List<Long> devices, final List<Long> groups) {
      this.devices = devices;
      this.groups = groups;
   }

   public List<Long> getDevices() {
      return this.devices;
   }

   public void setDevices(final List<Long> devices) {
      this.devices = devices;
   }

   public List<Long> getGroups() {
      return this.groups;
   }

   public void setGroups(final List<Long> groups) {
      this.groups = groups;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ExportDevicesViewModel)) {
         return false;
      }

      ExportDevicesViewModel that = (ExportDevicesViewModel)object;
      return new EqualsBuilder().append(this.getDevices(), that.getDevices()).append(this.getGroups(), that.getGroups()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getDevices(), this.getGroups());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("devices", this.getDevices()).append("groups", this.getGroups()).toString();
   }
}
