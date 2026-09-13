package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;

public class AutoRestartParameter implements DeviceSetting {
   private AutoRestartParameter.Status status;
   private int hour;
   private int minute;

   public AutoRestartParameter(AutoRestartParameter.Status status, int hour, int minute) {
      this.status = status;
      this.hour = hour;
      this.minute = minute;
   }

   public AutoRestartParameter.Status getStatus() {
      return this.status;
   }

   public void setStatus(AutoRestartParameter.Status status) {
      this.status = status;
   }

   public int getHour() {
      return this.hour;
   }

   public void setHour(int hour) {
      this.hour = hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public void setMinute(int minute) {
      this.minute = minute;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.hour, this.minute, this.status);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }

      if (obj == null) {
         return false;
      }

      if (this.getClass() != obj.getClass()) {
         return false;
      }

      AutoRestartParameter other = (AutoRestartParameter)obj;
      return this.hour == other.hour && this.minute == other.minute && this.status == other.status;
   }

   @Override
   public String toString() {
      return "AutoRestartParameter [status=" + this.status + ", hour=" + this.hour + ", minute=" + this.minute + "]";
   }

   public enum Status {
      OFF,
      ON;
   }
}
