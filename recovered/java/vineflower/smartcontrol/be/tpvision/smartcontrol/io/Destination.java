package be.tpvision.smartcontrol.io;

import java.util.Objects;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class Destination {
   private int controlId;
   private int groupId;

   public Destination(final int controlId, final int groupId) {
      this.controlId = controlId;
      this.groupId = groupId;
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

      if (!(object instanceof Destination)) {
         return false;
      }

      Destination that = (Destination)object;
      return new EqualsBuilder().append(this.getControlId(), that.getControlId()).append(this.getGroupId(), that.getGroupId()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getControlId(), this.getGroupId());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("controlId", this.getControlId()).append("groupId", this.getGroupId()).toString();
   }
}
