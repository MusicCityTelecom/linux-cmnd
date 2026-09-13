/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.row_mappers;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.settings.default_ftp_settings.MapRowMessages;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DefaultFtpSettingsRowMapper
implements RowMapper<FtpSettings> {
    private static final String USE_DEFAULT_COLUMN_NAME = "default_ftp_settings_use_default";
    private static final String PORT_COLUMN_NAME = "default_ftp_settings_port";
    private static final String USERNAME_COLUMN_NAME = "default_ftp_settings_username";
    private static final String PASSWORD_COLUMN_NAME = "default_ftp_settings_password";

    @Override
    public FtpSettings mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Assert.notNull((Object)resultSet, MapRowMessages.RESULT_SET_CAN_NOT_BE_NULL);
        boolean useDefault = resultSet.getBoolean(USE_DEFAULT_COLUMN_NAME);
        Object portObject = resultSet.getObject(PORT_COLUMN_NAME);
        if (portObject != null) {
            Assert.isInstanceOf(Integer.class, portObject, "Port object has to be an instance of Integer");
        }
        Integer port = (Integer)portObject;
        String username = resultSet.getString(USERNAME_COLUMN_NAME);
        String password = resultSet.getString(PASSWORD_COLUMN_NAME);
        return new FtpSettings(useDefault, port, username, password);
    }
}

