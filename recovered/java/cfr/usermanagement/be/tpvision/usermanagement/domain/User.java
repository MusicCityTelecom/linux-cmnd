/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.domain;

import be.tpvision.usermanagement.domain.Role;
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
@Table(name="users", uniqueConstraints={@UniqueConstraint(name="un_username", columnNames={"username"})})
public class User
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(length=50, unique=true, nullable=false)
    private String username;
    @Column(length=50, nullable=false)
    private String password;
    @Column(nullable=false)
    @Convert(converter=RoleConverter.class)
    private Role role;

    protected User() {
        this.username = null;
        this.password = null;
        this.role = null;
    }

    public User(String username, String password, Role role) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        Assert.state(this.username != null, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
        return this.username;
    }

    public void setUsername(String username) {
        Assert.notNull((Object)username, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
        this.username = username;
    }

    public String getPassword() {
        Assert.state(this.password != null, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        return this.password;
    }

    public void setPassword(String password) {
        Assert.notNull((Object)password, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        this.password = password;
    }

    public Role getRole() {
        Assert.state(this.role != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        return this.role;
    }

    public void setRole(Role role) {
        Assert.notNull((Object)role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        this.role = role;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User)) {
            return false;
        }
        User user = (User)object;
        return new EqualsBuilder().append(this.getId(), user.getId()).append(this.getUsername(), user.getUsername()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getUsername());
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("username", this.getUsername()).append("password", this.getPassword()).append("role", (Object)this.getRole()).toString();
    }
}

