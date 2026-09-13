package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoAlignment implements Convertibles {
   private VideoAlignment.Item item;

   public VideoAlignment(final VideoAlignment.Item item) {
      Objects.requireNonNull(item, MessageUtilities.VIDEO_ALIGNMENT_ITEM_NOT_NULL_MESSAGE);
      this.item = item;
   }

   @Override
   public byte[] convert() {
      byte itemByte = this.item.convert();
      byte reserved = 0;
      return new byte[]{itemByte, 0};
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoAlignment)) {
         return false;
      }

      VideoAlignment that = (VideoAlignment)object;
      return new EqualsBuilder().append(this.item, that.item).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.item);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("item", this.item).toString();
   }

   public enum Item implements Convertible {
      AUTO_ADJUST((byte)64);

      private byte data;

      Item(final byte data) {
         this.data = data;
      }

      public byte getData() {
         return this.data;
      }

      @Override
      public byte convert() {
         return this.data;
      }
   }
}
