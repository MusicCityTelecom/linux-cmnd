package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AudioParameters implements Convertibles {
   public static final int MINIMUM_VALUE = 0;
   public static final int MAXIMUM_VALUE = 100;
   private int treble;
   private int bass;

   public AudioParameters() {
      this(0, 0);
   }

   public AudioParameters(final int treble, final int bass) {
      this.setTreble(treble);
      this.setBass(bass);
   }

   public int getTreble() {
      return this.treble;
   }

   public void setTreble(final int treble) {
      this.treble = this.getValue(treble);
   }

   public int getBass() {
      return this.bass;
   }

   public void setBass(final int bass) {
      this.bass = this.getValue(bass);
   }

   private int getValue(final int value) {
      return ValueUtilities.getValue(value, 0, 100);
   }

   @Override
   public byte[] convert() {
      byte trebleByte = (byte)this.treble;
      byte bassByte = (byte)this.bass;
      return new byte[]{trebleByte, bassByte};
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof AudioParameters)) {
         return false;
      }

      AudioParameters that = (AudioParameters)object;
      return new EqualsBuilder().append(this.treble, that.treble).append(this.bass, that.bass).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.treble, this.bass);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("treble", this.treble).append("bass", this.bass).toString();
   }
}
