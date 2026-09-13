package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GroupId implements Convertible {
   public static final int MINIMUM = 0;
   public static final int MAXIMUM = 255;
   private int groupId;

   public GroupId() {
      this(0);
   }

   public GroupId(final int groupId) {
      this.setGroupId(groupId);
   }

   public int getGroupId() {
      return this.groupId;
   }

   public void setGroupId(final int groupId) {
      this.groupId = ValueUtilities.getValue(groupId, 0, 255);
   }

   @Override
   public byte convert() {
      return (byte)this.groupId;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof GroupId)) {
         return false;
      }

      GroupId groupId1 = (GroupId)object;
      return new EqualsBuilder().append(this.groupId, groupId1.groupId).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.groupId);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("groupId", this.groupId).toString();
   }
}
