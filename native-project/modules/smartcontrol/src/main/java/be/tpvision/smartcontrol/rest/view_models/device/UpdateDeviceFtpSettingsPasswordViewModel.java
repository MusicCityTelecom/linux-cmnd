package be.tpvision.smartcontrol.rest.view_models.device;

import java.util.Objects;

public class UpdateDeviceFtpSettingsPasswordViewModel {
   private String password;

   public UpdateDeviceFtpSettingsPasswordViewModel() {
   }

   public UpdateDeviceFtpSettingsPasswordViewModel(final String password) {
      this.password = password;
   }

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

      if (!(object instanceof UpdateDeviceFtpSettingsPasswordViewModel)) {
         return false;
      }

      UpdateDeviceFtpSettingsPasswordViewModel that = (UpdateDeviceFtpSettingsPasswordViewModel)object;
      return Objects.equals(this.getPassword(), that.getPassword());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getPassword());
   }

   @Override
   public String toString() {
      return this.getPassword();
   }
}
