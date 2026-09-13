package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.messages.repositories.jdbc.settings.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.settings.GetDefaultFtpSettingsMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
public class SettingsRepositoryJdbcImpl implements SettingsRepositoryJdbc {
   private final JdbcTemplate jdbcTemplate;
   private final RowMapper<FtpSettings> ftpSettingsRowMapper;

   @Autowired
   public SettingsRepositoryJdbcImpl(final JdbcTemplate jdbcTemplate, final RowMapper<FtpSettings> ftpSettingsRowMapper) {
      Assert.notNull(jdbcTemplate, ConstructorMessages.JDBC_TEMPLATE_CAN_NOT_BE_NULL);
      this.jdbcTemplate = jdbcTemplate;
      this.ftpSettingsRowMapper = ftpSettingsRowMapper;
   }

   @Override
   public String getServerIp() {
      String selectServerIpSql = "SELECT server_ip FROM settings";

      try {
         return this.jdbcTemplate.queryForObject("SELECT server_ip FROM settings", String.class);
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }

   @Override
   public FtpSettings getDefaultFtpSettings() {
      Assert.state(this.ftpSettingsRowMapper != null, GetDefaultFtpSettingsMessages.FTP_SETTINGS_ROW_MAPPER_CAN_NOT_BE_NULL);
      String selectDefaultFtpSettingsSql = "SELECT default_ftp_settings_use_default, default_ftp_settings_port, default_ftp_settings_username, default_ftp_settings_password FROM settings";

      try {
         return this.jdbcTemplate
            .queryForObject(
               "SELECT default_ftp_settings_use_default, default_ftp_settings_port, default_ftp_settings_username, default_ftp_settings_password FROM settings",
               this.ftpSettingsRowMapper
            );
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }
}
