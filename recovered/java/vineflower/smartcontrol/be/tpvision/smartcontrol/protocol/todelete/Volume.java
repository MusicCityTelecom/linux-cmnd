package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Volume implements Convertible {
   public static final int MINIMUM_VALUE = 0;
   public static final int MAXIMUM_VALUE = 100;
   private int volume;

   public Volume() {
      this(0);
   }

   public Volume(final int volume) {
      this.setVolume(volume);
   }

   public int getVolume() {
      return this.volume;
   }

   public void setVolume(final int volume) {
      this.volume = ValueUtilities.getValue(volume, 0, 100);
   }

   @Override
   public byte convert() {
      return (byte)this.volume;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Volume)) {
         return false;
      }

      Volume that = (Volume)object;
      return new EqualsBuilder().append(this.volume, that.volume).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.volume);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("volume", this.volume).toString();
   }
}
