/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.row_mappers;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.device.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.device.MapRowMessages;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import be.tpvision.smartcontrol.rest.mappers.StringWrapperMapper;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DeviceRowMapper
implements RowMapper<DeviceDTO> {
    private static final String ID_COLUMN_NAME = "id";
    private static final String ADDRESS_COLUMN_NAME = "address";
    private static final String CONTROL_ID_COLUMN_NAME = "control_id";
    private static final String GROUP_ID_COLUMN_NAME = "group_id";
    private static final String SERIAL_CODE_COLUMN_NAME = "serial_code";
    private static final String FTP_SETTINGS_USE_DEFAULT_COLUMN_NAME = "ftp_settings_use_default";
    private static final String FTP_SETTINGS_PORT_COLUMN_NAME = "ftp_settings_port";
    private static final String FTP_SETTINGS_USERNAME_COLUMN_NAME = "ftp_settings_username";
    private static final String FTP_SETTINGS_PASSWORD_COLUMN_NAME = "ftp_settings_password";
    private final AttributeConverter<InetSocketAddress, String> inetSocketAddressConverter;

    @Autowired
    public DeviceRowMapper(AttributeConverter<InetSocketAddress, String> inetSocketAddressConverter) {
        Assert.notNull(inetSocketAddressConverter, ConstructorMessages.INET_SOCKET_ADDRESS_CONVERTER_CAN_NOT_BE_NULL);
        this.inetSocketAddressConverter = inetSocketAddressConverter;
    }

    @Override
    public DeviceDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Assert.notNull((Object)resultSet, MapRowMessages.RESULT_SET_CAN_NOT_BE_NULL);
        DeviceDTO deviceDTO = new DeviceDTO();
        long id = resultSet.getLong(ID_COLUMN_NAME);
        deviceDTO.setId(id);
        String inetSocketAddressString = resultSet.getString(ADDRESS_COLUMN_NAME);
        Assert.state(inetSocketAddressString != null, MapRowMessages.INET_SOCKET_ADDRESS_STRING_CAN_NOT_BE_NULL);
        Assert.state(!inetSocketAddressString.isEmpty(), MapRowMessages.INET_SOCKET_ADDRESS_STRING_CAN_NOT_BE_EMPTY);
        InetSocketAddress inetSocketAddress = this.inetSocketAddressConverter.convertToEntityAttribute(inetSocketAddressString);
        int controlId = resultSet.getInt(CONTROL_ID_COLUMN_NAME);
        int groupId = resultSet.getInt(GROUP_ID_COLUMN_NAME);
        IpDestination ipDestination = new IpDestination(inetSocketAddress, controlId, groupId);
        deviceDTO.setAddress(ipDestination);
        String serialCodeString = resultSet.getString(SERIAL_CODE_COLUMN_NAME);
        StringWrapper serialCode = StringWrapperMapper.toStringWrapper(serialCodeString);
        deviceDTO.setSerialCode(serialCode);
        boolean ftpUseDefault = resultSet.getBoolean(FTP_SETTINGS_USE_DEFAULT_COLUMN_NAME);
        Object ftpPortObject = resultSet.getObject(FTP_SETTINGS_PORT_COLUMN_NAME);
        if (ftpPortObject != null) {
            Assert.isInstanceOf(Integer.class, ftpPortObject, "FTP port object has to be an instance of Integer");
        }
        Integer ftpPort = (Integer)ftpPortObject;
        String ftpUsername = resultSet.getString(FTP_SETTINGS_USERNAME_COLUMN_NAME);
        String ftpPassword = resultSet.getString(FTP_SETTINGS_PASSWORD_COLUMN_NAME);
        FtpSettings ftpSettings = new FtpSettings(ftpUseDefault, ftpPort, ftpUsername, ftpPassword);
        deviceDTO.setFtpSettings(ftpSettings);
        return deviceDTO;
    }
}

