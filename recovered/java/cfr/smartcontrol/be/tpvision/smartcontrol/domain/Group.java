/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
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
@Table(name="groups")
public class Group
implements DeviceListItem,
Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name="group_devices", joinColumns={@JoinColumn(name="group_id", foreignKey=@ForeignKey(name="FK_GROUP_ID"))}, inverseJoinColumns={@JoinColumn(name="device_id", foreignKey=@ForeignKey(name="FK_DEVICE_ID"))})
    private Set<Device> devices;

    protected Group() {
    }

    public Group(String name, Set<Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    public boolean contains(Device device) {
        return this.devices.contains(device);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Group)) {
            return false;
        }
        Group that = (Group)object;
        return new EqualsBuilder().append(this.getId(), that.getId()).append(this.getDevices(), that.getDevices()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getDevices());
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("name", this.getName()).append("devices", this.getDevices()).toString();
    }
}

