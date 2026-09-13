package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.messages.view_models.device.ip_destination.SetIpMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class IpDestinationViewModel {
   private String ip;
   private int port;
   private int controlId;
   private int groupId;

   protected IpDestinationViewModel() {
   }

   public IpDestinationViewModel(final String ip, final int port, final int controlId, final int groupId) {
      this.setIp(ip);
      this.port = port;
      this.controlId = controlId;
      this.groupId = groupId;
   }

   public String getIp() {
      return this.ip;
   }

   public void setIp(final String ip) {
      Assert.notNull(ip, SetIpMessages.IP_CAN_NOT_BE_NULL);
      Assert.isTrue(!ip.isEmpty(), SetIpMessages.IP_CAN_NOT_BE_EMPTY);
      this.ip = ip;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(final int port) {
      this.port = port;
   }

   public int getControlId() {
      return this.controlId;
   }

   public void setControlId(final int controlId) {
      this.controlId = controlId;
   }

   public int getGroupId() {
      return this.groupId;
   }

   public void setGroupId(final int groupId) {
      this.groupId = groupId;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof IpDestinationViewModel)) {
         return false;
      }

      IpDestinationViewModel that = (IpDestinationViewModel)object;
      return new EqualsBuilder()
         .append(this.getIp(), that.getIp())
         .append(this.getPort(), that.getPort())
         .append(this.getControlId(), that.getControlId())
         .append(this.getGroupId(), that.getGroupId())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getIp(), this.getPort(), this.getControlId(), this.getGroupId());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("ip", this.getIp())
         .append("port", this.getPort())
         .append("controlId", this.getControlId())
         .append("groupId", this.getGroupId())
         .toString();
   }
}
