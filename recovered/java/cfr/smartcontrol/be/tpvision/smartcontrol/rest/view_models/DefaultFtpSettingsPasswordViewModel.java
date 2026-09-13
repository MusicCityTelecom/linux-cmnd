/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import java.util.Objects;

public class DefaultFtpSettingsPasswordViewModel {
    private String password;

    public DefaultFtpSettingsPasswordViewModel() {
    }

    public DefaultFtpSettingsPasswordViewModel(String password) {
        this.password = password;
    }

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
        if (!(object instanceof DefaultFtpSettingsPasswordViewModel)) {
            return false;
        }
        DefaultFtpSettingsPasswordViewModel that = (DefaultFtpSettingsPasswordViewModel)object;
        return Objects.equals(this.getPassword(), that.getPassword());
    }

    public int hashCode() {
        return Objects.hash(this.getPassword());
    }

    public String toString() {
        return this.getPassword();
    }
}

