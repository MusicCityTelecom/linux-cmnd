/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.row_mappers;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.hardware.MapRowMessages;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class HardwareRowMapper
implements RowMapper<Hardware> {
    private static final String HARDWARE_KEY_COLUMN_NAME = "hardware_key";
    private static final String CONTENT_ID_COLUMN_NAME = "content_id";

    @Override
    public Hardware mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Assert.notNull((Object)resultSet, MapRowMessages.RESULT_SET_CAN_NOT_BE_NULL);
        Hardware hardware = new Hardware();
        String hardwareKey = resultSet.getString(HARDWARE_KEY_COLUMN_NAME);
        hardware.setHardwareKey(hardwareKey);
        String contentId = resultSet.getString(CONTENT_ID_COLUMN_NAME);
        hardware.setContentId(contentId);
        return hardware;
    }
}

