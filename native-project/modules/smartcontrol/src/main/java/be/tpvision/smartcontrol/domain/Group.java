package be.tpvision.smartcontrol.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "groups")
public class Group implements DeviceListItem, Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   @ManyToMany
   @JoinTable(
      name = "group_devices",
      joinColumns = @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "FK_GROUP_ID")),
      inverseJoinColumns = @JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "FK_DEVICE_ID"))
   )
   private Set<Device> devices;

   protected Group() {
   }

   public Group(final String name, final Set<Device> devices) {
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

   public Set<Device> getDevices() {
      return this.devices;
   }

   public void setDevices(final Set<Device> devices) {
      this.devices = devices;
   }

   public void addDevice(final Device device) {
      this.devices.add(device);
   }

   public void removeDevice(final Device device) {
      this.devices.remove(device);
   }

   public boolean contains(final Device device) {
      return this.devices.contains(device);
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Group)) {
         return false;
      }

      Group that = (Group)object;
      return new EqualsBuilder().append(this.getId(), that.getId()).append(this.getDevices(), that.getDevices()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getDevices());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("id", this.getId()).append("name", this.getName()).append("devices", this.getDevices()).toString();
   }
}
