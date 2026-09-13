package be.tpvision.usermanagement.viewModel;

import java.util.Objects;

public class UpdateUserPasswordViewModel {
   private String password;

   public String getPassword() {
      return this.password;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof UpdateUserPasswordViewModel)) {
         return false;
      }

      UpdateUserPasswordViewModel that = (UpdateUserPasswordViewModel)object;
      return Objects.equals(this.password, that.password);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.password);
   }

   @Override
   public String toString() {
      return this.password;
   }
}
