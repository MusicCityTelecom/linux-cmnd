/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.FtpSettings;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="settings")
public class Settings {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String serverIp;
    @Embedded
    @AttributeOverrides(value={@AttributeOverride(name="useDefault", column=@Column(name="default_ftp_settings_use_default")), @AttributeOverride(name="port", column=@Column(name="default_ftp_settings_port")), @AttributeOverride(name="username", column=@Column(name="default_ftp_settings_username")), @AttributeOverride(name="password", column=@Column(name="default_ftp_settings_password"))})
    private FtpSettings defaultFtpSettings;
    private int detectDevicesTimeoutInMilliseconds;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public FtpSettings getDefaultFtpSettings() {
        return this.defaultFtpSettings;
    }

    public void setDefaultFtpSettings(FtpSettings defaultFtpSettings) {
        this.defaultFtpSettings = defaultFtpSettings;
    }

    public int getDetectDevicesTimeoutInMilliseconds() {
        return this.detectDevicesTimeoutInMilliseconds;
    }

    public void setDetectDevicesTimeoutInMilliseconds(int detectDevicesTimeoutInMilliseconds) {
        this.detectDevicesTimeoutInMilliseconds = detectDevicesTimeoutInMilliseconds;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings settings = (Settings)object;
        return new EqualsBuilder().append(this.getServerIp(), settings.getServerIp()).append(this.getDefaultFtpSettings(), settings.getDefaultFtpSettings()).append(this.getDetectDevicesTimeoutInMilliseconds(), settings.getDetectDevicesTimeoutInMilliseconds()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getServerIp(), this.getDefaultFtpSettings(), this.getDetectDevicesTimeoutInMilliseconds());
    }

    public String toString() {
        return new ToStringBuilder(this).append("serverIp", this.getServerIp()).append("defaultFtpSettings", this.getDefaultFtpSettings()).append("detectDevicesTimeoutInMilliseconds", this.getDetectDevicesTimeoutInMilliseconds()).toString();
    }
}

