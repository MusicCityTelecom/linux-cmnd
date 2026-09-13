/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.viewModel;

import java.util.Objects;

public class UpdateUserPasswordViewModel {
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UpdateUserPasswordViewModel)) {
            return false;
        }
        UpdateUserPasswordViewModel that = (UpdateUserPasswordViewModel)object;
        return Objects.equals(this.password, that.password);
    }

    public int hashCode() {
        return Objects.hash(this.password);
    }

    public String toString() {
        return this.password;
    }
}

