/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Settings;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Settings.class)
public abstract class Settings_ {
    public static volatile SingularAttribute<Settings, FtpSettings> defaultFtpSettings;
    public static volatile SingularAttribute<Settings, String> serverIp;
    public static volatile SingularAttribute<Settings, Long> id;
    public static volatile SingularAttribute<Settings, Integer> detectDevicesTimeoutInMilliseconds;
    public static final String DEFAULT_FTP_SETTINGS = "defaultFtpSettings";
    public static final String SERVER_IP = "serverIp";
    public static final String ID = "id";
    public static final String DETECT_DEVICES_TIMEOUT_IN_MILLISECONDS = "detectDevicesTimeoutInMilliseconds";
}

