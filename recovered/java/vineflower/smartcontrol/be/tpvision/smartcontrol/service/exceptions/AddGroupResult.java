package be.tpvision.smartcontrol.service.exceptions;

import be.tpvision.smartcontrol.domain.Group;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddGroupResult {
   private Group group;
   private List<AddDeviceResult> addDeviceResults;
   private AddGroupResult.Result result;
   private String message;

   public AddGroupResult(final Group group, final List<AddDeviceResult> addDeviceResults, final AddGroupResult.Result result) {
      this(group, addDeviceResults, result, null);
   }

   public AddGroupResult(final Group group, final List<AddDeviceResult> addDeviceResults, final AddGroupResult.Result result, final String message) {
      this.group = group;
      this.addDeviceResults = addDeviceResults;
      this.result = result;
      this.message = message;
   }

   public Group getGroup() {
      return this.group;
   }

   public void setGroup(final Group group) {
      this.group = group;
   }

   public List<AddDeviceResult> getAddDeviceResults() {
      return this.addDeviceResults;
   }

   public void setAddDeviceResults(final List<AddDeviceResult> addDeviceResults) {
      this.addDeviceResults = addDeviceResults;
   }

   public AddGroupResult.Result getResult() {
      return this.result;
   }

   public void setResult(final AddGroupResult.Result result) {
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

      if (!(object instanceof AddGroupResult)) {
         return false;
      }

      AddGroupResult that = (AddGroupResult)object;
      return new EqualsBuilder()
         .append(this.getGroup(), that.getGroup())
         .append(this.getAddDeviceResults(), that.getAddDeviceResults())
         .append(this.getResult(), that.getResult())
         .append(this.getMessage(), that.getMessage())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getGroup(), this.getAddDeviceResults(), this.getResult(), this.getMessage());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("group", this.getGroup())
         .append("addDeviceResults", this.getAddDeviceResults())
         .append("result", this.getResult())
         .append("message", this.getMessage())
         .toString();
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
