package be.tpvision.smartcontrol.rest.view_models.group;

import be.tpvision.smartcontrol.rest.view_models.device.GroupDeviceViewModel;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GroupViewModel {
   private Long id;
   private String name;
   private List<GroupDeviceViewModel> devices;

   protected GroupViewModel() {
   }

   public GroupViewModel(final Long id, final String name, final List<GroupDeviceViewModel> devices) {
      this.id = id;
      this.name = name;
      this.devices = devices;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public List<GroupDeviceViewModel> getDevices() {
      return this.devices;
   }

   public void setDevices(final List<GroupDeviceViewModel> devices) {
      this.devices = devices;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof GroupViewModel)) {
         return false;
      }

      GroupViewModel that = (GroupViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getName(), that.getName())
         .append(this.getDevices(), that.getDevices())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getName(), this.getDevices());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("id", this.getId()).append("name", this.getName()).append("devices", this.getDevices()).toString();
   }
}
