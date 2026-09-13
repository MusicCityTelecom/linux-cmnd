package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.messages.repositories.jdbc.device.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.device.GetContentRotatedBySerialCodeMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.device.GetDeviceMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.device.GetSerialCodeMessages;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
public class DeviceRepositoryJdbcImpl implements DeviceRepositoryJdbc {
   private final JdbcTemplate jdbcTemplate;
   private final RowMapper<DeviceDTO> deviceRowMapper;

   @Autowired
   public DeviceRepositoryJdbcImpl(final JdbcTemplate jdbcTemplate, final RowMapper<DeviceDTO> deviceRowMapper) {
      Assert.notNull(jdbcTemplate, ConstructorMessages.JDBC_TEMPLATE_CAN_NOT_BE_NULL);
      this.jdbcTemplate = jdbcTemplate;
      this.deviceRowMapper = deviceRowMapper;
   }

   @Override
   public DeviceDTO getDeviceDTO(final long id) {
      Assert.state(this.deviceRowMapper != null, GetDeviceMessages.DEVICE_ROW_MAPPER_CAN_NOT_BE_NULL);
      Assert.isTrue(id >= 0L, GetDeviceMessages.ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      String selectDeviceSql = "SELECT id, address, control_id, group_id, serial_code, ftp_settings_use_default, ftp_settings_port, ftp_settings_username, ftp_settings_password FROM devices WHERE id = ?";

      try {
         return this.jdbcTemplate
            .queryForObject(
               "SELECT id, address, control_id, group_id, serial_code, ftp_settings_use_default, ftp_settings_port, ftp_settings_username, ftp_settings_password FROM devices WHERE id = ?",
               this.deviceRowMapper,
               id
            );
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }

   @Override
   public boolean getContentRotatedBySerialCode(final String serialCode) {
      Assert.notNull(serialCode, GetContentRotatedBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
      Assert.isTrue(!serialCode.isEmpty(), GetContentRotatedBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_EMPTY);
      String selectContentRotatedSql = "SELECT content_rotated FROM devices WHERE serial_code = ?";

      try {
         return this.jdbcTemplate.queryForObject("SELECT content_rotated FROM devices WHERE serial_code = ?", boolean.class, serialCode);
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         String message = GetContentRotatedBySerialCodeMessages.noDeviceFoundWithSerialCodeMessage(serialCode);
         throw new RuntimeException(message);
      }
   }

   @Override
   public String getSerialCode(final long id) {
      Assert.isTrue(id >= 0L, GetSerialCodeMessages.ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      String selectSerialCodeSql = "SELECT serial_code FROM devices WHERE id = ?";

      try {
         return this.jdbcTemplate.queryForObject("SELECT serial_code FROM devices WHERE id = ?", String.class, id);
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }
}
