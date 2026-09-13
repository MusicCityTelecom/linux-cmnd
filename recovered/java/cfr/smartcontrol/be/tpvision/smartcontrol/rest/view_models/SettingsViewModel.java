/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.device.FtpSettingsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SettingsViewModel {
    private String serverIp;
    private FtpSettingsViewModel defaultFtpSettings;

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public FtpSettingsViewModel getDefaultFtpSettings() {
        return this.defaultFtpSettings;
    }

    public void setDefaultFtpSettings(FtpSettingsViewModel defaultFtpSettings) {
        this.defaultFtpSettings = defaultFtpSettings;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SettingsViewModel)) {
            return false;
        }
        SettingsViewModel that = (SettingsViewModel)object;
        return new EqualsBuilder().append(this.getServerIp(), that.getServerIp()).append(this.getDefaultFtpSettings(), that.getDefaultFtpSettings()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getServerIp(), this.getDefaultFtpSettings());
    }

    public String toString() {
        return new ToStringBuilder(this).append("serverIp", this.getServerIp()).append("defaultFtpSettings", this.getDefaultFtpSettings()).toString();
    }
}

