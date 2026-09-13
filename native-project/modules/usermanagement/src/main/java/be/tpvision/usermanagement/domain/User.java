package be.tpvision.usermanagement.domain;

import be.tpvision.usermanagement.repository.converter.RoleConverter;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import util.MessageUtilities;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "un_username", columnNames = "username"))
public class User implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(length = 50, unique = true, nullable = false)
   private String username;
   @Column(length = 50, nullable = false)
   private String password;
   @Column(nullable = false)
   @Convert(converter = RoleConverter.class)
   private Role role;

   protected User() {
      this.username = null;
      this.password = null;
      this.role = null;
   }

   public User(final String username, final String password, final Role role) {
      this.setUsername(username);
      this.setPassword(password);
      this.setRole(role);
   }

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public String getUsername() {
      Assert.state(this.username != null, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
      return this.username;
   }

   public void setUsername(final String username) {
      Assert.notNull(username, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
      this.username = username;
   }

   public String getPassword() {
      Assert.state(this.password != null, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
      return this.password;
   }

   public void setPassword(final String password) {
      Assert.notNull(password, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
      this.password = password;
   }

   public Role getRole() {
      Assert.state(this.role != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      return this.role;
   }

   public void setRole(final Role role) {
      Assert.notNull(role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      this.role = role;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof User)) {
         return false;
      }

      User user = (User)object;
      return new EqualsBuilder().append(this.getId(), user.getId()).append(this.getUsername(), user.getUsername()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUsername());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("username", this.getUsername())
         .append("password", this.getPassword())
         .append("role", this.getRole())
         .toString();
   }
}
