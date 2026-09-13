package be.tpvision.usermanagement.viewModel;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserViewModel {
   private Long id;
   private String username;
   private String role;
   private String password;

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(final String username) {
      this.username = username;
   }

   public String getRole() {
      return this.role;
   }

   public void setRole(final String role) {
      this.role = role;
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

      if (!(object instanceof UserViewModel)) {
         return false;
      }

      UserViewModel that = (UserViewModel)object;
      return new EqualsBuilder().append(this.id, that.id).append(this.username, that.username).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id, this.username);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.id)
         .append("username", this.username)
         .append("role", this.role)
         .append("password", this.password)
         .toString();
   }
}
