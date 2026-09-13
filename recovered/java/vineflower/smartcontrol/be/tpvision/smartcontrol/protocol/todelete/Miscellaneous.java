package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Miscellaneous implements Convertibles {
   @Column(nullable = true)
   private int operatingHours;

   protected Miscellaneous() {
   }

   public Miscellaneous(final int operatingHours) {
      this.operatingHours = operatingHours;
   }

   public int getOperatingHours() {
      return this.operatingHours;
   }

   public void setOperatingHours(final int operatingHours) {
      this.operatingHours = operatingHours;
   }

   @Override
   public byte[] convert() {
      byte operatingHoursByte = (byte)this.operatingHours;
      return new byte[]{operatingHoursByte};
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Miscellaneous)) {
         return false;
      }

      Miscellaneous that = (Miscellaneous)object;
      return new EqualsBuilder().append(this.operatingHours, that.operatingHours).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.operatingHours);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("operatingHours", this.operatingHours).toString();
   }

   public enum Info implements Convertible {
      OPERATING_HOURS((byte)2);

      private byte data;

      Info(final byte data) {
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
