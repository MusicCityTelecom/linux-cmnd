package be.tpvision.smartcontrol.rest;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseWrapper<T> {
   private final T result;

   public ResponseWrapper(final T result) {
      this.result = result;
   }

   public T getResult() {
      return this.result;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ResponseWrapper)) {
         return false;
      }

      ResponseWrapper<?> that = (ResponseWrapper<?>)object;
      return new EqualsBuilder().append(this.getResult(), that.getResult()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getResult());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("result", this.getResult()).toString();
   }
}
