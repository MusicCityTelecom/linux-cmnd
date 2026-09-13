package be.tpvision.smartcontrol.protocol;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseWrapper implements Response {
   private Object response;

   public ResponseWrapper(final Object response) {
      this.response = response;
   }

   @Override
   public Object getResponse() {
      return this.response;
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("response", this.response).toString();
   }
}
