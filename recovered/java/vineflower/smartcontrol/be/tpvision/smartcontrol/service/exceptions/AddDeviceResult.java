package be.tpvision.smartcontrol.service.exceptions;

import be.tpvision.smartcontrol.domain.Device;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddDeviceResult {
   private Device device;
   private AddDeviceResult.Result result;
   private String message;

   public AddDeviceResult(final Device device, final AddDeviceResult.Result result) {
      this(device, result, null);
   }

   public AddDeviceResult(final Device device, final AddDeviceResult.Result result, final String message) {
      this.device = device;
      this.result = result;
      this.message = message;
   }

   public Device getDevice() {
      return this.device;
   }

   public void setDevice(final Device device) {
      this.device = device;
   }

   public AddDeviceResult.Result getResult() {
      return this.result;
   }

   public void setResult(final AddDeviceResult.Result result) {
      this.result = result;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(final String message) {
      this.message = message;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof AddDeviceResult)) {
         return false;
      }

      AddDeviceResult that = (AddDeviceResult)object;
      return new EqualsBuilder()
         .append(this.getDevice(), that.getDevice())
         .append(this.getResult(), that.getResult())
         .append(this.getMessage(), that.getMessage())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getDevice(), this.getResult(), this.getMessage());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("device", this.getDevice()).append("result", this.getResult()).append("message", this.getMessage()).toString();
   }

   public enum Result {
      SUCCESS("Success"),
      FAILED("Failed");

      private String name;

      Result(final String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }
   }
}
