package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.repositories.jdbc.hardware.AddHardwareMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.hardware.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.hardware.GetHardwareByContentIdMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.hardware.GetHardwareMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.hardware.UpdateHardwareMessages;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
public class HardwareRepositoryJdbcImpl implements HardwareRepositoryJdbc {
   private final JdbcTemplate jdbcTemplate;
   private final RowMapper<Hardware> hardwareRowMapper;

   @Autowired
   public HardwareRepositoryJdbcImpl(final JdbcTemplate jdbcTemplate, final RowMapper<Hardware> hardwareRowMapper) {
      Assert.notNull(jdbcTemplate, ConstructorMessages.JDBC_TEMPLATE_CAN_NOT_BE_NULL);
      this.jdbcTemplate = jdbcTemplate;
      this.hardwareRowMapper = hardwareRowMapper;
   }

   @Override
   public Set<Hardware> getHardwareByContentId(final String contentId) {
      Assert.state(this.hardwareRowMapper != null, GetHardwareByContentIdMessages.HARDWARE_ROW_MAPPER_CAN_NOT_BE_NULL);
      Assert.notNull(contentId, GetHardwareByContentIdMessages.CONTENT_ID_CAN_NOT_BE_NULL);
      Assert.isTrue(!contentId.isEmpty(), GetHardwareByContentIdMessages.CONTENT_ID_CAN_NOT_BE_EMPTY);
      String selectHardwareSql = "SELECT hardware_key, content_id FROM hardware WHERE content_id = ?";

      try {
         List<Hardware> hardwareList = this.jdbcTemplate
            .query("SELECT hardware_key, content_id FROM hardware WHERE content_id = ?", this.hardwareRowMapper, contentId);
         return new HashSet<>(hardwareList);
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return Collections.emptySet();
      }
   }

   @Override
   public Hardware getHardware(final String hardwareKey) {
      Assert.state(this.hardwareRowMapper != null, GetHardwareMessages.HARDWARE_ROW_MAPPER_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      String selectHardwareSql = "SELECT hardware_key, content_id FROM hardware WHERE hardware_key = ?";

      try {
         return this.jdbcTemplate.queryForObject("SELECT hardware_key, content_id FROM hardware WHERE hardware_key = ?", this.hardwareRowMapper, hardwareKey);
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }

   @Override
   public void addHardware(final Hardware hardware) {
      Assert.notNull(hardware, AddHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
      String hardwareKey = hardware.getHardwareKey();
      String contentId = hardware.getContentId();
      String insertHardwareSql = "INSERT INTO hardware (hardware_key, content_id) VALUES (?, ?)";
      this.jdbcTemplate.update("INSERT INTO hardware (hardware_key, content_id) VALUES (?, ?)", hardwareKey, contentId);
   }

   @Override
   public void updateHardware(final Hardware hardware) {
      Assert.notNull(hardware, UpdateHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
      String hardwareKey = hardware.getHardwareKey();
      Assert.state(hardwareKey != null, UpdateHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), UpdateHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      String contentId = hardware.getContentId();
      String updateHardwareSql = "UPDATE hardware SET content_id = ? WHERE hardware_key = ?";
      this.jdbcTemplate.update("UPDATE hardware SET content_id = ? WHERE hardware_key = ?", contentId, hardwareKey);
   }
}
