/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.MatrixPosition;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Device.class)
public abstract class Device_ {
    public static volatile SingularAttribute<Device, MatrixPosition> matrixPosition;
    public static volatile SingularAttribute<Device, IpDestination> address;
    public static volatile SingularAttribute<Device, Miscellaneous> miscellaneous;
    public static volatile SingularAttribute<Device, Tiling> tiling;
    public static volatile SingularAttribute<Device, StringWrapper> buildDate;
    public static volatile SingularAttribute<Device, StringWrapper> serialCode;
    public static volatile SingularAttribute<Device, StringWrapper> supportSources;
    public static volatile SingularAttribute<Device, FtpSettings> ftpSettings;
    public static volatile SingularAttribute<Device, PowerState> powerState;
    public static volatile SingularAttribute<Device, StringWrapper> sicpVersion;
    public static volatile SingularAttribute<Device, StringWrapper> platformVersion;
    public static volatile SingularAttribute<Device, String> name;
    public static volatile SingularAttribute<Device, TemperatureSensor> temperature;
    public static volatile SingularAttribute<Device, StringWrapper> modelNumber;
    public static volatile SingularAttribute<Device, Long> id;
    public static volatile SingularAttribute<Device, InputSource> inputSource;
    public static volatile SingularAttribute<Device, StringWrapper> firmwareVersion;
    public static volatile SingularAttribute<Device, StringWrapper> platformLabel;
    public static volatile SingularAttribute<Device, Boolean> contentRotated;
    public static volatile SingularAttribute<Device, StringWrapper> firmwareVersionAndroid;
    public static final String MATRIX_POSITION = "matrixPosition";
    public static final String ADDRESS = "address";
    public static final String MISCELLANEOUS = "miscellaneous";
    public static final String TILING = "tiling";
    public static final String BUILD_DATE = "buildDate";
    public static final String SERIAL_CODE = "serialCode";
    public static final String SUPPORT_SOURCES = "supportSources";
    public static final String FTP_SETTINGS = "ftpSettings";
    public static final String POWER_STATE = "powerState";
    public static final String SICP_VERSION = "sicpVersion";
    public static final String PLATFORM_VERSION = "platformVersion";
    public static final String NAME = "name";
    public static final String TEMPERATURE = "temperature";
    public static final String MODEL_NUMBER = "modelNumber";
    public static final String ID = "id";
    public static final String INPUT_SOURCE = "inputSource";
    public static final String FIRMWARE_VERSION = "firmwareVersion";
    public static final String PLATFORM_LABEL = "platformLabel";
    public static final String CONTENT_ROTATED = "contentRotated";
    public static final String FIRMWARE_VERSION_ANDROID = "firmwareVersionAndroid";
}

