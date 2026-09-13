/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.viewModel;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CreateOrUpdateUserViewModel {
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CreateOrUpdateUserViewModel)) {
            return false;
        }
        CreateOrUpdateUserViewModel that = (CreateOrUpdateUserViewModel)object;
        return new EqualsBuilder().append(this.username, that.username).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.username);
    }

    public String toString() {
        return new ToStringBuilder(this).append("username", this.username).append("password", this.password).append("role", this.role).toString();
    }
}

