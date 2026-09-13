package be.tpvision.smartcontrol.repository.row_mappers;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.hardware.MapRowMessages;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class HardwareRowMapper implements RowMapper<Hardware> {
   private static final String HARDWARE_KEY_COLUMN_NAME = "hardware_key";
   private static final String CONTENT_ID_COLUMN_NAME = "content_id";

   public Hardware mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
      Assert.notNull(resultSet, MapRowMessages.RESULT_SET_CAN_NOT_BE_NULL);
      Hardware hardware = new Hardware();
      String hardwareKey = resultSet.getString("hardware_key");
      hardware.setHardwareKey(hardwareKey);
      String contentId = resultSet.getString("content_id");
      hardware.setContentId(contentId);
      return hardware;
   }
}
