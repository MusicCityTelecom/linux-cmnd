package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.repository.converters.miscellaneous.pixel_shift.StateConverter;
import java.util.Objects;
import javax.persistence.Convert;

public class PixelShift implements DeviceSetting {
   @Convert(converter = StateConverter.class)
   private PixelShift.State state;
   private int value;

   public PixelShift() {
   }

   public PixelShift(PixelShift.State state, int value) {
      this.state = state;
      this.value = value;
   }

   public PixelShift.State getState() {
      return this.state;
   }

   public void setState(PixelShift.State state) {
      this.state = state;
   }

   public int getValue() {
      return this.value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PixelShift that = (PixelShift)o;
         return this.getState() == that.getState() && Objects.equals(this.getValue(), that.getValue());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getState(), this.getValue());
   }

   @Override
   public String toString() {
      return "PixelShift{state=" + this.state + ", value=" + this.value + '}';
   }

   public enum State {
      CUSTOM,
      AUTO;
   }
}
