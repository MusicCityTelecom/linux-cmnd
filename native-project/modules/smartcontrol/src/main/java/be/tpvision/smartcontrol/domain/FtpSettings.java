package be.tpvision.smartcontrol.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class FtpSettings {
   private boolean useDefault;
   private Integer port;
   private String username;
   private String password;

   protected FtpSettings() {
   }

   public FtpSettings(final boolean useDefault) {
      this(useDefault, null, null, null);
   }

   public FtpSettings(final Integer port, final String username, final String password) {
      this(false, port, username, password);
   }

   public FtpSettings(final boolean useDefault, final Integer port, final String username, final String password) {
      this.useDefault = useDefault;
      this.port = port;
      this.username = username;
      this.password = password;
   }

   public boolean isUseDefault() {
      return this.useDefault;
   }

   public void setUseDefault(final boolean useDefault) {
      this.useDefault = useDefault;
   }

   public Integer getPort() {
      return this.port;
   }

   public void setPort(final Integer port) {
      this.port = port;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(final String username) {
      this.username = username;
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

      if (!(object instanceof FtpSettings)) {
         return false;
      }

      FtpSettings that = (FtpSettings)object;
      return new EqualsBuilder()
         .append(this.isUseDefault(), that.isUseDefault())
         .append(this.getPort(), that.getPort())
         .append(this.getUsername(), that.getUsername())
         .append(this.getPassword(), that.getPassword())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.isUseDefault(), this.getPort(), this.getUsername(), this.getPassword());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("useDefault", this.isUseDefault())
         .append("port", this.getPort())
         .append("username", this.getUsername())
         .append("password", this.getPassword())
         .toString();
   }
}
