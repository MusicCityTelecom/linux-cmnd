package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class TemperatureSensor implements Convertible {
   public static final int MINIMUM_VALUE = 0;
   public static final int MAXIMUM_VALUE = 100;
   @Column(nullable = true)
   private int temperature;

   protected TemperatureSensor() {
   }

   public TemperatureSensor(final int temperature) {
      this.setTemperature(temperature);
   }

   public int getTemperature() {
      return this.temperature;
   }

   public void setTemperature(final int temperature) {
      this.temperature = ValueUtilities.getValue(temperature, 0, 100);
   }

   @Override
   public byte convert() {
      return (byte)this.temperature;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof TemperatureSensor)) {
         return false;
      }

      TemperatureSensor that = (TemperatureSensor)object;
      return new EqualsBuilder().append(this.temperature, that.temperature).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.temperature);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("temperature", this.temperature).toString();
   }
}
