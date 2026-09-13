package be.tpvision.smartcontrol.protocol;

import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RawCommand extends SicpMessage {
   private byte[] payload;

   public RawCommand(final byte controlId, final byte groupId, final byte[] payload) {
      super(controlId, groupId);
      this.payload = payload;
   }

   @Override
   protected byte[] getData() {
      return this.payload;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof RawCommand)) {
         return false;
      }

      RawCommand that = (RawCommand)object;
      return new EqualsBuilder().append(this.payload, that.payload).isEquals();
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.payload);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("payload", this.payload).toString();
   }
}
