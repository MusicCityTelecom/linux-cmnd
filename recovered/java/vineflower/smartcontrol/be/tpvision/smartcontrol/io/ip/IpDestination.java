package be.tpvision.smartcontrol.io.ip;

import be.tpvision.smartcontrol.io.Destination;
import be.tpvision.smartcontrol.repository.converters.InetSocketAddressConverter;
import java.net.InetSocketAddress;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class IpDestination extends Destination {
   @Basic
   @Convert(converter = InetSocketAddressConverter.class)
   private InetSocketAddress address;

   protected IpDestination() {
      super(0, 0);
   }

   public IpDestination(final InetSocketAddress address, final int controlId, final int groupId) {
      super(controlId, groupId);
      this.address = address;
   }

   public InetSocketAddress getAddress() {
      return this.address;
   }

   public void setAddress(final InetSocketAddress address) {
      this.address = address;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof IpDestination)) {
         return false;
      }

      IpDestination that = (IpDestination)object;
      return new EqualsBuilder()
         .append(super.getControlId(), that.getControlId())
         .append(super.getGroupId(), that.getGroupId())
         .append(this.getAddress(), that.getAddress())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.getControlId(), super.getGroupId(), this.getAddress());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("controlId", super.getControlId())
         .append("groupId", super.getGroupId())
         .append("address", this.getAddress())
         .toString();
   }
}
