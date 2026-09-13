/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FtpSettingsViewModel {
    private boolean useDefault;
    private Integer port;
    private String username;
    private String password;

    protected FtpSettingsViewModel() {
    }

    public FtpSettingsViewModel(boolean useDefault) {
        this(useDefault, null, null, null);
    }

    public FtpSettingsViewModel(Integer port, String username, String password) {
        this(false, port, username, password);
    }

    public FtpSettingsViewModel(boolean useDefault, Integer port, String username, String password) {
        this.useDefault = useDefault;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public boolean isUseDefault() {
        return this.useDefault;
    }

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

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

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FtpSettingsViewModel)) {
            return false;
        }
        FtpSettingsViewModel that = (FtpSettingsViewModel)object;
        return new EqualsBuilder().append(this.isUseDefault(), that.isUseDefault()).append(this.getPort(), that.getPort()).append(this.getUsername(), that.getUsername()).append(this.getPassword(), that.getPassword()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.isUseDefault(), this.getPort(), this.getUsername(), this.getPassword());
    }

    public String toString() {
        return new ToStringBuilder(this).append("useDefault", this.isUseDefault()).append("port", this.getPort()).append("username", this.getUsername()).append("password", this.getPassword()).toString();
    }
}

